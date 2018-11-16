package com.tdcrawl.tdc.levels;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.tdcrawl.tdc.events.CustomEvents;
import com.tdcrawl.tdc.events.Event;
import com.tdcrawl.tdc.events.EventCallback;
import com.tdcrawl.tdc.events.EventsHandler;
import com.tdcrawl.tdc.events.types.CollisionEvent;
import com.tdcrawl.tdc.events.types.CollisionEvent.CollisionState;
import com.tdcrawl.tdc.levels.rooms.Room;
import com.tdcrawl.tdc.levels.rooms.RoomBuilder;
import com.tdcrawl.tdc.events.types.WorldLockChangeEvent;
import com.tdcrawl.tdc.objects.GameObject;
import com.tdcrawl.tdc.objects.entities.living.Player;
import com.tdcrawl.tdc.objects.fixtures.ObjectFixture;
import com.tdcrawl.tdc.util.Helper;

/**
 * Stores all the rooms of a given level and handles their generation
 */
public class Level
{
	private List<Room> rooms = new ArrayList<>();
	private List<RoomBuilder> roomTypes = new ArrayList<>();
	
	private RoomBuilder spawnRoom;
	
	private final int FLOOR_NUMBER;
	
	/**
	 * Where every object will be
	 */
	private World world;
	
	/**
	 * This is the player
	 */
	private Player player;
	
	/**
	 * Creates the world and loads the room variations
	 * @throws IOException If there is an error reading the room variations
	 */
	public Level(int floorNo) throws IOException
	{
		FLOOR_NUMBER = floorNo;
		
		world = new World(new Vector2(0, -9.8f), true);
		
		// Handles any collision events that happen in the world
		world.setContactListener(new ContactListener()
			{
				@Override
				public void beginContact(Contact contact)
				{
					if(contact.getFixtureA().getBody().getUserData() instanceof GameObject && 
							contact.getFixtureB().getBody().getUserData() instanceof GameObject)
					{
						// Checks if it's 2 game objects colliding, and if it is call a new event
						
						GameObject obj1 = (GameObject) contact.getFixtureA().getBody().getUserData();
						GameObject obj2 = (GameObject) contact.getFixtureB().getBody().getUserData();
						
						ObjectFixture fix1 = (ObjectFixture) contact.getFixtureA().getUserData();
						ObjectFixture fix2 = (ObjectFixture) contact.getFixtureB().getUserData();
						
						EventsHandler.call(new CollisionEvent(obj1, obj2, fix1, fix2, CollisionState.BEGIN_COLLISION));
					}
				}
				 
				@Override
				public void endContact(Contact contact)
				{
					if(contact.getFixtureA().getBody().getUserData() instanceof GameObject && 
							contact.getFixtureB().getBody().getUserData() instanceof GameObject)
					{
						// Checks if it's 2 game objects colliding, and if it is call a new event
						
						GameObject obj1 = (GameObject) contact.getFixtureA().getBody().getUserData();
						GameObject obj2 = (GameObject) contact.getFixtureB().getBody().getUserData();
						
						ObjectFixture fix1 = (ObjectFixture) contact.getFixtureA().getUserData();
						ObjectFixture fix2 = (ObjectFixture) contact.getFixtureB().getUserData();
						
						EventsHandler.call(new CollisionEvent(obj1, obj2, fix1, fix2, CollisionState.END_COLLISION));
					}
				}
				
				// The below methods are just weird and are called in between the two methods above.
				
				@Override
				public void preSolve(Contact contact, Manifold oldManifold)
				{
					
				}

				@Override
				public void postSolve(Contact contact, ContactImpulse impulse)
				{
					
				}
			});
		
		init();
	}
	
	/**
	 * Assembles a randomly generated level based off the room variations loaded before
	 * TODO: do that ^
	 */
	public void create()
	{
		// For now this just loads everything in the spawner room
		rooms.add(spawnRoom.createRoom());
		
		for(GameObject o : rooms.get(0).getObjectsInRoom())
		{
			o.init(world);
			
			if(o instanceof Player)
				player = (Player)o;
		}
	}
	
	/**
	 * Loads all the rooms from the floor's folder
	 * @throws IOException If there is an error reading those room files
	 */
	private void init() throws IOException
	{
		int i = 0; // Each room number
		
		File f = new File(getFloorFolder() + roomName(i));
		
		// Keep going until we run out of rooms
		
		while (f.exists())
		{
			roomTypes.add(getRoomBuilder(f));
			
			i++;
			f = new File(getFloorFolder() + roomName(i));
		}
		
		f = new File(getFloorFolder() + "room-spawn.json");
		
		if(!f.exists())
			throw new IllegalStateException("No spawn room for floor " + FLOOR_NUMBER + "!");
		
		spawnRoom = getRoomBuilder(f);
		
		EventsHandler.subscribe(CustomEvents.WORLD_LOCK_CHANGE_EVENT, new EventCallback()
		{
			@Override
			public void callback(Event e)
			{
				WorldLockChangeEvent event = (WorldLockChangeEvent)e;
				
				if(!event.isCancelled() && !event.isLocked())
				{
					Helper.cleanup();
				}
			}
		});
	}
	
	/**
	 * Loads and creates a RoomBuilder from a file with RoomBuilder json
	 * @param f The file to load the json from
	 * @return The RoomBuilder generated from the json in the file
	 * @throws IOException If there is an error reading the room file
	 */
	private RoomBuilder getRoomBuilder(File f) throws IOException
	{
		StringBuilder lines = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(f));
		for(String line = br.readLine(); line != null; line = br.readLine())
		{
			lines.append(line);
		}
		br.close();
		
		// Creates the room builder to later build the room once create() is called
		RoomBuilder builder = new RoomBuilder();
		builder.createFromJSON(lines.toString());
		
		return builder;
	}
	
	private String roomName(int i)
	{
		return "room-" + i + ".json";
	}
	
	private String getFloorFolder()
	{
		return "assets/levels/floor-" + FLOOR_NUMBER + "/";
	}
	
	public void tick(float delta, Camera camera)
	{
		Helper.setWorldLocked(true);
		world.step(delta, 8, 3); // 8 and 3 are good* values I found online. *I assume they are good.
		// URL: http://www.iforce2d.net/b2dtut/worlds
		
		for(Room room : rooms)
			room.tick(delta, camera);
		
		Helper.setWorldLocked(false);
		
		Helper.cleanup(); // Does anything that had to be done after ticking
	}
	
	public void render(float delta, Camera cam, Box2DDebugRenderer debugRenderer)
	{
		if(player != null)
			cam.position.lerp(new Vector3(player.getPosition(), 0), 0.1f);
		cam.update();
		
		if(debugRenderer != null)
			debugRenderer.render(world, cam.combined);
	}
	
	public void dispose()
	{
		world.dispose();
	}
	
	// Getters & Setters //

	public World getWorld() { return world; }
}

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
import com.badlogic.gdx.physics.box2d.World;
import com.google.gson.Gson;
import com.tdcrawl.tdc.events.CustomEvents;
import com.tdcrawl.tdc.events.Event;
import com.tdcrawl.tdc.events.EventCallback;
import com.tdcrawl.tdc.events.EventsHandler;
import com.tdcrawl.tdc.events.types.WorldLockChangeEvent;
import com.tdcrawl.tdc.levels.rooms.Room;
import com.tdcrawl.tdc.levels.rooms.RoomBuilder;
import com.tdcrawl.tdc.objects.entities.living.Player;
import com.tdcrawl.tdc.physics.DefaultCollisionHandler;
import com.tdcrawl.tdc.util.Helper;
import com.tdcrawl.tdc.util.Reference;
import com.tdcrawl.tdc.util.Vector2I;

/**
 * Stores all the rooms of a given level and handles their generation
 */
public class Level
{
	private Room[][] rooms;
	private List<RoomBuilder> roomTypes = new ArrayList<>();	
	private RoomBuilder spawnRoom;
	
	private final int FLOOR_NUMBER;
	
	private final String name;
	private final Vector2I roomAmountXY;
	private final Vector2 roomDimensions;
	
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
		
		Reference.debugLog("Loading floor #" + floorNo + ".", this);
		
		Gson gson = new Gson();
		LevelTemplate t = gson.fromJson(new FileReader(getFloorFolder() + "level.json"), LevelTemplate.class);
		
		this.roomAmountXY = t.roomAmountXY;
		this.name = t.name;
		this.roomDimensions = t.roomDimensions;
		
		world = new World(new Vector2(0, -9.8f), true);
		
		// Handles any collision events that happen in the world
		world.setContactListener(new DefaultCollisionHandler());
		
		init();
	}
	
	/**
	 * Assembles a randomly generated level based off the room variations loaded before
	 */
	public void create()
	{
		float yOffset = 0;
		float xOffset = 0;
		
		float maxOffsetY = 0;
		
		rooms = new Room[roomAmountXY.y * 2][roomAmountXY.x * 2];
		
		for(int y = 0; y < 2 * roomAmountXY.y; y++)
		{
			for(int x = 0; x < 2 * roomAmountXY.x; x++)
			{
				Room r;
				
				boolean leftBorder = x == 0,
						rightBorder = x == roomAmountXY.x * 2 - 1, 
						topBorder = y == 0,
						bottomBorder = y == roomAmountXY.y * 2 - 1;
				
				if(x == roomAmountXY.x && y == roomAmountXY.y)
					r = spawnRoom.createRoom(this, new Vector2(xOffset, yOffset), leftBorder, rightBorder, topBorder, bottomBorder);
				else
					r = roomTypes.get((int)(Math.random() * roomTypes.size())).createRoom(this, new Vector2(xOffset, yOffset), leftBorder, rightBorder, topBorder, bottomBorder);
				
				xOffset += r.getDimensions().x;
				maxOffsetY = Math.max(r.getDimensions().y, maxOffsetY);
				
				r.init();
				
				rooms[y][x] = r;
			}
			
			xOffset = 0;
			yOffset += maxOffsetY;
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
		
		// Keep going until we run out of room types
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
		return "room_" + i + ".json";
	}
	
	private String getFloorFolder()
	{
		return "assets/levels/floor-" + FLOOR_NUMBER + "/";
	}
	
	/**
	 * Updates the level
	 * @param delta The time passed since last tick
	 * @param camera The camera used to render the scene
	 */
	public void tick(float delta, Camera camera)
	{
		Helper.setWorldLocked(true);
		world.step(delta, 8, 3); // 8 and 3 are good* values I found online. *I assume they are good.
		// URL: http://www.iforce2d.net/b2dtut/worlds
		
		for(int y = 0; y < rooms.length; y++)
		{
			for(int x = 0; x < rooms[y].length; x++)
			{
				Room room = rooms[y][x];
				
				if(getPlayer() != null)
				{
					if(getPlayer().getPosition().x > x * getRoomDimensions().x && getPlayer().getPosition().x < (x + 1) * getRoomDimensions().x
							&& getPlayer().getPosition().y > y * getRoomDimensions().y && getPlayer().getPosition().y < (y + 1) * getRoomDimensions().y)
					{
						if(!getPlayer().getRoom().equals(room))
						{
							getPlayer().getRoom().removeObject(getPlayer());
							getPlayer().setRoom(room);
							room.addObject(getPlayer());
						}
					}
				}
				
				room.tick(delta, camera);
			}
		}
		Helper.setWorldLocked(false);
	}
	
	/**
	 * Renders the level
	 * @param delta Time passed since last render
	 * @param cam The camera used to render it
	 * @param debugRenderer A renderer for it
	 */
	public void render(float delta, Camera cam, Box2DDebugRenderer debugRenderer)
	{
		if(player != null)
			cam.position.lerp(new Vector3(player.getPosition(), 0), 0.1f);
		cam.update();
		
		if(Reference.isDebug() && debugRenderer != null)
			debugRenderer.render(world, cam.combined);
	}
	
	/**
	 * Disposes all the level's assets
	 */
	public void dispose()
	{
		world.dispose();
	}
	
	// Getters & Setters //
	
	public World getWorld() { return world; }
	
	public String getName() { return name; }

	public Vector2 getRoomDimensions() { return roomDimensions.cpy(); }

	public void setPlayer(Player player) { this.player = player; }
	public Player getPlayer() { return player; }
}

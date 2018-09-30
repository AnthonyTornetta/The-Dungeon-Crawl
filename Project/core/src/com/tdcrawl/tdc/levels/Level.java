package com.tdcrawl.tdc.levels;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.tdcrawl.tdc.objects.GameObject;
import com.tdcrawl.tdc.room.Room;
import com.tdcrawl.tdc.room.RoomBuilder;

/**
 * Stores all the rooms of a given level and handles their generation
 */
public class Level
{
	private List<Room> rooms = new ArrayList<>();
	private List<RoomBuilder> roomTypes = new ArrayList<>();
	
	private final int FLOOR_NUMBER;
	
	/**
	 * Where every object will be
	 */
	private World world;
	
	/**
	 * Creates the world and loads the room variations
	 * @throws IOException If there is an error reading the room variations
	 */
	public Level(int floorNo) throws IOException
	{
		FLOOR_NUMBER = floorNo;
		
		world = new World(new Vector2(0, -9.8f), true);
		
		init();
	}
	
	/**
	 * Assembles a randomly generated level based off the room variations loaded before
	 * TODO: do that ^
	 */
	public void create()
	{
		// For now this just loads everything in the first room it finds
		rooms.add(roomTypes.get(0).createRoom());
		
		for(GameObject o : rooms.get(0).getObjectsInRoom())
		{
			o.init(world);
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
			roomTypes.add(builder);
			
			i++;
			
			f = new File(getFloorFolder() + roomName(i));
		}
	}
	
	private String roomName(int i)
	{
		return "room-" + i + ".json";
	}
	
	private String getFloorFolder()
	{
		return "assets/levels/floor-" + FLOOR_NUMBER + "/";
	}
	
	public void tick(float delta)
	{
		world.step(delta, 8, 3); // 8 and 3 are good* values I found online. *I assume they are good.
		// URL: http://www.iforce2d.net/b2dtut/worlds
	}
	
	public void render(float delta, Camera cam, Box2DDebugRenderer debugRenderer)
	{
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

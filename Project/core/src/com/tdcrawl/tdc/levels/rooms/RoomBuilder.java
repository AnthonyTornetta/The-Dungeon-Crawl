package com.tdcrawl.tdc.levels.rooms;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.google.gson.Gson;
import com.tdcrawl.tdc.levels.Level;
import com.tdcrawl.tdc.objects.GameObject;
import com.tdcrawl.tdc.objects.entities.Door;
import com.tdcrawl.tdc.objects.entities.Entity;
import com.tdcrawl.tdc.objects.entities.living.Player;
import com.tdcrawl.tdc.objects.staticobjects.Platform;
import com.tdcrawl.tdc.registries.ObjectRegistry;
import com.tdcrawl.tdc.registries.templates.ObjectData;
import com.tdcrawl.tdc.registries.templates.ObjectTemplate;

public class RoomBuilder
{
	private boolean enclosed;
	
	// These are parallel
	private List<ObjectTemplate> templates = new ArrayList<>();
	private List<ObjectData> templatesData = new ArrayList<>();
	
	/**
	 * Creates the RoomBuilder from json
	 * @param json The json to create it from
	 */
	public void createFromJSON(String json)
	{
		Gson gson = new Gson(); // This will parse json
		
		RoomBlueprint blueprint = gson.fromJson(json, RoomBlueprint.class);
		
		templates.clear();
		templatesData.clear();
		
		// Adds all the objects created fromt he object data to add to the room when we create the room
		for(ObjectData data : blueprint.objects)
		{
			ObjectTemplate template = ObjectRegistry.getObject(data.name);
			
			if(template != null)
			{
				templates.add(template);
				templatesData.add(data);
			}
			else
				throw new IllegalStateException("Bad object name \"" + data.name + "\".");
		}
		
		enclosed = blueprint.enclosed;
	}
	
	/**
	 * Creates a room and fills it up with the objects
	 * @param level the Level the room is a part of
	 * @return The room with all the objects in it
	 */
	public Room createRoom(Level level, boolean leftBorder, boolean rightBorder, boolean topBorder, boolean bottomBorder)
	{
		return createRoom(level, Vector2.Zero, leftBorder, rightBorder, topBorder, bottomBorder);
	}
	
	/**
	 * Creates a room and fills it up with the objects
	 * @param offset The offset of every object in the room
	 * @param level The level the room is a part of
	 * @return The room with all the objects in it
	 */
	public Room createRoom(Level level, Vector2 offset, boolean leftBorder, boolean rightBorder, boolean topBorder, boolean bottomBorder)
	{
		Room room = new Room(level);
		
		for(int i = 0; i < templates.size(); i++)
		{
			ObjectTemplate t = templates.get(i);
			
			GameObject o = t.create(templatesData.get(i).clone());
			
			o.getPosition().add(offset);
			
			if(o instanceof Player)
				System.out.println("YOEYET");
			
			room.addObject(o);
			if(o instanceof Entity)
				((Entity)o).setRoom(room);
		}
		
		if(enclosed)
		{
			final float gapWidth = 4f;
			
			Vector2 dimensions = room.getDimensions();
			
			float width = (dimensions.x - gapWidth) / 2;
			float height = (dimensions.y - gapWidth) / 2;
			float doorWidth = dimensions.x - width * 2;
			float doorHeight = dimensions.y - height * 2;
			
			float thickness = 0.25f;
			float platformWidth = width / 2;
			float platformHeight = height / 2;
			
			// Floor
			PolygonShape floorShape1 = new PolygonShape();
			floorShape1.setAsBox(platformWidth, thickness);
			Platform floorPlatform1 = new Platform(floorShape1, new Vector2(platformWidth, 0).add(offset), 1.0f, 0.0f);
			
			PolygonShape floorShape2 = new PolygonShape();
			floorShape2.setAsBox(platformWidth, thickness);
			Platform floorPlatform2 = new Platform(floorShape2, new Vector2(dimensions.x - platformWidth, 0).add(offset), 1.0f, 0.0f);
			
			Door floorDoor = new Door(new Vector2(dimensions.x / 2, dimensions.y).add(offset), new Vector2(thickness, doorWidth / 2), (float)Math.PI / 2.0f, doorWidth / 2);
			floorDoor.setLocked(bottomBorder);
			
			
			room.addObject(floorDoor);
			room.addObject(floorPlatform1);
			room.addObject(floorPlatform2);
			
			// Ceiling
			PolygonShape ceilingShape1 = new PolygonShape();
			ceilingShape1.setAsBox(platformWidth, thickness);
			Platform ceilingPlatform1 = new Platform(ceilingShape1, new Vector2(platformWidth, dimensions.y).add(offset), 1.0f, 0.0f);
			
			PolygonShape ceilingShape2 = new PolygonShape();
			ceilingShape2.setAsBox(platformWidth, thickness);
			Platform ceilingPlatform2 = new Platform(ceilingShape2, new Vector2(dimensions.x - platformWidth, dimensions.y).add(offset), 1.0f, 0.0f);
			
			Door ceilingDoor = new Door(new Vector2(dimensions.x / 2, 0).add(offset), new Vector2(thickness, doorWidth / 2), (float)Math.PI / 2.0f, doorWidth / 2);
			ceilingDoor.setLocked(topBorder);
			
			room.addObject(ceilingDoor);
			room.addObject(ceilingPlatform1);
			room.addObject(ceilingPlatform2);
			
			// Left wall
			PolygonShape leftWallShape1 = new PolygonShape();
			leftWallShape1.setAsBox(thickness, platformHeight);
			Platform leftWallPlatform1 = new Platform(leftWallShape1, new Vector2(0, platformHeight).add(offset), 1.0f, 0.0f);
			
			PolygonShape leftWallShape2 = new PolygonShape();
			leftWallShape2.setAsBox(thickness, platformHeight);
			Platform leftWallPlatform2 = new Platform(leftWallShape2, new Vector2(0, dimensions.y - platformHeight).add(offset), 1.0f, 0.0f);
			
			Door leftWallDoor = new Door(new Vector2(0, dimensions.y / 2).add(offset), new Vector2(thickness, doorHeight / 2), 0, doorHeight / 2);
			leftWallDoor.setLocked(leftBorder);
			
			room.addObject(leftWallPlatform1);
			room.addObject(leftWallPlatform2);
			room.addObject(leftWallDoor);
			
			// Right wall
			PolygonShape rightWallShape1 = new PolygonShape();
			rightWallShape1.setAsBox(thickness, platformHeight);
			Platform rightWallPlatform1 = new Platform(rightWallShape1, new Vector2(dimensions.x, platformHeight).add(offset), 1.0f, 0.0f);
			
			PolygonShape rightWallShape2 = new PolygonShape();
			rightWallShape2.setAsBox(thickness, platformHeight);
			Platform rightWallPlatform2 = new Platform(rightWallShape2, new Vector2(dimensions.y, dimensions.y - platformHeight).add(offset), 1.0f, 0.0f);
			
			Door rightWallDoor = new Door(new Vector2(dimensions.x, dimensions.y / 2).add(offset), new Vector2(thickness, doorHeight / 2), 0, doorHeight / 2);
			rightWallDoor.setLocked(rightBorder);
			
			room.addObject(rightWallDoor);
			room.addObject(rightWallPlatform1);
			room.addObject(rightWallPlatform2);
		}
		return room;
	}
}

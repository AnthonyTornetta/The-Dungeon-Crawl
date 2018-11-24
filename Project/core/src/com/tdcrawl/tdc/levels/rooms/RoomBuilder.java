package com.tdcrawl.tdc.levels.rooms;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.google.gson.Gson;
import com.tdcrawl.tdc.objects.GameObject;
import com.tdcrawl.tdc.objects.entities.Entity;
import com.tdcrawl.tdc.objects.staticobjects.Platform;
import com.tdcrawl.tdc.registries.ObjectRegistry;
import com.tdcrawl.tdc.registries.templates.ObjectData;
import com.tdcrawl.tdc.registries.templates.ObjectTemplate;

public class RoomBuilder
{
	private boolean enclosed;
	private Vector2 dimensions;
	
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
			
			System.out.println(template + "; " + data);
			
			if(template != null)
			{
				templates.add(template);
				templatesData.add(data);
			}
			else
				throw new IllegalStateException("Bad object name \"" + data.name + "\".");
		}
		
		enclosed = blueprint.enclosed;
		dimensions = blueprint.dimensions;
	}
	
	/**
	 * Creates a room and fills it up with the objects
	 * @return The room with all the objects in it
	 */
	public Room createRoom()
	{
		return createRoom(Vector2.Zero);
	}
	
	/**
	 * Creates a room and fills it up with the objects
	 * @param offset The offset of every object in the room
	 * @return The room with all the objects in it
	 */
	public Room createRoom(Vector2 offset)
	{
		Room room = new Room(dimensions);
		
		System.out.println(templates.size());
		
		for(int i = 0; i < templates.size(); i++)
		{
			ObjectTemplate t = templates.get(i);
			
			System.out.println(templatesData.get(i).position);
			GameObject o = t.create(templatesData.get(i).clone());
			
			o.getPosition().add(offset);
			
			room.addObject(o);
			if(o instanceof Entity)
				((Entity)o).setRoom(room);
		}
		
		if(enclosed)
		{
			Vector2 dimensions = room.getDimensions();
			
			PolygonShape wall1Shape = new PolygonShape();
			wall1Shape.setAsBox(dimensions.x, 0.2f);
			Platform wall1 = new Platform(wall1Shape, offset.cpy().add(0, dimensions.y), 0.6f, 0.0f);
			
			PolygonShape wall2Shape = new PolygonShape();
			wall2Shape.setAsBox(dimensions.x, 0.2f);
			Platform wall2 = new Platform(wall2Shape, offset.cpy().sub(0, dimensions.y), 0.6f, dimensions.y);
			
			PolygonShape wall3Shape = new PolygonShape();
			wall3Shape.setAsBox(0.2f, dimensions.y);
			Platform wall3 = new Platform(wall3Shape, offset.cpy().add(dimensions.x, 0), 0.6f, 0.0f);
			
			PolygonShape wall4Shape = new PolygonShape();
			wall4Shape.setAsBox(0.2f, dimensions.y);
			Platform wall4 = new Platform(wall4Shape, offset.cpy().sub(dimensions.x, 0), 0.6f, 0.0f);
			
			room.addObject(wall1);
			room.addObject(wall2);
			room.addObject(wall3);
			room.addObject(wall4);
		}
		return room;
	}

	public Vector2 getDimensions() { return dimensions; }
}

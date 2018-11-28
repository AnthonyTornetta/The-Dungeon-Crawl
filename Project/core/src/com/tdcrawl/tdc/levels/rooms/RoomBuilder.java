package com.tdcrawl.tdc.levels.rooms;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.google.gson.Gson;
import com.tdcrawl.tdc.levels.Level;
import com.tdcrawl.tdc.objects.GameObject;
import com.tdcrawl.tdc.objects.entities.Entity;
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
	public Room createRoom(Level level)
	{
		return createRoom(level, Vector2.Zero);
	}
	
	/**
	 * Creates a room and fills it up with the objects
	 * @param offset The offset of every object in the room
	 * @param level The level the room is a part of
	 * @return The room with all the objects in it
	 */
	public Room createRoom(Level level, Vector2 offset)
	{
		Room room = new Room(level);
		
		for(int i = 0; i < templates.size(); i++)
		{
			ObjectTemplate t = templates.get(i);
			
			GameObject o = t.create(templatesData.get(i).clone());
			
			o.getPosition().add(offset);
			
			room.addObject(o);
			if(o instanceof Entity)
				((Entity)o).setRoom(room);
		}
		
		if(enclosed)
		{
			Vector2 dimensions = room.getDimensions().cpy().add(offset);
			
			final float gap = 12f;
			
			PolygonShape shp = new PolygonShape();
			shp.setAsBox(dimensions.x, 0.25f);
			Platform p = new Platform(shp, new Vector2(0, (float) (Math.random() * 4 + dimensions.y)), 1.0f, 0.0f);
			
			room.addObject(p);
		}
		return room;
	}
}

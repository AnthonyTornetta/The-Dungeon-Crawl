package com.tdcrawl.tdc.levels.rooms;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.tdcrawl.tdc.objects.GameObject;
import com.tdcrawl.tdc.registries.ObjectRegistry;
import com.tdcrawl.tdc.registries.templates.ObjectData;
import com.tdcrawl.tdc.registries.templates.ObjectTemplate;

public class RoomBuilder
{
	private List<GameObject> objects = new ArrayList<>();
	
	/**
	 * Creates the RoomBuilder from json
	 * @param json The json to create it from
	 */
	public void createFromJSON(String json)
	{
		Gson gson = new Gson(); // This will parse json
		
		RoomBlueprint blueprint = gson.fromJson(json, RoomBlueprint.class);
		
		// Adds all the objects created fromt he object data to add to the room when we create the room
		for(ObjectData data : blueprint.objects)
		{
			ObjectTemplate template = ObjectRegistry.getObject(data.name);
			
			if(template != null)
				objects.add(template.create(data));
			else
				throw new IllegalStateException("Bad object name \"" + data.name + "\".");
		}
	}
	
	/**
	 * Creates a room and fills it up with the objects
	 * @return The room with all the objects in it
	 */
	public Room createRoom()
	{
		Room room = new Room();
		
		for(GameObject o : objects)
			room.addObject(o);
		
		return room;
	}
}

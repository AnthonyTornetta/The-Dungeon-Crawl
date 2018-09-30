package com.tdcrawl.tdc.room;

import java.util.ArrayList;
import java.util.List;

import com.tdcrawl.tdc.objects.GameObject;
import com.tdcrawl.tdc.objects.entities.Entity;

/**
 * Just a thing that stores a bunch of GameObjects
 */
public class Room
{
	private List<GameObject> objectsInRoom = new ArrayList<>();
	private List<Entity> entitiesInRoom = new ArrayList<>();
	
	public void tick(float delta)
	{
		for(Entity e : entitiesInRoom)
			e.tick(delta);
	}
	
	// Getters & Setters //
	
	public List<GameObject> getObjectsInRoom() { return objectsInRoom; }
	public List<Entity> getEntitiesInRoom() { return entitiesInRoom; }
	
	public void addObject(GameObject obj)
	{
		objectsInRoom.add(obj);
		if(obj instanceof Entity)
			entitiesInRoom.add((Entity)obj);
	}
}

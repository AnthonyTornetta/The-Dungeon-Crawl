package com.tdcrawl.tdc.levels.rooms;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.tdcrawl.tdc.objects.GameObject;
import com.tdcrawl.tdc.objects.entities.Entity;

/**
 * Just a thing that stores a bunch of GameObjects
 */
public class Room
{
	private List<GameObject> objectsInRoom = new ArrayList<>();
	private List<Entity> entitiesInRoom = new ArrayList<>();
	
	private Vector2 dimensions;
	
	public Room(Vector2 dimensions)
	{
		this.dimensions = dimensions;
	}
	
	public void tick(float delta, Camera cam)
	{
		for(Entity e : entitiesInRoom)
			e.tick(delta, cam);
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
	
	public Vector2 getDimensions() { return dimensions; }
}

package com.tdcrawl.tdc.room;

import java.util.ArrayList;
import java.util.List;

import com.tdcrawl.tdc.objects.GameObject;

/**
 * Just a thing that stores a bunch of GameObjects
 */
public class Room
{
	private List<GameObject> objectsInRoom = new ArrayList<>();
	
	// Getters & Setters //
	
	public List<GameObject> getObjectsInRoom() { return objectsInRoom; }
	
	public void addObject(GameObject obj) { objectsInRoom.add(obj); }
}

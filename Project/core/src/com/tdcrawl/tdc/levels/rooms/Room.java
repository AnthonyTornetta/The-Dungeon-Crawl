package com.tdcrawl.tdc.levels.rooms;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.tdcrawl.tdc.levels.Level;
import com.tdcrawl.tdc.objects.GameObject;
import com.tdcrawl.tdc.objects.entities.Door;
import com.tdcrawl.tdc.objects.entities.Entity;
import com.tdcrawl.tdc.objects.entities.living.LivingEntity;
import com.tdcrawl.tdc.objects.entities.living.Player;
import com.tdcrawl.tdc.objects.entities.living.types.EntityType;

/**
 * Just a thing that stores a bunch of GameObjects
 */
public class Room
{
	private Level level;
	
	private boolean isOpen = false;
	
	private List<GameObject> objectsInRoom = new ArrayList<>();
	private List<Entity> entitiesInRoom = new ArrayList<>();
	private Player player = null;
	
	public Room(Level level)
	{
		this.level = level;
	}
	
	public void tick(float delta, Camera cam)
	{
		for(Entity e : entitiesInRoom)
			e.tick(delta, cam);
		
		if(isPlayerIn() && !isOpen)
		{
			for(Entity e : entitiesInRoom)
			{
				if(e instanceof LivingEntity)
				{
					if(((LivingEntity)e).getEntityType() == EntityType.HOSTILE)
					{
						isOpen = true; // Inverts it after loop
						break;
					}
				}
			}
			
			// If no hostiles were found, this would be false so invert it to true, and if a hostile was found this would be true so invert it to false
			setOpen(!isOpen);
		}
	}
	
	private void setOpen(boolean isOpen)
	{
		this.isOpen = isOpen;
		
		for(GameObject obj : objectsInRoom)
		{
			if(obj instanceof Door)
			{
				((Door)obj).setOpen(isOpen);
			}
		}
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
	
	public Vector2 getDimensions() { return level.getRoomDimensions(); }
	
	public void playerEnter(Player p) { this.player = p; }
	public void playerLeave() { this.player = null; }
	public boolean isPlayerIn() { return player != null; }
	public Player getPlayer() { return player; }
}

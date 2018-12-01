package com.tdcrawl.tdc.levels.rooms;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.tdcrawl.tdc.TheDungeonCrawl;
import com.tdcrawl.tdc.levels.Level;
import com.tdcrawl.tdc.objects.GameObject;
import com.tdcrawl.tdc.objects.entities.Door;
import com.tdcrawl.tdc.objects.entities.Entity;
import com.tdcrawl.tdc.objects.entities.living.LivingEntity;
import com.tdcrawl.tdc.objects.entities.living.Player;
import com.tdcrawl.tdc.objects.entities.living.types.EntityType;
import com.tdcrawl.tdc.screens.YouDiedScreen;
import com.tdcrawl.tdc.util.box2ez.BoxHelper;

/**
 * Just a thing that stores a bunch of GameObjects
 */
public class Room
{
	private Level level;
	
	private boolean isOpen = true;
	
	private List<GameObject> objectsInRoom = new ArrayList<>();
	private List<Entity> entitiesInRoom = new ArrayList<>();
	private List<LivingEntity> thingsToSpawn = new ArrayList<>();
	
	private Player player = null;
	
	/**
	 * Handles the opening and closing of areas based off objects inside
	 * @param level The level it's a part of
	 */
	public Room(Level level)
	{
		this.level = level;
	}
	
	/**
	 * Ticks the room
	 * @param delta The time passed since last tick
	 * @param cam The camera used to render the room
	 */
	public void tick(float delta, Camera cam)
	{
		if(thingsToSpawn.size() != 0)
		{
			if(getPlayer() != null)
			{
				initiateRoom();
			}
		}
		
		List<LivingEntity> dead = new ArrayList<>();
		
		for(Entity e : entitiesInRoom)
		{
			e.tick(delta, cam);
			
			if(e instanceof Player)
			{
				Player p = (Player)e;
				
				if(p.getHealth() <= 0)
				{
					p.die();
					TheDungeonCrawl.get().setScreen(new YouDiedScreen());
				}
			}
			else if(e instanceof LivingEntity)
			{
				LivingEntity living = (LivingEntity)e;
				if(living.getHealth() <= 0)
					if(living.die())
						dead.add(living);
			}
		}
		
		while(dead.size() != 0)
		{
			System.out.println("AYE ADDED");
			LivingEntity deadBoi = dead.remove(dead.size() - 1);
			removeObject(deadBoi);
			BoxHelper.removeObject(deadBoi);
		}
		
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
	
	/**
	 * Sets if the room's doors are open
	 * @param isOpen If it is open
	 */
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
	
	/**
	 * Spawns all the hostile entities to spawn into the room
	 */
	public void initiateRoom()
	{
		if(thingsToSpawn.size() != 0)
			setOpen(false);
		else
			setOpen(true);
		
		for(LivingEntity ent : thingsToSpawn)
		{
			addObject(ent, true);
			
			if(ent.getBody() == null)
				ent.init(level.getWorld());
		}
		
		thingsToSpawn.clear();
	}
	
	/**
	 * Initializes the room
	 */
	public void init()
	{
		boolean spawnRoom = false;
		
		for(LivingEntity e : thingsToSpawn)
		{
			if(e instanceof Player)
			{
				spawnRoom = true;
				level.setPlayer((Player)e);
			}
		}
		
		for(GameObject o : getObjectsInRoom())
		{
			o.init(level.getWorld());
		}
		
		if(spawnRoom)
		{
			initiateRoom();
		}
	}
	
	// Getters & Setters //

	public List<GameObject> getObjectsInRoom() { return objectsInRoom; }
	public List<Entity> getEntitiesInRoom() { return entitiesInRoom; }
	
	public void addObject(GameObject obj)
	{
		addObject(obj, false);
	}
	
	private void addObject(GameObject obj, boolean ignoreClosed)
	{
		if(obj instanceof Player)
			setPlayer((Player)obj);
		
		if((isOpen || !ignoreClosed) && obj instanceof LivingEntity)
		{
			thingsToSpawn.add((LivingEntity)obj);
		}
		else
		{
			objectsInRoom.add(obj);
			if(obj instanceof Entity)
				entitiesInRoom.add((Entity)obj);
		}
	}
	
	public void removeObject(GameObject obj)
	{
		objectsInRoom.remove(obj);
		if(obj instanceof Entity)
		{
			entitiesInRoom.remove((Entity)obj);
			
			if(obj instanceof LivingEntity)
			{
				if(((LivingEntity)obj).getEntityType() == EntityType.HOSTILE)
				{
					boolean shouldOpen = true;
					for(Entity e : entitiesInRoom)
					{
						if(e instanceof LivingEntity && ((LivingEntity)e).getEntityType() == EntityType.HOSTILE)
						{
							shouldOpen = false;
							break;
						}
					}
					
					setOpen(shouldOpen);
				}
			}
		}
	}
	
	public Vector2 getDimensions() { return level.getRoomDimensions(); }
	
	public void playerEnter(Player p) { this.player = p; }
	public void playerLeave() { this.player = null; }
	public boolean isPlayerIn() { return player != null; }
	public Player getPlayer() { return player; }
	private void setPlayer(Player p) { this.player = p; }
}

package com.tdcrawl.tdc.events;

public class CustomEvents
{
	public static final String 
		WORLD_LOCK_CHANGE_EVENT = "onWorldLockChange",
		ON_GAMEOBJECT_COLLISION = "onCollide";
	
	/**
	 * Registers all the events in the main game
	 */
	public static void registerAll()
	{
		EventsHandler.registerEvent(WORLD_LOCK_CHANGE_EVENT);
		EventsHandler.registerEvent(ON_GAMEOBJECT_COLLISION);
	}
}

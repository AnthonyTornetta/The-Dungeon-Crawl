package com.tdcrawl.tdc.events.types;

import com.tdcrawl.tdc.events.CustomEvents;
import com.tdcrawl.tdc.events.Event;

public class WorldLockChangeEvent extends Event
{
	private boolean locked;
	
	/**
	 * Whenever the world is unlocked or locked, this event is called
	 * @param locked Whether or not the world is locked or unlocked
	 */
	public WorldLockChangeEvent(boolean locked)
	{
		this.locked = locked;
	}
	
	public boolean isLocked() { return locked; }
	
	@Override
	public boolean isCancellable() { return false; }
	
	@Override
	public String getId() { return CustomEvents.WORLD_LOCK_CHANGE_EVENT; }
}

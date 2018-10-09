package com.tdcrawl.tdc.events;

public abstract class Event
{
	private boolean cancelled = false;
	
	/**
	 * Sets whether an object is cancelled or not
	 * @param c If it is cancelled
	 */
	public void setCancelled(boolean c)
	{
		if(isCancellable())
			this.cancelled = c;
	}
	
	// Getters & Setters //
	
	/**
	 * If an event is cancelled and is cancellable, the action that caused the event to be fired should be reverted
	 * @return if the event was cancelled
	 */
	public boolean isCancelled() { return cancelled; }
	
	public abstract boolean isCancellable();
	public abstract String getId();
}

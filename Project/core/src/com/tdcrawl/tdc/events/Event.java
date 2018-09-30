package com.tdcrawl.tdc.events;

public abstract class Event
{
	private boolean cancelled = false;
	
	public void setCancelled(boolean c)
	{
		if(isCancellable())
			this.cancelled = c;
	}
	
	public boolean isCancelled() { return cancelled; }
	
	public abstract boolean isCancellable();
	public abstract String getId();
}

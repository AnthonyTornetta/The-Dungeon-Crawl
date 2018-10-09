package com.tdcrawl.tdc.events;

public interface EventCallback
{
	/**
	 * Called whenever a certain event happens by all its subscribers
	 * @param e The event that was executed
	 */
	public void callback(Event e);
}

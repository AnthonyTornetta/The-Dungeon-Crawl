package com.tdcrawl.tdc.events;

public class Events
{
	public static void registerAll()
	{
		System.out.println(EventHandler.registerEvent("onCollide"));
	}
}

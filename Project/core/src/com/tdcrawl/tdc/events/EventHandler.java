package com.tdcrawl.tdc.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventHandler
{
	private static Map<String, List<EventCallback>> subscribers = new HashMap<>();
	
	public static void call(Event e)
	{
		List<EventCallback> subs = subscribers.get(e.getId());
		
		if(subs == null)
			throw new UnregisteredEventException(e.getId() + " is an unregistered event!");
		
		for(EventCallback c : subs)
			c.callback(e);
	}
	
	public static boolean registerEvent(String eventId)
	{
		if(subscribers.containsKey(eventId))
			return false;
		
		subscribers.put(eventId, new ArrayList<EventCallback>());
		return true;
	}
	
	public static void subscribe(String eventId, EventCallback callback)
	{
		List<EventCallback> subs = subscribers.get(eventId);
		
		if(subs == null)
			throw new UnregisteredEventException(eventId + " is an unregistered event!");
		
		subs.add(callback);
	}
}

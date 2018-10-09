package com.tdcrawl.tdc.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tdcrawl.tdc.events.exceptions.UnregisteredEventException;

public class EventsHandler
{
	/**
	 * Holds all the event id's and their subscribers
	 */
	private static Map<String, List<EventCallback>> subscribers = new HashMap<>();
	
	/**
	 * Calls an event so all its subscribers can react to it
	 * @param e The event object to pass to the subscribers
	 */
	public static void call(Event e)
	{
		List<EventCallback> subs = subscribers.get(e.getId());
		
		// If subs == null, this event was never registered so throw an exception to prevent logic errors
		if(subs == null)
			throw new UnregisteredEventException(e.getId() + " is an unregistered event!");
		
		for(EventCallback c : subs)
			c.callback(e);
	}
	
	/**
	 * Registers an event with the EventsHandler for later use
	 * @param eventId What the event should be called
	 * @return false if that is already registered, true if it was successfully registered
	 */
	public static boolean registerEvent(String eventId)
	{
		if(subscribers.containsKey(eventId))
			return false;
		
		subscribers.put(eventId, new ArrayList<EventCallback>());
		return true;
	}
	
	/**
	 * Whenever an event with the eventId given is called, the callback will be executed
	 * @param eventId The event to listen for
	 * @param callback What to execute when this event is called
	 */
	public static void subscribe(String eventId, EventCallback callback)
	{
		List<EventCallback> subs = subscribers.get(eventId);
		
		if(subs == null)
			throw new UnregisteredEventException(eventId + " is an unregistered event!");
		
		subs.add(callback);
	}
}

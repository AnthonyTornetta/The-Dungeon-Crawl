package com.tdcrawl.tdc.registries;

import java.util.HashMap;
import java.util.Map;

import com.tdcrawl.tdc.registries.templates.ItemTemplate;

public class ItemRegistry
{
	/**
	 * Every object in the game corresponding with its ID
	 */
	private static Map<String, ItemTemplate> templates = new HashMap<>();
	
	/**
	 * Registers an object template with it's given ID
	 * @param id The ID to reference the object by
	 * @param template The template for creating the object
	 * @return true if no object with that ID is present, false if not (and it will not be registered)
	 */
	public static boolean registerObject(String id, ItemTemplate template)
	{
		if(templates.containsKey(id))
			return false;
		templates.put(id, template);
		return true;
	}
	
	/**
	 * Gets the object template from a given ID
	 * @param id The ID to get it from
	 * @return The ObjectTemplate registered with that ID
	 */
	public static ItemTemplate getItemTemplate(String id)
	{
		return templates.get(id);
	}
	
	/**
	 * Removes an object based off its ID
	 * @param id The ID & object to remove
	 * @return The removed object
	 */
	public static ItemTemplate removeObject(String id)
	{
		return templates.remove(id);
	}
}

package com.tdcrawl.tdc.items;

import com.tdcrawl.tdc.items.items.TestItem;
import com.tdcrawl.tdc.registries.ItemRegistry;

/**
 * A class for registering all the items
 */
public class Items
{
	/**
	 * Registers all the objects in the core game
	 */
	public static void registerAll()
	{
		ItemRegistry.registerObject("testItem", new TestItem.TestItemTemplate());
	}
}

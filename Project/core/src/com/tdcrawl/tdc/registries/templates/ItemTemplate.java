package com.tdcrawl.tdc.registries.templates;

import com.tdcrawl.tdc.items.Item;

public interface ItemTemplate
{
	/**
	 * Creates a GameObject object based off the given data
	 * @param data The data to base the object off of
	 * @return The uninitialized GameObject or null if there was an error parsing the data
	 */
	public Item create(ItemData data);
}

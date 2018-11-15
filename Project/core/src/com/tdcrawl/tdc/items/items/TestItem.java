package com.tdcrawl.tdc.items.items;

import com.tdcrawl.tdc.items.Item;
import com.tdcrawl.tdc.registries.templates.ItemData;
import com.tdcrawl.tdc.registries.templates.ItemTemplate;

public class TestItem extends Item
{
	public static class TestItemTemplate implements ItemTemplate
	{
		@Override
		public Item create(ItemData data)
		{
			return new TestItem();
		}
	}
}

package com.tdcrawl.tdc.items.items;

import com.badlogic.gdx.math.Vector2;
import com.tdcrawl.tdc.items.Item;
import com.tdcrawl.tdc.objects.entities.living.LivingEntity;
import com.tdcrawl.tdc.registries.templates.ItemData;
import com.tdcrawl.tdc.registries.templates.ItemTemplate;

public class TestItem extends Item
{
	@Override
	public Vector2 getDimensions()
	{
		return new Vector2(0.05f, 0.3f);
	}

	@Override
	public void use(LivingEntity by, LivingEntity on, UseType type)
	{
		System.out.print("Item Used (");
		
		switch(type)
		{
			case LEFT_CLICK:
				System.out.print("left click");
				break;
			case RIGHT_CLICK:
				System.out.print("right click");
				break;
			case MIDDLE_CLICK:
				System.out.print("middle click");
				break;
		}
		
		System.out.println(")!");
	}
	
	public static class TestItemTemplate implements ItemTemplate
	{
		@Override
		public Item create(ItemData data)
		{
			return new TestItem();
		}
	}
}

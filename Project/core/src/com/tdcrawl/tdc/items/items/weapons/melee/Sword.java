package com.tdcrawl.tdc.items.items.weapons.melee;

import com.badlogic.gdx.math.Vector2;
import com.tdcrawl.tdc.items.Item;
import com.tdcrawl.tdc.items.items.TestItem;
import com.tdcrawl.tdc.objects.entities.living.LivingEntity;
import com.tdcrawl.tdc.registries.templates.ItemData;
import com.tdcrawl.tdc.registries.templates.ItemTemplate;

public class Sword extends MeleeWeapon
{

	public Sword()
	{
		super(5);
	}

	@Override
	public Vector2 getDimensions()
	{
		return new Vector2(0.15f, 0.4f);
	}

	@Override
	public void use(LivingEntity by, LivingEntity on, UseType type)
	{
		on.takeDamage(getDamage());
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

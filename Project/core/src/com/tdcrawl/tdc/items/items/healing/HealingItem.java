package com.tdcrawl.tdc.items.items.healing;

import com.tdcrawl.tdc.items.Item;
import com.tdcrawl.tdc.objects.entities.living.LivingEntity;

public abstract class HealingItem extends Item
{
	private int healingValue;
	private int passiveHeal;
	
	public HealingItem(int h, int p)
	{
		healingValue = h;
		passiveHeal = p;
	}
	
	@Override
	/**
	 * Called whenever the item should be used
	 * @param by Who it was used by
	 * @param on Whom it was used on OR the same as by if it is being used on the caster
	 * @param type The type of use
	 */
	public void use(LivingEntity by, LivingEntity on, UseType type)
	{
		if(type.equals(UseType.LEFT_CLICK))
		{
			by.heal(healingValue);
		}
	}
}

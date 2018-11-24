package com.tdcrawl.tdc.items.items.weapons.ranged;

import com.tdcrawl.tdc.items.Item.UseType;
import com.tdcrawl.tdc.items.items.weapons.Weapon;
import com.tdcrawl.tdc.objects.entities.living.LivingEntity;

public abstract class RangedWeapon extends Weapon
{
	public RangedWeapon(int d)
	{
		super(d);
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
		
	}
}

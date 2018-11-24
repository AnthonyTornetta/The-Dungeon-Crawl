package com.tdcrawl.tdc.items.items.weapons.melee;

import com.badlogic.gdx.math.Vector2;
import com.tdcrawl.tdc.objects.entities.living.LivingEntity;

public class Sword extends MeleeWeapon
{

	public Sword()
	{
		super(5);
	}

	@Override
	public Vector2 getDimensions()
	{
		return new Vector2(0.3f, 0.6f);
	}

	@Override
	public void use(LivingEntity by, LivingEntity on, UseType type)
	{
		on.takeDamage(getDamage());
	}

}

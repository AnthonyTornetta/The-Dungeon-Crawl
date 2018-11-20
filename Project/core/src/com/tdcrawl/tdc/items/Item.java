package com.tdcrawl.tdc.items;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.tdcrawl.tdc.objects.entities.living.LivingEntity;

public abstract class Item
{
	/**
	 * Returns the dimensions for the item when someone will hold them (assuming no scaling)
	 * @return The dimensions for the item when someone will hold them (assuming no scaling)
	 */
	public abstract Vector2 getDimensions();
	
	/**
	 * Called whenever a living entity tries to hold the object
	 * @return true if they can hold it, false if not
	 */
	public boolean onHold(LivingEntity e) { return true; }
	
	/**
	 * Called every time the holder ticks AND is holding the item
	 * @param delta The time passed since last update
	 * @param cam The camera used to render where the holder is
	 */
	public void tick(float delta, Camera cam) {}
	
	/**
	 * Called whenever the item should be used
	 * @param by Who it was used bu
	 * @param on Who it was used on OR the same as by if it is being used on the caster
	 * @param type The type of use
	 */
	public abstract void use(LivingEntity by, LivingEntity on, UseType type);
	
	public enum UseType
	{
		LEFT_CLICK,
		RIGHT_CLICK,
		MIDDLE_CLICK
	}
}

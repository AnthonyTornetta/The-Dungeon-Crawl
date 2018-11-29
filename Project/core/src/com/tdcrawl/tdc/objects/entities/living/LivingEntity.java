package com.tdcrawl.tdc.objects.entities.living;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Shape;
import com.tdcrawl.tdc.items.inventory.Inventory;
import com.tdcrawl.tdc.objects.entities.Entity;
import com.tdcrawl.tdc.objects.entities.living.types.EntityType;
import com.tdcrawl.tdc.objects.fixtures.ObjectFixture;

/**
 * An Entity, but with health and items!
 * @see Entity
 * @author Cornchip
 */
public abstract class LivingEntity extends Entity
{
	private int health, maxHealth;
	
	private Inventory inventory;
	
	/**
	 * @see Entity#Entity(Shape, Vector2, BodyType, float, float, float, float, boolean, boolean, boolean)
	 * @param maxHealth The maximum health the LivingEntity can have
	 */
	public LivingEntity(Shape shape, Vector2 position, BodyType type, float density, float restitution, float friction,
			float angle, boolean bullet, boolean fixedRotation, boolean collidable, Vector2 centerPoint, int maxHealth)
	{
		super(shape, position, type, density, restitution, friction, angle, bullet, fixedRotation, collidable, centerPoint);
		
		this.maxHealth = maxHealth;
		this.health = maxHealth;
	}
	
	/**
	 * @see Entity#Entity(Shape, Vector2, BodyType, float, float, float, float, boolean, boolean, boolean, Vector2)
	 * @param maxHealth The maximum health the LivingEntity can have
	 */
	public LivingEntity(Shape shape, Vector2 position, BodyType type, float density, float restitution, float friction,
			float angle, boolean bullet, boolean fixedRotation, boolean collidable, int maxHealth)
	{
		super(shape, position, type, density, restitution, friction, angle, bullet, fixedRotation, collidable);
		
		this.maxHealth = maxHealth;
		this.health = maxHealth;
	}
	
	/**
	 * @see Entity#Entity(Vector2, BodyType, float, boolean, boolean, boolean, ObjectFixture)
	 * @param maxHealth The maximum health the LivingEntity can have
	 */
	public LivingEntity(Vector2 position, BodyType type, float angle, boolean bullet, boolean fixedRotation,
			boolean collidable, ObjectFixture centerFixture, int maxHealth)
	{
		super(position, type, angle, bullet, fixedRotation, collidable, centerFixture);
		
		this.maxHealth = maxHealth;
		this.health = maxHealth;
	}
	
	/**
	 * Called whenever a LivingEntity should die
	 * If this returns false, the living entity will not die
	 * @return false if it should not die, true if it should
	 */
	public abstract boolean die();
	
	// Getters & Setters //
	
	public abstract EntityType getEntityType();
	
	public void takeDamage(int amt) { health -= amt; if(health <= 0) die(); }
	public void heal(int amt) { health += amt; }
	
	public abstract boolean invulnerable();
	
	public int getMaxHealth() { return maxHealth; }
	public void setMaxHealth(int h) { this.maxHealth = h; }
	public int getHealth() { return health; }
	public void setHealth(int amt) { this.health = amt; }

	public Inventory getInventory() { return inventory; }
	public void setInventory(Inventory inventory) { this.inventory = inventory; }
	
	@Override
	public String toString()
	{
		return "LivingEntity [health=" + health + "/" + maxHealth + "; " + super.toString() + "]";
	}
}

package com.tdcrawl.tdc.objects.entities.living;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.tdcrawl.tdc.objects.entities.Entity;
import com.tdcrawl.tdc.objects.fixtures.ObjectFixture;

public abstract class LivingEntity extends Entity
{
	private int health, maxHealth;
	
	/**
	 * @see Entity
	 */
	public LivingEntity(Shape shape, Vector2 position, BodyType type, float density, float restitution, float friction,
			float angle, boolean bullet, boolean fixedRotation, boolean collidable, Vector2 centerPoint, int maxHealth)
	{
		super(shape, position, type, density, restitution, friction, angle, bullet, fixedRotation, collidable, centerPoint);
		
		this.maxHealth = maxHealth;
		this.health = maxHealth;
	}
	
	public LivingEntity(Shape shape, Vector2 position, BodyType type, float density, float restitution, float friction,
			float angle, boolean bullet, boolean fixedRotation, boolean collidable, int maxHealth)
	{
		super(shape, position, type, density, restitution, friction, angle, bullet, fixedRotation, collidable);
		
		this.maxHealth = maxHealth;
		this.health = maxHealth;
	}
	
	public LivingEntity(Vector2 position, BodyType type, float angle, boolean bullet, boolean fixedRotation,
			boolean collidable, ObjectFixture centerFixture, int maxHealth)
	{
		super(position, type, angle, bullet, fixedRotation, collidable, centerFixture);
		
		this.maxHealth = maxHealth;
		this.health = maxHealth;
	}
	
	public void takeDamage(int amt) { health -= amt; }
	public void heal(int amt) { health += amt; }
	
	public abstract boolean die();
	public abstract boolean invulnerable();
	
	public int getMaxHealth() { return maxHealth; }
	public void setMaxHealth(int h) { this.maxHealth = h; }
	public int getHealth() { return health; }
	public void setHealth(int amt) { this.health = amt; }
}

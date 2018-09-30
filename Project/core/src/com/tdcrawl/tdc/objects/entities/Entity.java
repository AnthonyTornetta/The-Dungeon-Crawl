package com.tdcrawl.tdc.objects.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Shape;
import com.tdcrawl.tdc.objects.GameObject;
import com.tdcrawl.tdc.objects.fixtures.ObjectFixture;

public abstract class Entity extends GameObject
{
	public Entity(Shape shape, Vector2 position, BodyType type, float density, float restitution, float friction,
			float angle, boolean bullet, boolean fixedRotation, boolean collidable, Vector2 centerPoint)
	{
		super(shape, position, type, density, restitution, friction, angle, bullet, fixedRotation, collidable, centerPoint);
	}
	
	public Entity(Shape shape, Vector2 position, BodyType type, float density, float restitution, float friction,
			float angle, boolean bullet, boolean fixedRotation, boolean collidable)
	{
		super(shape, position, type, density, restitution, friction, angle, bullet, fixedRotation, collidable);
	}
	
	public Entity(Vector2 position, BodyType type, float angle, boolean bullet, boolean fixedRotation,
			boolean collidable, ObjectFixture centerFixture)
	{
		super(position, type, angle, bullet, fixedRotation, collidable, centerFixture);
	}
	
	public abstract void tick(float delta);
}

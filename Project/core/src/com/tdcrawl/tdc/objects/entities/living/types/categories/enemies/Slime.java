package com.tdcrawl.tdc.objects.entities.living.types.categories.enemies;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Shape;
import com.tdcrawl.tdc.levels.Level;
import com.tdcrawl.tdc.objects.entities.living.types.categories.HostileEntity;

public class Slime extends HostileEntity
{
	public Slime(Shape shape, Vector2 position, BodyType type, float density, float friction,
			float angle, boolean bullet, boolean fixedRotation, boolean collidable, int maxHealth)
	{
		super(shape, position, type, 0.3f, 0.7f, friction, angle, bullet, fixedRotation, collidable, maxHealth);
	}
	
	@Override
	public boolean die()
	{
		return false;
	}

	@Override
	public boolean invulnerable()
	{
		return true;
	}
	
	public Vector2 jump()
	{
		//float[] dataChunk = super.getPath();
		return null;
	}

	@Override
	public void tick(float delta, Camera cam) 
	{
		getBody().applyForceToCenter(new Vector2(0, getBody().getMass() * 6.0f), true);
		
		
	}
}

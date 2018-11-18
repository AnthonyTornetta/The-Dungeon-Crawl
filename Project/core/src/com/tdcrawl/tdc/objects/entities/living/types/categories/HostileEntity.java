package com.tdcrawl.tdc.objects.entities.living.types.categories;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Shape;
import com.tdcrawl.tdc.objects.entities.living.types.PathedEntity;

public abstract class HostileEntity extends PathedEntity{

	public HostileEntity(Shape shape, Vector2 position, BodyType type, float density, float restitution, float friction,
			float angle, boolean bullet, boolean fixedRotation, boolean collidable, int maxHealth) {
		super(shape, position, type, density, restitution, friction, angle, bullet, fixedRotation, collidable, maxHealth);
		// TODO Auto-generated constructor stub
	}
	
	public void setPath()
	{
		
	}

}

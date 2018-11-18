package com.tdcrawl.tdc.objects.entities.living.types;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Shape;
import com.tdcrawl.tdc.objects.entities.living.LivingEntity;

public abstract class PathedEntity extends LivingEntity 
{

	public PathedEntity(Shape shape, Vector2 position, BodyType type, float density, float restitution, float friction,
			float angle, boolean bullet, boolean fixedRotation, boolean collidable, int maxHealth) {
		super(shape, position, type, density, restitution, friction, angle, bullet, fixedRotation, collidable, maxHealth);
		// TODO Auto-generated constructor stub
	}
	
	//going to be used to move the entity in the proper direction / get the new coordinates
	public void setPath()
	{
		//how to go about this I'm unsure. I could create sub classes for each path or just one singular path per class
	}

}

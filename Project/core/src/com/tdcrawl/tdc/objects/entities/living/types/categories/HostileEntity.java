package com.tdcrawl.tdc.objects.entities.living.types.categories;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Shape;
import com.tdcrawl.tdc.objects.entities.living.LivingEntity;
import com.tdcrawl.tdc.objects.entities.living.types.EntityType;
import com.tdcrawl.tdc.objects.entities.living.types.IPathable;

public abstract class HostileEntity extends LivingEntity implements IPathable
{

	public HostileEntity(Shape shape, Vector2 position, BodyType type, float density, float restitution, float friction,
			float angle, boolean bullet, boolean fixedRotation, boolean collidable, int maxHealth) {
		super(shape, position, type, density, restitution, friction, angle, bullet, fixedRotation, collidable, maxHealth);
		
		
	}
	
	@Override
	public void setPath()
	{
		
	}
	
	@Override
	public EntityType getEntityType() { return EntityType.HOSTILE; }
}

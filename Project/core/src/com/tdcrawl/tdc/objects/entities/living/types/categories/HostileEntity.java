package com.tdcrawl.tdc.objects.entities.living.types.categories;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Shape;
import com.tdcrawl.tdc.objects.entities.living.LivingEntity;
import com.tdcrawl.tdc.objects.entities.living.Player;
import com.tdcrawl.tdc.objects.entities.living.types.EntityType;
import com.tdcrawl.tdc.objects.entities.living.types.IPathable;

public abstract class HostileEntity extends LivingEntity implements IPathable
{

	public HostileEntity(Shape shape, Vector2 position, BodyType type, float density, float restitution, float friction,
			float angle, boolean bullet, boolean fixedRotation, boolean collidable, int maxHealth) {
		super(shape, position, type, density, restitution, friction, angle, bullet, fixedRotation, collidable, maxHealth);
		
		
	}
	/**
	 * Created to path to the player and attack them.
	 * Will return distance to player and angle to them, using x & y to see if negative
	 */
	public float[] getPath(Player play)
	{
		/*
		 * Goes like This:
		 * 0: X Difference
		 * 1: Y Difference
		 * 2: Angle (Degrees) [Absolute Angle]
		 * 
		 */
		float[] dataPack = new float[3];
		
		Vector2 playerCoord = new Vector2();
		playerCoord.set(play.getPosition());
		
		Vector2 curretCoord = new Vector2();
		curretCoord.set(this.getPosition());
		
		dataPack[0] = (float) playerCoord.x - curretCoord.x;
		dataPack[1] = (float) playerCoord.y - curretCoord.y;
		dataPack[2] = (float) Math.atan(Math.abs(dataPack[0]/dataPack[1]));
		
		if(dataPack[0] >= 0)
		{
			if(dataPack[1] <= 0)
				dataPack[2] += 270;
		}
		if(dataPack[0] <= 0)
		{
			if(dataPack[1] <= 0)
				dataPack[2] += 180;
			else
				dataPack[2] += 90;
		}
		
		
		for(int ye = 0; ye < dataPack.length; ye++)
			System.out.println("Data: " + dataPack[ye]);
		
		return dataPack;
	}
	
	@Override
	public EntityType getEntityType() { return EntityType.HOSTILE; }
}

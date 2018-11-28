package com.tdcrawl.tdc.objects.entities.living.types.categories.enemies;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.tdcrawl.tdc.objects.GameObject;
import com.tdcrawl.tdc.objects.entities.living.types.categories.HostileEntity;
import com.tdcrawl.tdc.registries.templates.ObjectData;
import com.tdcrawl.tdc.registries.templates.ObjectTemplate;

//whatever else corns needs, I'll get. I need to create the object and do testing.
public class Slime extends HostileEntity
{
	float lastJump = 0;
	
	public Slime(Shape shape, Vector2 position, float friction,
			float angle, boolean bullet, boolean fixedRotation, boolean collidable, int maxHealth)
	{
		super(shape, position, BodyType.DynamicBody, 0.3f, 0.7f, friction, angle, bullet, fixedRotation, collidable, maxHealth);
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
	
	public void jump()
	{
		float[] dataChunk = super.getPath(null);
		Vector2 hopDir = new Vector2();
		
		hopDir.x = getBody().getMass() * 6.0f;
		//might work on more advanced hops; higher pathing in later tests
		if(dataChunk[3] > 90 && dataChunk[3] < 270)
		{
			hopDir.y = 120;
		}
		else
		{
			hopDir.y = 60;
		}
		
		getBody().applyForceToCenter(hopDir, true);
	}
	
	public static class SlimeTemplate implements ObjectTemplate
	{
		@Override
		public GameObject create(ObjectData data)
		{
			CircleShape shape = new CircleShape();
			shape.setRadius(data.radius);
			
//(Shape shape, Vector2 position, float friction, float angle, boolean bullet, boolean fixedRotation, boolean collidable, int maxHealth)
			return new Slime(shape, data.position, data.getOrDef("friction", 0.3f), data.getOrDef("angle", 0f), true, true, true, (int)data.getOrDef("maxHealth", 20f));
		}
		
		/*
		 * Get or Def Exemplary:
		 * data.getOrDef("restitution", 0.1f)
		 * returns the 'restitution' based on string key, else gives value of 0.1f
		 */
		
	}

	@Override
	public void tick(float delta, Camera cam) 
	{
		//used to counteract gravity, makes it 'bouncier'
		getBody().applyForceToCenter(new Vector2(0, getBody().getMass() * 6.0f), true);
		if(getLastJump()+3 >= delta)
		{
			jump();
			setLastJump(delta);
		}
		
	}
	public float getLastJump() {return this.lastJump;}
	public void setLastJump(float currentTime) {this.lastJump = currentTime;}
}

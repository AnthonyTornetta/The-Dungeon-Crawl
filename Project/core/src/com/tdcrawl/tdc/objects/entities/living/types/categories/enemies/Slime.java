package com.tdcrawl.tdc.objects.entities.living.types.categories.enemies;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.tdcrawl.tdc.objects.GameObject;
import com.tdcrawl.tdc.objects.entities.Entity;
import com.tdcrawl.tdc.objects.entities.living.Player;
import com.tdcrawl.tdc.objects.entities.living.types.categories.HostileEntity;
import com.tdcrawl.tdc.registries.templates.ObjectData;
import com.tdcrawl.tdc.registries.templates.ObjectTemplate;
import com.tdcrawl.tdc.util.Helper;

//whatever else corns needs, I'll get. I need to create the object and do testing.
public class Slime extends HostileEntity
{
	float lastJump = 0;
	float timeCounter = 0;
	
	public Slime(Shape shape, Vector2 position, float friction,
			float angle, boolean bullet, boolean fixedRotation, boolean collidable, int maxHealth)
	{
		super(shape, position, BodyType.DynamicBody, 5f, 0f, friction, angle, bullet, fixedRotation, collidable, maxHealth);
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
	
	
	public void takeDamage(int amt) 
	{ super.takeDamage(amt); jump();}

	//Hops really high some times for no apparent reason.
	//Sometimes short after going off the player.
	public void jump()
	{
		Player currentPlayer = null;
		
		for(Entity o : getRoom().getEntitiesInRoom())
		{
			if(o instanceof Player)
			{
				 currentPlayer = (Player)o;
				// System.out.println("Player Found!");
			}
		}
		
		if(currentPlayer == null)
		{
			getBody().applyForceToCenter(getBody().getMass() * 90.0f, 0, true);
		}
		else
		{
				float[] dataChunk = super.getPath(currentPlayer);
				Vector2 hopDir = new Vector2();
				
				hopDir.y = Helper.randomizer(getBody().getMass() * 300.0f, getBody().getMass() * 200);
				//might work on more advanced hops; higher pathing in later tests
				if(dataChunk[2] > 90 && dataChunk[2] < 270)
				{
					hopDir.x = Helper.randomizer(getBody().getMass() * -50, getBody().getMass() * -25);
				}
				else
				{
					hopDir.x = Helper.randomizer(getBody().getMass() * 50, getBody().getMass() * 25);
				}
				
				System.out.println("Force is: " + getBody().getLinearVelocity());
				getBody().applyForceToCenter(hopDir, true);
		}
	}
	
	public static class SlimeTemplate implements ObjectTemplate
	{
		@Override
		public GameObject create(ObjectData data)
		{
			CircleShape shape = new CircleShape();
			if(data.radius == 0)
				data.radius = Helper.randomizer(0.25f, 0.5f);
			shape.setRadius(data.radius);
			
//(Shape shape, Vector2 position, float friction, float angle, boolean bullet, boolean fixedRotation, boolean collidable, int maxHealth)
			return new Slime(shape, data.position, data.getOrDef("friction", 0.2f), data.getOrDef("angle", 0f), true, true, true, (int)data.getOrDef("maxHealth", 20f));
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
		getBody().applyForceToCenter(new Vector2(0, getBody().getMass() * 2.0f), true);
		
		if(getLastJump()+3 <= timeCounter || this.isOnGround())
		{
			jump();
			setLastJump(timeCounter);
		}
		
		timeCounter += delta;
	}
	public float getLastJump() {return this.lastJump;}
	public void setLastJump(float currentTime) {this.lastJump = currentTime;}
}

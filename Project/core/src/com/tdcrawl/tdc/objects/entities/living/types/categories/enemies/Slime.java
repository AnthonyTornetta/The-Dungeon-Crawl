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

public class Slime extends HostileEntity
{
	//used to keep it from instantly jumping
	float lastJump = 0;
	float timeCounter = 0;
	
	public Slime(Shape shape, Vector2 position, float friction,
			float angle, boolean bullet, boolean fixedRotation, boolean collidable, int maxHealth)
	{
		super(shape, position, BodyType.DynamicBody, 5f, 0f, friction, angle, bullet, fixedRotation, collidable, maxHealth);
	}
	
	//removes the object from the world after HP runs out
	@Override
	public boolean die()
	{
		return true;
	}

	//whether it takes damage or not
	@Override
	public boolean invulnerable()
	{
		return false;
	}
	
	//Forces the entity to lose HP and jump instantly
	public void takeDamage(int amt) 
	{ super.takeDamage(amt); jump();}

	//Varied jumps based on random change, how it moves
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
		
		//if there is no player it hops in place
		if(currentPlayer == null)
		{
			getBody().applyForceToCenter(getBody().getMass() * 90.0f, 0, true);
		}
		else
		{
			float[] dataChunk = super.getPath(currentPlayer);
			Vector2 hopDir = new Vector2();
			
			//finds the vertical and horizontal hop strength as well as where it should hop based on player
			hopDir.y = Helper.randomizer(getBody().getMass() * 300.0f, getBody().getMass() * 200);
			
			if(dataChunk[2] > 90 && dataChunk[2] < 270)
			{
				hopDir.x = Helper.randomizer(getBody().getMass() * -50, getBody().getMass() * -25);
			}
			else
			{
				hopDir.x = Helper.randomizer(getBody().getMass() * 50, getBody().getMass() * 25);
			}
			getBody().applyForceToCenter(hopDir, true);
		}
	}
	
	//A good method built to allow uniform creation of all types of slimes
	public static class SlimeTemplate implements ObjectTemplate
	{
		@Override
		public GameObject create(ObjectData data)
		{
			CircleShape shape = new CircleShape();
			if(data.radius == 0)
				data.radius = Helper.randomizer(0.25f, 0.5f);
			shape.setRadius(data.radius);
			
			return new Slime(shape, data.position, data.getOrDef("friction", 0.2f), data.getOrDef("angle", 0f), true, true, true, (int)data.getOrDef("maxHealth", 20f));
		}		
	}

	//moves when the game updates
	@Override
	public void tick(float delta, Camera cam) 
	{
		//used to counteract gravity, makes it 'bouncier'
		getBody().applyForceToCenter(new Vector2(0, getBody().getMass() * 2.0f), true);
		
		//times so it doesn't infinitely jump
		if(getLastJump()+3 <= timeCounter || this.isOnGround())
		{
			jump();
			setLastJump(timeCounter);
		}
		
		timeCounter += delta;
	}
	
	//getters and setters
	public float getLastJump() {return this.lastJump;}
	public void setLastJump(float currentTime) {this.lastJump = currentTime;}
}
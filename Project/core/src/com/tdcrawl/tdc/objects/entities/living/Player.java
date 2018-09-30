package com.tdcrawl.tdc.objects.entities.living;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.tdcrawl.tdc.objects.GameObject;
import com.tdcrawl.tdc.objects.fixtures.ObjectFixture;
import com.tdcrawl.tdc.registries.templates.ObjectData;
import com.tdcrawl.tdc.registries.templates.ObjectTemplate;
import com.tdcrawl.tdc.util.Helper;

public class Player extends LivingEntity
{
	private final float ACCELERATION = 20.0f; // x m/s^2
	private final float JUMP_STRENGTH = 10.0f; // acceleration applied when jump occurs
	private final int JUMPS_ALLOWED = 2;
	private final float TIME_BETWEEN_JUMPS = 0.4f;
	
	private Vector2 startPos;
	
	private int jumps = 0;
	private float timeSinceLastJump = 0;
	
	public Player(Vector2 position, int maxHealth)
	{
		super(position, BodyType.DynamicBody, 0, true, true, true, createCenterFixture(), maxHealth);
		
		startPos = Helper.clone(position);
		
		CircleShape headShape = new CircleShape();
		headShape.setRadius(0.4f);
		ObjectFixture head = new ObjectFixture(true, 1.5f, 0.1f, 0.05f, headShape, new Vector2(0, 0.9f + 0.4f));
		
		this.addFixture(head);
	}
	
	private static ObjectFixture createCenterFixture()
	{
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.2f, 0.9f);
		
		ObjectFixture centerFixture = new ObjectFixture(true, 6.0f, 0.0f, 0.05f, shape, new Vector2(0, 0));
		return centerFixture;
	}
	
	@Override
	public void tick(float delta)
	{
		timeSinceLastJump += delta;
		
		if(Gdx.input.isKeyPressed(Input.Keys.R))
		{
			setPosition(Helper.clone(startPos));
			getBody().setLinearVelocity(0, 0);
		}
			
		Vector2 acceleration = new Vector2();
		
		acceleration.x = Helper.toInt(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT));
		acceleration.x -= Helper.toInt(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT));
		
		acceleration.x *= ACCELERATION * delta;
		
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Keys.W))
		{
			if(this.isOnGround())
				jumps = 0;
			
			if(timeSinceLastJump >= TIME_BETWEEN_JUMPS && jumps < JUMPS_ALLOWED)
			{
				timeSinceLastJump = 0;
				jumps++;
				acceleration.y += JUMP_STRENGTH;
			}
		}
		
		getBody().setLinearVelocity(getBody().getLinearVelocity().add(acceleration));
	}
	
	@Override
	public boolean die()
	{
		return false;
	}

	@Override
	public boolean invulnerable()
	{
		return false;
	}
	
	public static class PlayerTemplate implements ObjectTemplate
	{
		@Override
		public GameObject create(ObjectData data)
		{
			return new Player(data.position, 20);
		}
	}
}

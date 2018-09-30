package com.tdcrawl.tdc.objects.fixtures;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.tdcrawl.tdc.util.Helper;

/**
 * A nicer way of creating a fixture and adding it to a body
 */
public class ObjectFixture
{	
	private Fixture fixture;
	
	// Once the object is initialized, this values are not updated. Use the getters and setters instead
	private boolean collidable;
	private float density, restitution, friction;
	private Shape shape;
	private Vector2 position;
	
	/**
	 * A nicer way of creating a fixture and adding it to a body
	 * @param collidable If the fixture will stop things from passing through (overridden by body if body is noncollidable)
	 * @param density How dense this is
	 * @param restitution How bouncy this is
	 * @param friction How much friction this has
	 * @param shape The shape of this
	 * @param position The relative position of it to the body (e.g. 0, 0 would be directly at the center of the body)
	 */
	public ObjectFixture(boolean collidable, float density, float restitution, float friction, Shape shape, Vector2 position)
	{
		this.collidable = collidable;
		this.density = density;
		this.restitution = restitution;
		this.friction = friction;
		this.shape = shape;
		this.position = position;
	}
	
	/**
	 * Adds the fixture to the body
	 * @param b The body to append the fixture to
	 */
	public void init(Body b)
	{
		if(initialized())
			throw new IllegalStateException("This ObjectFixture has already been initialized!");
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = getDensity();
		fixtureDef.friction = getFriction();
				
		Helper.moveShape(getShape(), position);
		
		fixtureDef.shape = getShape();
		fixtureDef.restitution = getRestitution();
		fixtureDef.isSensor = !isCollidable() || !isCollidable();
		
		fixture = b.createFixture(fixtureDef);
		
		// This allows me to see what ObjectFixture this fixture belongs to later
		fixture.setUserData(this);
		
		shape.dispose(); // We no longer need this
	}
	
	/**
	 * Checks if the fixture has been added to a body
	 * @return true if it has been attached, false it not
	 */
	public boolean initialized()
	{
		return fixture != null;
	}
	
	// Getters & setters //
	
	public float getDensity()
	{
		return density;
	}
	
	public void setDensity(float density)
	{
		if(initialized())
			throw new IllegalStateException("Cannot set the density of an already-initialized object!");
		else
			this.density = density;
	}
	
	public float getRestitution()
	{
		if(initialized())
			return fixture.getRestitution();
		else
			return restitution;
	}
	
	public void setRestitution(float rest)
	{
		if(initialized())
			fixture.setRestitution(rest);
		else
			this.restitution = rest;
	}
	
	public float getFriction()
	{
		if(initialized())
			return fixture.getFriction();
		else
			return friction;
	}
	
	public void setFriction(float friction)
	{
		if(initialized())
			fixture.setFriction(friction);
		else
			this.friction = friction;
	}
	
	public Shape getShape()
	{
		if(initialized())
			return fixture.getShape();
		else
			return shape;
	}
	
	public void setShape(Shape shape)
	{
		if(initialized())
			throw new IllegalStateException("State cannot be set after initialization");
		else
			this.shape = shape;
	}

	public boolean isCollidable()
	{
		if(initialized())
			return !fixture.isSensor();
		else
			return collidable;
	}
	
	public void setCollidable(boolean collidable)
	{
		if(initialized())
			fixture.setSensor(!collidable);
		else
			this.collidable = collidable;
	}
}

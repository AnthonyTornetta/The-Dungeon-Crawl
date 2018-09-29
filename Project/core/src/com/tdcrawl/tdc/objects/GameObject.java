package com.tdcrawl.tdc.objects;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.tdcrawl.tdc.objects.fixtures.ObjectFixture;

public class GameObject
{
	private Body body;
	private World world;
	private Fixture centralPoint;
	
	// This value will be updated
	private float density;
	
	private ObjectFixture centerFixture;
	
	// Once the object is initialized, this values are not updated. Use the getters and setters instead
	private BodyType type;
	private Vector2 position;
	private float angle;
	private boolean bullet, fixedRotation;
	private boolean collidable;
	
	/**
	 * Stores fixtures to be added to be added to the body (other than the center one) once it is initialized. Then it is not used again
	 */
	private List<ObjectFixture> fixturesToAdd = new ArrayList<>();
	
	/**
	 * Creates a game object with all the information needed to initialize it when ready
	 * @param shape The shape the object will have
	 * @param shape The shape the object will have
	 * @param position Where the object will be placed in the world
	 * @param type The BodyType of the object
	 * @param density How dense the object is
	 * @param restitution How bouncy the object is between 0 to 1.0f inclusive
	 * @param friction How much friction it has between 0 to 1.0f inclusive
	 * @param angle The angle of the body (in degrees I think)
	 * @param bullet If the collision should be extra precise on this objects (used for really quick things)
	 * @param fixedRotation If rotation should be handled through physical interactions or just via programmatically setting it
	 * @param collidable If this has a solid collision box (true), or things will pass through it (false)
	 */
	public GameObject(Shape shape, Vector2 position, BodyType type, 
			float density, float restitution, float friction, float angle,
			boolean bullet, boolean fixedRotation, boolean collidable)
	{
		this(shape, position, type, density, restitution, friction, angle, bullet, fixedRotation, collidable, new Vector2(0, 0));
	}
	
	/**
	 * Creates a game object with all the information needed to initialize it when ready
	 * @param shape The shape the object will have
	 * @param position Where the object will be placed in the world
	 * @param type The BodyType of the object
	 * @param density How dense the object is
	 * @param restitution How bouncy the object is between 0 to 1.0f inclusive
	 * @param friction How much friction it has between 0 to 1.0f inclusive
	 * @param angle The angle of the body (in degrees I think)
	 * @param bullet If the collision should be extra precise on this objects (used for really quick things)
	 * @param fixedRotation If rotation should be handled through physical interactions or just via programmatically setting it
	 * @param collidable If this has a solid collision box (true), or things will pass through it (false)
	 * @param centerPoint The fixture that will be added to the body as the base fixture (if null no fixture will be added)
	 */
	public GameObject(Shape shape, Vector2 position, BodyType type, 
			float density, float restitution, float friction, float angle,
			boolean bullet, boolean fixedRotation, boolean collidable, Vector2 centerPoint)
	{
		this(position, type, angle, bullet, fixedRotation, collidable, 
				new ObjectFixture(collidable, density, restitution, friction, shape, centerPoint));
	}
	
	/**
	 * Creates a game object with all the information needed to initialize it when ready
	 * @param position Where the object will be placed in the world
	 * @param type The BodyType of the object
	 * @param angle The angle of the object (degrees I think)
	 * @param bullet If the collision should be extra precise on this objects (used for really quick things)
	 * @param fixedRotation If rotation should be handled through physical interactions or just via programmatically setting it
	 * @param collidable If this has a solid collision box (true), or things will pass through it (false)
	 * @param centerFixture The fixture that will be added to the body as the base fixture (if null no fixture will be added)
	 */
	public GameObject(Vector2 position, BodyType type, float angle,
			boolean bullet, boolean fixedRotation, boolean collidable, ObjectFixture centerFixture)
	{
		this.position = position;
		this.type = type;
		this.bullet = bullet;
		this.fixedRotation = fixedRotation;
		this.collidable = collidable;
		this.centerFixture = centerFixture;
		if(centerFixture != null)
			this.density = centerFixture.getDensity();
	}
	
	/**
	 * Adds the GameObject and all its fixtures to the world
	 * @param world The world to add this to
	 */
	public void init(World world)
	{
		if(initialized())
			throw new IllegalStateException("This object has already been initialized!");
		
		this.world = world;
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.allowSleep = true;
		bodyDef.angle = angle;
		bodyDef.bullet = bullet;
		bodyDef.fixedRotation = fixedRotation;
		bodyDef.type = type;
		
		body = world.createBody(bodyDef);
				
		if(centerFixture != null)
			centerFixture.init(getBody());
				
		while(fixturesToAdd.size() != 0)
			fixturesToAdd.remove(fixturesToAdd.size() - 1).init(getBody());
		
		body.setTransform(position, body.getAngle());
		
		// This allows me to see what object this belongs to later
		body.setUserData(this);
	}
	
	/**
	 * Appends a fixture to the object
	 * @param f The fixture to append
	 */
	public void addFixture(ObjectFixture f)
	{
		if(initialized())
		{
			if(centerFixture == null)
				centerFixture = f;
			f.init(getBody());	
		}
		else
		{
			if(centerFixture == null)
				centerFixture = f;
			else
				fixturesToAdd.add(f);
		}
		
		density += f.getDensity();
	}
	
	// Getters & Setters //
	
	public boolean initialized()
	{
		return body != null;
	}
	
	public World getWorld() { return world; }
	public Body getBody() { return body; }
	
	public Vector2 getPosition()
	{
		if(initialized())
			return body.getPosition();
		else
			return position;
	}
	
	public void setPosition(Vector2 position)
	{
		if(initialized())
			body.setTransform(getPosition(), getAngle());
		else
			this.position = position;
	}
	
	public float getAngle()
	{
		if(initialized())
			return body.getAngle();
		else
			return angle;
	}
	
	public void setAngle(float angle)
	{
		if(initialized())
			body.setTransform(getPosition(), angle);
		else
			this.angle = angle;
	}
	
	public BodyType getType()
	{
		if(initialized())
			return body.getType();
		else
			return type;
	}
	
	public boolean isCollidable()
	{
		if(initialized())
			return centralPoint.isSensor();
		else
			return collidable;
	}
	
	public void setCollidable(boolean collidable)
	{
		if(initialized())
			centralPoint.setSensor(!collidable);
		else
			this.collidable = collidable;
	}
	
	public boolean hasFixedRotation()
	{
		if(initialized())
			return body.isFixedRotation();
		else
			return fixedRotation;
	}
	
	public void setFixedRotation(boolean fixedRotation)
	{
		if(initialized())
			body.setFixedRotation(fixedRotation);
		else
			this.fixedRotation = fixedRotation;
	}
	
	public boolean isBullet()
	{
		if(initialized())
			return body.isBullet();
		else
			return bullet;
	}
	
	public void setBullet(boolean bullet)
	{
		if(initialized())
			body.setBullet(bullet);
		else
			this.bullet = bullet;
	}
	
	public float getDensity()
	{
		return density;
	}
}

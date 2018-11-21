package com.tdcrawl.tdc.objects.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Shape;
import com.tdcrawl.tdc.levels.rooms.Room;
import com.tdcrawl.tdc.objects.GameObject;
import com.tdcrawl.tdc.objects.fixtures.ObjectFixture;

/**
 * The same thing as a GameObject, but has a tick function called every game update
 */
public abstract class Entity extends GameObject
{
	/**
	 * The room the entity is currently in
	 */
	private Room room;
	
	/**
	 * Creates an Entity with all the information needed to initialize it when ready
	 * @param shape The shape the object will have
	 * @param position Where the object will be placed in the world
	 * @param type The BodyType of the object
	 * @param density How dense the object is
	 * @param restitution How bouncy the object is between 0 to 1.0f inclusive
	 * @param friction How much friction it has between 0 to 1.0f inclusive
	 * @param angle The angle of the body in radians
	 * @param bullet If the collision should be extra precise on this objects (used for really quick things)
	 * @param fixedRotation If rotation should be handled through physical interactions or just via programmatically setting it
	 * @param collidable If this has a solid collision box (true), or things will pass through it (false)
	 * @param centerPoint The fixture that will be added to the body as the base fixture (if null no fixture will be added)
	 */
	public Entity(Shape shape, Vector2 position, BodyType type, float density, float restitution, float friction,
			float angle, boolean bullet, boolean fixedRotation, boolean collidable, Vector2 centerPoint)
	{
		super(shape, position, type, density, restitution, friction, angle, bullet, fixedRotation, collidable, centerPoint);
	}
	
	/**
	 * Creates an Entity with all the information needed to initialize it when ready
	 * @param shape The shape the object will have
	 * @param shape The shape the object will have
	 * @param position Where the object will be placed in the world
	 * @param type The BodyType of the object
	 * @param density How dense the object is
	 * @param restitution How bouncy the object is between 0 to 1.0f inclusive
	 * @param friction How much friction it has between 0 to 1.0f inclusive
	 * @param angle The angle of the body in radians
	 * @param bullet If the collision should be extra precise on this objects (used for really quick things)
	 * @param fixedRotation If rotation should be handled through physical interactions or just via programmatically setting it
	 * @param collidable If this has a solid collision box (true), or things will pass through it (false)
	 */
	public Entity(Shape shape, Vector2 position, BodyType type, float density, float restitution, float friction,
			float angle, boolean bullet, boolean fixedRotation, boolean collidable)
	{
		super(shape, position, type, density, restitution, friction, angle, bullet, fixedRotation, collidable);
	}
	
	/**
	 * Creates an Entity with all the information needed to initialize it when ready
	 * @param position Where the object will be placed in the world
	 * @param type The BodyType of the object
	 * @param angle The angle of the object (degrees I think)
	 * @param bullet If the collision should be extra precise on this objects (used for really quick things)
	 * @param fixedRotation If rotation should be handled through physical interactions or just via programmatically setting it
	 * @param collidable If this has a solid collision box (true), or things will pass through it (false)
	 * @param centerFixture The fixture that will be added to the body as the base fixture (if null no fixture will be added)
	 */
	public Entity(Vector2 position, BodyType type, float angle, boolean bullet, boolean fixedRotation,
			boolean collidable, ObjectFixture centerFixture)
	{
		super(position, type, angle, bullet, fixedRotation, collidable, centerFixture);
	}
	
	/**
	 * Called once every game tick
	 * @param delta Time since this was last called
	 * @param cam The camera used to render the game
	 */
	public abstract void tick(float delta, Camera cam);
	
	// Getters & Setters //
	
	public void setRoom(Room room) { this.room = room; }
	public Room getRoom() { return room; }
}

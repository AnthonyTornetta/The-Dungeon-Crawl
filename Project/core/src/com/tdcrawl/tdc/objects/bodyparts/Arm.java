package com.tdcrawl.tdc.objects.bodyparts;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Shape;

public class Arm extends BodyPart
{
	/**
	 * For now the exact same as a {@link BodyPart}
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
	public Arm(Shape shape, Vector2 position, BodyType type, float density, float restitution, 
			float friction, float angle, boolean bullet, boolean fixedRotation, boolean collidable)
	{
		super(shape, position, type, density, restitution, friction, angle, bullet, fixedRotation, collidable);
	}
}

package com.tdcrawl.tdc.objects.bodyparts;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.tdcrawl.tdc.joints.ObjectJoint;
import com.tdcrawl.tdc.objects.GameObject;
import com.tdcrawl.tdc.objects.fixtures.ObjectFixture;
import com.tdcrawl.tdc.util.Helper;

/**
 * A game object that is easily attachable to another object
 */
public abstract class BodyPart extends GameObject
{
	private ObjectJoint joint;
	
	/**
	 * A {@link GameObject} that is easily attachable to another object
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
	 * @param centerPoint The position the center fixture will be placed out
	 */
	public BodyPart(Shape shape, Vector2 position, BodyType type, float density, float restitution, float friction,
			float angle, boolean bullet, boolean fixedRotation, boolean collidable, Vector2 centerPoint)
	{
		super(shape, position, type, density, restitution, friction, angle, bullet, fixedRotation, collidable, centerPoint);
	}
	
	/**
	 * A {@link GameObject} that is easily attachable to another object
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
	public BodyPart(Shape shape, Vector2 position, BodyType type, float density, float restitution, float friction,
			float angle, boolean bullet, boolean fixedRotation, boolean collidable)
	{
		super(shape, position, type, density, restitution, friction, angle, bullet, fixedRotation, collidable);
	}
	
	/**
	 * A {@link GameObject} that is easily attachable to another object
	 * @param position Where the object will be placed in the world
	 * @param type The BodyType of the object
	 * @param angle The angle of the object (degrees I think)
	 * @param bullet If the collision should be extra precise on this objects (used for really quick things)
	 * @param fixedRotation If rotation should be handled through physical interactions or just via programmatically setting it
	 * @param collidable If this has a solid collision box (true), or things will pass through it (false)
	 * @param centerFixture The fixture that will be added to the body as the base fixture (if null no fixture will be added)
	 */
	public BodyPart(Vector2 position, BodyType type, float angle, boolean bullet, boolean fixedRotation,
			boolean collidable, ObjectFixture centerFixture)
	{
		super(position, type, angle, bullet, fixedRotation, collidable, centerFixture);
	}
	
	/**
	 * Attaches this object to the specified object
	 * @param go The object to attach to
	 * @param anchor The offset of the GameObject's origin to attach this to (Vector.Zero for no offset)
	 */
	public void attatch(GameObject go, Vector2 anchor)
	{
		if(go.initialized() && initialized())
		{
			RevoluteJointDef jointDef = new RevoluteJointDef();
			jointDef.bodyA = go.getBody(); 
			jointDef.bodyB = getBody();
			jointDef.localAnchorA.set(anchor);
			
			ObjectJoint joint = new ObjectJoint(jointDef);
			
			joint.attach();
		}
		else
			throw new IllegalStateException("Cannot add a part to an uninitialized object!");
	}
	
	/**
	 * Detaches the part from the attached object
	 */
	public void detatch()
	{
		if(attached())
		{
			Helper.removeJoint(getJoint());
			joint = null;
		}
	}
	
	public boolean attached() { return joint != null; }
	public ObjectJoint getJoint() { return joint; }
	public JointType getJointType() { return joint.getJointType(); }
}

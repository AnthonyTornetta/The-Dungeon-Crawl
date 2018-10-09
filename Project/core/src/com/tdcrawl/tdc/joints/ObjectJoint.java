package com.tdcrawl.tdc.joints;

import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;

public class ObjectJoint
{
	/**
	 * This is initialized once the joint is initialized, and set to null when it is detached
	 */
	private Joint joint;
	
	private JointDef def;
	
	/**
	 * A simpler way of handling joints with GameObjects
	 * @param def The definition for the joint
	 */
	public ObjectJoint(JointDef def)
	{
		this.def = def;
	}
	
	/**
	 * Attaches the joint to the bodies specified in its definition, assuming they have been initialized
	 */
	public void attach()
	{
		if(def.bodyA != null && def.bodyB != null && !attached())
			joint = getDefinition().bodyA.getWorld().createJoint(getDefinition());
		else
			throw new IllegalStateException("Cannot add a part to an uninitialized object!");
	}
	
	/**
	 * Removes the joint from the bodies specified in its definition
	 */
	public void detatch()
	{
		if(attached())
			getDefinition().bodyA.getWorld().destroyJoint(getJoint());
		
		joint = null;
	}
	
	public boolean attached() { return joint != null; }
	public JointDef getDefinition() { return def; }
	public Joint getJoint() { return joint; }
	public JointType getJointType() { return joint.getType(); }
}

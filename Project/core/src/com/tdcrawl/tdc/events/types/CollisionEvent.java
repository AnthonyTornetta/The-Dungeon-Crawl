package com.tdcrawl.tdc.events.types;

import com.tdcrawl.tdc.events.CustomEvents;
import com.tdcrawl.tdc.events.Event;
import com.tdcrawl.tdc.objects.GameObject;
import com.tdcrawl.tdc.objects.fixtures.ObjectFixture;

public class CollisionEvent extends Event
{
	public static enum CollisionState
	{
		BEGIN_COLLISION,
		END_COLLISION
	}
	
	private CollisionState state;
	private GameObject obj1, obj2;
	private ObjectFixture fix1, fix2;
	
	/**
	 * Called whenever an object collides with another.
	 * Note: This will be called twice per collision, with the objects in question switched in the argument order
	 * @param obj1 The first object in the collision
	 * @param obj2 The second object in the collision
	 * @param fix1 The actual fixture that touched from obj1
	 * @param fix2 The actual fixture that touched from obj2
	 */
	public CollisionEvent(GameObject obj1, GameObject obj2, ObjectFixture fix1, ObjectFixture fix2, CollisionState state)
	{
		this.obj1 = obj1;
		this.obj2 = obj2;
		
		this.fix1 = fix1;
		this.fix2 = fix2;
		
		this.state = state;
	}
	
	// Getters & Setters //
	
	public GameObject getObject1() { return obj1; }
	public GameObject getObject2() { return obj2; }
	
	public ObjectFixture getFixture1() { return fix1; }
	public ObjectFixture getFixture2() { return fix2; }
	
	public CollisionState getState() { return state; }
	
	@Override
	public boolean isCancellable() { return false; }
	
	@Override
	public String getId() { return CustomEvents.ON_GAMEOBJECT_COLLISION; }
}

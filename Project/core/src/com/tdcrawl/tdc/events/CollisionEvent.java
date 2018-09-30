package com.tdcrawl.tdc.events;

import com.tdcrawl.tdc.objects.GameObject;
import com.tdcrawl.tdc.objects.fixtures.ObjectFixture;

public class CollisionEvent extends Event
{
	private GameObject obj1, obj2;
	private ObjectFixture fix1, fix2;
	
	public CollisionEvent(GameObject obj1, GameObject obj2, ObjectFixture fix1, ObjectFixture fix2)
	{
		this.obj1 = obj1;
		this.obj2 = obj2;
		
		this.fix1 = fix1;
		this.fix2 = fix2;
	}
	
	public GameObject getObject1() { return obj1; }
	public GameObject getObject2() { return obj2; }
	
	public ObjectFixture getFixture1() { return fix1; }
	public ObjectFixture getFixture2() { return fix2; }
	
	@Override
	public boolean isCancellable() { return false; }
	
	@Override
	public String getId() { return "onCollide"; }
}

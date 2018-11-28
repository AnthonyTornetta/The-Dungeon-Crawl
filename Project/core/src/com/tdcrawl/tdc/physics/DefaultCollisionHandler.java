package com.tdcrawl.tdc.physics;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.tdcrawl.tdc.events.EventsHandler;
import com.tdcrawl.tdc.events.types.CollisionEvent;
import com.tdcrawl.tdc.events.types.CollisionEvent.CollisionState;
import com.tdcrawl.tdc.objects.GameObject;
import com.tdcrawl.tdc.objects.fixtures.ObjectFixture;

public class DefaultCollisionHandler implements ContactListener
{
	@Override
	public void beginContact(Contact contact)
	{
		if(contact.getFixtureA().getBody().getUserData() instanceof GameObject && 
				contact.getFixtureB().getBody().getUserData() instanceof GameObject)
		{
			// Checks if it's 2 game objects colliding, and if it is call a new event
			
			GameObject obj1 = (GameObject) contact.getFixtureA().getBody().getUserData();
			GameObject obj2 = (GameObject) contact.getFixtureB().getBody().getUserData();
			
			ObjectFixture fix1 = (ObjectFixture) contact.getFixtureA().getUserData();
			ObjectFixture fix2 = (ObjectFixture) contact.getFixtureB().getUserData();
			
			EventsHandler.call(new CollisionEvent(obj1, obj2, fix1, fix2, CollisionState.BEGIN_COLLISION));
		}
	}
	
	@Override
	public void endContact(Contact contact)
	{
		if(contact.getFixtureA().getBody().getUserData() instanceof GameObject && 
				contact.getFixtureB().getBody().getUserData() instanceof GameObject)
		{
			// Checks if it's 2 game objects colliding, and if it is call a new event
			
			GameObject obj1 = (GameObject) contact.getFixtureA().getBody().getUserData();
			GameObject obj2 = (GameObject) contact.getFixtureB().getBody().getUserData();
			
			ObjectFixture fix1 = (ObjectFixture) contact.getFixtureA().getUserData();
			ObjectFixture fix2 = (ObjectFixture) contact.getFixtureB().getUserData();
			
			EventsHandler.call(new CollisionEvent(obj1, obj2, fix1, fix2, CollisionState.END_COLLISION));
		}
	}
	
	// The below methods are just weird and are called in between the two methods above.
	
	@Override
	public void preSolve(Contact contact, Manifold oldManifold)
	{
		
	}
	
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse)
	{
		
	}
}

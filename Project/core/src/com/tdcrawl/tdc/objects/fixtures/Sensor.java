package com.tdcrawl.tdc.objects.fixtures;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;
import com.tdcrawl.tdc.events.Event;
import com.tdcrawl.tdc.events.EventCallback;
import com.tdcrawl.tdc.events.EventsHandler;
import com.tdcrawl.tdc.events.types.CollisionEvent;
import com.tdcrawl.tdc.events.types.CollisionEvent.CollisionState;
import com.tdcrawl.tdc.objects.GameObject;

public abstract class Sensor extends ObjectFixture
{
	static
	{
		EventsHandler.subscribe("onCollide", new EventCallback()
		{			
			@Override
			public void callback(Event e)
			{
				CollisionEvent event = (CollisionEvent)e;
				
				Object data = event.getFixture2() != null ? event.getFixture2().getFixture().getUserData() : null;
				
				if(data instanceof Sensor)
				{
					if(event.getState() == CollisionState.BEGIN_COLLISION)
						((Sensor)data).onCollide(event.getObject2(), event.getFixture2());
					else if(event.getState() == CollisionState.END_COLLISION)
						((Sensor)data).onUncollide(event.getObject2(), event.getFixture2());
				}
					
			}
		});
	}
	
	public Sensor(Shape shape, Vector2 position)
	{
		super(false, 0, 0, 0, shape, position);
	}
	
	public abstract void onCollide(GameObject other, ObjectFixture fixture);
	public abstract void onUncollide(GameObject other, ObjectFixture fixture);

	@Override
	public String toString()
	{
		return "Sensor [" + super.toString() + "]";
	}
}

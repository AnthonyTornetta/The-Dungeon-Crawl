package com.tdcrawl.tdc.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.tdcrawl.tdc.events.Event;
import com.tdcrawl.tdc.events.EventCallback;
import com.tdcrawl.tdc.events.EventsHandler;
import com.tdcrawl.tdc.events.types.CollisionEvent;
import com.tdcrawl.tdc.events.types.CollisionEvent.CollisionState;
import com.tdcrawl.tdc.objects.entities.living.Player;
import com.tdcrawl.tdc.objects.fixtures.ObjectFixture;
import com.tdcrawl.tdc.registries.templates.ObjectData;
import com.tdcrawl.tdc.registries.templates.ObjectTemplate;

public class Ball extends GameObject
{
	public Ball(Vector2 position, float angle, boolean bullet, boolean fixedRotation, boolean collidable,
			ObjectFixture centerFixture)
	{
		super(position, BodyType.DynamicBody, angle, bullet, fixedRotation, collidable, centerFixture);
		
		EventsHandler.subscribe("onCollide", new EventCallback()
		{	
			@Override
			public void callback(Event e)
			{
				CollisionEvent event = (CollisionEvent)e;
				
				if(event.getState() == CollisionState.BEGIN_COLLISION)
				{
					if(event.getObject1() instanceof Player)
					{
						getBody().applyForceToCenter(new Vector2(0, 19.6f), true);
					}
				}
			}
		});
	}
	
	public static class BallTemplate implements ObjectTemplate
	{
		@Override
		public GameObject create(ObjectData data)
		{
			float restitution = 0.1f;
			
			if(data.misc != null)
				restitution = Float.parseFloat(data.misc);
			
			CircleShape shape = new CircleShape();
			shape.setRadius(data.radius);
			
			ObjectFixture fixture = new ObjectFixture(true, 1, restitution, 0.1f, shape, new Vector2(0, 0));
			
			return new Ball(data.position, 0, false, false, true, fixture);
		}
		
	}
}

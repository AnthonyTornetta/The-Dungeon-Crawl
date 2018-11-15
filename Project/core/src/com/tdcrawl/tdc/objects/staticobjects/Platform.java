package com.tdcrawl.tdc.objects.staticobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.tdcrawl.tdc.objects.GameObject;
import com.tdcrawl.tdc.registries.templates.ObjectData;
import com.tdcrawl.tdc.registries.templates.ObjectTemplate;
import com.badlogic.gdx.physics.box2d.Shape;

/**
 * An example object that is basically just a static box that something can stand on
 */
public class Platform extends GameObject
{
	/**
	 * An example object that is basically just a static box that something can stand on
	 * @param shape The shape of the static platform
	 * @param position Where its center point is located
	 * @param friction The friction of it
	 * @param angle What is its angle
	 */
	public Platform(Shape shape, Vector2 position, float friction, float angle)
	{
		super(shape, position, BodyType.StaticBody, 1.0f, 0, friction, angle, false, true, true);
	}
	
	/**
	 * The template for creating a Platform
	 */
	public static class PlatformTemplate implements ObjectTemplate
	{
		@Override
		public GameObject create(ObjectData data)
		{
			PolygonShape shape = new PolygonShape();
			shape.setAsBox(data.dimensions.x, data.dimensions.y);
			
			return new Platform(shape, data.position, 1f, 0);
		}
	}
}

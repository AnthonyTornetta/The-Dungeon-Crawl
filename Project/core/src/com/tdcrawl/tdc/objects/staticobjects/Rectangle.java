package com.tdcrawl.tdc.objects.staticobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.tdcrawl.tdc.objects.GameObject;
import com.tdcrawl.tdc.registries.templates.ObjectData;
import com.tdcrawl.tdc.registries.templates.ObjectTemplate;

/**
 * An example object that is basically just a box
 */
public class Rectangle extends GameObject
{
	/**
	 * An example object that is basically just a box
	 * @param dimensions How big the rectangle is (from the center)
	 * @param position Where its center point is located
	 * @param density How dense it is
	 * @param restitution How bouncy it is
	 * @param friction The friction of it
	 * @param angle What is its angle
	 */
	public Rectangle(Vector2 dimensions, Vector2 position, float density, float restitution, float friction, float angle)
	{
		super(null, position, BodyType.DynamicBody, density, restitution, friction, angle, false, false, true);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(dimensions.x, dimensions.y);
		setShape(shape);
	}
	
	/**
	 * The template for making a Cube object
	 */
	public static class CubeTemplate implements ObjectTemplate
	{
		@Override
		public GameObject create(ObjectData data)
		{
			return new Rectangle(data.dimensions, data.position, data.getOrDef("density", 5.0f), 
									data.getOrDef("restitution", 0.1f), data.getOrDef("friction", 0.1f), data.getOrDef("angle", 0));
		}
	}
}

package com.tdcrawl.tdc.objects.staticobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
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
	 * @param shape The shape of the static platform
	 * @param position Where its center point is located
	 * @param density How dense it is
	 * @param restitution How bouncy it is
	 * @param friction The friction of it
	 * @param angle What is its angle
	 */
	public Rectangle(Shape shape, Vector2 position, float density, float restitution, float friction, float angle)
	{
		super(shape, position, BodyType.DynamicBody, density, restitution, friction, angle, false, false, true);
	}
	
	/**
	 * The template for making a Cube object
	 */
	public static class CubeTemplate implements ObjectTemplate
	{
		@Override
		public GameObject create(ObjectData data)
		{
			PolygonShape shape = new PolygonShape();
			shape.setAsBox(data.dimensions.x, data.dimensions.y);
			
			float restitution = 0.1f;
			if(data.extraData != null && data.extraData.containsKey("restitution"))
				restitution = (Float) data.extraData.get("restitution");
			
			return new Rectangle(shape, data.position, 5.0f, restitution, 0.1f, 0);
		}
	}
}

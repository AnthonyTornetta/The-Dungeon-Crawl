package com.tdcrawl.tdc.util;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

public final class Helper
{
	public static final float PX_TO_M = 32.0f; // 32 pixels for every meter
	
	public static Vector2 clamp(Vector2 vec, float xMin, float yMin, float xMax, float yMax)
	{
		if(vec.x < xMin)
			vec.x = xMin;
		else if(vec.x > xMax)
			vec.x = xMax;
		if(vec.y < yMin)
			vec.y = yMin;
		else if(vec.y > yMax)
			vec.y = yMax;
		return vec;
	}
	
	public static float randomizer(float baseValue, float range)
	{
		float value = (float) (Math.random() * range + baseValue);
		return value;
	}
	
	public static Vector2 clamp(Vector2 vec, Vector2 min, Vector2 max)
	{
		return clamp(vec, min.x, min.y, max.x, max.y);
	}
	
	public static double clamp(double var, double min, double max)
	{
		if(var < min) return min;
		if(var > max) return max;
		return var;
	}
	
	public static double clamp(float var, float min, float max)
	{
		if(var < min) return min;
		if(var > max) return max;
		return var;
	}
	
	public static double clamp(int var, int min, int max)
	{
		if(var < min) return min;
		if(var > max) return max;
		return var;
	}
	
	public static float pxToM(float px)
	{
		return px / PX_TO_M;
	}
	
	public static float mToPx(float m)
	{
		return m * PX_TO_M;
	}
	
	public static int toInt(boolean b)
	{
		return b ? 1 : 0;
	}
	
	public static Vector2 clone(Vector2 c)
	{
		return new Vector2(c.x, c.y);
	}
	
	public static float angleTo(float x1, float y1, float x2, float y2)
	{
		return angleTo(new Vector2(x1, y1), new Vector2(x2, y2));
	}
	
	public static float angleTo(Vector2 x, Vector2 y)
	{
		return angleTo(x, y, Vector2.Zero);
	}
	
	public static float angleTo(Vector2 x, Vector2 y, Vector2 offset)
	{
		return new Vector2(y).sub(new Vector2(x)).add(offset).angleRad();
	}

	public static Vector2 screenCoordinatesToMeters(Vector2 coords, Camera cam)
	{
		Vector3 v3 = new Vector3(coords, 0);
		cam.unproject(v3);
		return new Vector2(v3.x, v3.y);
	}

	/**
	 * Returns a fancy String for those objects not kind enough to create their on toString() method
	 * @param object The object to stringify (no properties will be changed)
	 * @return A super fancy String descringing that object, but if none can be generated will just use that object's toString
	 */
	public static String toString(Object object)
	{
		if(object instanceof Fixture)
		{
			Fixture f = (Fixture)object;
			
			return "Fixture [density=" + f.getDensity() + ", friction=" + f.getFriction() + ", restitution=" + f.getRestitution() + ", shape=" + toString(f.getShape()) + "]";
		}
		else if(object instanceof Body)
		{
			Body b = (Body)object;
			
			return "Body [position=" + b.getPosition() + ", mass=" + b.getMass() + ", velocity=" + b.getLinearVelocity() + ", type=" + b.getType() + ", localCenter=" + b.getLocalCenter() + "]";
		}
		
		return object != null ? object.toString() : "null";
	}
}

package com.tdcrawl.tdc.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

public final class Helper
{
	public static final float PX_TO_M = 40.0f; // 40 px per meter
	
	/**
	 * Moves a shape based on the amount of change (it just adds its position to the change amount)
	 * @param s The shape to move
	 * @param change The amount to move it by
	 */
	public static void moveShape(Shape s, Vector2 change)
	{
		if(s instanceof CircleShape)
		{
			CircleShape shape = (CircleShape)s;
			shape.setPosition(shape.getPosition().add(change)); // That was easy
		}
		else if(s instanceof PolygonShape)
		{
			PolygonShape shape = (PolygonShape)s;
			
			int vertexAmt = shape.getVertexCount();
			Vector2[] vertexes = new Vector2[vertexAmt];
			
			for(int i = 0; i < vertexAmt; i++)
			{
				Vector2 vertex = new Vector2();
				shape.getVertex(i, vertex); // Updates the vertex to the actual vertex
				vertexes[i] = vertex.add(change);
			}
			
			shape.set(vertexes);
		}
		else if(s instanceof ChainShape)
		{
			ChainShape shape = (ChainShape)s;
			
			boolean isLooped = shape.isLooped();
			
			int vertexAmt = shape.getVertexCount();
			Vector2[] vertexes = new Vector2[vertexAmt];
			
			for(int i = 0; i < vertexAmt; i++)
			{
				Vector2 vertex = new Vector2();
				shape.getVertex(i, vertex); // Updates the vertex to the actual vertex
				vertexes[i] = vertex.add(change);
			}
			
			// The only way to move this is to make a new one
			shape = new ChainShape();
			if(isLooped)
				shape.createLoop(vertexes);
			else
				shape.createChain(vertexes);
		}
		else if(s instanceof EdgeShape)
		{
			// Not tested yet
			
			EdgeShape shape = (EdgeShape)s;
			
			Vector2 temp = new Vector2();
			
			shape.getVertex0(temp);
			temp.add(change);
			shape.setVertex0(temp);
			
			shape.getVertex3(temp);
			temp.add(change);
			shape.setVertex3(temp);
		}
	}
	
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
}

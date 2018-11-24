package com.tdcrawl.tdc.util;

import com.badlogic.gdx.math.Vector2;

/**
 * Like the {@link Vector2} but for integers
 */
public class Vector2I implements Cloneable
{
	public int x, y;
	
	public static final Vector2I Zero = new Vector2I();
	public static final Vector2I One = new Vector2I(1, 1);
	
	/**
	 * Initializes a Vector2I with both numbers as 0
	 */
	public Vector2I()
	{
		this(0, 0);
	}
	
	/**
	 * Initializes a Vector2I with both numbers the given parameters
	 * @param x The x value
	 * @param y The y value
	 */
	public Vector2I(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Clones the vector passed in and sets this's values to that
	 * @param v The vector to clone
	 */
	public Vector2I(Vector2I v)
	{
		this(v.x, v.y);
	}
	
	/**
	 * Adds two vectors
	 * @param other The vector to add to this
	 * @return this for chaining
	 */
	public Vector2I add(Vector2I other)
	{
		x += other.x;
		y += other.y;
		return this;
	}
	
	/**
	 * Subtracts two vectors
	 * @param other The vector to add to this
	 * @return this for chaining
	 */
	public Vector2I sub(Vector2I other)
	{
		x -= other.x;
		y -= other.y;
		return this;
	}
	
	/**
	 * Multiplies two vectors
	 * @param other The vector to add to this
	 * @return this for chaining
	 */
	public Vector2I scl(Vector2I other)
	{
		x *= other.x;
		y *= other.y;
		return this;
	}
	
	/**
	 * Multiplies two vectors
	 * @param other The vector to add to this
	 * @return this for chaining
	 */
	public Vector2I mul(Vector2I other)
	{
		return scl(other); // Because who would guess "scl" means multiply?? I sure didn't
	}
	
	/**
	 * Divides two vectors
	 * @param other The vector to add to this
	 * @return this for chaining
	 */
	public Vector2I divide(Vector2I other)
	{
		x /= other.x;
		y /= other.y;
		return this;
	}
	
	/**
	 * Moduluses two vectors
	 * @param other The vector to add to this
	 * @return this for chaining
	 */
	public Vector2I mod(Vector2I other)
	{
		x %= other.x;
		y %= other.y;
		return this;
	}
	
	/**
	 * Averages a bunch of vectors given, or returns 0 if there are none
	 * @param vectors The Vectors to average
	 * @return A Vector containing the x and y average
	 */
	public static Vector2I avg(Vector2I... vectors)
	{
		if(vectors.length == 0)
			return Vector2I.Zero;
		
		Vector2I sum = new Vector2I();
		
		for(Vector2I v : vectors)
			sum.add(v);
		
		return sum.divide(new Vector2I(vectors.length, vectors.length));
	}
	
	@Override
	public String toString()
	{
		return "(" + x + "," + y + ")";
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result *= prime + x;
		result *= prime + y;
		return result;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if(other instanceof Vector2I)
		{
			Vector2I o = (Vector2I)other;
			return o.x == x && o.y == y;
		}
		return false;
	}
	
	/**
	 * Clones the Vector
	 * <br>See {@link Vector2I#clone()}
	 * @return a clone
	 */
	public Vector2I cpy()
	{
		return new Vector2I(this);
	}
	
	@Override
	public Vector2I clone()
	{
		return cpy();
	}
}

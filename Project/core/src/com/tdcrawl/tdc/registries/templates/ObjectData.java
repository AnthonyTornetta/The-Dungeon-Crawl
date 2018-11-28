package com.tdcrawl.tdc.registries.templates;

import java.util.Map;

import com.badlogic.gdx.math.Vector2;

/**
 * A simple class that just contains some data for objects
 * Everything is public in this because this is basically just a struct (see https://docs.microsoft.com/en-us/dotnet/csharp/language-reference/keywords/struct)
 * so there is no point to making getters and setters for it
 */
public class ObjectData implements Cloneable
{
	public String name;
	public Vector2 position;
	public Vector2 dimensions;
	public float radius;
	public Map<String, Object> extraData;
	
	public float getOrDef(String key, float def)
	{
		if(extraData == null)
			return def;
		try
		{
			return Float.parseFloat((String)extraData.getOrDefault(key, def + ""));
		}
		catch(NumberFormatException ex)
		{
			return def;
		}
	}

	public Vector2 getOrDef(String key, Vector2 def)
	{
		if(extraData == null)
			return def;
		try
		{
			if(extraData.containsKey(key))
			{
				// It looks like this: (x,y) - aka (0.0,0.0)
				
				String[] split =("" + extraData.get(key)).split(",");
				return new Vector2(Float.parseFloat(split[0].substring(1) + "f"),
									Float.parseFloat(split[1].substring(0, split[1].length() - 1) + "f"));
			}
			else
				return def;
		}
		catch(Exception ex)
		{
			return def;
		}
	}
	
	public boolean getOrDef(String key, boolean def)
	{
		if(extraData == null)
			return def;
		try
		{
			return Boolean.parseBoolean("" + extraData.get(key));
		}
		catch(Exception ex)
		{
			return def;
		}
	}
	
	public ObjectData clone()
	{
		ObjectData d = new ObjectData();
		d.name = name;
		d.position = position != null ? position.cpy() : null;
		d.dimensions = dimensions != null ? dimensions.cpy() : null;
		d.radius = radius;
		d.extraData = extraData;
		return d;
	}
}

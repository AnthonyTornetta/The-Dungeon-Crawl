package com.tdcrawl.tdc.registries.templates;

import java.util.Map;

import com.badlogic.gdx.math.Vector2;

/**
 * A simple class that just contains some data for objects
 * Everything is public in this because this is basically just a struct (see https://docs.microsoft.com/en-us/dotnet/csharp/language-reference/keywords/struct)
 * so there is no point to making getters and setters for it
 */
public class ObjectData
{
	public String name;
	public Vector2 position;
	public Vector2 dimensions;
	public float radius;
	public Map<String, Object> extraData;
}

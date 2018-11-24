package com.tdcrawl.tdc.levels.rooms;

import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.tdcrawl.tdc.registries.templates.ObjectData;

/**
 * This class will instruct GSON on how to properly read and save room Json files
 */
public class RoomBlueprint
{
	public boolean enclosed = true;
	public Vector2 dimensions;
	public List<ObjectData> objects;
}

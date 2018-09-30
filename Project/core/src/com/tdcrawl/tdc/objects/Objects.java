package com.tdcrawl.tdc.objects;

import com.tdcrawl.tdc.registries.ObjectRegistry;

/**
 * A class for registering all the objects
 */
public class Objects
{
	/**
	 * Registers all the objects in the core game
	 */
	public static void registerAll()
	{
		ObjectRegistry.registerObject("rectangle", new Rectangle.CubeTemplate());
		ObjectRegistry.registerObject("platform", new Platform.PlatformTemplate());
	}
}

package com.tdcrawl.tdc.objects;

import com.tdcrawl.tdc.objects.entities.Door;
import com.tdcrawl.tdc.objects.entities.living.Player;
import com.tdcrawl.tdc.objects.entities.living.types.categories.enemies.Slime;
import com.tdcrawl.tdc.objects.staticobjects.Ball;
import com.tdcrawl.tdc.objects.staticobjects.ItemObject;
import com.tdcrawl.tdc.objects.staticobjects.Platform;
import com.tdcrawl.tdc.objects.staticobjects.Rectangle;
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
		ObjectRegistry.registerObject("player", new Player.PlayerTemplate());
		ObjectRegistry.registerObject("ball", new Ball.BallTemplate());
		ObjectRegistry.registerObject("item", new ItemObject.ItemObjectTemplate());
		ObjectRegistry.registerObject("slime", new Slime.SlimeTemplate());
		ObjectRegistry.registerObject("door", new Door.DoorTemplate());
	}
}

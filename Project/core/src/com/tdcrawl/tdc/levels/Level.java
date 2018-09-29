package com.tdcrawl.tdc.levels;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class Level
{
	/**
	 * Where every object will be
	 */
	private World world;
	
	public Level()
	{
		world = new World(new Vector2(0, -9.8f), true);
	}
	
	public void tick(float delta)
	{
		world.step(delta, 8, 3); // 8 and 3 are good* values I found online. *I assume they are good.
		// URL: http://www.iforce2d.net/b2dtut/worlds
	}
	
	public void render(float delta, Camera cam, Box2DDebugRenderer debugRenderer)
	{
		if(debugRenderer != null)
			debugRenderer.render(world, cam.combined);
	}

	public World getWorld() { return world; }
}

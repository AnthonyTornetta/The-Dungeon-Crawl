package com.tdcrawl.tdc.screens;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.tdcrawl.tdc.levels.Level;
import com.tdcrawl.tdc.util.Helper;

public class GameScreen implements Screen
{	
	private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	private OrthographicCamera cam = new OrthographicCamera();
	
	private Level level;
	
	@Override
	public void show()
	{
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		try
		{
			level = new Level(0);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		level.create();
	}

	@Override
	public void render(float delta)
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		level.tick(delta, cam);
		
		level.render(delta, cam, debugRenderer);
	}

	@Override
	public void resize(int width, int height)
	{
		// width and height are in px, so convert them to meters then adjust camera
		cam = new OrthographicCamera(Helper.pxToM(width), Helper.pxToM(height));
		cam.zoom = Math.min(720f / width, 480f / height);
		cam.update();
	}

	@Override
	public void pause()
	{
		
	}

	@Override
	public void resume()
	{
		
	}

	@Override
	public void hide()
	{
		
	}

	@Override
	public void dispose()
	{
		level.dispose();
	}
}

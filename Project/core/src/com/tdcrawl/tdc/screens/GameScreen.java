package com.tdcrawl.tdc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.tdcrawl.tdc.levels.Level;
import com.tdcrawl.tdc.objects.GameObject;
import com.tdcrawl.tdc.util.Helper;

public class GameScreen implements Screen
{	
	private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	private OrthographicCamera cam = new OrthographicCamera();
	
	private Level level = new Level();
	
	@Override
	public void show()
	{
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		// Can be any polygon
		PolygonShape shapeRect = new PolygonShape();
		// Sets it to be a 1x1 rectangle
		shapeRect.setAsBox(1f, 1f);
		
		// It's a circle
		CircleShape shapeCircle = new CircleShape();
		shapeCircle.setRadius(1.5f);
		
		// Can contain any number of points
		ChainShape shapeChain = new ChainShape();
		shapeChain.createChain(new Vector2[]
				{
						new Vector2(-6.0f, 1f),
						new Vector2(0, 0),
						new Vector2(6.0f, 1f),
				});
		
		// Basically a chain shape but only a start & end
		EdgeShape shapeEdge = new EdgeShape();
		shapeEdge.set(-2.0f, -2.0f, 2.0f, -3.0f);
		
		GameObject testRectangle = new GameObject(shapeRect, new Vector2(0, 3), BodyType.DynamicBody, 4.0f, 0.2f, 0.1f, 0, false, false, true);
		GameObject testCircle    = new GameObject(shapeCircle, new Vector2(4, 40), BodyType.DynamicBody, 20.0f, 0.2f, 0.1f, 0, false, false, true);
		GameObject testChain     = new GameObject(shapeChain, new Vector2(0, -3f), BodyType.StaticBody, 20.0f, 0.2f, 0.1f, 0, false, false, true);
		GameObject testEdge      = new GameObject(shapeEdge, new Vector2(0, 2f), BodyType.StaticBody, 20.0f, 0.2f, 0.1f, 0, false, false, true);
		
		testRectangle.init(level.getWorld());
		testCircle.init(level.getWorld());
		testChain.init(level.getWorld());
		testEdge.init(level.getWorld());
	}

	@Override
	public void render(float delta)
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		level.tick(delta);
		
		level.render(delta, cam, debugRenderer);
	}

	@Override
	public void resize(int width, int height)
	{
		cam = new OrthographicCamera(Helper.pxToM(width), Helper.pxToM(height)); // Zooms everything in a bunch because Box2D doesn't like really small or large objects
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
		
	}
}

package com.tdcrawl.tdc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tdcrawl.tdc.util.Helper;

public class YouDiedScreen implements Screen
{
	SpriteBatch batch = new SpriteBatch();
	private OrthographicCamera cam = new OrthographicCamera();
	BitmapFont font = new BitmapFont();

	@Override
	public void show()
	{
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void render(float delta)
	{
	    batch.setProjectionMatrix(cam.combined);

	    batch.begin();

	    font.draw(batch, "Hello World!", 10, 10);

	    batch.end();
	 }

	@Override
	public void resize(int width, int height)
	{
		cam = new OrthographicCamera(Helper.pxToM(width), Helper.pxToM(height));
		cam.zoom = Math.min(720f / width, 480f / height);
		cam.update();
	}

	@Override
	public void pause()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub
		
	}

}

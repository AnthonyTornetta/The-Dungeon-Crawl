package com.tdcrawl.tdc.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class YouDiedScreen implements Screen
{
	SpriteBatch batch = new SpriteBatch();
	private OrthographicCamera cam = new OrthographicCamera();

	@Override
	public void show()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta)
	{
		BitmapFont font = new BitmapFont();

		batch.setProjectionMatrix(cam.combined); //or your matrix to draw GAME WORLD, not UI

		batch.begin();
	
		font.draw(batch, "Hello World!", 10, 10);

	    batch.end();
	 }

	@Override
	public void resize(int width, int height)
	{
		// TODO Auto-generated method stub
		
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

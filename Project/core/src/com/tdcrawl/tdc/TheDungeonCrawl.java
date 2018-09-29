package com.tdcrawl.tdc;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.tdcrawl.tdc.screens.GameScreen;

public class TheDungeonCrawl extends Game
{
	static // Runs before anything else (even main method)
	{
	    GdxNativesLoader.load();
	}
	
	@Override
	public void create()
	{
		setScreen(new GameScreen());
	}
}

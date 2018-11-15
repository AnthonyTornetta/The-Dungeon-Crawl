package com.tdcrawl.tdc;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.tdcrawl.tdc.events.CustomEvents;
import com.tdcrawl.tdc.items.Items;
import com.tdcrawl.tdc.objects.Objects;
import com.tdcrawl.tdc.screens.GameScreen;
import com.tdcrawl.tdc.util.Reference;

public class TheDungeonCrawl extends Game
{
	static // Runs before anything else (even main method)
	{
	    GdxNativesLoader.load();
	}
	
	@Override
	public void create()
	{
		CustomEvents.registerAll();
		Items.registerAll();
		Objects.registerAll();
		
		try
		{
			setScreen(new GameScreen());
		}
		catch (Exception ex)
		{
			Reference.handleError(ex);
		}
	}
}

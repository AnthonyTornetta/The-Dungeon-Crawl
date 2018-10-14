package com.tdcrawl.tdc.desktop;

import java.lang.Thread.UncaughtExceptionHandler;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tdcrawl.tdc.TheDungeonCrawl;
import com.tdcrawl.tdc.util.Reference;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = Reference.NAME;
		config.foregroundFPS = Reference.FPS;
		config.width = Reference.WINDOW_WIDTH;
		config.height = Reference.WINDOW_HEIGHT;
		
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler()
		{
			@Override
			public void uncaughtException (Thread thread, final Throwable ex)
			{
				Reference.handleError(ex);
				System.exit(1);
			}
	    });
		
		new LwjglApplication(new TheDungeonCrawl(), config);
	}
}

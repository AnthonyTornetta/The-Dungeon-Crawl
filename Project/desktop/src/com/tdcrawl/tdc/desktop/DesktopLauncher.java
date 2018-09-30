package com.tdcrawl.tdc.desktop;

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
		
		new LwjglApplication(new TheDungeonCrawl(), config);
	}
}

package com.tdcrawl.tdc.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tdcrawl.tdc.TheDungeonCrawl;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "The Dungeon Crawl";
		config.foregroundFPS = 60;
		
		new LwjglApplication(new TheDungeonCrawl(), config);
	}
}

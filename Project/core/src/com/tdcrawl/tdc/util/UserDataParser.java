package com.tdcrawl.tdc.util;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.tdcrawl.tdc.objects.GameObject;
import com.tdcrawl.tdc.objects.fixtures.ObjectFixture;

public class UserDataParser
{
	public static ObjectFixture getObjectFixture(Fixture f)
	{
		if(f.getUserData() instanceof ObjectFixture)
			return (ObjectFixture)f.getUserData();
		else
			return null;
	}
	
	public static GameObject getGameObject(Body b)
	{
		if(b.getUserData() instanceof GameObject)
			return (GameObject)b.getUserData();
		else
			return null;
	}

}

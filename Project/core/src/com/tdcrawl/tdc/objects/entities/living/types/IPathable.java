package com.tdcrawl.tdc.objects.entities.living.types;

import com.tdcrawl.tdc.objects.entities.living.Player;

public interface IPathable 
{
	//going to be used to move the entity in the proper direction / get the new coordinates
	public float[] getPath(Player play);
}

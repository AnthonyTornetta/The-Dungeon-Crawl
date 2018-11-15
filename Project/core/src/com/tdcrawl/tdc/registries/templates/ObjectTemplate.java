package com.tdcrawl.tdc.registries.templates;

import com.tdcrawl.tdc.objects.GameObject;

/**
 * Tells how a given object should be created based off certain parameters
 * The same ObjectTemplate may be used to create multiple different objects with different parameters, so try to avoid having instance variables
 */
public interface ObjectTemplate
{
	/**
	 * Creates a GameObject object based off the given data
	 * @param data The data to base the object off of
	 * @return The uninitialized GameObject or null if there was an error parsing the data
	 */
	public GameObject create(ObjectData data);
}

package com.tdcrawl.tdc.items.items.weapons;

import com.tdcrawl.tdc.items.Item;

//DANIEL
public abstract class Weapon extends Item
{
	private int damage;
	
	public Weapon(int d)
	{
		damage = d;
	}
	
	public int getDamage() {return damage;}
	public void setDamage(int d) {damage = d;}
}

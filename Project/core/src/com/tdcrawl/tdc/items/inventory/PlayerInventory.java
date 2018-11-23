package com.tdcrawl.tdc.items.inventory;

import com.tdcrawl.tdc.items.Item;
import com.tdcrawl.tdc.items.items.healing.HealingItem;
import com.tdcrawl.tdc.items.items.weapons.melee.MeleeWeapon;
import com.tdcrawl.tdc.items.items.weapons.ranged.RangedWeapon;

public class PlayerInventory implements Inventory
{
	private Item[] items;
	
	public PlayerInventory()
	{
		items = new Item[3];
	}
	
	public PlayerInventory(MeleeWeapon m, RangedWeapon r, HealingItem h)
	{
		items = new Item[]{m, r, h};
	}
	
	@Override
	public int getItemCount()
	{
		int count = 0;
		
		for(int i = 0; i < items.length; i++)
		{
			if(items[i] != null)
			{
				count++;
			}
		}
		
		return count;
	}

	@Override
	public Item[] getItems()
	{
		return items;
	}

	@Override
	public void setItems(Item[] items) throws IndexOutOfBoundsException
	{
		this.items = items;
	}

	@Override
	public Item getItemAt(int i)
	{
		return items[i];
	}

	@Override
	public int getIndexOf(Item item)
	{
		for(int i = 0; i < items.length; i++)
		{
			if(item.equals(items[i]))
			{
				return i;
			}
		}
		
		return -1;
	}

	@Override
	public boolean containsItem(Item i)
	{
		return getIndexOf(i) != -1;
	}

	@Override
	public void clear()
	{
		items = new Item[3];
	}

	@Override
	public int size()
	{
		return items.length;
	}

}

package com.tdcrawl.tdc.items.inventory;

import com.tdcrawl.tdc.items.Item;

public interface Inventory
{
	public int getItemCount();
	public Item[] getItems();
	public void setItems(Item[] items);
	public void getItemAt(int i);
	public boolean containsItem(Item i);
	public void clear();
}

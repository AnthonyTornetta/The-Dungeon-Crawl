package com.tdcrawl.tdc.items.inventory;

import com.tdcrawl.tdc.items.Item;

public interface Inventory
{
	/**
	 * Gets how many items are currently in the invenory
	 * @return How many items are currently in the invenory
	 */
	public int getItemCount();
	
	/**
	 * Returns every item in the inventory as an Item array
	 * @return Every item in the inventory as an Item array
	 */
	public Item[] getItems();
	
	/**
	 * Sets the items of the inventory
	 * @throws IndexOutOfBoundsException if the items provided length is outside of the appropriate inventory size
	 * @param items The items to set
	 */
	public void setItems(Item[] items) throws IndexOutOfBoundsException;
	
	/**
	 * Gets an item at the item slot's index
	 * @param i The index to grab it at
	 */
	public Item getItemAt(int i);
	
	/**
	 * Returns the index of an item in the inventory based off slot indexes, or -1 if not present
	 * @param i The item to search for
	 * @return The index of an item in the inventory based off slot indexes, or -1 if not present
	 */
	public int getIndexOf(Item item);
	
	/**
	 * Checks if the inventory contains a given item (regardless of number of items)
	 * @param i The item to check for
	 * @return True if it contains it, false if not
	 */
	public boolean containsItem(Item item);
	
	/**
	 * Removes all items in the inventory
	 */
	public void clear();
	
	/**
	 * Returns how many items the inventory can hold, or -1 for an infinite amouht
	 * @return Returns how many items the inventory can hold, or -1 for an infinite amount
	 */
	public int size();
}

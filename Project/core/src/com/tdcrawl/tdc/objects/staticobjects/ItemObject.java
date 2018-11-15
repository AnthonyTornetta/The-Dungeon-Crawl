package com.tdcrawl.tdc.objects.staticobjects;

import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.tdcrawl.tdc.items.Item;
import com.tdcrawl.tdc.objects.GameObject;
import com.tdcrawl.tdc.objects.fixtures.ObjectFixture;
import com.tdcrawl.tdc.registries.ItemRegistry;
import com.tdcrawl.tdc.registries.templates.ItemData;
import com.tdcrawl.tdc.registries.templates.ItemTemplate;
import com.tdcrawl.tdc.registries.templates.ObjectData;
import com.tdcrawl.tdc.registries.templates.ObjectTemplate;

public class ItemObject extends GameObject
{
	// This is not used once the object has been initialized
	private Vector2 velocity = Vector2.Zero;
	
	/**
	 * Stores the item that this object will hold
	 */
	private Item item;
	
	/**
	 * This constructor may change in the future, so it is kept private to avoid it's use other than in the {@link ItemObject#asObject(Item)} method.
	 * @param i The item to create the item with
	 */
	private ItemObject(Item i)
	{
		super(null, BodyType.DynamicBody, 0, false, false, false, new ObjectFixture(false, 0.7f, 0.0f, 0.0f, createShape(), Vector2.Zero));
		this.item = i;
	}
	
	/**
	 * Creates a new ItemObject that hasn't been initialized to the world yet that stores an item
	 * @param i The item to create it with
	 * @return The newly constructed ItemObject
	 */
	public static ItemObject asObject(Item i)
	{
		return new ItemObject(i);
	}
	
	/**
	 * <p><b>DO NOT CALL UNLESS POSITION HAS BEEN SET!</b></p>
	 * <p>Instead, call {@link ItemObject#init(World, Vector2, Vector2)}</p>
	 */
	@Override
	public void init(World world)
	{
		if(getPosition() == null)
			throw new IllegalStateException("GameObject#init(World) cannot be called on an ItemObject without position being set. The method ItemObject#init(World, Vector2, Vector2) must be used!");
		else
			init(world, getPosition(), getVelocity());
	}
	
	/**
	 * <p><b>CALL THIS INSTEAD OF </b>{@link GameObject#init(World)}</p>
	 * Initializes the object like normal, but with special information the item needs.
	 * @param world The world the item is in
	 * @param position Where to place the item
	 * @param velocity How fast to start the item moving. If no velocity is wanted, use {@code Vector2.Zero} or {@code null}
	 */
	public void init(World world, Vector2 position, Vector2 velocity)
	{
		setPosition(position);
		
		super.init(world);
		
		// "F = ma" used to calculate how much force to apply
		if(velocity != null)
			getBody().applyForceToCenter(velocity.x * getBody().getMass(), velocity.y * getBody().getMass(), true);
	}
	
	/**
	 * Used in the constructor to create the shape
	 * @return The shape to use
	 */
	private static Shape createShape()
	{
		PolygonShape s = new PolygonShape();
		s.setAsBox(0.3f, 0.3f);
		return s;
	}
	
	public static class ItemObjectTemplate implements ObjectTemplate
	{
		@SuppressWarnings("unchecked")
		@Override
		public GameObject create(ObjectData data)
		{
			if(data.extraData != null && data.extraData.containsKey("itemId"))
			{				
				ItemTemplate template = ItemRegistry.getItemTemplate((String) data.extraData.get("itemId"));
				
				ItemData itemData = new ItemData();
				itemData.name = (String) data.extraData.get("itemId");
				
				itemData.extraData = (Map<String, Object>) data.extraData.getOrDefault("itemData", null);
				
				ItemObject obj = asObject(template.create(itemData));
				if(data.position != null)
				{
					obj.setPosition(data.position);
				}
				if(data.extraData.containsKey("velocity"))
					obj.setVelocity((Vector2) data.extraData.get("velocity"));
				
				return obj;
			}
			
			return null;
		}		
	}
	
	// Getters & Setters //
	
	public Item getItem() { return item; }
	public void setItem(Item i) { this.item = i; }
	
	public Vector2 getVelocity() { return this.velocity; }
	public void setVelocity(Vector2 v) { this.velocity = v; }
}

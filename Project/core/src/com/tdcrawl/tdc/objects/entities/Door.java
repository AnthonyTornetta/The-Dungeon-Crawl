package com.tdcrawl.tdc.objects.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.tdcrawl.tdc.objects.GameObject;
import com.tdcrawl.tdc.objects.fixtures.ObjectFixture;
import com.tdcrawl.tdc.registries.templates.ObjectData;
import com.tdcrawl.tdc.registries.templates.ObjectTemplate;
import com.tdcrawl.tdc.util.Helper;
import com.tdcrawl.tdc.util.Reference;

/**
 * @author Cornchip
 */
public class Door extends Entity
{
	private boolean opened = true;
	private boolean locked = false;
	
	private ObjectFixture top, bottom;
	
	private float amtOpen = 0;
	private float openAmount;
	
	private Vector2 dimensions;
	
	public Door(Vector2 position, Vector2 dimensions, float angle, float openAmount)
	{
		super(position, BodyType.KinematicBody, angle, false, true, true, setupCenterFixture());
		
		this.dimensions = dimensions;
		this.openAmount = openAmount;
	}
	
	@Override
	public void init(World world)
	{
		super.init(world);
		
		PolygonShape topDoorShape = new PolygonShape();
		
		topDoorShape.setAsBox(dimensions.x, dimensions.y / 2);
		top = new ObjectFixture(true, 1.0f, 0.1f, 0.6f, topDoorShape, new Vector2(0, dimensions.y / 2));
		
		PolygonShape bottomDoorShape = new PolygonShape();
		
		bottomDoorShape.setAsBox(dimensions.x, dimensions.y / 2);
		bottom = new ObjectFixture(true, 1.0f, 0.1f, 0.6f, bottomDoorShape, new Vector2(0, -dimensions.y / 2));
		
		Helper.addFixture(top, this);
		Helper.addFixture(bottom, this);
	}
	
	@Override
	public void tick(float delta, Camera cam)
	{      
		if(amtOpen < 1e-4f)
			amtOpen = 0;
		
		if(opened)
			open();
		else
			close();
	}
	
	private static ObjectFixture setupCenterFixture()
	{
		CircleShape center = new CircleShape();
		center.setRadius(0.1f);
		return new ObjectFixture(false, 0, 0, 0, center, new Vector2(0, 0));
	}
	
	public void setOpen(boolean open) { opened = open; }
	
	private void close()
	{
		if(!locked && amtOpen > 0)
		{
			amtOpen -= 0.1f;
			Helper.moveShape(top.getShape(), new Vector2(0, -0.1f));
			Helper.moveShape(bottom.getShape(), new Vector2(0, 0.1f));
			
			getBody().setAwake(true);
		}
	}

	private void open()
	{
		if(!locked && amtOpen < openAmount)
		{
			amtOpen += 0.1f;
			Helper.moveShape(top.getShape(), new Vector2(0, 0.1f));
			Helper.moveShape(bottom.getShape(), new Vector2(0, -0.1f));
			
			getBody().setAwake(true);
		}
	}
	
	public static class DoorTemplate implements ObjectTemplate
	{
		@Override
		public GameObject create(ObjectData data)
		{
			Reference.debugLog("" + data.getOrDef("angle", 0), this);
			Door d = new Door(data.position, data.dimensions, data.getOrDef("angle", 0), data.getOrDef("openAmount", 1f));
			d.setOpen(data.getOrDef("opened", false));
			return d;
		}
	}
	
	public boolean isLocked() { return locked; }
	public void setLocked(boolean locked) { this.locked = locked; }
}

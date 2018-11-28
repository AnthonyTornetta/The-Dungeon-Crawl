package com.tdcrawl.tdc.objects.entities.living;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.tdcrawl.tdc.items.Item;
import com.tdcrawl.tdc.items.inventory.PlayerInventory;
import com.tdcrawl.tdc.items.items.weapons.melee.MeleeWeapon;
import com.tdcrawl.tdc.items.items.weapons.melee.Sword;
import com.tdcrawl.tdc.objects.GameObject;
import com.tdcrawl.tdc.objects.bodyparts.Arm;
import com.tdcrawl.tdc.objects.entities.living.types.EntityType;
import com.tdcrawl.tdc.objects.fixtures.ObjectFixture;
import com.tdcrawl.tdc.objects.fixtures.Sensor;
import com.tdcrawl.tdc.registries.templates.ObjectData;
import com.tdcrawl.tdc.registries.templates.ObjectTemplate;
import com.tdcrawl.tdc.util.Helper;
import com.tdcrawl.tdc.util.Reference;
import com.tdcrawl.tdc.util.UserDataParser;

public class Player extends LivingEntity
{
	private final float ACCELERATION = 10.0f; // x m/s^2
	private final float JUMP_STRENGTH = 10.5f; // acceleration applied when jump occurs
	private final int JUMPS_ALLOWED_IN_AIR = 1;
	private final float TIME_BETWEEN_JUMPS = 0.4f;
	
	private Vector2 startPos;
	
	private int inAirJumps = 0;
	private float timeSinceLastJump = 0;
	
	private static final float height = 0.45f;
	private static final float headRadius = 0.2f;
	private static final float width = 0.2f;
	
	private Arm arm;
	
	private static final Vector2 ARM_OFFSET = new Vector2(0f, height - 0.2f);
	
	private int numFootContacts = 0;
	
	private PlayerInventory inventory;
	private Item heldItem;
	private Sensor itemSensor;
	
	private float timeSinceSwing = 0.0f;
	private static final float SWING_STRENGTH = 0.25f;
	private static final float TIME_BETWEEN_SWINGS = 0.3f;
	
	private ShapeRenderer sr = new ShapeRenderer();
	
	private boolean flying = false, noclip = false; // I did this to test stuff dan, dont worry about it (press f or c to activate them respectively)
	
	public Player(Vector2 position, int maxHealth)
	{
		super(position, BodyType.DynamicBody, 0, true, true, true, createCenterFixture(), maxHealth);
		
		CircleShape headShape = new CircleShape();
		headShape.setRadius(headRadius);
		ObjectFixture head = new ObjectFixture(true, 1.5f, 0.1f, 0.05f, headShape, new Vector2(0, height + headRadius));
		
		this.addFixture(head);
		
		inventory = new PlayerInventory(new Sword(), null, null);
	}
	
	@Override
	public void init(World world)
	{
		super.init(world);
		
		PolygonShape armShape = new PolygonShape();
		armShape.setAsBox(0.3f, 0.08f, new Vector2(0.25f, 0), 0.0f); // MAKE THE 0.25f NEGATIVE WHEN LEFT
		arm = new Arm(armShape, getPosition(), BodyType.DynamicBody, getDensity(), 0, 0, 0, false, true, false);
		
		arm.init(world);
		arm.attatch(this, ARM_OFFSET);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width - 0.05f, 0.05f);
		
		Sensor footSensor = new Sensor(shape, new Vector2(0,  -height))
		{
			@Override
			public void onCollide(GameObject other, ObjectFixture fixture)
			{
				numFootContacts++;
			}
			
			@Override
			public void onUncollide(GameObject other, ObjectFixture fixture)
			{
				numFootContacts--;
			}
		};
		
		footSensor.init(getBody());

		startPos = Helper.clone(getPosition());
		
		switchItem(inventory.getItems()[0], true);
	}
	
	/**
	 * Just for use in the constructor to make the center fixture
	 * @return The center fixture
	 */
	private static ObjectFixture createCenterFixture()
	{
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width, height);
		
		ObjectFixture centerFixture = new ObjectFixture(true, 6.0f, 0.0f, 0.2f, shape, new Vector2(0, 0));
		return centerFixture;
	}
	
	@Override
	public void tick(float delta, Camera cam)
	{
		timeSinceLastJump += delta;
		timeSinceSwing += delta;
		
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && timeSinceSwing > TIME_BETWEEN_SWINGS)
		{
			timeSinceSwing = 0.0f;
		}
		
		if(timeSinceSwing < TIME_BETWEEN_SWINGS / 5)
		{
			arm.setAngle(arm.getAngle() + SWING_STRENGTH);
		}
		else if(timeSinceSwing <= TIME_BETWEEN_SWINGS * 9 / 10)
		{
			arm.setAngle(arm.getAngle() - SWING_STRENGTH);
		}
		
		int mX = Gdx.input.getX();
		int mY = Gdx.input.getY();
		
		Vector2 mouseCoordsInMeters = new Vector2(mX, mY);
		mouseCoordsInMeters = Helper.screenCoordinatesToMeters(mouseCoordsInMeters, cam);
		
		if(mX >= 0 && mX < Gdx.graphics.getWidth() && mY >= 0 && mY < Gdx.graphics.getHeight())
		{
			if(timeSinceSwing > TIME_BETWEEN_SWINGS * 9 / 10)
			{
				arm.setAngle(Helper.angleTo(getPosition().cpy().add(ARM_OFFSET).add(ARM_OFFSET), mouseCoordsInMeters, ARM_OFFSET));
			}
		}
		
		// Draws a fancy line to ur cursor based off where ur arm starts
		if(Reference.isDebug() && cam != null)
		{
	        sr.setColor(Color.RED);
	        sr.setProjectionMatrix(cam.combined);
	
	        sr.begin(ShapeType.Line);
	        sr.line(getPosition().x + ARM_OFFSET.x, getPosition().y + ARM_OFFSET.y,
	        		mouseCoordsInMeters.x, mouseCoordsInMeters.y);
	        
	        sr.end();
		}
		
		// Resets you to where the player started
		if(Reference.isDebug())
		{
			if(Gdx.input.isKeyPressed(Input.Keys.R))
			{
				setPosition(startPos);
				getBody().setLinearVelocity(0, 0);
			}
			
			if(Gdx.input.isKeyJustPressed(Input.Keys.F))
			{
				flying = !flying;
			}
			
			if(Gdx.input.isKeyJustPressed(Input.Keys.C)) // WARNING: Once this is turned on then off again, sensors and anything else that was set to no collide WILL become collidable.
				// And I really dont feel the need to create a list of noncollidable fixtures and check them when undoing the noclip.
			{
				noclip = !noclip;
				
				for(Fixture f : getBody().getFixtureList())
				{
				    if(!(UserDataParser.getObjectFixture(f) instanceof Sensor))
				    {
				        f.setSensor(noclip);
				    }
				}
			}
		}
		
		Vector2 acceleration = new Vector2();
		
		acceleration.x = Helper.toInt(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT));
		acceleration.x -= Helper.toInt(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT));
		
		acceleration.x *= ACCELERATION * delta;
		
		if(this.isOnGround())
			inAirJumps = 0;
		
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Keys.W))
		{			
			if(timeSinceLastJump >= TIME_BETWEEN_JUMPS)
			{
				if(flying || inAirJumps < JUMPS_ALLOWED_IN_AIR || this.isOnGround())
				{
					if(!this.isOnGround())
					{
						inAirJumps++;
					}
					
					timeSinceLastJump = 0;
					acceleration.y += JUMP_STRENGTH;
				}
			}
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.NUM_1))
		{
			switchItem(inventory.getItems()[0], false);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.NUM_2))
		{
			switchItem(inventory.getItems()[1], false);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.NUM_3))
		{
			switchItem(inventory.getItems()[2], false);
		}
		
		if(arm.getAngle() > Math.PI / 2 && arm.getAngle() < Math.PI * 2 / 3)
		{
			switchItem(heldItem, false);
		}
			
		getBody().setLinearVelocity(getBody().getLinearVelocity().add(acceleration));
	}
	
	private void switchItem(Item newItem, boolean initial)
	{
		heldItem = newItem;
		float yoffset = 0.0f;
		float xoffset = 0.0f;
		
		if(!initial)
		{	
			Helper.removeFixture(itemSensor);
		}
		
		PolygonShape item = new PolygonShape();
		if(heldItem == null)
		{
			item.setAsBox(0.1f, -0.1f);
			xoffset = 0.05f;
			yoffset = -0.05f;
		}
		else
		{
			item.setAsBox(heldItem.getDimensions().x, heldItem.getDimensions().y);
			xoffset = heldItem.getDimensions().x / 2;
			yoffset = -heldItem.getDimensions().y / 2;
			
			if(arm.getAngle() > Math.PI / 2 && arm.getAngle() < Math.PI * 2 / 3)
			{
				yoffset = Byte.decode((Byte.decode(yoffset + "").toString().charAt(0) == '1' ? "0" : "1") + Byte.decode(yoffset + "").toString().substring(1, Byte.decode(yoffset + "").toString().length())).floatValue();
			}
		}
		
		itemSensor = new Sensor(item, new Vector2(0.5f + xoffset, 0.2f + yoffset))
		{
			
			@Override
			public void onCollide(GameObject other, ObjectFixture fixture)
			{
				if(other instanceof LivingEntity)
				{
					System.out.println("Hit");
					if(heldItem instanceof MeleeWeapon)
					{
						((LivingEntity) other).takeDamage(((MeleeWeapon) heldItem).getDamage());
					}
					else
					{
						((LivingEntity) other).takeDamage(1);
					}
				}

			}
			
			@Override
			public void onUncollide(GameObject other, ObjectFixture fixture)
			{
				//Nothing to do here
			}
		};
		
		
		Helper.addFixture(itemSensor, arm);
	}

	@Override
	public boolean die()
	{
		return false;
	}

	@Override
	public boolean invulnerable()
	{
		return false;
	}
	
	public static class PlayerTemplate implements ObjectTemplate
	{
		@Override
		public GameObject create(ObjectData data)
		{
			return new Player(data.position, 20);
		}
	}
	
	@Override
	public boolean isOnGround()
	{
		return numFootContacts >= 1;
	}
	
	@Override
	public EntityType getEntityType() { return EntityType.PLAYER; }
}

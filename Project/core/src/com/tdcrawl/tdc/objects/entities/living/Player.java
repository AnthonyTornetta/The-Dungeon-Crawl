package com.tdcrawl.tdc.objects.entities.living;

import java.util.ArrayList;

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
import com.tdcrawl.tdc.items.items.weapons.Weapon;
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
	private boolean facingLeft = false;
	
	private static final Vector2 ARM_OFFSET = new Vector2(0f, height - 0.2f);
	
	private int numFootContacts = 0;
	
	private PlayerInventory inventory;
	private Item heldItem;
	private Sensor itemSensor;
	
	private float timeSinceSwing = 0.0f;
	private static final float SWING_STRENGTH = 0.25f;
	private static final float TIME_BETWEEN_SWINGS = 0.3f;
	private ArrayList<LivingEntity> alreadyHit = new ArrayList<LivingEntity>();
	
	private ShapeRenderer sr = new ShapeRenderer();
	
	private boolean flying = false, noclip = false;
	
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
		
		switchItem(inventory.getItems()[0], true, false);
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
		
		//DANIEL'S CODE
		//If the player clicks, start a swing.
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && timeSinceSwing > TIME_BETWEEN_SWINGS)
		{
			timeSinceSwing = 0.0f;
		}
		
		//Angle needed for proper swing mechanic is inverted if the player is facing left.
		if(facingLeft)
		{
			//For the first fifth of the swing time, pull arm upwards.
			if(timeSinceSwing < TIME_BETWEEN_SWINGS / 5)
			{
				arm.setAngle(arm.getAngle() - SWING_STRENGTH);
			}
			//For the rest of the swing, push arm downwards.
			else if(timeSinceSwing <= TIME_BETWEEN_SWINGS * 9 / 10)
			{
				arm.setAngle(arm.getAngle() + SWING_STRENGTH);
			}
		}
		else
		{
			//For the first fifth of the swing time, pull arm upwards.
			if(timeSinceSwing < TIME_BETWEEN_SWINGS / 5)
			{
				arm.setAngle(arm.getAngle() + SWING_STRENGTH);
			}
			//For the rest of the swing, push arm downwards.
			else if(timeSinceSwing <= TIME_BETWEEN_SWINGS * 9 / 10)
			{
				arm.setAngle(arm.getAngle() - SWING_STRENGTH);
			}
		}
		//END OF DANIEL'S CODE
		
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
		
		//DANIEL'S CODE
		//Switches items if the player presses "1", "2", or "3"
		if(Gdx.input.isKeyPressed(Input.Keys.NUM_1))
		{
			switchItem(inventory.getItems()[0], false, facingLeft);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.NUM_2))
		{
			switchItem(inventory.getItems()[1], false, facingLeft);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.NUM_3))
		{
			switchItem(inventory.getItems()[2], false, facingLeft);
		}
		
		//Switches items in order to correctly orient the item if the player rotates left or right.
		if(Math.abs(arm.getAngle()) > Math.PI / 2 && Math.abs(arm.getAngle()) < Math.PI * 3 / 2 && !facingLeft && timeSinceSwing > TIME_BETWEEN_SWINGS * 9 / 10)
		{
			switchItem(heldItem, false, true);
		}
		
		if((Math.abs(arm.getAngle()) < Math.PI / 2 || Math.abs(arm.getAngle()) > Math.PI * 3 / 2) && facingLeft && timeSinceSwing > TIME_BETWEEN_SWINGS * 9 / 10)
		{
			switchItem(heldItem, false, false);
		}
			
		getBody().setLinearVelocity(getBody().getLinearVelocity().add(acceleration));
	}
	
	//Called when anything causes the item in the player's hand to be reloaded.
	//(Switching to a new item, rotating the arm, loading a level.)
	private void switchItem(Item newItem, boolean initial, boolean left)
	{
		heldItem = newItem;
		
		//Difference of coordinates to center the item in the player's hand.
		float yoffset = 0.0f;
		float xoffset = 0.0f;
		
		//Removes the old item from the world.
		if(!initial)
		{	
			Helper.removeFixture(itemSensor);
		}
		
		//Defines the shape and size of the new item on the screen.
		PolygonShape item = new PolygonShape();
		if(heldItem == null)
		{
			//Setting the new item to the player's hand.
			item.setAsBox(0.1f, 0.1f);
			xoffset = 0.05f;
			yoffset = -0.17f;
			facingLeft = false;
			
			//If the method was called to flip the arm to the left.
			if(left)
			{
				facingLeft = true;
			}	
		}
		else
		{
			//Setting the new item to the size and offset of the item selected.
			item.setAsBox(heldItem.getDimensions().x, heldItem.getDimensions().y);
			xoffset = heldItem.getDimensions().x / 2;
			yoffset = heldItem.getDimensions().y / 2;
			facingLeft = false;
			
			//If the method was called to flip the arm to the left.
			if(left)
			{
				//Flip the item to face downwards.
				yoffset = -2 * yoffset;
				facingLeft = true;
			}
		}

	
		//Defining a sensor for collision detection, with the size and coordinates of the item.
		itemSensor = new Sensor(item, new Vector2(0.5f + xoffset, 0.2f + yoffset))
		{
			
			@Override
			public void onCollide(GameObject other, ObjectFixture fixture)
			{
				//If the item hits a living entity, the player is swinging, and the entity has not been hit yet this swing.
				if(other instanceof LivingEntity && timeSinceSwing < TIME_BETWEEN_SWINGS * 9 / 10 && !alreadyHit.contains(other))
				{
					System.out.print("Hit for ");
					if(heldItem instanceof MeleeWeapon)
					{
						//Applying the proper damage based on the weapon used.
						((LivingEntity) other).takeDamage(((MeleeWeapon) heldItem).getDamage());
						System.out.print(((Weapon) heldItem).getDamage());
					}
					else
					{
						//Applying one damage if the player is hitting with something other than a melee weapon.
						((LivingEntity) other).takeDamage(1);
						System.out.print("1");
					}
					System.out.println(" damage.");
					alreadyHit.add((LivingEntity) other);
				}

			}
			
			@Override
			public void onUncollide(GameObject other, ObjectFixture fixture)
			{
				//Set the item to be able to hit any living entity on the next swing.
				alreadyHit.clear();
			}
		};
		
		//Add the new item to the world, attached to the player's arm.
		Helper.addFixture(itemSensor, arm);
	}
	//END OF DANIEL'S CODE.

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

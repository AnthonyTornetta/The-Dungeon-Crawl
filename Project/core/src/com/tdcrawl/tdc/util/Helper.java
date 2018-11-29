package com.tdcrawl.tdc.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.tdcrawl.tdc.events.EventsHandler;
import com.tdcrawl.tdc.events.types.WorldLockChangeEvent;
import com.tdcrawl.tdc.joints.ObjectJoint;
import com.tdcrawl.tdc.objects.GameObject;
import com.tdcrawl.tdc.objects.fixtures.ObjectFixture;

public final class Helper
{
	public static final float PX_TO_M = 32.0f; // 32 pixels for every meter
	
	private static boolean worldLocked = false;
	
	private static List<ObjectFixture> fixturesToRemove = new ArrayList<>();
	private static Map<ObjectFixture, GameObject> fixturesToAdd = new HashMap<>();
	
	private static List<ObjectJoint> jointsToRemove = new ArrayList<>();
	private static List<ObjectJoint> jointsToAdd = new ArrayList<>();
	
	private static List<GameObject> objectsToRemove = new ArrayList<>();
	private static Map<GameObject, World> objectsToAdd = new HashMap<>();
	
	/**
	 * Moves a shape based on the amount of change (it just adds its position to the change amount)
	 * @param s The shape to move
	 * @param change The amount to move it by
	 */
	public static void moveShape(Shape s, Vector2 change)
	{
		if(s instanceof CircleShape)
		{
			CircleShape shape = (CircleShape)s;
			shape.setPosition(shape.getPosition().add(change)); // That was easy
		}
		else if(s instanceof PolygonShape)
		{
			PolygonShape shape = (PolygonShape)s;
			
			int vertexAmt = shape.getVertexCount();
			Vector2[] vertexes = new Vector2[vertexAmt];
			
			for(int i = 0; i < vertexAmt; i++)
			{
				Vector2 vertex = new Vector2();
				shape.getVertex(i, vertex); // Updates the vertex to the actual vertex
				vertexes[i] = vertex.add(change);
			}
			
			shape.set(vertexes);
		}
		else if(s instanceof ChainShape)
		{
			ChainShape shape = (ChainShape)s;
			
			boolean isLooped = shape.isLooped();
			
			int vertexAmt = shape.getVertexCount();
			Vector2[] vertexes = new Vector2[vertexAmt];
			
			for(int i = 0; i < vertexAmt; i++)
			{
				Vector2 vertex = new Vector2();
				shape.getVertex(i, vertex); // Updates the vertex to the actual vertex
				vertexes[i] = vertex.add(change);
			}
			
			// The only way to move this is to make a new one
			shape = new ChainShape();
			if(isLooped)
				shape.createLoop(vertexes);
			else
				shape.createChain(vertexes);
		}
		else if(s instanceof EdgeShape)
		{
			EdgeShape shape = (EdgeShape)s;
			
			Vector2 temp = new Vector2();
			
			shape.getVertex0(temp);
			temp.add(change);
			shape.setVertex0(temp);
			
			shape.getVertex3(temp);
			temp.add(change);
			shape.setVertex3(temp);
		}
	}
	
	public static Vector2 clamp(Vector2 vec, float xMin, float yMin, float xMax, float yMax)
	{
		if(vec.x < xMin)
			vec.x = xMin;
		else if(vec.x > xMax)
			vec.x = xMax;
		if(vec.y < yMin)
			vec.y = yMin;
		else if(vec.y > yMax)
			vec.y = yMax;
		return vec;
	}
	
	public static float randomizer(float baseValue, float range)
	{
		float value = (float) (Math.random() * range + baseValue);
		return value;
	}
	
	public static Vector2 clamp(Vector2 vec, Vector2 min, Vector2 max)
	{
		return clamp(vec, min.x, min.y, max.x, max.y);
	}
	
	public static double clamp(double var, double min, double max)
	{
		if(var < min) return min;
		if(var > max) return max;
		return var;
	}
	
	public static double clamp(float var, float min, float max)
	{
		if(var < min) return min;
		if(var > max) return max;
		return var;
	}
	
	public static double clamp(int var, int min, int max)
	{
		if(var < min) return min;
		if(var > max) return max;
		return var;
	}
	
	public static float pxToM(float px)
	{
		return px / PX_TO_M;
	}
	
	public static float mToPx(float m)
	{
		return m * PX_TO_M;
	}
	
	public static int toInt(boolean b)
	{
		return b ? 1 : 0;
	}
	
	public static Vector2 clone(Vector2 c)
	{
		return new Vector2(c.x, c.y);
	}
	
	public static float angleTo(float x1, float y1, float x2, float y2)
	{
		return angleTo(new Vector2(x1, y1), new Vector2(x2, y2));
	}
	
	public static float angleTo(Vector2 x, Vector2 y)
	{
		return angleTo(x, y, Vector2.Zero);
	}
	
	public static float angleTo(Vector2 x, Vector2 y, Vector2 offset)
	{
		return new Vector2(y).sub(new Vector2(x)).add(offset).angleRad();
	}

	public static void removeFixture(ObjectFixture f)
	{
		if(isWorldLocked())
			fixturesToRemove.add(f);
		else
			f.remove();
	}
	
	public static void addFixture(ObjectFixture f, GameObject gameObject)
	{
		if(isWorldLocked())
			fixturesToAdd.put(f, gameObject);
		else
			f.init(gameObject.getBody());
	}
	
	public static void removeJoint(ObjectJoint joint)
	{
		if(isWorldLocked())
			jointsToRemove.add(joint);
		else
			joint.detatch();
	}

	public static void addJoint(ObjectJoint joint) 
	{
		if(isWorldLocked())
			jointsToAdd.add(joint);
		else
			joint.attach();
	}
	
	public static void addObject(GameObject obj, World world)
	{
		if(isWorldLocked())
			objectsToAdd.put(obj, world);
		else
			obj.init(world);
	}
	
	public static void removeObject(GameObject obj)
	{
		if(isWorldLocked())
			objectsToRemove.add(obj);
		else
			obj.getWorld().destroyBody(obj.getBody());
	}
	
	public static void cleanup()
	{
		if(!isWorldLocked())
		{
			while(fixturesToRemove.size() != 0)
			{
				ObjectFixture f = fixturesToRemove.remove(fixturesToRemove.size() - 1);
				if(fixturesToAdd.containsKey(f))
					fixturesToAdd.remove(f);
				else
					removeFixture(f);
			}
			
			for(ObjectFixture f : fixturesToAdd.keySet())
			{
				addFixture(f, fixturesToAdd.get(f));
			}
			
			fixturesToAdd.clear();
			
			while(jointsToAdd.size() != 0)
				addJoint(jointsToAdd.remove(jointsToAdd.size() - 1));
			while(jointsToRemove.size() != 0)
				removeJoint(jointsToRemove.remove(jointsToRemove.size() - 1));
			
			for(GameObject obj : objectsToAdd.keySet())
				addObject(obj, objectsToAdd.get(obj));
			
			objectsToAdd.clear();
			
			while(objectsToRemove.size() != 0)
				removeObject(objectsToRemove.get(objectsToRemove.size() - 1));
		}
		else
			throw new IllegalStateException("cleanup cannot be called when world is locked!");
	}
	
	public static Vector2 screenCoordinatesToMeters(Vector2 coords, Camera cam)
	{
		Vector3 v3 = new Vector3(coords, 0);
		cam.unproject(v3);
		return new Vector2(v3.x, v3.y);
	}
	
	public static void setWorldLocked(boolean b)
	{
		worldLocked = b;
		EventsHandler.call(new WorldLockChangeEvent(isWorldLocked()));
	}
	
	public static boolean isWorldLocked() { return worldLocked; }

	/**
	 * Returns a fancy String for those objects not kind enough to create their on toString() method
	 * @param object The object to stringify (no properties will be changed)
	 * @return A super fancy String descringing that object, but if none can be generated will just use that object's toString
	 */
	public static String toString(Object object)
	{
		if(object instanceof Fixture)
		{
			Fixture f = (Fixture)object;
			
			return "Fixture [density=" + f.getDensity() + ", friction=" + f.getFriction() + ", restitution=" + f.getRestitution() + ", shape=" + toString(f.getShape()) + "]";
		}
		else if(object instanceof Body)
		{
			Body b = (Body)object;
			
			return "Body [position=" + b.getPosition() + ", mass=" + b.getMass() + ", velocity=" + b.getLinearVelocity() + ", type=" + b.getType() + ", localCenter=" + b.getLocalCenter() + "]";
		}
		
		return object != null ? object.toString() : "null";
	}

	public static void flipX(PolygonShape armShape)
	{
		int len = armShape.getVertexCount();
		Vector2[] vertexes = new Vector2[len];
		
		for(int i = 0; i < len; i++)
		{
			Vector2 vertex = new Vector2();
			armShape.getVertex(i, vertex);
			vertex.x = -vertex.x;
			vertexes[i] = vertex;
		}
		
		armShape.set(vertexes);
	}
}

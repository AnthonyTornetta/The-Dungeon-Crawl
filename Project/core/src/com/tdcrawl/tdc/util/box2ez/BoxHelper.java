package com.tdcrawl.tdc.util.box2ez;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.tdcrawl.tdc.events.EventsHandler;
import com.tdcrawl.tdc.events.types.WorldLockChangeEvent;
import com.tdcrawl.tdc.joints.ObjectJoint;
import com.tdcrawl.tdc.objects.GameObject;
import com.tdcrawl.tdc.objects.fixtures.ObjectFixture;

public class BoxHelper
{
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
		{
			objectsToRemove.add(obj);
		}
		else if(obj.getBody() != null)
		{
			obj.getWorld().destroyBody(obj.getBody());
			obj.setBody(null);
		}
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
			{
				GameObject obj = objectsToRemove.remove(objectsToRemove.size() - 1);
				removeObject(obj);
			}
		}
		else
			throw new IllegalStateException("cleanup cannot be called when world is locked!");
	}
	
	public static void setWorldLocked(boolean b)
	{
		worldLocked = b;
		EventsHandler.call(new WorldLockChangeEvent(isWorldLocked()));
	}
	
	public static boolean isWorldLocked() { return worldLocked; }
}

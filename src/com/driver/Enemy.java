package com.driver;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.sjsu.physics.collisiondetection.FineCollision;
import com.sjsu.physics.core.Contact;
import com.sjsu.physics.shapes.PolyBody;
import com.sjsu.physics.shapes.Polygon;
import com.sjsu.physics.shapes.RigidBody;
import com.sjsu.physics.utils.Vector2;

import android.util.Log;

/**
 * 
 * @author John Linford
 * 
 *	Enemy class that will 'attack' the player
 *	and player can destroy them by hitting them (thus taking dmg)
 *	or by shooting them..
 *
 */
public class Enemy extends Sprite implements GameObject
{
	private int HEALTH = 10;
	//private static final float VELOCITY = 3;
	private static final int DAMAGE = 10;

	private DriverActivity activity;
	private PolyBody body;

	public Enemy(final float pX, final float pY, final TiledTextureRegion pTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager,
			final DriverActivity a, final int vx, final int vy)
	{
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		activity = a;
		
		this.setRotation(-90);

		int w = (int) this.getHeight() / 2;
		int h = (int) this.getWidth() / 2;
		
		int[] xpoints = { -w, w, w, -w };
		int[] ypoints = { -h, -h, h, h };

		Polygon p = new Polygon(xpoints, ypoints, 4);
		Vector2 loc = new Vector2(pX, pY);
		body = new PolyBody(p, loc);
		body.setVelocity(vx * 15, vy * 15);
		body.setMass(50);
		body.setAngularDamping(.8f);
		body.setAcceleration(new Vector2(1, 0));
		
		setUserData(body);
		
//		body.rotateBy((float)Math.PI / 2);
//		this.setRotationCenter(body.center().x(), body.center().y());
//		this.setRotation(100);
		
		// add to world
		a.getPhysicsWorld().addBodyToWorld(body);
	}

	@Override
	public void onManagedUpdate(final float pSecondsElapsed) 
	{
		this.setPosition(body.center().x(), body.center().y());
		this.setRotation((float) ((body.orientation() * 190 / Math.PI) - 90));
		
		if (body == null || body.center().x() > activity.CAMERA_WIDTH || body.center().y() > activity.CAMERA_HEIGHT ||
				body.center().x() < 0 || body.center().y() < 0 )
		{
			this.dealDamage(HEALTH + 1);
			return;
		}
		
		// if we go out of bounds set health to -1 so it is removed next game loop without explosion
		if (this.getX() > activity.CAMERA_WIDTH || this.getX() < 0 || this.getY() > activity.CAMERA_HEIGHT ||
				this.getY() < 0)
		{
			this.dealDamage(HEALTH + 1);
			return;
		}

		// check for collisions with player
		if (this.collidesWith(activity.getPlayer()) && !activity.getPlayer().getImmune())
		{
			Log.i("Driver", "Player-Enemy collision");
//			activity.getPlayer().dealDamage(DAMAGE);
			
			// create contact
			Contact c = FineCollision.getContactPoints(activity.getPlayer().getBody(), (RigidBody) body);
			if (c != null)
			{
				// dampen the impact
				body.setDamping(.8f);
				activity.getPhysicsWorld().addContact(c);
			}
			
			// add explosion animation, in same direction as car was going
//			final FixtureDef objectFixtureDef = PhysicsFactory.createFixtureDef(1f, 0f, 0f, true);
//			AnimatedSprite explosion = new AnimatedSprite(this.getX(), this.getY(), activity.explosionTextureRegion, activity.getVertexBufferObjectManager());
//			Body b = PhysicsFactory.createBoxBody(activity.getPhysicsWorld(), explosion, BodyType.KinematicBody, objectFixtureDef);
//			explosion.setUserData(b);
//			explosion.animate(30);
//			activity.getPhysicsWorld().registerPhysicsConnector(new PhysicsConnector(explosion, b, true, true));
//			b.setLinearVelocity(8, 0);
//			activity.getScene().attachChild(explosion);
//			// add another explosion to make it look more real
//			AnimatedSprite explosion2 = new AnimatedSprite(this.getX() + 15, this.getY(), activity.explosionTextureRegion, activity.getVertexBufferObjectManager());
//			Body b2 = PhysicsFactory.createBoxBody(activity.getPhysicsWorld(), explosion2, BodyType.KinematicBody, objectFixtureDef);
//			explosion2.setUserData(b2);
//			explosion2.animate(40);
//			activity.getPhysicsWorld().registerPhysicsConnector(new PhysicsConnector(explosion2, b2, true, true));
//			b2.setLinearVelocity(8, 0);
//			activity.getScene().attachChild(explosion2);
		}
		
		// check for collisions with boss
		if (activity.level == 1 && activity.spawner.boss != null && this.collidesWith(activity.spawner.boss))
		{
			Log.i("Driver", "Boss - Enemy collision");
			HEALTH = 0;
			
//			// add explosion animation, in same direction as car was going
//			final FixtureDef objectFixtureDef = PhysicsFactory.createFixtureDef(1f, 0f, 0f, true);
//			AnimatedSprite explosion = new AnimatedSprite(this.getX(), this.getY(), activity.explosionTextureRegion, activity.getVertexBufferObjectManager());
//			Body b = PhysicsFactory.createBoxBody(activity.getPhysicsWorld(), explosion, BodyType.KinematicBody, objectFixtureDef);
//			explosion.setUserData(b);
//			explosion.animate(30);
//			activity.getPhysicsWorld().registerPhysicsConnector(new PhysicsConnector(explosion, b, true, true));
//			b.setLinearVelocity(8, 0);
//			activity.getScene().attachChild(explosion);
//			// add another explosion to make it look more real
//			AnimatedSprite explosion2 = new AnimatedSprite(this.getX() + 15, this.getY(), activity.explosionTextureRegion, activity.getVertexBufferObjectManager());
//			Body b2 = PhysicsFactory.createBoxBody(activity.getPhysicsWorld(), explosion2, BodyType.KinematicBody, objectFixtureDef);
//			explosion2.setUserData(b2);
//			explosion2.animate(40);
//			activity.getPhysicsWorld().registerPhysicsConnector(new PhysicsConnector(explosion2, b2, true, true));
//			b2.setLinearVelocity(8, 0);
//			activity.getScene().attachChild(explosion2);
		}
	}
	
	public void rotate(float deg)
	{
		body.rotateBy((float) (deg * (Math.PI / 190)));
		this.setRotation(deg);
	}
	
	
	//---------------------//
	// Getters and Setters //
	//---------------------//
	public void dealDamage(int damage)
	{
		HEALTH -= damage;
	}
	public int getDamage()
	{
		return DAMAGE;
	}
	public int getHealth() 
	{
		return HEALTH;
	}

	public RigidBody getBody() 
	{
		return body;
	}
}

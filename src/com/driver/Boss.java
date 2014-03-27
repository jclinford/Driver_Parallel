package com.driver;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

import com.sjsu.physics.shapes.PolyBody;
import com.sjsu.physics.shapes.Polygon;
import com.sjsu.physics.utils.Vector2;


public class Boss extends Sprite implements GameObject
{
	private int HEALTH = 10;
	private static final int DAMAGE = 10;

	private DriverActivity activity;
	private PolyBody body;

	public Boss(final float pX, final float pY, final TiledTextureRegion pTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager,
			final DriverActivity a, final int vx, final int vy)
	{
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		activity = a;

		int[] xpoints = { -100, 100, 100, -100 };
		int[] ypoints = { -40, -40, 40, 40 };

		Polygon p = new Polygon(xpoints, ypoints, 4);
		Vector2 loc = new Vector2(pX, pY);
		body = new PolyBody(p, loc);
		body.setVelocity(vx * 5, vy * 5);
		
		// rotate body to face north                                     
//		this.setRotation((float)(3.14 / 2));
		this.setRotation(90);

		
		setUserData(body);
		
		// add to world
		a.getPhysicsWorld().addBodyToWorld(body);
	}

	@Override
	public void onManagedUpdate(final float pSecondsElapsed) 
	{
		this.setPosition(body.center().x(), body.center().y());
		
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
			activity.getPlayer().dealDamage(DAMAGE);
			this.dealDamage(HEALTH + 1);
//			// add explosion animation, in same direction as car was going
////			final FixtureDef objectFixtureDef = PhysicsFactory.createFixtureDef(1f, 0f, 0f, true);
//			AnimatedSprite explosion = new AnimatedSprite(this.getX(), this.getY(), activity.explosionTextureRegion, activity.getVertexBufferObjectManager());
////			Body b = PhysicsFactory.createBoxBody(activity.getPhysicsWorld(), explosion, BodyType.KinematicBody, objectFixtureDef);
////			explosion.setUserData(b);
//			explosion.animate(30);
////			activity.getPhysicsWorld().registerPhysicsConnector(new PhysicsConnector(explosion, b, true, true));
////			b.setLinearVelocity(8, 0);
//			activity.getScene().attachChild(explosion);
//			// add another explosion to make it look more real
//			AnimatedSprite explosion2 = new AnimatedSprite(this.getX() + 15, this.getY(), activity.explosionTextureRegion, activity.getVertexBufferObjectManager());
////			Body b2 = PhysicsFactory.createBoxBody(activity.getPhysicsWorld(), explosion2, BodyType.KinematicBody, objectFixtureDef);
////			explosion2.setUserData(b2);
//			explosion2.animate(40);
////			activity.getPhysicsWorld().registerPhysicsConnector(new PhysicsConnector(explosion2, b2, true, true));
////			b2.setLinearVelocity(8, 0);
//			activity.getScene().attachChild(explosion2);
		}
		
		// TODO check for collision against the wall
	}
	
	public void rotate(float rad)
	{
		body.rotateBy(rad);
		this.setRotation(rad);
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

	public PolyBody getBody() 
	{
		return body;
	}

}

package com.driver;

import org.andengine.entity.IEntity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

import com.sjsu.physics.shapes.Circle;
import com.sjsu.physics.shapes.RigidBody;
import com.sjsu.physics.utils.Vector2;

/**
 * 
 * @author John Linford
 * 
 *	Power up class can create three different power ups
 *	depending on the type parameter 
 *	0 = kill all enemies
 *	1 = heal player
 *	2 = temp immune
 *
 */
public class PowerUp extends Sprite implements GameObject
{
	private int HEALTH = 1;
	private static final int DAMAGE = 10;

	private int type;

	private DriverActivity activity;
	private Circle body;
	

	public PowerUp(final float pX, final float pY, final TiledTextureRegion pTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager,
			final DriverActivity a, final int vx, final int vy, final int t)
	{
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		activity = a;
		type = t;

		Vector2 loc = new Vector2(pX, pY);
		body = new Circle(loc, 10);

		// rotate body to face north          
//		this.setRotation((float)(3.14 / 2));


		setUserData(body);

		body.setVelocity(vx * 5, vy * 5);
	}

	@Override
	public void onManagedUpdate(final float pSecondsElapsed) 
	{
		this.setPosition(body.center().x() + (.3f), 
				body.center().y());
		body.setCenter(this.mX, this.mY);
		
		// check for collisions with player
		if (this.collidesWith(activity.getPlayer()))
		{
			Log.i("Driver", "Power up collected");

			// kill all enemies
			if (type == 0)
			{
				for (int i = 0; i < activity.getScene().getChildCount(); i++)
				{
					try
					{
						GameObject obj = (GameObject) activity.getScene().getChildByIndex(i);

						// deal damage to all obj's to remove all
						if (obj != (IEntity) activity.getPlayer())
							obj.dealDamage(10);
					}
					catch (Exception e){}
				}
			}
			else if (type == 1)
			{
				// deal negative damage to heal player, then destroy the object
				activity.getPlayer().dealDamage(-10);
				this.dealDamage(HEALTH + 1);
			}
			else if (type == 2)
			{
				activity.getPlayer().setImmune(true);
				this.dealDamage(HEALTH + 1);
			}
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

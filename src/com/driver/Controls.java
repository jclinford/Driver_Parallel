package com.driver;

import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;
import android.view.KeyEvent;

import com.sjsu.physics.utils.Vector2;

/**
 * 
 * @author John Linford
 * 
 *	Handles all controls including
 *	accelerometer and touch 
 *
 */
public class Controls implements IAccelerationListener, IOnSceneTouchListener, IOnAreaTouchListener, IOnMenuItemClickListener
{
	protected static final int MENU_RESET = 0;
	protected static final int MENU_QUIT = MENU_RESET + 1;
	protected static final int MENU_OK = MENU_QUIT + 1;
	protected static final int MENU_NEXT_LEVEL = MENU_OK + 1;

	private DriverActivity activity;

	public Controls(DriverActivity a)
	{
		activity = a;
	}

	@Override
	public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent)
	{
		//		if(activity.getPhysicsWorld() != null)
		//		{
		//			if(pSceneTouchEvent.isActionDown()) 
		//			{
		//				activity.addFace(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
		//				return true;
		//			}
		//		}
		return false;
	}

	@Override
	public void onAccelerationChanged(AccelerationData pAccelerationData) 
	{
		// get new gravity
		final Vector2 velocity = new Vector2(pAccelerationData.getX() * 10, pAccelerationData.getY() * 10);

		//activity.getPhysicsWorld().setGravity(velocity);
		activity.getPlayer().getBody().setVelocity(velocity);	
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, ITouchArea pTouchArea, float pTouchAreaLocalX, float pTouchAreaLocalY) 
	{
		return false;
	}

	@Override
	public boolean onMenuItemClicked(final MenuScene pMenuScene, final IMenuItem pMenuItem, final float pMenuItemLocalX, final float pMenuItemLocalY) 
	{
		switch(pMenuItem.getID()) 
		{
		case MENU_RESET:
			// reset game
			//activity.getScene().reset();

			// Remove the menu and reset it.
			//activity.getScene().clearChildScene();
			//activity.getMenuScene().reset();
			activity.reset();
			return true;
		case MENU_QUIT:
			// restart activity on game over
			activity.getScene().reset();

			activity.getScene().clearChildScene();
			activity.reset();
			activity.finish();
			return true;
		case MENU_OK:
			// TODO make it restart activity
			activity.getScene().reset();

			activity.getScene().clearChildScene();
			activity.reset();
			//activity.finish();
			return true;
		case MENU_NEXT_LEVEL:
			// load level 2
			Log.i("Driver", "Loading next level");
			activity.loadNextLevel();

		default:
			return false;
		}
	}
}

package com.driver;

import com.sjsu.physics.shapes.RigidBody;


/**
 * @author John Linford
 * 
 *	A game object that all moving objects will implement
 *	such as the player, enemies, projectiles and powerups
 *
 */
public interface GameObject 
{
	
	// Update Loop
	public void onManagedUpdate(final float pSecondsElapsed);
	
	// rotate the body/sprite
	public void rotate(float rad);

	
	//---------------------//
	// Getters and Setters //
	//---------------------//
	public void dealDamage(int damage);
	public RigidBody getBody();
	public int getHealth();
	public int getDamage();
}

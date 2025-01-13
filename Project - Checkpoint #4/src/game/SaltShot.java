/**
 * This class extends GameObject.
 * It's purpose is to draw and update salt shots,
 * these are created through the saltshaker objects and
 * they are "shot" at enemy objects and expires them.
 *
 * @author  Jadon Olson
 * @version December 2, 2022
 */
package game;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class SaltShot extends GameObject {

	//Fields
	private Control control;
	private State state;
	private int x;
	private int y;
	private SaltShaker ss;
	private Enemy e;
	private int xV;
	private int yV;
	private double time;

	/**
	 * This is a constructor for salt shot objects.
	 * 
	 * @param state
	 * @param control
	 * @param ss
	 */
	public SaltShot(State state, Control control, SaltShaker ss) {
		//Initialize fields
		this.state = state;
		this.control = control;
		this.ss = ss;

		isVisible = true;
		isExpired = false;
		//Find nearest enemy that will be the target
		e = state.findNearestEnemy(ss.getPosition().x, ss.getPosition().y);
		x = ss.getPosition().x;
		y = ss.getPosition().y;
		
		time = 0.0;

		if(e == null) //Check the null enemy case
			return;
		xV = e.getPosition().x - ss.getPosition().x; //Set x velocity
		yV = e.getPosition().y - ss.getPosition().y; //Set y velocity

	}

	@Override
	public void update(double elapsedTime) {

		//Salt shots expire after one second
		time += elapsedTime;
		if(time > 1.0)
			this.isExpired = true;

		x = (int) (x + (xV * elapsedTime));
		y = (int) (y + (yV * elapsedTime));

		for(GameObject go : state.getFrameObjects()) {
			if(go instanceof Enemy) {
				Enemy eTwo = (Enemy) go;
				if(eTwo == null)
					return;
				//Update position of salt shot, move it in direction of the nearest enemy.
				if(Math.abs(eTwo.getPosition().x - x) < 58 && Math.abs(eTwo.getPosition().y - y) < 58) {
					eTwo.isDead = true;//Kill the enemy
					this.isExpired = true;//expire the salt shot
					//Killed snails give one money 
					if(eTwo instanceof Snail)
						state.gainMoney(1);
					//Killed scargo give three money
					if(eTwo instanceof SCargo)
						state.gainMoney(3);
				}
			}
		}

	}

	@Override
	public void draw(Graphics g) {
		BufferedImage saltShot = control.getImage("salt_crystals.png"); //Gets the snail image from control

		g.drawImage(saltShot, x - saltShot.getWidth()/2, y - saltShot.getHeight()/2, null); //Draws the snail at the correct point




	}

}

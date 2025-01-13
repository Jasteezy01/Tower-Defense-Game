/**
 * This class extends GameObject.
 * This is a super class for all enemy objects,
 * it lays out their basic blueprint.
 *
 * @author  Jadon Olson
 * @version December 1, 2022
 */
package game;

import java.awt.Graphics;
import java.awt.Point;

public abstract class Enemy extends GameObject {

	//Fields
	protected double percentage;
	protected Control control;
	protected State state;
	protected boolean isDead;

	/**
	 * Constructor for an enemy object.
	 * 
	 * @param state
	 * @param control
	 * @param percentage
	 */
	public Enemy(State state, Control control, double percentage) {
		this.state = state;
		this.control = control;
		this.percentage = percentage;
		
		
		isVisible = true;
		isExpired = false;
		isDead = false;
	}
	
	abstract public Point getPosition();
	

}

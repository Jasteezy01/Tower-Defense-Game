/**
 * This abstract class sets up the framework for GameObjects.
 *
 * @author  Jadon Olson
 * @version November 17, 2022
 */
package game;

import java.awt.Graphics;

abstract public class GameObject {
	//Fields
	protected boolean isVisible;
	protected boolean isExpired;

	public boolean isVisible() {return isVisible;} //Checks if the game object is visible
	public boolean isExpired() {return isExpired;} //Checks if the game object has expired
	
	abstract public void update(double elapsedTime); //Updates the game after an elapsed amount of time, that takes in a double for time.
	abstract public void draw(Graphics g); //A draw function that takes in a graphics object.
	
}
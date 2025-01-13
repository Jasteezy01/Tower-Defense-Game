/**
 * This class extends the GameObject class.
 * It's purpose is to draw the background.
 *
 * @author  Jadon Olson
 * @version November 17, 2022
 */

package game;

import java.awt.Graphics;

public class Background extends GameObject {

	private Control control;
	private State state;

	/**
	 * Default constructor, sets background to visible and not expired.
	 */
	public Background (State state, Control c) {
		this.control = c;
		this.state = state;
		
		isVisible = true;
        isExpired = false;
        
	}
	
	@Override
	/**
	 * This will be empty
	 */
	public void update(double elapsedTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	/**
	 * Draws the background
	 */
	public void draw(Graphics g) {
		g.drawImage(control.getImage("path_2a.jpg"), 0, 0, null);
		
	}

}

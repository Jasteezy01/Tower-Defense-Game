/**
 * This class extends Enemy.
 * It's purpose is to draw and update snail game objects.
 *
 * @author  Jadon Olson
 * @version December 2, 2022
 */
package game;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Snail extends Enemy{

	private double showSplat;

	/**
	 * Constructor for a snail object.
	 * 
	 * @param c - a control object.
	 */
	public Snail(State state, Control control, double percentage) {
		super(state, control, percentage);

		showSplat = 0.0; //Helps with displaying the splat picture after snails are killed
	}

	@Override
	/**
	 * Updates the frames for animation of the snail.
	 * Snail moves at 1/10th the length of the elapsed time.
	 * Controls end of path scenario.
	 * 
	 * @param Time of the frame.
	 */
	public void update(double elapsedTime) {
		double velocity = 1 /15.0;
		if(!isDead)
			percentage += velocity * elapsedTime; //Takes about 15 seconds to get to the end of the path.

		//If snail reaches the end of the path
		if(percentage > 1) {
			isExpired = true; //Expiring the current snail
			//state.addGameObject(new Snail(state, control)); //Adding a new snail to replace the expired one
			state.loseLife(1); //Lose a life
		}

		//Keeps displaying for half a second then is expired
		if(showSplat >= 0.5) {
			isExpired = true;
			if(!(state.getPercentToWave() >= 100.0))
				state.addPercentToWave(0.5);
		}

	}

	@Override
	/**
	 * Draws a snail
	 */
	public void draw(Graphics g) {
		Point loc = control.getPath().convertToCoordinates(percentage); //Gets the point the snail should be at

		BufferedImage snail = control.getImage("snail.png"); //Gets the snail image from control
		BufferedImage splat = control.getImage("splat.png"); //Gets the splat image

		if(!isDead)
			g.drawImage(snail, loc.x - snail.getWidth()/2, loc.y - snail.getHeight()/2, null); //Draws the snail at the correct point
		if(isDead) {
			g.drawImage(splat, loc.x - splat.getWidth()/2, loc.y - splat.getHeight()/2, null); //Draws the splat when it is dead but not expired
			showSplat += state.getElapsedTime();
		}

	}

	@Override
	/**
	 * Helper function that simply returns the point that the enemy is at.
	 * @return the point.
	 */
	public Point getPosition() {
		Point loc = control.getPath().convertToCoordinates(percentage);
		Point pos = new Point(loc.x, loc.y);
		return pos;
	}

}

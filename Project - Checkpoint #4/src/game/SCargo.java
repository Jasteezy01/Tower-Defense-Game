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

public class SCargo extends Enemy{

	private double showCrash;


	/**
	 * Constructor for a snail object.
	 * 
	 * @param c - a control object.
	 */
	public SCargo(State state, Control control, double percentage) {
		super(state, control, percentage);

		showCrash = 0.0; //Helps to show the crash image 

	}

	@Override
	/**
	 * Updates the frames for animation of the snail.
	 * Scargo moves at 1/20th the length of the elapsed time.
	 * Controls end of path scenario.
	 */
	public void update(double elapsedTime) {
		double velocity = 1 /10.0;
		if(!isDead)
			percentage += velocity * elapsedTime; //Takes about 30 seconds to get to the end of the path.

		//If snail reaches the end of the path
		if(percentage > 1) {
			isExpired = true; //Expiring the current snail
			//state.addGameObject(new SCargo(state, control)); //Adding a new scargo to replace the expired one
			state.loseLife(3); //Lose 3 lives
		}

		//Shows the crash image for half a second before expiring the object
		if(showCrash >= 0.5) {
			isExpired = true;
			if(!(state.getPercentToWave() >= 100.0))
				state.addPercentToWave(1.0);
		}

	}

	@Override
	/**
	 * Draws an scargo van
	 */
	public void draw(Graphics g) {
		Point loc = control.getPath().convertToCoordinates(percentage); //gets the point that the s-cargo should currently be at

		BufferedImage sCargo = control.getImage("s-cargo.png"); //Gets the image from control
		BufferedImage crash = control.getImage("crash.png");

		if(!isDead)
			g.drawImage(sCargo, loc.x - sCargo.getWidth()/2, loc.y - sCargo.getHeight()/2, null); //Draws the snail at the correct point
		if(isDead) {
			g.drawImage(crash, loc.x - crash.getWidth()/2, loc.y - crash.getHeight()/2, null); //When is dead but not expired draws a crash
			showCrash += state.getElapsedTime();
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

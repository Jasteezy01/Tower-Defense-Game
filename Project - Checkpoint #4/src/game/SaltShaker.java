/**
 * This class extends GameObject and implements clickable.
 * It's purpose is to draw and update salt shaker towers,
 * it also gives those objects instruction on when they are clicked.
 *
 * @author  Jadon Olson
 * @version December 2, 2022
 */
package game;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import path.Path;

public class SaltShaker extends GameObject implements Clickable{

	//Fields
	private Control control;
	private State state;
	private boolean isMoving;
	private int x;
	private int y;
	private double time;

	/**
	 * Constructor for a salt shaker object.
	 * 
	 * @param state
	 * @param control
	 */
	public SaltShaker(State state, Control control) {
		this.control = control;
		this.state = state;

		isExpired = false;
		isVisible = true;
		isMoving = true;
		
		time = 0.0;

	}

	@Override
	/**
	 * Allows the salt shaker tower to follow the mouse if it "isMoving"
	 */
	public void update(double elapsedTime) {
		// TODO Auto-generated method stub
		if(isMoving) {
			x = control.getMouseX();
			y = control.getMouseY();
		}
		if(!isMoving) {
			if(time >= 1.0) {
				state.addGameObject(new SaltShot(state, control, this));
				time -= 1.0;
			}
			time += elapsedTime;
		}
	}

	@Override
	/**
	 * Draws the image for the salt shaker tower
	 */
	public void draw(Graphics g) {
		BufferedImage salt = control.getImage("salt.png");

		g.drawImage(salt, x - salt.getWidth()/2, y - salt.getHeight()/2, null);

	}

	@Override
	public boolean consumeClick(int mouseX, int mouseY) {
		Path p = control.getPath();
		
		//Check to see if the object isMoving
		if(isMoving) {
			isMoving = false;
			x = mouseX;
			y = mouseY;
			if(state.getMoney() < 50) {
				//If there isn't enough money (50) to buy a tower the object expires
				isExpired = true;
				return false;
			}
			if(mouseX < 0 || mouseX > 600 || mouseY < 0 || mouseY > 600) {
				//If the object isn't placed in the game-zone the tower expires
				isExpired = true;
				return false;
			}
			
			//If saltshaker is near or on the path it will expire
			if(control.getPath().isNearPath(mouseX, mouseY)) {
				isExpired = true;
				return false;
			}


			state.loseMoney(50); //The tower has been "bought" it has a price
			return true;
		}

		return false;
	}

	/**
	 * Returns the position of the salt shaker
	 * @return a point
	 */
	public Point getPosition() {
		return new Point(x, y);
	}

}

/**
 * This class extends GameObject.
 * It's purpose is to draw and update beer puddles,
 * these are created through the beercan objects and
 * they are "shot" at enemy objects and expires them.
 *
 * @author  Jadon Olson
 * @version December 2, 2022
 */
package game;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class BeerPuddle extends GameObject {

	//Fields
	private Control control;
	private State state;
	private int x;
	private int y;
	private BeerCan bc;
	private Enemy e;
	private int xV;
	private int yV;
	private double time;


	/**
	 * Constructor for beer puddle objects.
	 * 
	 * @param state
	 * @param control
	 * @param bc
	 */
	public BeerPuddle(State state, Control control, BeerCan bc) {
		//Initialize fields
		this.state = state;
		this.control = control;
		this.bc = bc;

		isVisible = true;
		isExpired = false;

		//Find nearest enemy for the target.
		e = state.findNearestEnemy(bc.getPosition().x, bc.getPosition().y);
		x = bc.getPosition().x;
		y = bc.getPosition().y;

		time = 0.0;

		if(e == null)//Null enemy case
			return;
		xV = e.getPosition().x - bc.getPosition().x; //Set x velocity
		yV = e.getPosition().y - bc.getPosition().y; //Set y velocity

	}

	@Override
	public void update(double elapsedTime) {

		//Beer puddles expire after one second
		time += elapsedTime;
		if(time > 1.0)
			this.isExpired = true;

		x = (int) (x + (xV * elapsedTime));
		y = (int) (y + (yV * elapsedTime));

		for(GameObject go : state.getFrameObjects()) {
			if(go instanceof Enemy) {
				Enemy eTwo = (Enemy) go;
				if(eTwo == null) //Null enemy case
					return;
				if(Math.abs(eTwo.getPosition().x - x) < 64 && Math.abs(eTwo.getPosition().y - y) < 30) {
					eTwo.isDead = true; //Kill enemy when touching
					this.isExpired = true; //Expires beer puddle
					//Killed snail give one money
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
		BufferedImage beerPuddle = control.getImage("BeerPuddle.png"); //Gets the snail image from control

		g.drawImage(beerPuddle, x - beerPuddle.getWidth()/2, y - beerPuddle.getHeight()/2, null); //Draws the snail at the correct point





	}

}

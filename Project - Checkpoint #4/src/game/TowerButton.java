/**
 * This class extends GameObject.
 * It's purpose is to draw the button to purchase 
 * salt shaker towers.
 *
 * @author  Jadon Olson
 * @version December 2, 2022
 */
package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class TowerButton extends GameObject implements Clickable{
	
	//Fields
	private State state;
	private Control control;
	
	/**
	 * Constructor for towerButton objects.
	 * @param state
	 * @param control
	 */
	public TowerButton(State state, Control control) {
		this.state = state;
		this.control = control;
		
		isExpired = false;
		isVisible = true;
		
	}
	@Override
	public void update(double elapsedTime) {
		// nothing here
		
	}

	@Override
	public void draw(Graphics g) {
		//Creating the button for salt shaker towers
		g.setColor(Color.BLACK);
		g.fillRoundRect(615, 220, 120, 50, 10, 10); //Black rectangle
		g.setColor(Color.WHITE);
		g.fillRoundRect(625, 230, 100, 30, 10, 10); //White Rectangle
		g.setColor(Color.BLACK);
		g.setFont(new Font("Ink Free", Font.BOLD, 15));
		g.drawString("Cost 50", 635, 250); //Text for the cost of this tower
		BufferedImage salt = control.getImage("salt.png"); //Salt shaker image
		g.drawImage(salt, 740, 215, null);
		
		//Creating the button for beer objects
		g.setColor(Color.BLACK);
		g.fillRoundRect(615, 300, 120, 50, 10, 10); //Black rectangle
		g.setColor(Color.WHITE);
		g.fillRoundRect(625, 310, 100, 30, 10, 10); //White Rectangle
		g.setColor(Color.BLACK);
		g.setFont(new Font("Ink Free", Font.BOLD, 15));
		g.drawString("Cost 150", 635, 330); //Text for the cost of this tower
		BufferedImage beer = control.getImage("beer.png"); //Beer Image image
		g.drawImage(beer, 740, 295, null);
		
	}
	@Override
	public boolean consumeClick(int mouseX, int mouseY) {
		
		//Check if the click occurred on the saltshaker button
		if(mouseX > 625 && mouseX < 725 && mouseY > 230 && mouseY < 260) {
			//Create a new saltshaker tower
			state.addGameObject(new SaltShaker(state, control));
			return true;
		}
		//Check if the beer button was clicked
		if(mouseX > 625 && mouseX < 725 && mouseY > 310 && mouseY <340) {
			//Create a new Beer can tower
			state.addGameObject(new BeerCan(state, control));
			return true;
		}
		return false;
	}

}

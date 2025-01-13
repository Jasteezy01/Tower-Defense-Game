/**
 * This class extends GameObject.
 * It's purpose is to draw and update the menu, 
 * which displays the current lives and money.
 *
 * @author  Jadon Olson
 * @version December 2, 2022
 */
package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Menu extends GameObject{

	//Fields
	private State state;
	private Control control;
	
	/**
	 * Constructor for menu objects.
	 * @param state
	 * @param control
	 */
	public Menu(State state, Control control) {
		this.state = state;
		this.control = control;
		
		isExpired = false;
		isVisible = true;
		
	}
	
	@Override
	public void update(double elapsedTime) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Draws the menu area for the game.
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(600, 0, 250, 600); //Background for the menu
		g.setColor(Color.WHITE);
		g.setFont(new Font("Ink Free", Font.BOLD, 50));
		g.drawString("Menu", 615, 50); //Text for the Menu
		g.setFont(new Font("Ink Free", Font.BOLD, 30));
		g.setColor(Color.GREEN);
		g.drawString("Money: " + state.getMoney(), 615, 100); //The money display
		g.drawString("Lives: " + state.getLives(), 615, 150); //The life display
		g.setColor(Color.RED);
		g.drawString("Wave: " + state.getPercentToWave() + "%", 615, 200); //Display for the wave percentage
		
//		g.setColor(Color.WHITE);
//		g.drawString("Time: " + state.getTotalTime(), 615, 200);

		
	}

}

/**
 * This class extends the GameObject class.
 * It's purpose is to draw the game over screen
 * when lives reaches 0.
 *
 * @author  Jadon Olson
 * @version November 24, 2022
 */

package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class GameOver extends GameObject {

	private Control control;
	private State state;

	/**
	 * Default constructor, sets game over screen to visible and not expired.
	 */
	public GameOver (State state, Control c) {
		this.control = c;
		this.state = state;
		
		isVisible = true;
        isExpired = false;
        
        state.setGameOver(true);
        
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
	 * Draws the game over screen.
	 */
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 850, 600);
		g.setColor(Color.RED);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		g.drawString("Game Over", 800/4, 600/2);
		
	}

}

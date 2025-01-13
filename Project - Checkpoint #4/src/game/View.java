/**
 * This class extends JPanel,
 * this is the visual class whose purpose is to draw everything
 * to the game panel after setting it up.
 *
 * @author  Jadon Olson
 * @version December 2, 2022
 */

package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class View extends JPanel{

	//Fields
	protected Control control;
	protected State state;

	/**
	 * Constructor for a view object, takes in 2 parameters a control and state object.
	 * @param control - a control object
	 * @param state - a state object
	 */
	public View(Control control, State state) {
		//Initializing the field objects
		this.control = control;
		this.state = state;

		//Creating the JFrame
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Setting the size
		this.setMinimumSize(new Dimension(850,600));
		this.setPreferredSize(this.getMinimumSize());
		this.setMaximumSize(this.getMinimumSize());

		//Creating the j panel
		JPanel topLevel = new JPanel();
		topLevel.setLayout(new BorderLayout());
		topLevel.add(this,BorderLayout.CENTER);
		f.setContentPane(topLevel);
		f.setTitle("Snail Tower Defense");


		//Packing and setting visible.
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	
	/**
	 * The paint method.
	 */
	public void paint(Graphics g) {
		
		//g.drawImage(control.getImage("path_2a.jpg"), 0, 0, null);
		
		//Drawing game objects
		for (GameObject go : state.getFrameObjects())
            if (go.isVisible() && !go.isExpired)
                go.draw(g);

		
	}

}

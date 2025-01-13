/**
 * This class controls all of the mechanical game functions,
 * it implements Runnable and ActionListener.
 *
 * @author  Jadon Olson
 * @version November 17, 2022
 */

package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import path.Path;

public class Control implements Runnable, ActionListener, MouseListener, MouseMotionListener {

	//Fields
	protected State state;
	protected View view;
	private Path path;
	private Map <String, BufferedImage> imageCache;
	private int mouseX;
	private int mouseY;

	/**
	 * Constructor for control objects.
	 */
	public Control() {
		SwingUtilities.invokeLater(this);
	}

	@SuppressWarnings("unused")
	@Override
	public void run() {

		//Loading in the path
		ClassLoader myLoader = this.getClass().getClassLoader();
		InputStream pathStream = myLoader.getResourceAsStream("resources/path_2.txt");
		Scanner pathScanner = new Scanner(pathStream);
		if(pathScanner == null) {
			System.out.println("Path Scanner is null.");
			return;
		}

		//Initializing the path
		path = new Path(pathScanner);

		//Initializing the map
		imageCache = new TreeMap();




		//Initializing additional fields
		state = new State();
		view = new View(this, state); 

		state.startFrame();  // Prepares the creation of the 'next' frame

		state.addGameObject(new Background(state,this));  // Add one background object to our list
		state.addGameObject(new Menu(state, this)); //Add the menu area
		state.addGameObject(new TowerButton(state, this)); //Add the tower purchase button
		state.addGameObject(new enemyGenerator(state, this)); //Add the enemy generator.


		state.finishFrame();    // Mark the next frame as ready

		Timer t = new Timer(16, this);  // Triggers every 16 milliseconds, reports actions to 'this' object.
		t.start();

		//Mouse listeners
		view.addMouseListener(this);
		view.addMouseMotionListener(this);

		view.repaint(); //Repaint



	}

	/**
	 * Returns the current path object.
	 * @return
	 */
	public Path getPath() {
		return this.path;
	}

	/**
	 * This method loads images if they have not been loaded
	 * and adds them to a map. If they have been loaded,
	 * it finds the image in the map.
	 * 
	 * @param filename
	 * @return a buffered image
	 */
	public BufferedImage getImage (String filename) {
		//Return the image if it has already been loaded
		if(imageCache.containsKey(filename))
			return imageCache.get(filename);

		//If we get this far we did not find the image in the map. Load it, and store it.

		try
		{
			//System.out.println("Loading " + filename);
			BufferedImage image = javax.imageio.ImageIO.read(new File("src/resources/" + filename));
			//Put the image in the map.
			imageCache.put(filename, image);
			return image;
		}
		catch (IOException e)
		{
			System.out.println("Unable to load:" + filename);
			return null;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		state.startFrame(); //Start a new frame

		if(!state.getGameOver()) {
			for (GameObject go : state.getFrameObjects())
				go.update(state.getElapsedTime());    //Update all the objects in the current frame
		}


		state.finishFrame(); //End the current frame

		if(state.getLives() <= 0)
			state.addGameObject(new GameOver(state, this)); // If lives are 0 the game ends.

		view.repaint(); //Repaint


	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX(); //Mouse X coordinate
		mouseY = e.getY(); //Mouse Y coordinate
		//System.out.println("(" + mouseX + ", " + mouseY + ")");

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		List<GameObject> list = state.getFrameObjects();


		//Checks all game objects, if they are "clickable" it runs their consume click method.
		for(GameObject go : list)
			if(go instanceof Clickable) {
				Clickable c = (Clickable) go;
				if(c.consumeClick(mouseX, mouseY))
					break;
			}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * Returns the x coordinate of the mouse.
	 * @return x coordinate as an int.
	 */
	public int getMouseX() {
		return mouseX;
	}

	/**
	 * Returns the y c0ordinate of the mouse.
	 * @return y coordinate as an int.
	 */
	public int getMouseY() {
		return mouseY;
	}
}

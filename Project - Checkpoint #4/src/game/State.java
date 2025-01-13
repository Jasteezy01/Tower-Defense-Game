/**
 * This class helps to load and keep track of what
 * should be going on in the current animation frame
 * and the next.
 *
 * @author  Jadon Olson
 * @version December 2, 2022
 */

package game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class State {

	//Fields	
	private List<GameObject> currentFrameGameObjects;
	private List<GameObject> nextFrameGameObjects;
	private int lives;
	private int money;
	private double percentToWave;//Percentage to next wave of enemies
	private boolean gameOver;
	private long elapsedTime;
	private long lastFrameStartTime; //The time the last frame was started.
	private long totalTime; //Total time game has been running
	private long startTime; //Time when the first start frame was run.


	/**
	 * Constructor for state objects
	 */
	public State() {
		//Initialize fields
		currentFrameGameObjects = new ArrayList<GameObject>();
		money = 100;
		lives = 100;
		percentToWave = 0.0;

		gameOver = false;

		startTime = System.currentTimeMillis(); //Start time of the game.

	}

	/**
	 * Returns the current frame game objects
	 * @return
	 */
	public List<GameObject> getFrameObjects() {
		return currentFrameGameObjects;
	}

	/**
	 * This is called at the start of a new frame, the "next" frame is set to empty
	 * and then is filled with the "current" frame.
	 * 
	 * The control class will call this method once at the start of every animation frame.
	 */
	public void startFrame() {
		nextFrameGameObjects = new ArrayList<GameObject>(); //Creates empty list
		nextFrameGameObjects.addAll(currentFrameGameObjects); //Add all the current ones to the new list.

		//On the first frame just get the time of this start frame.
		if(lastFrameStartTime == 0) {
			lastFrameStartTime = System.currentTimeMillis();
			return;
		}

		elapsedTime = System.currentTimeMillis() - lastFrameStartTime; //How much time has passed between "now" and the start of the last frame
		lastFrameStartTime = System.currentTimeMillis(); //Update the frame start time to the current frame start time.

	}

	/**
	 * The current frame is filled with the changes that may or may not have been made in the next
	 * frame list.
	 * 
	 * The control class will call this method at the end of every animation frame.
	 */
	public void finishFrame() {
		//Before you make the next frame the current frame
		//remove all expired objects from the frame
		for(GameObject g: currentFrameGameObjects) {
			if(g.isExpired)
				nextFrameGameObjects.remove(g);
		}
		currentFrameGameObjects = nextFrameGameObjects; //Set current frame to updated next frame

	}

	/**
	 * This method adds game objects to the next frame of the game. 
	 * This method must only be called if a frame has been started, but not finished.
	 * 
	 * @param go - a game object that is being added
	 */
	public void addGameObject(GameObject go) {
		nextFrameGameObjects.add(go);
	}

	/**
	 * Returns money value
	 * @return
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * Returns lives value
	 * @return
	 */
	public int getLives() {
		return lives;
	}

	/**
	 * Takes away life.
	 * Occurs when an enemy reaches the end of the path.
	 * The amount of life taken depends on the type of enemy.
	 * (SCargo or Snail)
	 * 
	 * @param x - the amount of life lost
	 */
	public void loseLife(int x) {
		lives-=x;
	}

	/**
	 * Add money
	 */
	public void gainMoney(int x) {
		money+=x;
	}

	/**
	 * Lose money due to purchasing a tower.
	 * 
	 * @param i - the cost of the tower
	 */
	public void loseMoney(int x) {
		money -=x;
	}

	/**
	 * Sets the status of the game over boolean.
	 * 
	 */
	public void setGameOver(boolean b) {
		gameOver = b;
	}

	/**
	 * Returns the status of the game over boolean.
	 * @return game over status as a boolean.
	 */
	public boolean getGameOver() {
		return gameOver;
	}

	/**
	 * Returns the elapsed time since the start of the last frame in seconds.
	 * @return time as a double
	 */
	public double getElapsedTime() {
		return elapsedTime/1000.0;
	}

	/**
	 * Returns the total time since the start of the game in seconds.
	 * @return time as a double
	 */
	public double getTotalTime() {
		totalTime = System.currentTimeMillis() - startTime; //Calculating total time
		return totalTime/1000.0;
	}

	/**
	 * This method receives a coordinate of a mouse click,
	 * it then finds the nearest enemy to that click and 
	 * returns it.
	 * 
	 * @param x - coord of mouse
	 * @param y - coord of mouse
	 * @return the nearest enemy
	 */
	public Enemy findNearestEnemy(int x, int y) {
		//System.out.println("Finding nearest enemy...");
		Enemy closestEnemy = null; //Object to be returned
		double closestDistance = 0; //Distance to the enemy


		for(GameObject go : currentFrameGameObjects) {
			if(go instanceof Enemy) {
				Enemy e = (Enemy) go;
				//Pythagorean theorem to calculate distance.
				double distance = Math.sqrt((e.getPosition().getY() - y) * (e.getPosition().getY() - y) + (e.getPosition().getX() - x) * (e.getPosition().getX() - x));
				//Deal with null enemy case
				if(closestEnemy == null) {
					closestEnemy = e;
					closestDistance = distance;
				}
				//Find closest enemy
				else
					if(distance < closestDistance)
						closestEnemy = e;

			} else if(closestEnemy == null) {

				continue;
			}
		}
		return closestEnemy;
	}

	/**
	 * Everytime an enemy is killed a certain percentage is added
	 * to the percent to wave double.
	 * @param x
	 */
	public void addPercentToWave(double x) {
		percentToWave += x;
	}

	/**
	 * Returns the current percent to wave
	 * @return
	 */
	public double getPercentToWave() {
		// TODO Auto-generated method stub
		return percentToWave;
	}

	/**
	 * Resets the percent to wave
	 */
	public void resetPercentToWave() {
		percentToWave = 0.0;
	}

	/**
	 * sets the percent to wave
	 * @param d
	 */
	public void setPercentToWave(double d) {
		percentToWave = d;

	}

}

/**
 * This class extends the GameObject class.
 * It's purpose is to add enemy objects after certain
 * amounts of time have passed.
 *
 * @author  Jadon Olson
 * @version December 2, 2022
 */
package game;

import java.awt.Graphics;

public class enemyGenerator extends GameObject{

	//Fields
	private State state;
	private Control control;
	private double percentage;
	private double nextSnail;
	private double nextSCargo;
	private double wave;
	private double waveCooldown;

	/**
	 * Constructor for the enemy generator object.
	 * @param state
	 * @param control
	 */
	public enemyGenerator(State state, Control control) {
		//Initializing fields
		this.state = state;
		this.control = control;

		nextSnail = 0.25;
		nextSCargo = 2.5;
		wave = 10.0;
		waveCooldown = 10.0;
	}

	@Override
	public void update(double elapsedTime) {
		double time = state.getTotalTime();
		percentage = 0; //Helps with delaying the drawing of enemies.

		//If money is greater than 10_000, endless stream of enemies
		if(state.getMoney() > 10000) {
			state.addGameObject(new Snail(state, control, percentage));
			state.addGameObject(new SCargo(state, control, percentage));
		}

		//This generates the wave of enemies
		if(state.getPercentToWave() >= 100.0) {
			while(wave >= 0.0) {
				//Generates enemies every frame for 10 seconds
				state.addGameObject(new Snail(state, control, percentage));
				state.addGameObject(new SCargo(state, control, percentage));
				wave -= elapsedTime;
				return;
			}
			while(waveCooldown >= 0 && state.getPercentToWave() >= 100.0) {
				//After the wave there is a 10 second cooldown before more enemies are added
				waveCooldown -= elapsedTime;
				return;
			}
			state.resetPercentToWave();
		}


		wave = 10.0;
		waveCooldown = 10.0;

		//After 25 seconds snails spawn 4 times a second
		if(time / 5 > 5) {

			nextSnail -= elapsedTime;
			if(nextSnail <= 0) {
				state.addGameObject(new Snail(state, control, percentage));
				nextSnail += 0.25;
			}
		} 
		//Adds snails every 5 seconds. 1 more snail every time.
		else if(time % 5 >= 0.0 && time % 5 <= elapsedTime) {
			for(int i = 0; i < (int)(time / 5); i++) {
				Enemy e = new Snail(state, control, percentage);
				state.addGameObject(e);
				percentage -= .01; //A delay so snails don't overlap when being drawn
			}
		}

		//After 30 seconds scargo spawns once every 2.5 seconds
		if(time / 10 > 3) {

			nextSCargo -= elapsedTime;
			if(nextSCargo <= 0) {
				state.addGameObject(new SCargo(state, control, percentage));
				nextSCargo += 2.5;
			}
		} 
		//Adds S-cargo every 10 seconds. 1 more s-cargo every time.
		else if(time % 10 >= 0.0 && time % 10 <= elapsedTime) {
			for(int i = 0; i < (int)(time / 10); i++) {
				Enemy e = new SCargo(state, control, percentage);
				state.addGameObject(e);
				percentage -= .01; //A delay so s-cargo doesn't overlap when being drawn
			}
		}

	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub

	}

}

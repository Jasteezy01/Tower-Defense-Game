/**
 * This interface is for any classes that are clickable.
 *
 * @author  Jadon Olson
 * @version November 20, 2022
 */
package game;

public interface Clickable {
	
	public boolean consumeClick(int mouseX, int mouseY); //boolean constructor for if an object should consume a click
	
}

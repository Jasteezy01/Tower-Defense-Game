/**
 * This class creates the path for enemy game objects to follow.
 * This class is also used for the enemy game objects to move along 
 * the path.
 *
 * @author  Jadon Olson
 * @version December 2, 2022
 */
package path;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Scanner;

public class Path {
	ArrayList<Point> points;
	int pointCount;
	int pathLength;

	/**
	 * Creates a path constructor with no arguments.
	 */
	public Path() {
		pointCount = 0;
		points = new ArrayList<>();
	}

	/**
	 * This Constructor takes in a scanner, sets the pointCount variable and creates a point object,
	 * then that object is added to the points array list.
	 * 
	 * @param in a scanner
	 */
	public Path(Scanner in) {
		points = new ArrayList<>();
		pointCount = in.nextInt(); //The number of points that scanner should have
		for(int i = 0; i < pointCount; i++) {
			Point e = new Point(in.nextInt(), in.nextInt()); // Creates the point
			points.add(e); //Add the point to the array

		}
	}



	/**
	 * Returns the number of points in a path as an int.
	 * @return int pointCount
	 */
	public int getPointCount() {
		return pointCount;
	}

	/**
	 * Returns the x point in the nth point object.
	 * @param n
	 * @return the int value of the x point.
	 */
	public int getX(int n) {
		Point d = new Point(points.get(n));
		return d.x;
	}

	/**
	 * Returns the y point in the nth point object.
	 * @param n
	 * @return the int value of the y point.
	 */
	public int getY(int n) {
		Point d = new Point(points.get(n));
		return d.y;
	}

	/**
	 * Adds a point object to the end of the points array list
	 * @param x
	 * @param y
	 */
	public void add(int x, int y) {
		this.pointCount++;
		Point d = new Point(x,y);
		points.add(d);
	}

	/**
	 * This method takes the values inside of the points array
	 * and puts them into a string in the format provided in the 
	 * assignment explanation.
	 */
	public String toString() {
		String s = "";
		s = s + pointCount; //Starts the string with point count
		for(int i = 0; i < points.size(); i++) { //Iterates through the points array list
			Point d = new Point(points.get(i));  //The current point object
			s = s + "\n"; 
			s = s + d.x + " " + d.y; 
		}
		return s; //Returns a string
	}

	public void draw(Graphics g) {
		for(int i = 0; i < points.size()-1; i++) {
			Point a = new Point(points.get(i));
			Point b = new Point(points.get(i+1));
			g.setColor(Color.RED);
			g.drawLine(a.x, a.y, b.x, b.y);
		}
	}

	/** 
	 * Given a percentage between 0% and 100%, this method calculates
	 * the location along the path that is exactly this percentage
	 * along the path. The location is returned in a Point object
	 * (integer x and y), and the location is a screen coordinate.
	 * 
	 * If the percentage is less than 0%, the starting position is
	 * returned. If the percentage is greater than 100%, the final
	 * position is returned.
	 * 
	 * Callers must not change the x or y coordinates of any returned
	 * point object (or the caller could be changing the path).
	 * 
	 * @param percentTraveled a distance along the path
	 * @return the screen coordinate of this position along the path
	 */
	public Point convertToCoordinates(double percentTraveled){
		setPathLength();
		int currentPos = (int) (pathLength * percentTraveled);
		int pixelsPast = 0;
		double segLength = 0;
		double segX;
		double segY;
		double segPercent;
		int j = 0;
		int x;
		int y;


		//Starting Position
		if(percentTraveled == 0.0) {
			return points.get(0);
		}
		//Ending Position, will need to adjust to only equal to one
		if(percentTraveled == 1.0) {
			return points.get(pointCount-1);
		}

		if(percentTraveled > 1.0) {
			return points.get(pointCount-1);
		}

		for(int i = 0; i < pointCount - 1; i++) {
			segX = points.get(i+1).getX() - points.get(i).getX();
			segY = points.get(i+1).getY() - points.get(i).getY();
			segLength = Math.sqrt((segX * segX) + (segY * segY));
			pixelsPast += segLength;
			if(pixelsPast > currentPos) {
				j = i;
				break;
			}
		}
		segPercent = (pixelsPast - currentPos)/segLength;
		//		x = (int) (((1 - segPercent) * points.get(j).getX()) + (segPercent * points.get(j+1).getX()));
		//		y = (int) (((1 - segPercent) * points.get(j).getY()) + (segPercent * points.get(j+1).getY()));

		x = (int) ((segPercent * points.get(j).getX()) + ((1 - segPercent) * points.get(j+1).getX()));
		y = (int) ((segPercent * points.get(j).getY()) + ((1 - segPercent) * points.get(j + 1).getY()));

		//		System.out.println(x + ", " + y);
		//		System.out.println(percentTraveled);
		return new Point(x,y);
	}

	/**
	 * Sets the path length of the current path
	 * @return the path length as an int.
	 */
	private int setPathLength() {
		pathLength = 0;
		for(int i = 0; i < pointCount-1; i++) {
			int xVal = (int) ( points.get(i+1).getX() - points.get(i).getX());
			xVal = xVal * xVal;
			int yVal = (int) ( points.get(i+1).getY() - points.get(i).getY());
			yVal = yVal * yVal;
			pathLength = (int) (pathLength + Math.sqrt(yVal + xVal));
		}
		return pathLength;
	}

	/**
	 * Returns the length of the path.
	 * @return
	 */
	public int getPathLength() {
		return pathLength;
	}

	/**
	 * Checks if mouse click was too close to the path.
	 * If so returns true.
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isNearPath(int x, int y) {
		for(Point p : points) {
			//Checks if mouse click is within 30 pixels of path
			if(Math.abs(p.x - x) < 30 && Math.abs(p.y - y) < 30) {
				
				return true;
			}
		}

		return false;
	}

}

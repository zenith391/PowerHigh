package org.powerhigh.utils;

public class Point {

	public int x, y;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point() {}
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(Point src) {
		this.x = src.x;
		this.y = src.y;
	}
	
}

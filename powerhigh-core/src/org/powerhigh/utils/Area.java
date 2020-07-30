package org.powerhigh.utils;

public class Area {

	public int x, y, width, height;

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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setBounds(int x, int y, int width, int height) {
		setWidth(width);
		setHeight(height);
		setX(x);
		setY(y);
	}
	
	public Area(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Area(int width, int height) {
		this(0, 0, width, height);
	}
	
	public Area(Area src) {
		this(src.x, src.y, src.width, src.height);
	}
	
	public boolean equals(Object o) {
		if (o instanceof Area) {
			Area area = (Area) o;
			return area.x == x && area.y == y && area.width == width && area.height == height;
		}
		return false;
	}
	
	public String toString() {
		return "Area[x="+x+", y="+y+", width="+width+", height="+height+"]";
	}
	
}

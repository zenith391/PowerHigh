package org.lggl.graphics.objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import org.lggl.graphics.Window;

public abstract class GameObject {

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	protected int x;
	protected int y;
	protected int width = 15;
	protected int height = 15;
	private String name = Integer.toHexString(hashCode());
	private boolean visible = true;
	protected Rectangle hitbox = new Rectangle(0, 0, 15, 15);
	protected Color color = Color.BLACK;

	public abstract void paint(Graphics g, Window source);
	
	public void onEvent(String type, Object... args) {
		
	}

	public GameObject() {
	}

	public int getX() {
		return x;
	}

	public String toString() {
		return getClass().getName() + "[type="+getClass().getSimpleName()+",x="+x+",y="+y+",width="+width+",height="+height+"]";
	}

	public void centerTo(Window window) {
		x = window.getWidth() / 2 - (width / 2);
		y = window.getHeight() / 2 - (height / 2);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getY() {
		return y;
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

	public void setX(int nx) {
		x = nx;
	}

	public Rectangle getHitbox() {
		hitbox.setBounds(x, y, width, height);
		return hitbox;
	}

	public void setY(int ny) {
		y = ny;
	}

	public boolean isColliding(GameObject g) {
		return getHitbox().intersects(g.getHitbox());
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void setSize(int w, int h) {
		this.width = w;
		this.height = h;
	}
	
	public void setSize(Dimension dim) {
		setSize(dim.width, dim.height);
	}
	
	public Dimension getSize() {
		return new Dimension(width, height);
	}
}

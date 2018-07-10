package org.lggl.objects;

import java.awt.Color;
import java.awt.Graphics;

import org.lggl.graphics.Window;

public class Rectangle extends GameObject {
	
	private boolean rounded;
	
	public boolean isRounded() {
		return rounded;
	}

	public void setRounded(boolean rounded) {
		this.rounded = rounded;
	}

	public Rectangle() {
		this(0, 0, 50, 50, Color.BLACK);
	}

	public Rectangle(int x, int y, int width, int height, Color c) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		color = c;
		hitbox = new java.awt.Rectangle(x, y, width, height);
	}
	
	@Override
	public void paint(Graphics g, Window source) {
		g.setColor(color);
		g.fillRect(x, y, getWidth(), getHeight());
	}

}

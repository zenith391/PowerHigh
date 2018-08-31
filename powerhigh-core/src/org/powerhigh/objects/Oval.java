package org.powerhigh.objects;

import java.awt.Color;
import java.awt.Graphics;

import org.powerhigh.graphics.Interface;

public class Oval extends GameObject {

	private boolean filled;
	
	public boolean isFilled() {
		return filled;
	}

	public void setFilled(boolean filled) {
		this.filled = filled;
	}

	public Oval() {
		this(0, 0, 50, 50, Color.BLACK);
	}

	public Oval(int x, int y, int width, int height, Color c) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		color = c;
		hitbox = new java.awt.Rectangle(x, y, width, height);
	}
	
	@Override
	public void paint(Graphics g, Interface source) {
		g.setColor(getColor());
		if (!filled)
			g.drawOval(x, y, width, height);
		else
			g.fillOval(x, y, width, height);
	}

	
	
}

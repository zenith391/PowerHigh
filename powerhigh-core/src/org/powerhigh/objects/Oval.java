package org.powerhigh.objects;

import org.powerhigh.utils.Color;

import org.powerhigh.graphics.Drawer;
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
	}
	
	@Override
	public void paint(Drawer g, Interface source) {
		g.setColor(getColor());
		if (!filled)
			g.drawOval(x, y, width, height);
		else
			g.fillOval(x, y, width, height);
	}
	
}

package org.powerhigh.objects;

import org.powerhigh.utils.Color;
import org.powerhigh.graphics.Drawer;
import org.powerhigh.graphics.Interface;

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
	}
	
	@Override
	public void paint(Drawer drawer, Interface source) {
		drawer.setColor(color);
		drawer.fillRect(x, y, getWidth(), getHeight());
	}

}

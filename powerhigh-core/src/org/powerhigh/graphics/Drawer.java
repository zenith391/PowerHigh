package org.powerhigh.graphics;

import org.powerhigh.utils.Color;

public abstract class Drawer {

	public abstract void fillRect(int x, int y, int width, int height);
	public abstract void setColor(Color color);
	public abstract Color getColor();
	
}

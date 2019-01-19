package org.powerhigh;

import org.powerhigh.graphics.Interface;
import org.powerhigh.utils.Area;

public class EditableViewport extends ViewportManager {

	private int x, y, w, h;
	
	public EditableViewport(int w, int h) {
		this.w = w;
		this.h = h;
	}
	
	public int getWidth() {
		return w;
	}
	
	public int getHeight() {
		return h;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setWidth(int width) {
		w = width;
	}
	
	public void setHeight(int height) {
		h = height;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public Area getViewport(Interface win) {
		return new Area(x, y, w, h);
	}

}

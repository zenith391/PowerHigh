package org.powerhigh;

import org.powerhigh.graphics.Interface;
import org.powerhigh.utils.Area;

public class SizedViewport extends ViewportManager {

	private int w, h;
	
	public SizedViewport(int w, int h) {
		this.w = w;
		this.h = h;
	}
	
	public void setWidth(int width) {
		w = width;
	}
	
	public void setHeight(int height) {
		h = height;
	}
	
	public int getWidth() {
		return w;
	}
	
	public int getHeight() {
		return h;
	}

	@Override
	public Area getViewport(Interface win) {
		return new Area((win.getWidth() - w) / 2, (win.getHeight() - h) / 2, w, h);
	}

}

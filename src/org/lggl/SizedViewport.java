package org.lggl;

import java.awt.Rectangle;

import org.lggl.graphics.Window;

public class SizedViewport extends ViewportManager {

	private int w, h;
	
	public SizedViewport(int w, int h) {
		this.w = w;
		this.h = h;
	}

	@Override
	public Rectangle getViewport(Window win) {
		return new Rectangle((win.getWidth() - w) / 2, (win.getHeight() - h), w, h);
	}

}

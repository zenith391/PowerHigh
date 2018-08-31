package org.powerhigh;

import java.awt.Rectangle;

import org.powerhigh.graphics.Interface;

public class StretchViewport extends ViewportManager {

	private int w, h;
	
	public StretchViewport(int w, int h) {
		this.w = w;
		this.h = h;
		this.props.put("stretchToWindow", true);
	}
	
	@Override
	public Rectangle getViewport(Interface win) {
		return new Rectangle(0, 0, w, h);
	}

}

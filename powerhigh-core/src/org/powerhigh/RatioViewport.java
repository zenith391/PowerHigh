package org.powerhigh;

import org.powerhigh.graphics.Interface;
import org.powerhigh.utils.Area;

public class RatioViewport extends ViewportManager {

	private int rW, rH;
	
	public RatioViewport(int rW, int rH) {
		this.rW = rW;
		this.rH = rH;
	}
	
	@Override
	public Area getViewport(Interface win) {
		return new Area((win.getWidth() / rW) * rW, (win.getHeight() / rH) * rH);
	}
	
}

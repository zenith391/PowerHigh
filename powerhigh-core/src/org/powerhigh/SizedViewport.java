package org.powerhigh;

import org.powerhigh.graphics.Interface;
import org.powerhigh.utils.Area;

public class SizedViewport extends ViewportManager {

	private int w, h;
	
	public SizedViewport(int w, int h) {
		this.w = w;
		this.h = h;
	}

	@Override
	public Area getViewport(Interface win) {
		return new Area((win.getWidth() - w) / 2, (win.getHeight() - h) / 2, w, h);
	}

}

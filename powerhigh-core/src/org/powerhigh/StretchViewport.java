package org.powerhigh;

import org.powerhigh.graphics.Interface;
import org.powerhigh.utils.Area;

public class StretchViewport extends ViewportManager {

	private int w, h;
	
	public StretchViewport(int w, int h) {
		this.w = w;
		this.h = h;
		this.props.put("stretchToWindow", true);
	}
	
	@Override
	public Area getViewport(Interface win) {
		return new Area(0, 0, w, h);
	}

}

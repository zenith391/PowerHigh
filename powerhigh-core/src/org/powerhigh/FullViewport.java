package org.powerhigh;

import org.powerhigh.graphics.Interface;
import org.powerhigh.utils.Area;

/**
 * Set the viewport to (0, 0, width, height), so the maximum usable drawing space.
 */
public class FullViewport extends ViewportManager {
	
	@Override
	public Area getViewport(Interface win) {
		return new Area(0, 0, win.getWidth(), win.getHeight());
	}

}

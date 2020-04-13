package org.powerhigh.objects;

import org.powerhigh.graphics.Drawer;
import org.powerhigh.graphics.Interface;

/**
 * On-screen key
 */
public class ScreenButton extends GameObject {

	@Override
	public void paint(Drawer drawer, Interface source) {
		drawer.drawOval(x, y, width, height);
	}
	
}

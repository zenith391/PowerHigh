package org.lggl;

import java.awt.Rectangle;

import org.lggl.graphics.Window;

public abstract class ViewportManager {

	public ViewportManager() {
		// TODO Auto-generated constructor stub
	}
	
	public abstract Rectangle getViewport(Window win);

}

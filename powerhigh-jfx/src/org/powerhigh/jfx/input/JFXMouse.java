package org.powerhigh.jfx.input;

import org.powerhigh.graphics.Interface;
import org.powerhigh.input.Mouse;

public class JFXMouse extends Mouse {

	public JFXMouse(int defaultX, int defaultY, Interface win) {
		super(defaultX, defaultY, win);
	}
	
	public void mouseDragged(int button, int x, int y, int screenX, int screenY) {
		super.mouseDragged(button, x, y, screenX, screenY);
	}
	
	public void mouseMoved(int x, int y, int screenX, int screenY) {
		super.mouseMoved(x, y, screenX, screenY);
	}
	
	public void mouseClicked(int button, int x, int y) {
		super.mouseClicked(button, x, y);
	}
	
	public void mousePressed(int button, int x, int y) {
		super.mousePressed(button, x, y);
	}
	
	public void mouseReleased(int button, int x, int y) {
		super.mouseReleased(button, x, y);
	}

}

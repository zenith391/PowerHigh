package org.powerhigh.jfx.input;

import org.powerhigh.graphics.Interface;
import org.powerhigh.input.AbstractKeyboard;

public class JFXKeyboard extends AbstractKeyboard {

	public JFXKeyboard(Interface win) {
		super(win);
	}
	
	public void keyTyped(int keyCode) {
		typeKey(transform(keyCode));
	}
	
	public void keyPressed(int keyCode) {
		pressKey(transform(keyCode));
	}
	
	private int transform(int code) {
		int c = code;
		if (c >= 65 && c <= 90) {
			c -= 64;
			if (code >= 74) {
				c--;
			}
		}
		return c;
	}
	
	public void keyReleased(int keyCode) {
		releaseKey(transform(keyCode));
	}

}

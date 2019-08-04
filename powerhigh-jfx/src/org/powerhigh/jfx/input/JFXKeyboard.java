package org.powerhigh.jfx.input;

import org.powerhigh.graphics.Interface;
import org.powerhigh.input.AbstractKeyboard;

public class JFXKeyboard extends AbstractKeyboard {

	public JFXKeyboard(Interface win) {
		super(win);
	}
	
	public void keyTyped(char ch, int keyCode) {
		typeKey(ch, transform(keyCode));
	}
	
	public void keyPressed(char ch, int keyCode) {
		pressKey(ch, transform(keyCode));
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
	
	public void keyReleased(char ch, int keyCode) {
		releaseKey(ch, transform(keyCode));
	}

}

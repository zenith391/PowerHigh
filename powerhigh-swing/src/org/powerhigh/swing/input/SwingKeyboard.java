package org.powerhigh.swing.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.powerhigh.graphics.Interface;
import org.powerhigh.input.AbstractKeyboard;

public class SwingKeyboard extends AbstractKeyboard implements KeyListener {

	
	
	public SwingKeyboard(Interface win) {
		super(win);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		typeKey(transform(e.getKeyCode()));
	}

	@Override
	public void keyPressed(KeyEvent e) {
		pressKey(transform(e.getKeyCode()));
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

	@Override
	public void keyReleased(KeyEvent e) {
		releaseKey(transform(e.getKeyCode()));
	}

}

package org.lggl.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import org.lggl.utils.KeyCodes;

public class Keyboard extends KeyCodes implements KeyListener {
	
	private ArrayList<Integer> downKeys = new ArrayList<Integer>();

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (downKeys.indexOf(key) == -1) {
			downKeys.add(key);
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (downKeys.indexOf(key) != -1) {
			downKeys.remove(downKeys.indexOf(key));
		}
	}

	public void keyTyped(KeyEvent e) {}
	
	public void setKeyDown(int key, boolean press) {
		if (press == false) {
			if (isKeyDown(key)) {
				downKeys.remove(downKeys.indexOf(key));
			}
		} else {
			if (!isKeyDown(key)) {
				downKeys.add(key);
			}
		}
	}

	public boolean isKeyDown(int key) {
		if (downKeys.indexOf(key) != -1) {
			return true;
		}
		return false;
	}

}

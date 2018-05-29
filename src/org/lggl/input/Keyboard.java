package org.lggl.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import org.lggl.utils.KeyCodes;

public class Keyboard extends KeyCodes implements KeyListener {
	static ArrayList<Integer> PressedKeys = new ArrayList<Integer>();
	static ArrayList<Integer> DownKeys = new ArrayList<Integer>();

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (DownKeys.indexOf(key) == -1) {
			PressedKeys.add(key);
			DownKeys.add(key);
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (PressedKeys.indexOf(key) != -1) {
			PressedKeys.remove(PressedKeys.indexOf(key));
			DownKeys.remove(DownKeys.indexOf(key));
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public boolean isKeyPressed(int key) {
		if (PressedKeys.indexOf(key) != -1) {
			return true;
		}
		return false;
	}

	public boolean isKeyDown(int key) {
		if (DownKeys.indexOf(key) != -1) {
			return true;
		}
		return false;
	}

}

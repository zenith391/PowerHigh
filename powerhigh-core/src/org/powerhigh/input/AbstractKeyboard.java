package org.powerhigh.input;

import java.util.ArrayList;

import org.powerhigh.graphics.Interface;

public abstract class AbstractKeyboard extends KeyCodes {
	
	private ArrayList<Integer> downKeys = new ArrayList<Integer>();
	private Interface win;
	
	public AbstractKeyboard(Interface win) {
		this.win = win;
	}

	protected void pressKey(char ch, int key) {
		if (downKeys.indexOf(key) == -1) {
			downKeys.add(key);
		}
		win.fireEvent("keyPressed", ch, key);
	}

	protected void releaseKey(char ch, int key) {
		if (downKeys.indexOf(key) != -1) {
			downKeys.remove(downKeys.indexOf(key));
		}
		win.fireEvent("keyReleased", ch, key);
	}

	protected void typeKey(char ch, int key) {
		win.fireEvent("keyTyped", ch, key);
	}
	
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
	
	public Integer[] getDownKeys() {
		return downKeys.toArray(new Integer[downKeys.size()]);
	}

	public boolean isKeyDown(int key) {
		if (downKeys.indexOf(key) != -1) {
			return true;
		}
		return false;
	}

}

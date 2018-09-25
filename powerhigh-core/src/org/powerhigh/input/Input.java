package org.powerhigh.input;

import java.util.HashMap;
import java.util.Map;

import org.powerhigh.graphics.Interface;

public class Input {

	private Interface win;
	private static AbstractKeyboard key;
	private static Mouse mouse;
	
	public static void setKeyboardImpl(AbstractKeyboard keyboard) {
		Input.key = keyboard;
	}
	
	public static void setMouseImpl(Mouse mouse) {
		Input.mouse = mouse;
	}
	
	private int lastUserX, lastUserY;
	
	public int getLastUserX() {
		return lastUserX;
	}

	public int getLastUserY() {
		return lastUserY;
	}

	private boolean useDelta = true;
	
	private Map<Integer, Float> keysAxisHorizental = null;
	private Map<Integer, Float> keysAxisVertical = null;
	
	public Input(Interface win) {
		this.win = win;
		keysAxisHorizental = new HashMap<>();
		keysAxisVertical = new HashMap<>();
		keysAxisHorizental.put(KeyCodes.KEY_Q, -1.0f);
		keysAxisHorizental.put(KeyCodes.KEY_D, 1.0f);
		keysAxisVertical.put(KeyCodes.KEY_Z, -1.0f);
		keysAxisVertical.put(KeyCodes.KEY_S, 1.0f);
	}
	
	public AbstractKeyboard getKeyboard() {
		return key;
	}
	
	public Mouse getMouse() {
		return mouse;
	}
	
	public float getAxis(String axis) {
		float ax = 0.0f;
		if (axis.equals("Horizental")) {
			for (int i : keysAxisHorizental.keySet()) {
				if (key.isKeyDown(i)) {
					ax = keysAxisHorizental.get(i);
				}
			}
		}
		if (axis.equals("Vertical")) {
			for (int i : keysAxisVertical.keySet()) {
				if (key.isKeyDown(i)) {
					ax = keysAxisVertical.get(i);
				}
			}
		}
		if (useDelta) {
			ax *= win.getEventThread().getDelta();
		}
		return ax;
	}

}

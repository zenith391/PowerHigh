package org.lggl.input;

import java.util.HashMap;
import java.util.Map;

import org.lggl.graphics.Window;

public class Input {

	private Window win;
	private Keyboard key;
	private Mouse mouse;
	
	private boolean useDelta;
	
	private Map<Integer, Float> keysAxis = null;
	
	public Input(Window win) {
		this.win = win;
		key = win.getKeyboard();
		mouse = win.getMouse();
		keysAxis = new HashMap<>();
	}
	
	
	
	public float getAxis(String axis) {
		float ax = 0.0f;
		if (useDelta) {
			ax *= win.getEventThread().getDelta();
		}
		return ax;
	}

}

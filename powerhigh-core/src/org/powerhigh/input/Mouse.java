package org.powerhigh.input;

import org.powerhigh.graphics.Interface;
import org.powerhigh.graphics.Texture;

public abstract class Mouse {
	
	private static int x = 0;
	private static int y = 0;
	private static int dx, dy;
	
	private static int screenX = 0;
	private static int screenY = 0;
	private static boolean clicked = false;
	private static boolean dragged = false;
	private static boolean[] pressedButtons = {false, false, false};
	
	private static boolean countDragAsMove = false;
	
	
	public static boolean doCountDragAsMove() {
		return countDragAsMove;
	}

	public static void setCountDragAsMove(boolean countDragAsMove) {
		Mouse.countDragAsMove = countDragAsMove;
	}

	static Interface window;

	public Mouse(int defaultX, int defaultY, Interface win) {
		x = defaultX;
		y = defaultY;
		window = win;
	}

	public void mouseClicked(int x, int y, int button) {
		clicked = true;
		Thread t = null;
		t = new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				clicked = false;
			}
		});
		t.setName("Click4Thread");
		t.setPriority(Thread.MIN_PRIORITY);
		t.start();
		
		window.fireEvent("mouseClicked", x, y, button - 1);
	}

	public void mousePressed(int button, int x, int y) {
		pressedButtons[button] = true;
		window.fireEvent("mousePressed", x, y, button - 1);
	}

	public void mouseReleased(int button, int x, int y) {
		pressedButtons[button] = false;
		window.fireEvent("mouseReleased", x, y, button - 1);
	}

	public void mouseDragged(int button, int x, int y, int screenX, int screenY) {
		window.fireEvent("mouseDragged", x, y, button);
		if (countDragAsMove) {
			Mouse.screenX = screenX;
			Mouse.x = x;
			Mouse.screenY = screenY;
			Mouse.y = y;
			window.fireEvent("mouseMoved", x, y, screenX, screenY);
		}
	}

	public static void clearMouseVelocity() {
		dx = 0;
		dy = 0;
	}
	
	public void mouseMoved(int x, int y, int screenX, int screenY) {
		Mouse.screenX = screenX;
		Mouse.x = x;
		Mouse.screenY = screenY;
		Mouse.y = y;
		
		window.fireEvent("mouseMoved", x, y);
	}

	public static int getDX() {
		return dx;
	}

	public static int getDY() {
		return dy;
	}

	public static boolean isClicking() {
		return clicked;
	}

	public static boolean isDragging() {
		return dragged;
	}
	
	public static boolean isButtonPressed(int buttonType) {
		if (buttonType > 2)
			return false;
		return pressedButtons[buttonType];
	}
	
	public static int getPressedButton() {
		int pressed = -1;
		for (int i = 0; i < pressedButtons.length; i++) {
			boolean bool = pressedButtons[i];
			if (bool == true) {
				pressed = i+1;
			}
		}
		return pressed;
	}
	
	public static boolean isPressed() {
		return isButtonPressed(0) || isButtonPressed(1) || isButtonPressed(2);
	}
	
	public static int getX() {
		return x;
	}
	
	public static int getY() {
		return y;
	}

	public static int getScreenX() {
		return screenX;
	}

	public static int getScreenY() {
		return screenY;
	}

	public void setCursorHidden(boolean hidden) {
		throw new UnsupportedOperationException("This method should be overriden.");
	}
	
	public void setGrabbed(boolean grab) {
		throw new UnsupportedOperationException("This method should be overriden.");
	}

	//@Deprecated
	public void setCursor(Texture cursor) {
		throw new UnsupportedOperationException("This method should be overriden.");
	}

}

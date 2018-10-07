package org.powerhigh.input;

import org.powerhigh.graphics.Interface;

public abstract class Mouse {
	
	private static int x = 0;
	private static int y = 0;
	private static int dx, dy;
	
	private static int screenX = 0;
	private static int screenY = 0;
	private static boolean clicked = false;
	private static boolean dragged = false;
	private static boolean[] pressedButtons = {false, false, false};
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

	public void mouseDragged(int button, int x, int y) {
		dragged = true;
//		screenX = m.getXOnScreen();
//		screenY = m.getYOnScreen();
		window.fireEvent("mouseDragged", x, y, button);
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		dragged = false;
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
		
//		if (grab) {
//			if (osx == 0) {
//				//osx = window.getJFrame().getX() + (window.getJFrame().getWidth() / 2);
//				//osy = window.getJFrame().getY() + (window.getJFrame().getHeight() / 2);
//			}
//			if (dx == 0) {
//				//dx = (m.getXOnScreen() - osx) -  window.getJFrame().getX();
//			}
//			if (dy == 0) {
//				//dy = (m.getYOnScreen() - osy) - window.getJFrame().getY();
//			}
//			grabRobot.mouseMove(osx, osy);
//		}
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

	public static void setCursorHidden(boolean hidden) {
		throw new UnsupportedOperationException("This method should be overriden.");
	}
	
	public static void setGrabbed(boolean grab) {
		throw new UnsupportedOperationException("This method should be overriden.");
	}

//	@Deprecated
//	public static void setCursor(Cursor cursor) {
//		throw new UnsupportedOperationException("This method should be overriden.");
//	}

}

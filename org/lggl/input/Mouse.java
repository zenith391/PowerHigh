package org.lggl.input;

import java.awt.event.MouseMotionListener;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.lggl.graphics.Window;

public class Mouse implements MouseListener, MouseMotionListener {
	
	private static int x = 0;
	private static int y = 0;
	private static boolean clicked = false;
	private static boolean dragged = false;
	private static boolean[] pressedButtons = {false, false, false};
	static Window window;
	static MouseMotionListener instance;
	static MouseListener instance2;

	public Mouse(int defaultX, int defaultY, Window win) {
		x = defaultX;
		y = defaultY;
		instance = this;
		instance2 = this;
		window = win;
	}

	public void mouseClicked(MouseEvent m) {
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
		t.setName("");
		t.start();
	}

	public void mouseEntered(MouseEvent m) {
		
	}

	public void mouseExited(MouseEvent m) {
		x = -1;
		y = -1;
		// Fix for buttons
	}

	public void mousePressed(MouseEvent m) {
		if (m.getButton() == MouseEvent.BUTTON1) {
			pressedButtons[0] = true;
		}
		if (m.getButton() == MouseEvent.BUTTON2) {
			pressedButtons[1] = true;
		}
		if (m.getButton() == MouseEvent.BUTTON3) {
			pressedButtons[2] = true;
		}
		window.fireEvent("mousePressed", m.getX(), m.getY());
	}

	public void mouseReleased(MouseEvent m) {
		if (m.getButton() == MouseEvent.BUTTON1) {
			pressedButtons[0] = false;
		}
		if (m.getButton() == MouseEvent.BUTTON2) {
			pressedButtons[1] = false;
		}
		if (m.getButton() == MouseEvent.BUTTON3) {
			pressedButtons[2] = false;
		}
		window.fireEvent("mouseReleased", m.getX(), m.getY());
	}

	@Override
	public void mouseDragged(MouseEvent m) {
		dragged = true;
		x = m.getX();
		y = m.getY();
		window.fireEvent("mouseDragged", x, y);
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		dragged = false;
	}

	@Override
	public void mouseMoved(MouseEvent m) {
		x = m.getX();
		y = m.getY();
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

	public static MouseMotionListener getListener() {
		return instance;
	}

	public static MouseListener getListener2() {
		return instance2;
	}

	public static void setCursorHidden(boolean hidden) {
		if (hidden) {
			Mouse.setCursor(Cursors.HIDDEN_CURSOR);
		} else {
			Mouse.setCursor(Cursors.DEFAULT_CURSOR);
		}
	}

	public static void setCursor(Cursor cursor) {
		window.getJFrame().setCursor(cursor);
	}

}

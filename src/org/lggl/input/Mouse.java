package org.lggl.input;

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.lggl.graphics.Window;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {
	
	private static int x = 0;
	private static int y = 0;
	private static int dx, dy;
	private static Robot grabRobot;
	private static boolean grab;
	
	private static int screenX = 0;
	private static int screenY = 0;
	private static boolean clicked = false;
	private static boolean dragged = false;
	private static boolean[] pressedButtons = {false, false, false};
	static Window window;

	public Mouse(int defaultX, int defaultY, Window win) {
		x = defaultX;
		y = defaultY;
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
		t.setName("Click4Thread");
		t.start();
		
		window.fireEvent("mouseClicked", m.getX(), m.getY(), m.getButton() - 1);
	}

	public void mouseEntered(MouseEvent m) {
		
	}

	public void mouseExited(MouseEvent m) {
		
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
		window.fireEvent("mousePressed", m.getX(), m.getY(), m.getButton() - 1);
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
		window.fireEvent("mouseReleased", m.getX(), m.getY(), m.getButton() - 1);
	}

	@Override
	public void mouseDragged(MouseEvent m) {
		dragged = true;
		x = m.getX();
		y = m.getY();
		screenX = m.getXOnScreen();
		screenY = m.getYOnScreen();
		window.fireEvent("mouseDragged", x, y, m.getButton() - 1);
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
	
	private static int osx;
	private static int osy;
	private int sdx;
	private int sdy;
	@Override
	public void mouseMoved(MouseEvent m) {
		x = m.getX();
		y = m.getY();
		screenX = m.getXOnScreen();
		screenY = m.getYOnScreen();
		
		window.fireEvent("mouseMoved", x, y);
		
		if (grab) {
			if (osx == 0) {
				osx = window.getJFrame().getX() + (window.getJFrame().getWidth() / 2);
				osy = window.getJFrame().getY() + (window.getJFrame().getHeight() / 2);
			}
			if (dx == 0) {
				dx = (m.getXOnScreen() - osx) -  window.getJFrame().getX();
			}
			if (dy == 0) {
				dy = (m.getYOnScreen() - osy) - window.getJFrame().getY();
			}
			grabRobot.mouseMove(osx, osy);
		}
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
		if (hidden) {
			Mouse.setCursor(Cursors.HIDDEN_CURSOR);
		} else {
			Mouse.setCursor(Cursors.DEFAULT_CURSOR);
		}
	}
	
	public static void setGrabbed(boolean grab) {
		if (grab) {
			try {
				if (grabRobot == null) {
					grabRobot = new Robot();
					grabRobot.mouseMove(osx, osy);
				}
				Mouse.grab = grab;
			} catch (AWTException e) {
				e.printStackTrace();
			}
		} else {
			grabRobot = null;
			Mouse.grab = false;
		}
	}

	public static void setCursor(Cursor cursor) {
		window.getJFrame().setCursor(cursor);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		
	}

}

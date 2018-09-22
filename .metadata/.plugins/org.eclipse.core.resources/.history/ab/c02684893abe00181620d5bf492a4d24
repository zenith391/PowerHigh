package org.powerhigh.swing;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import org.powerhigh.graphics.Interface;
import org.powerhigh.input.Mouse;
import org.powerhigh.objects.GameObject;

public class SwingObject extends GameObject {

	private JComponent content;
	private Point mouseLastPos;
	private boolean hasEntered;
	
	@Override
	public void paint(Graphics g, Interface source) {
		if (Mouse.getX() != mouseLastPos.x || Mouse.getY() != mouseLastPos.y) {
			mouseLastPos = new Point(Mouse.getX(), Mouse.getY());
			for (MouseMotionListener lis : content.getMouseMotionListeners()) {
				lis.mouseMoved(new MouseEvent(content, MouseEvent.MOUSE_MOVED, System.currentTimeMillis(), MouseEvent.BUTTON1_DOWN_MASK, Mouse.getX() - x, Mouse.getY() - y, 1, false));
			}
		}
		if (hasEntered) {
			if (!isInBounds(Mouse.getX(), Mouse.getY(), getX(), getY(), getWidth(), getHeight())) {
				hasEntered = false;
				for (MouseListener lis : content.getMouseListeners()) {
					lis.mouseExited(new MouseEvent(content, MouseEvent.MOUSE_EXITED, System.currentTimeMillis(), 0, Mouse.getX(), Mouse.getY(), 1, false));
				}
			}
		} else {
			if (isInBounds(Mouse.getX(), Mouse.getY(), getX(), getY(), getWidth(), getHeight())) {
				hasEntered = true;
				for (MouseListener lis : content.getMouseListeners()) {
					lis.mouseEntered(new MouseEvent(content, MouseEvent.MOUSE_ENTERED, System.currentTimeMillis(), 0, Mouse.getX(), Mouse.getY(), 1, false));
				}
			}
		}
		content.setSize(getSize());
		BufferedImage img = new BufferedImage(content.getWidth(), content.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		content.paint(img.createGraphics());
		g.drawImage(img, x, y, null);
	}
	
	public void onEvent(String name, Object... args) {
		int x = 0;
		int y = 0;
		if (name.contains("mouse")) {
			x = (int) args[0];
			y = (int) args[1];
		}
		
		if (name.equals("mousePressed")) {
			content.dispatchEvent(new MouseEvent(content, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), MouseEvent.BUTTON1_DOWN_MASK, x - this.x, y - this.y, Mouse.getScreenX(), Mouse.getScreenY(), 1, false, Mouse.getPressedButton()));
		}
		if (name.equals("mouseReleased")) {
			for (MouseListener lis : content.getMouseListeners()) {
				
				lis.mouseReleased(new MouseEvent(content, MouseEvent.MOUSE_RELEASED, System.currentTimeMillis(), Mouse.getPressedButton(), x - this.x, y - this.y, 1, false));
				content.dispatchEvent(new MouseEvent(content, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), Mouse.getPressedButton(), x - this.x, y - this.y, 1, false));
			}
		}
//		if (name.equals("mouseClicked")) {
//			content.dispatchEvent(new MouseEvent(content, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), Mouse.getPressedButton(), x, y, Mouse.getScreenX(), Mouse.getScreenY(), 1, false, Mouse.getPressedButton()));
////			for (MouseListener lis : content.getMouseListeners()) {
////				
////				lis.mouseClicked(new MouseEvent(content, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), Mouse.getPressedButton(), x, y, Mouse.getScreenX(), Mouse.getScreenY(), 1, false, Mouse.getPressedButton()));
////			}
//		}
	}
	
	public void setContent(JComponent c) {
		mouseLastPos = new Point(0, 0);
		content = c;
		setSize(c.getPreferredSize());
	}
	
	public static boolean isInBounds(double x, double y, double d, double e, double f, double g) {
		boolean cond1 = false;
		boolean cond2 = false;
		for (int i = (int) d; i < f + d; i++) {
			if (x == i) {
				cond1 = true;
			}
			for (int i2 = (int) e; i2 < g + e; i2++) {
				if (y == i2) {
					cond2 = true;
				}
			}
		}
		return cond1 && cond2;
	}
	
}

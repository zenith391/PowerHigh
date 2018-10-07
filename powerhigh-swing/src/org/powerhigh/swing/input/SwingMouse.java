package org.powerhigh.swing.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.powerhigh.graphics.Interface;
import org.powerhigh.input.Mouse;

public class SwingMouse extends Mouse implements MouseListener, MouseMotionListener {

	public SwingMouse(int defaultX, int defaultY, Interface win) {
		super(defaultX, defaultY, win);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		this.mouseDragged(e.getButton() - 1, e.getX(), e.getY());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.mouseMoved(e.getX(), e.getY(), e.getXOnScreen(), e.getYOnScreen());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		this.mouseClicked(e.getButton() - 1, e.getX(), e.getY());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.mousePressed(e.getButton() - 1, e.getX(), e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.mouseReleased(e.getButton() - 1, e.getX(), e.getY());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	
	
}

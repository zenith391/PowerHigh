package org.lggl.graphics;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ConcurrentModificationException;

import javax.swing.JPanel;

import org.lggl.graphics.objects.GameObject;

class WindowPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private Window win;

	WindowPanel(Window w) {
		setBackground(Color.BLACK);
		win = w;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Window.getRenderer().render(win, (Graphics2D) g);
	}
}

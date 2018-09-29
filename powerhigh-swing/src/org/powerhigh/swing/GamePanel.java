package org.powerhigh.swing;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel {

	private SwingInterfaceImpl intr;
	
	public GamePanel(SwingInterfaceImpl intr) {
		this.intr = intr;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		SwingInterfaceImpl.getRenderer().render(intr, new JDrawer2D((Graphics2D) g));
	}
	
}

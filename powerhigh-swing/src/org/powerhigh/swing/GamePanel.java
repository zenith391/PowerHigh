package org.powerhigh.swing;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private SwingInterfaceImpl intr;
	private JDrawer2D drawer2D;
	
	public GamePanel(SwingInterfaceImpl intr) {
		this.intr = intr;
		this.setDoubleBuffered(false);
	}
	
	public void paint(Graphics g) {
		//super.paint(g);
		if (drawer2D == null) {
			drawer2D = new JDrawer2D((Graphics2D) g);
		} else {
			if (drawer2D.getGraphics() != g) {
				drawer2D.setGraphics((Graphics2D) g);
			}
		}
		SwingInterfaceImpl.getRenderer().render(intr, drawer2D);
	}
	
}

package org.powerhigh.swing;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private SwingInterfaceImpl intr;
	private JDrawer2D drawer2D;
	
	private Composite composite = new Composite() {
		
		CompositeContext ctx;
		
		@Override
		public CompositeContext createContext(ColorModel srcColorModel, ColorModel dstColorModel, RenderingHints hints) {
			AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER);
			System.out.println("src: " + srcColorModel);
			System.out.println("dst: " + dstColorModel);
			if (ctx == null) {
				ctx = new CompositeContext() {
	
					CompositeContext alphaCtx = alpha.createContext(srcColorModel, dstColorModel, hints);
					
					@Override
					public void compose(Raster src, Raster dstIn, WritableRaster dstOut) {
						alphaCtx.compose(src, dstIn, dstOut);
					}
	
					@Override
					public void dispose() {
						//System.exit(0);
						alphaCtx.dispose();
					}
					
				};
			}
			return ctx;
		}
		
	};
	
	public GamePanel(SwingInterfaceImpl intr) {
		this.intr = intr;
		//this.setDoubleBuffered(false);
	}
	
	public void paint(Graphics g) {
		//super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		if (intr.getPostProcessor() != null)
			g2d.setComposite(AlphaComposite.SrcOver);
		
		if (drawer2D == null) {
			drawer2D = new JDrawer2D(g2d);
		} else {
			if (drawer2D.getGraphics() != g2d) {
				drawer2D.setGraphics(g2d);
			}
		}
		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		SwingInterfaceImpl.getRenderer().render(intr, drawer2D);
	}
	
}

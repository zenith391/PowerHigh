package org.powerhigh.swing;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import javax.swing.JPanel;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

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
	
	private int[] unpackIntArray(ScriptObjectMirror mirror) {
		String[] keys = mirror.getOwnKeys(false);
		int[] array = new int[keys.length];
		for (int i = 0; i < keys.length; i++) {
			array[i] = ((Number) mirror.get(keys[i])).intValue();
		}
		return array;
	}
	
	private BufferedImage img;
	public void paint(Graphics g) {
		//super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(new Paint() {

			@Override
			public int getTransparency() {
				return Transparency.TRANSLUCENT;
			}

			@Override
			public PaintContext createContext(ColorModel cm, Rectangle deviceBounds, Rectangle2D userBounds,
					AffineTransform xform, RenderingHints hints) {
				System.out.println("new context");
				return new PaintContext() {

					@Override
					public void dispose() {
						
					}

					@Override
					public ColorModel getColorModel() {
						return cm;
					}

					@Override
					public Raster getRaster(int x, int y, int w, int h) {
						System.out.println("raster " + x + ", " + y + ", " + w + ", " + h);
						return null;
					}
					
				};
			}
			
		});
		if (drawer2D == null) {
			drawer2D = new JDrawer2D(g2d);
		} else {
			if (drawer2D.getGraphics() != g2d) {
				drawer2D.setGraphics(g2d);
			}
		}
		
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		SwingInterfaceImpl.getRenderer().render(intr, drawer2D);
	}
	
}

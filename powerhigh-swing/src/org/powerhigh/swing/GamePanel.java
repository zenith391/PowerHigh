package org.powerhigh.swing;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
		
		if (drawer2D == null) {
			drawer2D = new JDrawer2D(g2d);
		} else {
			if (drawer2D.getGraphics() != g2d) {
				drawer2D.setGraphics(g2d);
			}
		}
		
		try {
			if (intr.getPostProcessor() != null && intr.hasMethod("color")) {
				if (img == null || img.getWidth() != intr.getWidth() || img.getHeight() != intr.getHeight())
					img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
				drawer2D.setGraphics(img.createGraphics(), false);
				SwingInterfaceImpl.getRenderer().render(intr, drawer2D);
				for (int y = 0; y < img.getHeight(); y++) {
					for (int x = 0; x < img.getWidth(); x++) {
						int rgb = img.getRGB(x, y);
						int red = (rgb >> 16) & 0xFF;
						int green = (rgb >> 8) & 0xFF;
						int blue = rgb & 0xFF;
						int[] array = unpackIntArray((ScriptObjectMirror)
								intr.invokeShader("color", x, y, red, green, blue));
						rgb = array[0];
						rgb = (rgb << 8) + array[1];
						rgb = (rgb << 8) + array[2];
						img.setRGB(x, y, rgb);
					}
				}
				g2d.drawImage(img, 0, 0, null);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		SwingInterfaceImpl.getRenderer().render(intr, drawer2D);
	}
	
}

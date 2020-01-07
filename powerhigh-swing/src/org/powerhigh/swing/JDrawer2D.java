package org.powerhigh.swing;

import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.powerhigh.graphics.Drawer;
import org.powerhigh.graphics.Font;
import org.powerhigh.graphics.Texture;
import org.powerhigh.swing.input.SwingTexturePlugin.SwingGPUTexture;
import org.powerhigh.utils.Color;

public class JDrawer2D extends Drawer {

	private Graphics2D g2d;
	private AffineTransform state;
	private Map<Texture, Image> cache = new WeakHashMap<>();
	private Map<Font, java.awt.Font> fonts = new WeakHashMap<>();
	private Font font;
	
	public void setGraphics(Graphics2D g2d, boolean dispose) {
		if (this.g2d != null && dispose) {
			this.g2d.dispose();
		}
		this.g2d = g2d;
	}
	
	public void setGraphics(Graphics2D g2d) {
		setGraphics(g2d, true);
	}
	
	public JDrawer2D(Graphics2D g2d) {
		setGraphics(g2d);
	}
	
	public Graphics2D getGraphics() {
		return g2d;
	}

	@Override
	public void fillRect(int x, int y, int width, int height) {
		g2d.fillRect(x, y, width, height);
	}

	@Override
	public void setColor(Color color) {
		g2d.setColor(SwingUtils.phColorToAwtColor(color));
	}

	@Override
	public Color getColor() {
		return SwingUtils.awtColorTophColor(g2d.getColor());
	}

	@Override
	public void drawTexture(int x, int y, Texture texture) {
		drawTexture(x, y, texture.getWidth(), texture.getHeight(), texture);
	}
	
	private Image decodeTexture(Texture tex) {
		if (tex.getAccelerator() != null) {
			if (tex.getAccelerator() instanceof SwingGPUTexture) {
				SwingGPUTexture g = (SwingGPUTexture) tex.getAccelerator();
				return g.getImage(); // use the volatile image
			}
		}
		BufferedImage img = new BufferedImage(tex.getWidth(), tex.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < tex.getWidth(); x++) {
			for (int y = 0; y < tex.getHeight(); y++) {
				img.setRGB(x, y, tex.getRGB(x, y));
			}
		}
		System.gc();
		return img;
	}

	@Override
	public void clearCache() {
		cache.clear();
	}

	@Override
	public void clearTextureFromCache(Texture texture) {
		cache.put(texture, null);
	}

	@Override
	public void drawText(int x, int y, String string) {
		g2d.drawString(string, x, y);
	}

	@Override
	public void localRotate(double radians, int orx, int ory) {
		g2d.rotate(radians, orx, ory);
	}

	@Override
	public void saveState() {
		state = g2d.getTransform();
	}

	@Override
	public void restoreState() {
		g2d.setTransform(state);
	}

	@Override
	public void drawTexture(int x, int y, int width, int height, Texture texture) {
		Image img = cache.get(texture);
		if (img == null) {
			img = decodeTexture(texture);
			if (cacheEnabled) {
				cache.put(texture, img);
			}
		}
		if (img != null) {
			g2d.drawImage(img, x, y, width, height, null);
		}
	}

	@Override
	public void translate(int x, int y) {
		g2d.translate(x, y);
	}

	@Override
	public int getEstimatedWidth(String text) {
		return g2d.getFontMetrics().stringWidth(text);
	}

	@Override
	public int getEstimatedHeight() {
		return g2d.getFontMetrics().getHeight();
	}

	@Override
	public boolean supportsTextEstimations() {
		return true;
	}

	@Override
	public void drawLine(int x, int y, int x2, int y2) {
		g2d.drawLine(x, y, x2, y2);
	}

	@Override
	public void drawRect(int x, int y, int width, int height) {
		g2d.drawRect(x, y, width, height);
	}

	@Override
	public void drawCircle(int x, int y, int radius) {
		g2d.drawOval(x, y, radius, radius);
	}

	@Override
	public void fillCircle(int x, int y, int radius) {
		g2d.fillOval(x, y, radius, radius);
	}

	@Override
	public void drawOval(int x, int y, int width, int height) {
		g2d.drawOval(x, y, width, height);
	}

	@Override
	public void fillOval(int x, int y, int width, int height) {
		g2d.fillOval(x, y, width, height);
	}

	@Override
	public Font getFont() {
		return font;
	}

	@Override
	public void setFont(Font font) {
		this.font = font;
		if (font.getFamily() == null) {
			if (!fonts.containsKey(font) || fonts.get(font).getSize2D() == font.getSize()) {
				try {
					fonts.put(font, java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new File(font.getPath()))
							.deriveFont(font.getSize()));
				} catch (FontFormatException | IOException e) {
					System.err.println("Invalid font " + font.getPath());
				}
			}
			g2d.setFont(fonts.get(font));
		} else {
			g2d.setFont(java.awt.Font.getFont(font.getFamily())
					.deriveFont(font.getSize()));
		}
	}
	
}

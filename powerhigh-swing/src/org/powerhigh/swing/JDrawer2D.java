package org.powerhigh.swing;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.WeakHashMap;

import org.powerhigh.graphics.Drawer;
import org.powerhigh.graphics.Texture;
import org.powerhigh.utils.Color;

public class JDrawer2D extends Drawer {

	private Graphics2D g2d;
	private AffineTransform state;
	private Map<Texture, BufferedImage> cache = new WeakHashMap<>();
	
	public JDrawer2D(Graphics2D g2d) {
		this.g2d = g2d;
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
	
	private BufferedImage decodeTexture(Texture tex) {
		BufferedImage img = new BufferedImage(tex.getWidth(), tex.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < tex.getWidth(); x++) {
			for (int y = 0; y < tex.getHeight(); y++) {
				img.setRGB(x, y, tex.getRGB(x, y));
			}
		}
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
		BufferedImage img = cache.get(texture);
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
	
}
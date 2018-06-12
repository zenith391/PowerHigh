package org.lggl.graphics;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.lggl.input.Mouse;
import org.lggl.utils.debug.DebugLogger;

public class DefaultPostProcessor extends PostProcessor {
	
	public boolean enableLighting = false;
	
	public DefaultPostProcessor() {

	}
	
	public static BufferedImage updateLightLevels(BufferedImage img, BufferedImage original, int x, int y, int width, int height, float light)
	{
	    Graphics2D g2d = img.createGraphics();
	    if (x + width > img.getWidth()) {
	    	width -= (x + width) - img.getWidth();
	    }
	    if (y + height > img.getHeight()) {
	    	height -= (y + height) - img.getHeight();
	    }
	    try {
	    	g2d.drawImage(original.getSubimage(x, y, width, height), x, y, null);
	    	g2d.setColor(new Color(0, 0, 0, light));
			g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
	    } catch (Exception e) {
	    	
	    }
	    return img;
	}
	
	public static BufferedImage shadowImage(BufferedImage img) {
		Graphics2D g2d = img.createGraphics();
		g2d.setColor(new Color(0, 0, 0, .94f));
		g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
		return img;
	}
	
	@Override
	public BufferedImage process(BufferedImage src) {
		int x = Mouse.getX();
		int y = Mouse.getY();
		if (x < 0) x = 0;
		if (y < 0) y = 0;
		int radius = 200;
		if (enableLighting) {
			BufferedImage lighting = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);
			lighting.createGraphics().drawImage(src, 0, 0, null);
			lighting = shadowImage(lighting);
			lighting = updateLightLevels(lighting, src, x, y, radius, radius, 0.5f);
			return lighting;
		} else {
			return src;
		}
	}

}

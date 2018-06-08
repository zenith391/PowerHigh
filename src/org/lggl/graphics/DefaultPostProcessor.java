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

	BufferedImage shadowedImage;
	int acc = 0;
	
	public DefaultPostProcessor() {

	}
	
	public static BufferedImage updateLightLevels(BufferedImage img, BufferedImage original, int x, int y, int width, int height, float light)
	{
	    Graphics2D g2d = img.createGraphics();
	    try {
	    	g2d.drawImage(original.getSubimage(x, y, width, height), x, y, null);
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
		int radius = 100;

		int x0 = x - radius;
		int x1 = x + radius;
		int y0 = y - radius;
		int y1 = y + radius;

		if (x0 < 0)
			x0 = 0;
		if (y0 < 0)
			y0 = 0;
		if (x1 > src.getWidth())
			x1 = src.getWidth();
		if (y1 > src.getHeight())
			y1 = src.getHeight();

		BufferedImage lighting = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_RGB);
		BufferedImage clone = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_RGB);
		clone.createGraphics().drawImage(src, 0, 0, null);
		lighting = clone;
//		lighting = shadowImage(clone);
//		lighting = updateLightLevels(lighting, src, x, y, radius, radius, 1.0f);

//		Graphics2D gl = lighting.createGraphics();
//		gl.setColor(new Color(0, 0, 0, 255));
//		//gl.fillRect(0, 0, 1920, 1080);
//		gl.setColor(new Color(0, 0, 0, 255));
//
//		BufferedImage lightImage = new BufferedImage(620, 480, BufferedImage.TYPE_INT_RGB);
//		lightImage.getGraphics().setColor(Color.white);
//		lightImage.getGraphics().fillRect(0, 0, 620, 480);
//		Composite oldComp = gl.getComposite();
//		for (int i = 0; i < 2; i++) {
//			Point2D.Double pt = new Point2D.Double(x, y);
//			gl.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.75f));
//			//gl.drawImage(lighting, (int) pt.x, (int) pt.y, radius * 1, radius * 1, null);
//			gl.setColor(Color.white);
//			gl.fillOval((int) pt.x, (int) pt.y, radius * 1, radius * 1);
//			}
//
//		gl.setComposite(oldComp);
//		gl.dispose();
		return lighting;
	}

}

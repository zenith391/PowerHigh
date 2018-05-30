package org.lggl.graphics;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import org.lggl.input.Mouse;
import org.lggl.utils.debug.DebugLogger;

public class DefaultPostProcessor extends PostProcessor {

	BufferedImage shadowedImage;
	int acc = 0;
	
	public DefaultPostProcessor() {

	}
	
	public static BufferedImage updateLightLevels(BufferedImage img, int x, int y, int Width, int Height, float light)
	{
	    BufferedImage brightnessBuffer = img;

	    for(int i = x; i < x + Width; i++)
	    {
	        for(int a = y; a < y + Height; a++)
	        {
	        	
	            //get the color at the pixel
	        	int rgb = 0 ;
	        	try {
	            rgb = brightnessBuffer.getRGB(i, a);
	        	} catch (Exception e) {
	        		//DebugLogger.logInfo(i + ", " + a);
	        	}

	            //check to see if it is transparent
	            int alpha = (rgb >> 24) & 0x000000FF;

	            if(alpha != 0)
	            {
	                //make a new color
	                Color rgbColor = new Color(rgb);

	                //turn it into an hsb color
	                float[] hsbCol = Color.RGBtoHSB(rgbColor.getRed(), rgbColor.getGreen(), rgbColor.getBlue(), null);

	                //lower it by the certain amount
	                //if the pixel is already darker then push it all the way to black
	                if(hsbCol[2] <= light)
	                    hsbCol[2] -= (hsbCol[2]) - .01f;
	                else
	                    hsbCol[2] -= light;

	                //turn the hsb color into a rgb color
	                int rgbNew = Color.HSBtoRGB(hsbCol[0], hsbCol[1], hsbCol[2]);

	                //set the pixel to the new color
	                brightnessBuffer.setRGB(i, a, rgbNew);
	            }


	        }
	    }

	    return brightnessBuffer;
	}

	@Override
	public BufferedImage process(BufferedImage src) {
		int x = Mouse.getX();
		int y = Mouse.getY();
		if (x < 0) x = 0;
		if (y < 0) y = 0;
		int radius = 500;

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
		lighting = src;

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

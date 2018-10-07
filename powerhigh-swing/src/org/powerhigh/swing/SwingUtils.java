package org.powerhigh.swing;

import java.awt.image.BufferedImage;

import org.powerhigh.graphics.Texture;
import org.powerhigh.utils.Color;

public class SwingUtils {

	public static Color awtColorTophColor(java.awt.Color src) {
		return new Color(src.getRed(), src.getGreen(), src.getBlue(), src.getAlpha());
	}
	
	public static java.awt.Color phColorToAwtColor(Color src) {
		return new java.awt.Color(src.getRed(), src.getGreen(), src.getBlue(), src.getAlpha());
	}
	
	public static BufferedImage fromTexture(Texture tex) {
		BufferedImage img = new BufferedImage(tex.getWidth(), tex.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < tex.getWidth(); x++) {
			for (int y = 0; y < tex.getHeight(); y++) {
				img.setRGB(x, y, tex.getRGB(x, y));
			}
		}
		return img;
	}
	
	public static Texture toTexture(BufferedImage img) {
		int[][] pixels = new int[img.getWidth()][img.getHeight()];
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				pixels[x][y] = img.getRGB(x, y);
			}
		}
		return new Texture(pixels);
	}
	
}

package org.powerhigh.graphics;

import org.powerhigh.utils.Color;

public class Texture {
	
	private int[][] pixels;
	private int width, height;
	private TextureAccelerator accelerator;
	
	public Texture(int[][] pixels) {
		this(pixels, null);
	}
	
	public Texture(int[][] pixels, TextureAccelerator acc) {
		accelerator = acc;
		this.pixels = pixels;
		if (pixels == null) {
			width = acc.getWidth();
			height = acc.getHeight();
			accelerator = acc;
		} else {
			width = pixels.length;
			height = pixels[0].length;
		}
	}
	
	public TextureAccelerator getAccelerator() {
		return accelerator;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getRGB(int x, int y) {
		if (accelerator == null) {
			return pixels[x][y];
		} else {
			return accelerator.getRGB(x, y);
		}
	}
	
	public void setRGB(int x, int y, int rgb) {
		if (accelerator == null) {
			pixels[x][y] = rgb;
		} else {
			accelerator.setRGB(x, y, rgb);
		}
	}
	
	private void softwareFillRect(int x, int y, int width, int height, int rgb) {
		for (int i = x; i < x + width; i++) {
			for (int j = y; j < y + height; j++) {
				setRGB(i, j, rgb);
			}
		}
	}
	
	public void fillRect(int x, int y, int width, int height, int rgb) {
		if (accelerator == null) {
			softwareFillRect(x, y, width, height, rgb);
		} else {
			if (accelerator.supportsFillRect()) {
				accelerator.fillRect(x, y, width, height, rgb);
			} else {
				softwareFillRect(x, y, width, height, rgb);
			}
		}
	}
	
	public Color getColor(int x, int y) {
		return new Color(getRGB(x, y), true);
	}
	
	public Texture getSubTexture(int x, int y, int width, int height) {
		int[][] np = new int[width][height];
		for (int i = x; i < x+width; i++) {
			for (int j = y; j < y+height; j++) {
				if (accelerator != null)
					np[i-x][j-y] = accelerator.getRGB(i, j); // the subtexture will lose acceleration
				else
					np[i-x][j-y] = pixels[i][j];
			}
		}
		return new Texture(np);
	}
	
}

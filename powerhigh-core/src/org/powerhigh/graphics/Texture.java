package org.powerhigh.graphics;

import org.powerhigh.utils.Color;

public class Texture {
	
	private int[][] pixels;
	private int width, height;
	
	public Texture(int[][] pixels) {
		this.pixels = pixels;
		width = pixels.length;
		height = pixels[0].length;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getRGB(int x, int y) {
		return pixels[x][y];
	}
	
	public Color getColor(int x, int y) {
		return new Color(getRGB(x, y), true);
	}
	
	public Texture getSubTexture(int x, int y, int width, int height) {
		int[][] np = new int[width][height];
		for (int i = x; i < x+width; i++) {
			for (int j = y; j < y+height; j++) {
				np[i-x][j-y] = pixels[i][j];
			}
		}
		return new Texture(np);
	}
	
}

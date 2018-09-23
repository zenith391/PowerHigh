package org.powerhigh.graphics;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class Texture {
	
	private int[][] pixels;
	private int width, height;
	
	public Texture(int[][] pixels) {
		this.pixels = pixels;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Texture getSubTexture(int x, int y, int width, int height) {
		return new Texture(img.getSubimage(x, y, width, height));
	}
	
}

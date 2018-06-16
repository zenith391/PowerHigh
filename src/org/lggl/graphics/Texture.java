package org.lggl.graphics;

import java.awt.image.BufferedImage;

public class Texture {

	private BufferedImage img;
	
	Texture(BufferedImage img) {
		this.img = img;
	}
	
	public BufferedImage getAWTImage() {
		return img;
	}
	
	public int getWidth() {
		return img.getWidth();
	}
	
	public int getHeight() {
		return img.getHeight();
	}
	
	public Texture getSubTexture(int x, int y, int width, int height) {
		return new Texture(img.getSubimage(x, y, width, height));
	}
	
}

package org.lggl.graphics.renderers.lightning;

import java.awt.image.BufferedImage;

public class LightningRenderBuffer {

	private BufferedImage img;
	
	public LightningRenderBuffer(BufferedImage img) {
		this.img = img;
	}

	public BufferedImage getImage() {
		return img;
	}
	
}

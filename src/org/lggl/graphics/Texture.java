package org.lggl.graphics;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

public class Texture {

	private BufferedImage img;
	
	public static byte[] imageToBytes(BufferedImage img) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(img, "png", baos);
		} catch (Exception e) {
			System.err.println("Erorr when serializing BufferedImage");
			e.printStackTrace();
		}
		return baos.toByteArray();
	}
	
	public static BufferedImage imageFromBytes(byte[] b, boolean decode) {
		if (decode) {
			b = Base64.getDecoder().decode(b);
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(b);
		try {
			BufferedImage img = ImageIO.read(bais);
			bais.close();
			return img;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static BufferedImage imageFromBase64(String b64) {
		return imageFromBytes(Base64.getDecoder().decode(b64), false);
	}
	
	/**
	 * Private API constructor.<br/>
	 * Please <b>do not use!</b> Unless you're sure of this.
	 */
	public Texture(BufferedImage img) {
		this.img = img;
	}
	
	public Texture(byte[] serialized, boolean decode) {
		this.img = imageFromBytes(serialized, decode);
	}
	
	public Texture(String serialized) {
		this.img = imageFromBase64(serialized);
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

package org.powerhigh.swing.input;

import java.awt.AWTException;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.ImageCapabilities;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.powerhigh.graphics.Texture;
import org.powerhigh.graphics.TextureAccelerator;
import org.powerhigh.graphics.TextureLoader;

public class SwingTexturePlugin implements TextureLoader.TextureLoaderPlugin {

	public class SwingGPUTexture extends TextureAccelerator {

		VolatileImage img;
		BufferedImage snapshot;
		
		public VolatileImage getImage() {
			return img;
		}
		
		SwingGPUTexture(VolatileImage img) {
			this.img = img;
		}
		
		@Override
		public int getRGB(int x, int y) {
			if (snapshot == null) {
				snapshot = img.getSnapshot();
			}
			return snapshot.getRGB(x, y);
		}

		@Override
		public void setRGB(int x, int y, int rgb) {
			
		}

		@Override
		public int getWidth() {
			return img.getWidth();
		}

		@Override
		public int getHeight() {
			return img.getHeight();
		}

		@Override
		public boolean isFullyAccelerated() {
			return true;
		}

		@Override
		public boolean tryAccelerate() {
			return true;
		}

		@Override
		public boolean canLose() {
			return true;
		}

		@Override
		public boolean isLost() {
			return img.contentsLost();
		}
		
	}
	
	@Override
	public Texture load(InputStream input) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		VolatileImage vol = null;
		boolean tryAccelerate = Boolean.parseBoolean(System.getProperty("powerhigh.swing.volatile", "false"));
		try {
			if (!tryAccelerate)
				throw new AWTException("volatile image not enabled");
			vol = GraphicsEnvironment
					.getLocalGraphicsEnvironment()
					.getDefaultScreenDevice()
					.getDefaultConfiguration()
					.createCompatibleVolatileImage(img.getWidth(), img.getHeight(), new ImageCapabilities(true), VolatileImage.BITMASK);
			vol.getGraphics().drawImage(img, 0, 0, null); // copy image to it
		} catch (HeadlessException | AWTException e) {
			if (tryAccelerate || true)
				e.printStackTrace();
			// could not accelerate
			int[][] pixels = new int[img.getWidth()][img.getHeight()];
			for (int x = 0; x < img.getWidth(); x++) {
				for (int y = 0; y < img.getHeight(); y++) {
					pixels[x][y] = img.getRGB(x, y);
				}
			}
			return new Texture(pixels);
		}
		return new Texture(null, new SwingGPUTexture(vol));
	}

	@Override
	public Texture load(String path) {
		try {
			return load(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}

package org.powerhigh.swing.input;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.ByteArrayInputStream;
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
		byte[] imageData;
		
		void checkContent() {
			if (img.contentsLost()) {
				System.out.println("Content Lost! Rebuilding image content..");
				BufferedImage i = null;
				try {
					ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
					i = ImageIO.read(bais);
					bais.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				Graphics2D g = img.createGraphics();
				if (img.getTransparency() == Transparency.TRANSLUCENT)
					g.setBackground(new Color(0, 0, 0, 0));
				g.clearRect(0, 0, img.getWidth(), img.getHeight());
				g.drawImage(i, 0, 0, null); // copy image to it
				g.dispose();
			}
		}
		
		public VolatileImage getImage() {
			return img;
		}
		
		SwingGPUTexture(VolatileImage img, byte[] imageData) { // storing image data uses less memory than storing each pixel.
			this.img = img;
			this.imageData = imageData;
			checkContent();
		}
		
		@Override
		public int getRGB(int x, int y) {
			if (snapshot == null) {
				checkContent();
				snapshot = img.getSnapshot();
			}
			return snapshot.getRGB(x, y);
		}

		@Override
		public void setRGB(int x, int y, int rgb) {
			Graphics2D g2d = img.createGraphics();
			g2d.setColor(new Color(rgb));
			g2d.fillRect(x, y, 1, 1);
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
			checkContent();
			return img.contentsLost();
		}
		
	}
	
	@Override
	public Texture load(InputStream input) {
		BufferedImage img = null;
		byte[] imageData = null;
		try {
			imageData = input.readAllBytes();
			ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
			img = ImageIO.read(bais);
			bais.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		VolatileImage vol = null;
		boolean tryAccelerate = Boolean.parseBoolean(System.getProperty("powerhigh.swing.volatile", "true"));
		try {
			if (!tryAccelerate)
				throw new AWTException("volatile image not enabled");
			GraphicsConfiguration conf = GraphicsEnvironment
					.getLocalGraphicsEnvironment()
					.getDefaultScreenDevice()
					.getDefaultConfiguration();
			vol = conf.createCompatibleVolatileImage(img.getWidth(), img.getHeight(), img.getTransparency());
			vol.validate(conf);
			Graphics2D g = vol.createGraphics();
			if (vol.getTransparency() == Transparency.TRANSLUCENT)
				g.setBackground(new Color(0, 0, 0, 0));
			g.clearRect(0, 0, vol.getWidth(), vol.getHeight());
			g.drawImage(img, 0, 0, null); // copy image to it
			g.dispose();
		} catch (HeadlessException | AWTException e) {
			if (tryAccelerate) {
				System.err.println("Could not load volatile images.");
				e.printStackTrace();
			}
			// could not accelerate
			int[][] pixels = new int[img.getWidth()][img.getHeight()];
			for (int x = 0; x < img.getWidth(); x++) {
				for (int y = 0; y < img.getHeight(); y++) {
					pixels[x][y] = img.getRGB(x, y);
				}
			}
			return new Texture(pixels);
		}
		return new Texture(null, new SwingGPUTexture(vol, imageData));
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

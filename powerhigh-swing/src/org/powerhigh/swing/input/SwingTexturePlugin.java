package org.powerhigh.swing.input;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.powerhigh.graphics.Texture;
import org.powerhigh.graphics.TextureLoader;

public class SwingTexturePlugin implements TextureLoader.TextureLoaderPlugin {

	@Override
	public Texture load(InputStream input) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int[][] pixels = new int[img.getWidth()][img.getHeight()];
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				pixels[x][y] = img.getRGB(x, y);
			}
		}
		return new Texture(pixels);
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

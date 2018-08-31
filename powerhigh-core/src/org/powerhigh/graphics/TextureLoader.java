package org.powerhigh.graphics;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class TextureLoader {

	public static Texture getTexture(File file) throws IOException {
		return getTexture(new FileInputStream(file));
	}
	
	public static Texture getTexture(InputStream is) throws IOException {
		return new Texture(ImageIO.read(is));
	}
	
}

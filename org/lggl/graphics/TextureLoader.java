package org.lggl.graphics;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TextureLoader {

	public static Texture getTexture(File file) throws IOException {
		return new Texture(ImageIO.read(file));
	}
	
}

package org.powerhigh.jfx.input;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.powerhigh.graphics.Texture;
import org.powerhigh.graphics.TextureLoader.TextureLoaderPlugin;

import javafx.beans.value.ChangeListener;
import javafx.scene.image.Image;

public class JFXTexturePlugin implements TextureLoaderPlugin {

	@Override
	public Texture load(InputStream input) {
		Image img = new Image(input);
		img.progressProperty().addListener((ChangeListener<Number>) (value, old, ne) -> {
			
		});
		int[][] pixels = new int[(int) img.getWidth()][(int) img.getHeight()];
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				pixels[x][y] = img.getPixelReader().getArgb(x, y);
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

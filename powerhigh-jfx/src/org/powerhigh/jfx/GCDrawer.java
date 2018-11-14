package org.powerhigh.jfx;

import java.util.HashMap;

import org.powerhigh.graphics.Drawer;
import org.powerhigh.graphics.Texture;
import org.powerhigh.utils.Color;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;

public class GCDrawer extends Drawer {

	private GraphicsContext gc;
	private Color color;
	private HashMap<Texture, WritableImage> texCache = new HashMap<>();

	public void setGC(GraphicsContext gc) {
		this.gc = gc;
	}

	@Override
	public void fillRect(int x, int y, int width, int height) {
		gc.fillRect(x, y, width, height);
	}

	@Override
	public void setColor(Color color) {
		// System.out.println(color);
		if (color != null) {
			this.color = color;
			gc.setFill(javafx.scene.paint.Color.rgb(color.getRed(), color.getGreen(), color.getBlue()));
		}
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void drawTexture(int x, int y, Texture texture) {
		drawTexture(x, y, texture.getWidth(), texture.getHeight(), texture);
	}

	@Override
	public void drawTexture(int x, int y, int width, int height, Texture texture) {
		if (texCache.get(texture) == null) {
			texCache.put(texture, decodeTexture(texture));
		}
		gc.drawImage(texCache.get(texture), x, y, width, height);
	}

	private WritableImage decodeTexture(Texture t) {
		System.out.println("Compiling texture..");
		WritableImage img = new WritableImage(t.getWidth(), t.getHeight());
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				int rgb = t.getRGB(x, y);
				int alpha = 255;
				int red = 0;
				int green = 0;
				int blue = 0;
				if (rgb > Integer.decode("0x000000") || false) {
					red = (rgb >> 24) & 0xFF;
					green = (rgb >> 16) & 0xFF;
					blue = (rgb >> 8) & 0xFF;
					alpha = (rgb) & 0xFF;
				} else {
					red = (rgb >> 16) & 0xFF;
					green = (rgb >> 8) & 0xFF;
					blue = (rgb) & 0xFF;
				}
				img.getPixelWriter().setColor(x, y,
						javafx.scene.paint.Color.rgb(red, green, blue, ((double) alpha / 255)));
			}
		}
		System.gc();
		return img;
	}

	@Override
	public void clearCache() {
		texCache.clear();
	}

	@Override
	public void clearTextureFromCache(Texture texture) {
		texCache.remove(texture);
	}

	@Override
	public void drawText(int x, int y, String string) {
		gc.fillText(string, x, y);
	}

	@Override
	public int getEstimatedWidth(String text) {
		return 0;
	}

	@Override
	public int getEstimatedHeight() {
		return 0;
	}

	@Override
	public boolean supportsTextEstimations() {
		return false;
	}

	@Override
	public void localRotate(double radians, int orx, int ory) {
		// gc.rotate(Math.toDegrees(radians));
	}

	@Override
	public void translate(int x, int y) {
		gc.translate(x, y);
	}

	@Override
	public void saveState() {
		gc.save();
	}

	@Override
	public void restoreState() {
		gc.restore();
	}

}

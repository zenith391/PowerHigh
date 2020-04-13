package org.powerhigh.jfx;

import java.util.HashMap;

import org.powerhigh.graphics.Drawer;
import org.powerhigh.graphics.Font;
import org.powerhigh.graphics.Texture;
import org.powerhigh.utils.Color;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

/**
 * Implementation properties:<br/>
 * <li>
 * 		<ul><code>jfx.linear_filtering</code>: <code>true</code> - enable or disable linear filtering for textures
 * </li>
 */
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
		if (color != null) {
			this.color = color;
			gc.setFill(javafx.scene.paint.Color.rgb(color.getRed(), color.getGreen(), color.getBlue()));
			gc.setStroke(javafx.scene.paint.Color.rgb(color.getRed(), color.getGreen(), color.getBlue()));
		}
	}
	
	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void drawTexture(int x, int y, Texture texture) {
		if (texCache.get(texture) == null) {
			texCache.put(texture, decodeTexture(texture));
		}
		gc.drawImage(texCache.get(texture), x, y);
	}

	@Override
	public void drawTexture(int x, int y, int width, int height, Texture texture) {
		if (texCache.get(texture) == null) {
			texCache.put(texture, decodeTexture(texture));
		}
		gc.setImageSmoothing(getImplementationProperties().getProperty("jfx.linear_filtering", "true").equalsIgnoreCase("false"));
		gc.drawImage(texCache.get(texture), x, y, width, height);
	}

	private WritableImage decodeTexture(Texture t) {
		WritableImage img = new WritableImage(t.getWidth(), t.getHeight());
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				int rgb = t.getRGB(x, y);
				img.getPixelWriter().setArgb(x, y, rgb);
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
		Text theText = new Text(text);
		theText.setFont(gc.getFont());
		return (int) theText.getBoundsInLocal().getWidth();
	}

	@Override
	public int getEstimatedHeight() {
		Text theText = new Text("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234568790");
		theText.setFont(gc.getFont());
		return (int) theText.getBoundsInLocal().getHeight();
	}

	@Override
	public boolean supportsTextEstimations() {
		return true;
	}

	@Override
	public void localRotate(double radians, int orx, int ory) {
		rotate(Math.toDegrees(radians), orx, ory);
	}

	private void rotate(double angle, double px, double py) {
		Rotate r = new Rotate(angle, px, py);
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
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

	@Override
	public void drawLine(int x, int y, int x2, int y2) {
		gc.beginPath();
		gc.moveTo(x, y);
		gc.lineTo(x2, y2);
		gc.fill();
		gc.closePath();
	}

	@Override
	public void drawRect(int x, int y, int width, int height) {
		gc.strokeRect(x, y, width, height);
	}

	@Override
	public void drawCircle(int x, int y, int radius) {
		gc.strokeOval(x, y, radius, radius);
	}

	@Override
	public void fillCircle(int x, int y, int radius) {
		gc.fillOval(x, y, radius, radius);
	}

	@Override
	public void drawOval(int x, int y, int width, int height) {
		gc.strokeOval(x, y, width, height);
	}

	@Override
	public void fillOval(int x, int y, int width, int height) {
		gc.fillOval(x, y, width, height);
	}

	@Override
	public Font getFont() {
		return null;
	}

	@Override
	public void setFont(Font font) {
		
	}

}

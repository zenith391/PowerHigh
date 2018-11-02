package org.powerhigh.jfx;

import org.powerhigh.graphics.Drawer;
import org.powerhigh.graphics.Texture;
import org.powerhigh.utils.Color;

import javafx.scene.canvas.GraphicsContext;

public class GCDrawer extends Drawer {

	private GraphicsContext gc;
	private Color color;

	public void setGC(GraphicsContext gc) {
		this.gc = gc;
	}
	
	@Override
	public void fillRect(int x, int y, int width, int height) {
		gc.fillRect(x, y, width, height);
	}

	@Override
	public void setColor(Color color) {
		//System.out.println(color);
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
		
	}

	@Override
	public void drawTexture(int x, int y, int width, int height, Texture texture) {
		
	}

	@Override
	public void clearCache() {
		
	}

	@Override
	public void clearTextureFromCache(Texture texture) {
		
	}

	@Override
	public void drawText(int x, int y, String string) {
		
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
		
	}

	@Override
	public void translate(int x, int y) {
		
	}

	@Override
	public void saveState() {
		
	}

	@Override
	public void restoreState() {
		
	}
	
}

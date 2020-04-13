package org.powerhigh.graphics;

import java.util.Properties;

import org.powerhigh.utils.Color;

public abstract class Drawer {

	protected static boolean cacheEnabled = true;
	protected static Properties implProperties = new Properties();
	
	public abstract void fillRect(int x, int y, int width, int height);
	public abstract void drawRect(int x, int y, int width, int height);
	public abstract void drawCircle(int x, int y, int radius);
	public abstract void fillCircle(int x, int y, int radius);
	public abstract void drawOval(int x, int y, int width, int height);
	public abstract void fillOval(int x, int y, int width, int height);
	public abstract void setColor(Color color);
	public abstract Color getColor();
	public abstract void drawTexture(int x, int y, Texture texture);
	
	/**
	 * Draws a resized version of the texture (platform-dependent for better performances)
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param texture
	 */
	public abstract void drawTexture(int x, int y, int width, int height, Texture texture);
	public abstract void clearCache();
	public abstract void clearTextureFromCache(Texture texture);
	public abstract void drawText(int x, int y, String string);
	public abstract Font getFont();
	public abstract void setFont(Font font);
	public abstract void drawLine(int x, int y, int x2, int y2);
	
	public abstract int getEstimatedWidth(String text);
	public abstract int getEstimatedHeight();
	public abstract boolean supportsTextEstimations();
	
	/**
	 * Local rotate (next drawing calls) in radians
	 * @param radians
	 */
	public abstract void localRotate(double radians, int orx, int ory);
	
	/**
	 * Translating.
	 * @param x
	 * @param y
	 */
	public abstract void translate(int x, int y);
	
	/**
	 * Platform-dependent state saving (overwrite older saves)
	 */
	public abstract void saveState();
	
	/**
	 * Platform-dependent state restoring from last save
	 */
	public abstract void restoreState();
	
	public static boolean isCacheEnabled() {
		return cacheEnabled;
	}
	
	public static void setCacheEnabled(boolean cacheEnabled) {
		Drawer.cacheEnabled = cacheEnabled;
	}
	
	public static Properties getImplementationProperties() {
		return implProperties;
	}
	
}

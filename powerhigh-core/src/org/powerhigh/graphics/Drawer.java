package org.powerhigh.graphics;

import org.powerhigh.utils.Color;

public abstract class Drawer {

	protected boolean cacheEnabled;
	
	public abstract void fillRect(int x, int y, int width, int height);
	public abstract void setColor(Color color);
	public abstract Color getColor();
	public abstract void drawTexture(int x, int y, Texture texture);
	
	/**
	 * Draws a resized-version of the texture (platform-dependent for better performances)
	 * @param x
	 * @param y
	 * @param string
	 */
	public abstract void drawTexture(int x, int y, int width, int height, Texture texture);
	public abstract void clearCache();
	public abstract void clearTextureFromCache(Texture texture);
	public abstract void drawText(int x, int y, String string);
	
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
	
	public boolean isCacheEnabled() {
		return cacheEnabled;
	}
	
	public void setCacheEnabled(boolean cacheEnabled) {
		this.cacheEnabled = cacheEnabled;
	}
	
}

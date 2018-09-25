package org.powerhigh.graphics;

import org.powerhigh.utils.Color;

public abstract class Drawer {

	protected boolean cacheEnabled;
	
	public abstract void fillRect(int x, int y, int width, int height);
	public abstract void setColor(Color color);
	public abstract Color getColor();
	public abstract void drawTexture(int x, int y, Texture texture);
	public abstract void clearCache();
	public abstract void clearTextureFromCache(Texture texture);
	
	public boolean isCacheEnabled() {
		return cacheEnabled;
	}
	
	public void setCacheEnabled(boolean cacheEnabled) {
		this.cacheEnabled = cacheEnabled;
	}
	
}

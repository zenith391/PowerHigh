package org.powerhigh.graphics;

public class Font {

	protected float size;
	protected String family;
	protected String path;
	
	public static Font loadFont(String path, float size) {
		Font font = new Font();
		font.path = path;
		font.size = size;
		return font;
	}
	
	public static Font systemFont(String family, float size) {
		Font font = new Font();
		font.family = family;
		font.size = size;
		return font;
	}
	
	public Font deriveFont(float size) {
		this.size = size;
		return this;
	}
	
	public String getFamily() {
		return family;
	}
	
	/**
	 * Should not be used.
	 */
	public String getPath() {
		return path;
	}
	
	public float getSize() {
		return size;
	}
	
}

package org.powerhigh.graphics;

public class Font {

	protected float size;
	protected String family;
	
	public Font deriveFont(float size) {
		this.size = size;
		return this;
	}
	
}

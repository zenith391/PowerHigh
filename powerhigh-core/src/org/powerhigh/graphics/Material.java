package org.powerhigh.graphics;

import org.powerhigh.utils.Color;

public class Material {

	private Color color;
	
	public Material() {
		this(Color.BLACK);
	}
	
	public Material(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
}

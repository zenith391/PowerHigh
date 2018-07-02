package org.lggl.graphics;

import java.awt.Color;

public class ParticleBlueprint {

	private Color color;
	private Texture texture;
	private int size;
	private short maxLife;
	
	public ParticleBlueprint(Color color, int size, short life) {
		this.color = color;
		this.size = size;
		this.maxLife = life;
	}
	
	public ParticleBlueprint(Texture tex, int size, short life) {
		this.texture = tex;
		this.size = size;
		this.maxLife = life;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public short getMaxLife() {
		return maxLife;
	}
	
	public int getSize() {
		return size;
	}
	
	public Color getColor() {
		return color;
	}

}

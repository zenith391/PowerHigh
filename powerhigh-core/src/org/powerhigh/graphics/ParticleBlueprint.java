package org.powerhigh.graphics;

import java.awt.Color;
import java.awt.Graphics;

import org.powerhigh.objects.Particle;

public class ParticleBlueprint {

	private Color color;
	private Texture texture;
	private int size;
	private short maxLife;
	private ParticleRenderer r;
	
	public abstract static class ParticleRenderer {
		
		public abstract void render(Graphics g, Particle p);
		
	}
	
	public ParticleBlueprint(Color color, int size, short life) {
		this.color = color;
		this.size = size;
		this.maxLife = life;
	}
	
	public ParticleBlueprint(int size, short life, ParticleRenderer r) {
		this.size = size;
		this.maxLife = life;
		this.r = r;
	}
	
	public ParticleRenderer getParticleRenderer() {
		return r;
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

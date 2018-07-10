package org.lggl.objects;

import org.lggl.graphics.ParticleBlueprint;

public class Particle {

	private ParticleBlueprint blueprint;
	private short life;
	
	private int x, y;
	
	public Particle(ParticleBlueprint bp, int x, int y) {
		blueprint = bp;
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public short getLife() {
		return life;
	}
	
	public void setLife(short life) {
		this.life = life;
	}
	
	public ParticleBlueprint getBlueprint() {
		return blueprint;
	}

}

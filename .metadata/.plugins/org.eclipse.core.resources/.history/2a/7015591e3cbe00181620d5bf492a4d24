package org.powerhigh.graphics;

import java.awt.Graphics;
import java.util.Random;

import org.powerhigh.graphics.ParticleBlueprint.ParticleRenderer;
import org.powerhigh.objects.GameObject;
import org.powerhigh.objects.Particle;

public class ParticleBox extends GameObject {

	private Particle[] particles;

	public ParticleBox(int max) {
		particles = new Particle[max];
	}

	public void windRight(int mx, int my) {
		Random r = new Random();
		for (Particle p : particles) {
			if (p != null) {
				int y = r.nextInt(my * 2);
				y -= my;
				int x = r.nextInt(mx);
				p.setX(p.getX() + x);
				p.setY(p.getY() + y);
			}
		}
	}

	public void windLeft(int mx, int my) {
		Random r = new Random();
		for (Particle p : particles) {
			if (p != null) {
				int y = r.nextInt(my * 2);
				y -= my;
				int x = r.nextInt(mx);
				p.setX(p.getX() - x);
				p.setY(p.getY() + y);
			}
		}
	}

	private int freeSlot() {
		for (int i = 0; i < particles.length; i++) {
			if (particles[i] == null) {
				return i;
			}
		}
		return -1;
	}

	public void addParticle(Particle p) {
		int freeSlot = freeSlot();
		if (freeSlot != -1) {
			particles[freeSlot] = p;
		}
	}

	@Override
	public void paint(Graphics g, Interface source) {
		for (int i = 0; i < particles.length; i++) {
			Particle particle = particles[i];
			if (particle != null) {
				if (particle.getBlueprint().getParticleRenderer() == null) {
					if (particle.getBlueprint().getTexture() == null) {
						g.setColor(particle.getBlueprint().getColor());
						g.fillRect(particle.getX() + x, particle.getY() + y, particle.getBlueprint().getSize(),
								particle.getBlueprint().getSize());

					} else {
						g.drawImage(particle.getBlueprint().getTexture().getAWTImage(), particle.getX() + x,
								particle.getY() + y, null);
					}
				} else {
					ParticleRenderer r = particle.getBlueprint().getParticleRenderer();
					r.render(g, particle);
				}
				particle.setLife((short) (particle.getLife() + 1));

				if (particle.getLife() >= particle.getBlueprint().getMaxLife()) {
					particles[i] = null;
				}
			}
		}
	}

}

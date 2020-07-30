package org.powerhigh.objects;

import org.powerhigh.utils.Color;
import org.powerhigh.components.MeshRender;
import org.powerhigh.components.Renderer;
import org.powerhigh.components.Transform;
import org.powerhigh.graphics.Drawer;
import org.powerhigh.graphics.IMeshRenderer;
import org.powerhigh.graphics.Material;
import org.powerhigh.math.Vector2;

public class Oval extends GameObject {

	private boolean filled;
	
	public boolean isFilled() {
		return filled;
	}

	public void setFilled(boolean filled) {
		this.filled = filled;
	}

	public Oval() {
		this(0, 0, 50, 50, Color.BLACK);
	}

	public Oval(int x, int y, int width, int height, Color c) {
		Transform t = addComponent(Transform.class);
		t.position.set(x, y);
		t.size.set(width, height);
		
		Renderer r = addComponent(Renderer.class);
		r.material = new Material(c);
		
		MeshRender render = addComponent(MeshRender.class);
		render.meshRenderer = new IMeshRenderer() {

			@Override
			public void render(Renderer r, Vector2 size, Drawer d) {
				System.out.println("render");
				d.fillOval(0, 0, (int) size.x, (int) size.y);
			}
			
		};
	}
	
}

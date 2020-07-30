package org.powerhigh.components;

import org.powerhigh.graphics.Drawer;
import org.powerhigh.graphics.Material;
import org.powerhigh.math.Vector2;
import org.powerhigh.objects.GameObject;
import org.powerhigh.utils.Area;

public class RendererSystem extends ComponentSystem<Renderer> {
	
	public static final RendererSystem INSTANCE = new RendererSystem();

	@Override
	public void process(Renderer[] components, int off, int len) {}
	
	public void render(Renderer[] components, int off, int len, Drawer d) {
		Vector2 size = new Vector2();
		for (int i = off; i < len; i++) {
			Renderer r = components[i];
			GameObject obj = r.gameObject;
			Material mat = r.material;
			d.setColor(mat.getColor());
			if (obj.isVisible()) {
				d.saveState();
				Transform t = obj.getComponent(Transform.class);
				if (t != null) {
					size.set(t.size);
					int rx = (int) (t.position.x + t.size.x * t.rotationPivot.x);
					int ry = (int) (t.position.y + t.size.y * t.rotationPivot.y);
					d.localRotate(Math.toRadians(t.rotation), rx, ry);
					d.translate((int)t.position.x, (int)t.position.y);
					//d.setClip(new Area((int)t.position.x, (int)t.position.y, (int)t.size.x, (int)t.size.y));
				} else {
					size.x = 1;
					size.y = 1;
				}
				MeshRender render = obj.getComponent(MeshRender.class);
				if (render != null) {
					render.meshRenderer.render(r, size, d);
				}
				d.restoreState();
			}
		}
	}

}

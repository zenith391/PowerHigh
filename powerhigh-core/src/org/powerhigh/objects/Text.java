package org.powerhigh.objects;

import org.powerhigh.components.MeshRender;
import org.powerhigh.components.Renderer;
import org.powerhigh.components.Transform;
import org.powerhigh.graphics.Drawer;
import org.powerhigh.graphics.IMeshRenderer;
import org.powerhigh.graphics.Interface;
import org.powerhigh.graphics.Material;
import org.powerhigh.math.Vector2;
import org.powerhigh.utils.Color;

public class Text extends GameObject {

	Object font;
	String text;

	public Text(int x, int y, String text, Color color) {
		this(x, y, text, color, null);
	}

	public Text(String text) {
		this(0, 0, text);
	}

	public Text(int x, int y, String text) {
		this(x, y, text, Color.BLACK);
	}

	public Text() {
		this("");
	}

	public Text(int x, int y, String text, Color color, Object font) {
		Transform t = addComponent(Transform.class);
		t.position.set(x, y);
		
		Renderer r = addComponent(Renderer.class);
		r.material = new Material(color);
		
		MeshRender mr = addComponent(MeshRender.class);
		mr.meshRenderer = new IMeshRenderer() {

			@Override
			public void render(Renderer r, Vector2 size, Drawer d) {
				paint(d);
			}
			
		};
		
		this.text = text;
		this.font = font;
	}

	public void paint(Drawer g) {
		if (!text.contains("\n")) {
			getTransform().size.x = g.getEstimatedWidth(text);
			g.drawText(0, 0 + g.getEstimatedHeight(), text);
			getTransform().size.y = g.getEstimatedHeight();
		} else {
			getTransform().size.x = 0;
			int txtY = g.getEstimatedHeight();
			getTransform().size.y = g.getEstimatedHeight() * text.split("\n").length;
			for (String str : text.split("\n")) {
				g.drawText(0, txtY, str);
				txtY += g.getEstimatedHeight();
				getTransform().size.x = Math.max(getTransform().size.x, g.getEstimatedWidth(str));
			}
		}
	}

	public void setText(String txt) {
		text = txt;
	}

	public Object getFont() {
		return font;
	}

	public void setFont(Object font) {
		this.font = font;
	}

	public String getText() {
		return text;
	}

}

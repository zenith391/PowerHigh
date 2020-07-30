package org.powerhigh.graphics;

import org.powerhigh.components.Renderer;
import org.powerhigh.components.Transform;
import org.powerhigh.math.Vector2;

public interface IMeshRenderer {

	public void render(Renderer r, Vector2 size, Drawer d);
	
}

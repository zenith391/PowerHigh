package org.lggl.graphics.renderers;

import java.awt.Graphics2D;

import org.lggl.graphics.Window;
import org.lggl.graphics.objects.GameObject;

public interface IRenderer {

	/** Note: The renderer does not manipulate OUT OF the viewport. For the renderer: 0 = viewport x */
	public void render(Window win, Graphics2D g);
	
	public boolean shouldRender(Window w, GameObject obj);
	
	public void unpause(); public void pause();
	
	public boolean isPaused();
	
}

package org.lggl.graphics.renderers;

import java.awt.Graphics2D;

import org.lggl.graphics.PostProcessor;
import org.lggl.graphics.Window;
import org.lggl.objects.GameObject;

public interface IRenderer {

	/** Note: Renderers cannot manipulate out of the viewport. For the renderer: 0, 0 = viewport x, viewport y, and it can't draw outside of it. */
	public void render(Window win, Graphics2D g);
	
	public boolean shouldRender(Window w, GameObject obj);
	
	public void unpause();
	public void pause();
	
	public boolean isPaused();
	
	public void addPostProcessor(PostProcessor processor);
	public PostProcessor[] getPostProcessors();
	public void setUsePostProcessing(boolean use);
	public boolean isUsingPostProcessing();
	
}

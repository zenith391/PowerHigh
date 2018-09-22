package org.powerhigh.graphics.renderers;

import org.powerhigh.graphics.PostProcessor;
import org.powerhigh.graphics.Drawer;
import org.powerhigh.graphics.Interface;
import org.powerhigh.objects.GameObject;

/**
 * Renderers are objects manipulating a Drawer object to render game objects, post-processors, and do some optimizations.
 */
public interface IRenderer {

	/** Note: Renderers cannot manipulate out of the viewport. For the renderer: 0, 0 = viewport x, viewport y, and it can't draw outside of it. */
	public void render(Interface win, Drawer draw);
	
	public boolean shouldRender(Interface w, GameObject obj);
	
	public void addPostProcessor(PostProcessor processor);
	public PostProcessor[] getPostProcessors();
	public void setUsePostProcessing(boolean use);
	public boolean isUsingPostProcessing();
	
}

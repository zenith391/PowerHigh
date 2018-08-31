package org.powerhigh.graphics.renderers;

import java.awt.Graphics2D;

import org.powerhigh.graphics.PostProcessor;
import org.powerhigh.graphics.Interface;
import org.powerhigh.objects.GameObject;

public interface IRenderer {

	/** Note: Renderers cannot manipulate out of the viewport. For the renderer: 0, 0 = viewport x, viewport y, and it can't draw outside of it. */
	public void render(Interface win, Graphics2D g);
	
	public boolean shouldRender(Interface w, GameObject obj);
	
	public void unpause();
	public void pause();
	
	public boolean isPaused();
	
	public void addPostProcessor(PostProcessor processor);
	public PostProcessor[] getPostProcessors();
	public void setUsePostProcessing(boolean use);
	public boolean isUsingPostProcessing();
	
}

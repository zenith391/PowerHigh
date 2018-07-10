package org.lggl.graphics.renderers;

import java.awt.Graphics2D;

import org.lggl.graphics.PostProcessor;
import org.lggl.graphics.Window;
import org.lggl.objects.GameObject;

/**
 * Yet it is the most simplest renderer. This renderer is a template for people
 * who would wish to create a custom renderer for LGGL.<br/>
 * But this renderer does not have any optimizations. Not even for GameObjects not visible
 * to user space. And it don't even support post-processors, i let you do this <i>^_^</i>
 * @author zenith391
 * @see <code>org.lggl.graphics.renderers.IRenderer</code>
 */
public class SimpleRenderer implements IRenderer {

	private boolean pause;

	@Override
	public void render(Window win, Graphics2D g) {
		for (GameObject obj : win.getObjects()) {
			if (shouldRender(win, obj)) {
				obj.paint(g, win);
			}
		}
	}

	@Override
	public boolean shouldRender(Window w, GameObject obj) {
		return true;
	}

	@Override
	public void unpause() {
		pause = false;
	}

	@Override
	public void pause() {
		pause = true;
	}

	@Override
	public boolean isPaused() {
		return pause;
	}

	@Override
	public void addPostProcessor(PostProcessor processor) {}

	@Override
	public PostProcessor[] getPostProcessors() {
		return null;
	}

	@Override
	public void setUsePostProcessing(boolean use) {}

	@Override
	public boolean isUsingPostProcessing() {
		return false;
	}

}

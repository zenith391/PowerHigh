package org.powerhigh.graphics.renderers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import org.powerhigh.graphics.PostProcessor;
import org.powerhigh.graphics.Interface;
import org.powerhigh.objects.GameObject;

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
	public void render(Interface win, Graphics2D g) {
		g.setColor(win.getBackground());
		g.fillRect(0, 0, win.getViewport().width, win.getViewport().height);
		g.rotate(Math.toRadians(win.getCamera().getRotation()), win.getWidth()/2, win.getHeight()/2);
		g.translate(win.getCamera().getXOffset(), win.getCamera().getYOffset());
		g.scale(win.getCamera().getScale(), win.getCamera().getScale());
		for (GameObject obj : win.getObjects()) {
			if (shouldRender(win, obj)) {
				AffineTransform old = g.getTransform();
				g.rotate(Math.toRadians(obj.getRotation()), obj.getX()+(obj.getWidth() / 2), obj.getY()+(obj.getHeight() / 2));
				obj.paint(g, win);
				g.setColor(Color.RED);
				g.drawRect(obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight());
				g.setTransform(old);
			}
		}
	}

	@Override
	public boolean shouldRender(Interface w, GameObject obj) {
		return obj.isVisible();
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

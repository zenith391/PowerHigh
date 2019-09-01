package org.powerhigh.graphics.renderers;

import org.powerhigh.graphics.Drawer;
import org.powerhigh.graphics.Interface;
import org.powerhigh.objects.GameObject;

/**
 * This renderer is a template for people
 * who would wish to create a custom renderer for PowerHigh.<br/>
 * This renderer is simple and will render all GameObjects that it gets. 
 * And it don't even support post-processors, however it supports translation and camera.
 * @see IRenderer
 */
public class SimpleRenderer implements IRenderer {

	@Override
	public void render(Interface win, Drawer g) {
		g.setColor(win.getBackground());
		g.fillRect(0, 0, win.getViewport().getWidth(), win.getViewport().getHeight());
		g.localRotate(Math.toRadians(win.getCamera().getRotation()), win.getWidth()/2, win.getHeight()/2);
		g.translate(win.getCamera().getXOffset(), win.getCamera().getYOffset());
		for (GameObject obj : win.getObjects()) {
			if (shouldRender(win, obj)) {
				g.saveState();
				g.localRotate(obj.getRotation(), obj.getX(), obj.getY());
				obj.paint(g, win);
				g.restoreState();
			}
		}
	}

	@Override
	public boolean shouldRender(Interface w, GameObject obj) {
		return obj.isVisible();
	}

	@Override
	public void setUsePostProcessing(boolean use) {}

	@Override
	public boolean isUsingPostProcessing() {
		return false;
	}

}

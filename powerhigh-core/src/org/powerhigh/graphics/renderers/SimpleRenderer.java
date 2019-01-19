package org.powerhigh.graphics.renderers;

import org.powerhigh.graphics.Drawer;
import org.powerhigh.graphics.Interface;
import org.powerhigh.objects.GameObject;

/**
 * Yet it is the most simplest renderer. This renderer is a template for people
 * who would wish to create a custom renderer for LGGL.<br/>
 * But this renderer does not have any optimizations. Not even for GameObjects not visible
 * to user space. And it don't even support post-processors, i let you do this <i>^_^</i>
 * @author zenith391
 * @see <code>org.powerhigh.graphics.renderers.IRenderer</code>
 */
public class SimpleRenderer implements IRenderer {

	@Override
	public void render(Interface win, Drawer g) {
		g.setColor(win.getBackground());
		g.fillRect(0, 0, win.getViewport().getWidth(), win.getViewport().getHeight());
		g.localRotate(Math.toRadians(win.getCamera().getRotation()), win.getWidth()/2, win.getHeight()/2);
		g.translate(win.getCamera().getXOffset(), win.getCamera().getYOffset());
//		g.scale(win.getCamera().getScale(), win.getCamera().getScale());
		for (GameObject obj : win.getObjects()) {
			if (shouldRender(win, obj)) {
				g.localRotate(obj.getRotation(), obj.getX(), obj.getY());
				obj.paint(g, win);
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

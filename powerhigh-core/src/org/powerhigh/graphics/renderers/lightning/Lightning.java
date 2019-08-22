package org.powerhigh.graphics.renderers.lightning;

import org.powerhigh.graphics.Drawer;
import org.powerhigh.graphics.Interface;
import org.powerhigh.graphics.renderers.IRenderer;
import org.powerhigh.objects.GameObject;
import org.powerhigh.utils.Area;
import org.powerhigh.utils.Color;

public final class Lightning implements IRenderer {

	private boolean pp = true;

	private void render(Interface win, Drawer g, Area rect) {
		g.setColor(win.getBackground());
		g.fillRect(0, 0, rect.getWidth(), rect.getHeight());
		g.localRotate(Math.toRadians(win.getCamera().getRotation()), win.getWidth() / 2, win.getHeight() / 2);
		g.translate(win.getCamera().getXOffset(), win.getCamera().getYOffset());
//		g.scale(win.getCamera().getScale(), win.getCamera().getScale());
		for (GameObject obj : win.getObjects()) {
			if (obj.isVisible() && shouldRender(rect, obj)) {
				g.saveState();
				g.localRotate(Math.toRadians(obj.getRotation()), obj.getX() + (obj.getWidth() / 2),
						obj.getY() + (obj.getHeight() / 2));

				obj.paint(g, win);

				//float a = obj.getMaterial().reflectance;
				//a /= 2;
				//g.setColor(new Color(.0f, 0f, 0f, a));
				//g.fillRect(obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight());
				g.restoreState();
			}
		}
	}

	@Override
	public void render(Interface win, Drawer g) {
		render(win, g, win.getViewport());
	}

	@Override
	public boolean shouldRender(Interface w, GameObject obj) {
		Area area = w.getViewport();
		return shouldRender(area, obj);
	}

	private boolean shouldRender(Area rect, GameObject obj) {
		return obj.isVisible() && obj.getX() + obj.getWidth() >= 0 && obj.getY() + obj.getHeight() >= 0
				&& obj.getX() <= rect.getWidth() && obj.getY() <= rect.getHeight();
	}

	public void setUsePostProcessing(boolean enable) {
		pp = enable;
	}

	@Override
	public boolean isUsingPostProcessing() {
		return pp;
	}

}

package org.lggl.graphics.renderers.lightning;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.WeakHashMap;

import org.lggl.graphics.Window;
import org.lggl.graphics.objects.GameObject;
import org.lggl.graphics.renderers.IRenderer;
import org.lggl.utils.debug.DebugLogger;

public final class Lightning implements IRenderer {

	private WeakHashMap<Window, LightningRenderBuffer> buffers = new WeakHashMap<>();
	private int acc;
	private boolean paused;
	private Window lastWin;

	private void render(Window win, Graphics g, Rectangle rect) {
		for (GameObject obj : win.getObjects()) {
			if (obj.isVisible()) {
				obj.paint(g, win);
			}
		}
	}

	@Override
	public void render(Window win, Graphics2D g) {
		if (!paused) {
			lastWin = win;

			if (!buffers.containsKey(win)) {
				DebugLogger.logInfo("Allocating render buffer for game..");
				buffers.put(win,
						new LightningRenderBuffer(new BufferedImage(2000, 2000, BufferedImage.TYPE_3BYTE_BGR)));
				DebugLogger.logInfo("Sucefully allocated render buffer");

			}
			render(win, g, win.getViewport());
//			acc++;
//			if (acc >= 50) {
//				acc = 0;
//				drawOnBuffer(win); // ACTIVE BUFFER PAINTING GOT USELESS
//			}
		} else {
			g.drawImage(buffers.get(win).getImage(), 0, 0, null);
		}
	}

	public void drawOnBuffer(Window win) {
		LightningRenderBuffer buffer = buffers.get(win);
		BufferedImage img = buffer.getImage();
		Graphics2D g2d = img.createGraphics();
		g2d.setColor(win.getBackground());
		g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
		render(win, g2d, win.getViewport());
	}

	@Override
	public boolean shouldRender(Window w, GameObject obj) {
		Rectangle rect = w.getViewport();
		return shouldRender(rect, obj);
	}

	private boolean shouldRender(Rectangle rect, GameObject obj) {
		return obj.isVisible() && obj.getX() + obj.getWidth() >= 0 && obj.getY() + obj.getHeight() >= 0
				&& obj.getX() <= rect.getWidth() && obj.getY() <= rect.getHeight();
	}

	@Override
	public void unpause() {
		paused = false;
	}

	@Override
	public void pause() {
		drawOnBuffer(lastWin);
		paused = true;
	}

	@Override
	public boolean isPaused() {
		return paused;
	}

}

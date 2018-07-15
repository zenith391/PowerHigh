package org.lggl.graphics;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

public class WindowEventThread extends Thread {

	private Window win;
	private int targetFPS = 60;
	private int frames;
	private int fps;

	private double delta;

	private long lastTick;

	private Runnable runnable = null;

	private List<Runnable> updateListeners = new ArrayList<Runnable>();

	public int getTargetFPS() {
		return targetFPS;
	}

	public void addUpdateListener(Runnable r) {
		updateListeners.add(r);
	}

	public double getDelta() {
		return delta;
	}

	public void setFrameRate(int frames) {
		this.targetFPS = frames;
	}

	public int getFPS() {
		return fps;
	}

	public WindowEventThread(Window w) {
		win = w;
	}

	public void runLater(Runnable r) {
		runnable = r;
	}

	public void run() {
		setName("Update Thread");
		Dimension lastSize = new Dimension(win.getWidth(), win.getHeight());
		Dimension size = new Dimension(win.getWidth(), win.getHeight());
		long sleepTime = 1000 / 60;
		while (true) {
			long estimatedTime = 0;
			if (targetFPS > 0) {
				estimatedTime = 1000 / targetFPS;
			}
			if (lastTick < System.currentTimeMillis()) {
				lastTick = System.currentTimeMillis() + 1000;
				fps = frames;
				frames = 0;
				delta = (double) targetFPS / (double) fps;
			}
			if (runnable != null) {
				runnable.run();
				runnable = null;
			}

			size = new Dimension(win.getWidth(), win.getHeight());
			long start = System.currentTimeMillis();
			win.update();
			for (Runnable r : updateListeners) {
				r.run();
			}

			if (!lastSize.equals(size)) {
				lastSize = size;
			}
			long end = System.currentTimeMillis();
			estimatedTime = end - start;
			sleepTime = (1000 + estimatedTime) / targetFPS;
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				interrupt();
			}
			frames++;
		}
	}
}

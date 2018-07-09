package org.lggl.graphics;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

public class WindowEventThread extends Thread {

	private Window win;
	private short targetFPS = 60;
	private int frames;
	private int fps;
	
	private double delta;
	
	private long lastTick;

	private Runnable runnable = null;
	private Runnable arunnable = null;
	
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

	public void setTargetFPS(int fps) {
		this.targetFPS = (short) fps;
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
	
	public void runAlways(Runnable r) {
		arunnable = r;
	}

	public void run() {
		setName("Window-EventThread");
		Dimension lastSize = new Dimension(win.getWidth(), win.getHeight());
		Dimension size = new Dimension(win.getWidth(), win.getHeight());
		while (true) {
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
			if (arunnable != null) {
				arunnable.run();
			}

			size = new Dimension(win.getWidth(), win.getHeight());
			
			win.update();
			for (Runnable r : updateListeners) {
				r.run();
			}
			
			if (!lastSize.equals(size)) {
				lastSize = size;
			}
			if (targetFPS > 0) {
				try {
					Thread.sleep(1000 / targetFPS);
				} catch (InterruptedException e) {
				}
			}
			frames++;
		}
	}
}

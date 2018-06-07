package org.lggl.graphics;

import java.awt.Dimension;

public class WindowEventThread extends Thread {

	private Window win;
	private boolean arleadyVisible = false;
	private short targetFPS = 60;
	private short frames;
	private short fps;
	
	private double delta;
	
	private long lastTick;

	private Runnable runnable = null;
	private Runnable arunnable = null;

	public int getTargetFPS() {
		return targetFPS;
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
				delta = (double) fps / (double) targetFPS;
			}
			if (runnable != null) {
				runnable.run();
				runnable = null;
			}
			if (arunnable != null) {
				arunnable.run();
			}
			if (arleadyVisible) {
				if (win.getCloseOperation() == Window.EXIT_ON_CLOSE) {
					if (win.isClosed()) {
						System.exit(0);
					}
				}
			}

			size = new Dimension(win.getWidth(), win.getHeight());
			
				win.update();
			
			if (!lastSize.equals(size)) {
				win.throwEvent(new ResizeEvent(size));
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

package org.powerhigh.graphics;

import java.util.ArrayList;
import java.util.List;

// TODO Remove thread for GWT compatibility (only run thread when not in GWT environment?)
public class WindowEventThread extends Thread {

	private Interface win;
	private int targetFPS = 60;
	private int frames;
	private int fps;
	public long estimatedTime;
	private boolean stop;

	private double delta;

	private long lastTick;
	private long sleepTime = 1000 / targetFPS;

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
	
	public long getSleepTime() {
		return sleepTime;
	}

	public void setFrameRate(int frames) {
		this.targetFPS = frames;
	}

	public int getFPS() {
		return fps;
	}

	public WindowEventThread(Interface w) {
		win = w;
	}

	public void runLater(Runnable r) {
		runnable = r;
	}
	
	public void interrupt() {
		stop = true;
	}

	public void run() {
		setName("powerhigh-interface-thread");
		while (!stop) {
			if (lastTick < System.currentTimeMillis()) {
				lastTick = System.currentTimeMillis() + 1000;
				fps = frames;
				frames = 0;
				delta = (double) targetFPS / (double) fps;
				if (Double.isInfinite(delta)) delta = 0d;
			}
			if (runnable != null) {
				runnable.run();
				runnable = null;
			}
			long start = System.currentTimeMillis();
			win.update();
			for (Runnable r : updateListeners) {
				r.run();
			}
			long end = System.currentTimeMillis();
			estimatedTime = end - start;
			sleepTime = (1000 - (estimatedTime - (1000 / targetFPS))) / targetFPS;
			if (sleepTime < 0) sleepTime = 0;
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				interrupt();
			}
			frames++;
		}
		stop = false;
	}
}

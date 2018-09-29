package org.powerhigh.graphics;

import org.powerhigh.Camera;
import org.powerhigh.ViewportManager;
import org.powerhigh.graphics.renderers.IRenderer;
import org.powerhigh.graphics.renderers.SimpleRenderer;
import org.powerhigh.graphics.renderers.lightning.Lightning;
import org.powerhigh.input.Input;
import org.powerhigh.objects.Container;
import org.powerhigh.objects.GameObject;
import org.powerhigh.utils.Area;
import org.powerhigh.utils.Color;

public abstract class Interface {

	protected Input input = new Input(this);
	protected GameObject focusedObj;
	protected WindowEventThread thread = new WindowEventThread(this);
	protected ViewportManager viewportManager;
	protected Area viewport = new Area(0, 0, 0, 0);
	
	private Container objectContainer;
	
	private Camera camera;
	
	public Container getObjectContainer() {
		return objectContainer;
	}

	private static IRenderer render;

	public static IRenderer getRenderer() {
		return render;
	}

	public static void setRenderer(IRenderer render) {
		Interface.render = render;
	}

	public boolean shouldRender(GameObject obj) {
		return render.shouldRender(this, obj);
	}

	public int getFPS() {
		return thread.getFPS();
	}

	public float getSPF() {
		return 1.0f / getFPS();
	}

	public abstract void setBackground(Color color);
	public abstract Color getBackground();

	public ViewportManager getViewportManager() {
		return viewportManager;
	}

	public void setViewportManager(ViewportManager manager) {
		viewportManager = manager;
	}
	
	public WindowEventThread getEventThread() {
		return thread;
	}

	/**
	 * Set whether or not if the Window is visible. If visible equals to true, it's
	 * will execute <code>show()</code>. If visible equals to false, it's will
	 * execute <code>hide()</code>.
	 * 
	 * @param visible
	 */
	public void setVisible(boolean visible) {
		if (visible == true) {
			show();
		}
		if (visible == false) {
			hide();
		}
	}

	protected void init() {
		if (render == null)
			setRenderer(new Lightning());
		thread.start();
		objectContainer = new Container();
		camera = new Camera();
	}

	public void setViewport(int x, int y, int width, int height) {
		objectContainer.setSize(width, height);
		
	}

	/**
	 * Return a clone (editing values will not have effect) of the current viewport
	 * @return current viewport clone
	 */
	public Area getViewport() {
		return new Area(viewport);
	}

	public abstract void show();
	public abstract void hide();

	public Input getInput() {
		return input;
	}

	public abstract boolean isCloseRequested();
	public abstract boolean isVisible();

	public void add(GameObject obj) {
		objectContainer.add(obj);
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public void setCamera(Camera cam) {
		camera = cam;
	}
	
	public abstract void setSize(int width, int height);
	public abstract Area getSize();

	public void update() {
		if (viewport != null) {
			
			// Re-working on this
			if (viewportManager != null) {
				viewport = viewportManager.getViewport(this);
			}
//			Rectangle view = viewport.getViewport(this);
//			if (viewport.getSpecialProperties().containsKey("stretchToWindow")) {
//				view = new Rectangle(getWidth(), getHeight());
//			}
//			if (!view.equals(panel.getBounds())) {
//				if (!viewport.getSpecialProperties().containsKey("stretchToWindow")) {
//					setViewport(view.x, view.y, view.width, view.height);
//					panel.setStretch(false);
//				} else {
//					if (viewport.getSpecialProperties().containsKey("stretchToWindow")) {
//						setViewport(view.x, view.y, getWidth(), getHeight());
//						panel.setStretch(true);
//					}
//				}
//			}
		}
	}

	public GameObject[] getObjects() {
		return objectContainer.getObjects();
	}

	public void remove(GameObject obj) {
		try {
			objectContainer.remove(obj);
		} catch (Exception e) {
			throw e;
		}
	}

	public abstract int getWidth();
	public abstract int getHeight();

	public void fireEvent(String type, Object... args) {
		if (type.equals("mousePressed")) {
			GameObject[] a = getObjects();
			int mx = (int) args[0];
			int my = (int) args[1];
			focusedObj = null;
			for (GameObject b : a) {
				if (mx > b.getX() && my > b.getY() && mx < b.getX() + b.getWidth() && my < b.getY() + b.getHeight()) {
					focusedObj = b;
					break;
				}
			}
		}
		if (focusedObj != null)
			focusedObj.onEvent(type, args);
	}

	public void removeAll() {
		objectContainer.removeAll();
	}
}

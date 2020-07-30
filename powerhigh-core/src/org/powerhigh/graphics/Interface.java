package org.powerhigh.graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.powerhigh.Camera;
import org.powerhigh.ViewportManager;
import org.powerhigh.components.ComponentSystem;
import org.powerhigh.components.Renderer;
import org.powerhigh.components.RendererSystem;
import org.powerhigh.input.Input;
import org.powerhigh.objects.Container;
import org.powerhigh.objects.GameObject;
import org.powerhigh.utils.Area;
import org.powerhigh.utils.Color;
import org.powerhigh.utils.Point;
import org.powerhigh.utils.PowerHighException;

public abstract class Interface {

	protected Input input = new Input(this);
	protected WindowEventThread thread = new WindowEventThread(this);
	protected ViewportManager viewportManager;
	protected Area viewport = new Area(0, 0, 0, 0);
	protected Map<Class<? extends ComponentSystem>, List<GameObject>> archetypes = new HashMap<>();
	
	private Container objectContainer;
	
	private Camera camera;
	
	/**
	 * The only instance that can be used through the current process.
	 */
	public static Interface singleInstance;
	
	public static Map<Class<? extends ComponentSystem>, List<GameObject>> getArchetypesMap() {
		if (singleInstance == null) {
			throw new IllegalStateException("An interface must have been init before getting archetypes.");
		}
		return singleInstance.archetypes;
	}
	
	public Interface() {
		if (singleInstance != null) {
			throw new RuntimeException("There can only be one Interface instance.");
		}
		singleInstance = this;
	}
	
	public Container getObjectContainer() {
		return objectContainer;
	}

	public boolean isVisible(GameObject obj) {
		//return render.shouldRender(this, obj);
		return true;
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
		thread.start();
		objectContainer = new Container();
		camera = new Camera();
	}

	public void setViewport(int x, int y, int width, int height) {
		objectContainer.setSize(width, height);
	}
	
	public void setObjectContainer(Container container) {
		objectContainer = container;
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
	
	/**
	 * Unregister platform specific tools (threads, callbacks, listeners)
	 */
	public abstract void unregister();

	public void add(GameObject obj) {
		objectContainer.add(obj);
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public void setCamera(Camera cam) {
		camera = cam;
	}
	
	public void setResizable(boolean resizable) {
		throw new UnsupportedOperationException(); // To be defined (or not) by implementation.
	}
	
	public void setTitle(String title) {
		throw new UnsupportedOperationException(); // To be defined (or not) by implementation.
	}
	
	public void setPosition(int x, int y) {
		throw new UnsupportedOperationException();
	}
	
	public Point getPosition() {
		throw new UnsupportedOperationException();
	}
	
	public void setPosition(Point pos) {
		setPosition(pos.getX(), pos.getY());
	}
	
	public void setPostProcessor(PostProcessor post) throws PowerHighException {
		throw new PowerHighException("No shader implemented.");
	}
	
	public PostProcessor getPostProcessor() {
		return null;
	}

	public void update() {
		if (viewportManager != null) {
			viewport = viewportManager.getViewport(this);
		}
	}
	
	public void render(Drawer g) {
		List<GameObject> renderables = archetypes.get(RendererSystem.class);
		g.localRotate(Math.toRadians(camera.getRotation()), getWidth() / 2, getHeight() / 2);
		g.translate(camera.getXOffset(), camera.getYOffset());
		
		List<Renderer> components = new ArrayList<>(renderables.size());
		for (GameObject go : renderables) {
			Renderer r = go.getComponent(Renderer.class);
			components.add(r);	
		}
		Renderer[] array = components.toArray(new Renderer[components.size()]);
		if (array.length > 0) {
			RendererSystem rs = RendererSystem.INSTANCE;
			rs.render(array, 0, array.length, g);
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

	/**
	 * This returns the actual <b>drawable</b> width, excluding space
	 * the game cannot draw to like windows borders
	 * @return drawable width
	 */
	public abstract int getWidth();
	
	/**
	 * This returns the actual <b>drawable</b> height, excluding space
	 * the game cannot draw to like title bar or app bar.
	 * @return drawable height
	 */
	public abstract int getHeight();
	
	/**
	 * This returns the actual <b>drawable</b> area, excluding space
	 * the game cannot draw to like title bar, app bar and window borders.
	 * @return drawable size
	 */
	public Area getSize() {
		return new Area(getWidth(), getHeight());
	}
	
	/**
	 * This sets the <b>window</b> size to <code>width</code> and
	 * <code>height</code>, as this sets the window size, {@link #getSize()}
	 * will probably not return the same size as inputted unless the window
	 * is undecorated and without borders
	 * 
	 * @param width new window width
	 * @param height new window height
	 * @see #getSize()
	 */
	public abstract void setSize(int width, int height);
	
	public void setSize(Area size) {
		setSize(size.getWidth(), size.getHeight());
	}

	public void fireEvent(String type, Object... args) {
		objectContainer.onEvent(type, args);
	}

	public void removeAll() {
		objectContainer.removeAll();
	}
}

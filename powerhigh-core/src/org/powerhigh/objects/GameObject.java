package org.powerhigh.objects;

import org.powerhigh.utils.Area;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.powerhigh.components.Component;
import org.powerhigh.components.ComponentSystem;
import org.powerhigh.components.Transform;
import org.powerhigh.graphics.Drawer;
import org.powerhigh.graphics.Interface;

public class GameObject {
	
	private String name = Integer.toHexString(hashCode());
	private boolean visible = true;
	protected Map<Class<? extends Component>, Component> components = new HashMap<>();

	/**
	 * Dispose game object's resources if needed.
	 */
	public void dispose() {
		components.clear();
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Component> T getComponent(Class<? extends Component> type) {
		return (T) components.get(type);
	}
	
	public boolean hasComponent(Class<? extends Component> type) {
		return components.containsKey(type);
	}
	
	public ComponentSystem<?>[] getComponentSystem() {
		Set<ComponentSystem<?>> systems = new HashSet<>();
		for (Component cp : components.values()) {
			if (cp.getInstance() != null) {
				systems.add(cp.getInstance());
			}
		}
		return systems.toArray(new ComponentSystem[systems.size()]);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Component> T addComponent(Class<? extends Component> type) {
		if (Interface.singleInstance == null) {
			throw new IllegalStateException("An interface must have been init before adding a component.");
		}
		if (components.containsKey(type)) {
			return (T) components.get(type);
		} else {
			T component = null;
			try {
				component = (T) type.getConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				throw new Error("Could not instantiate component class " + type, e);
			}
			ComponentSystem cs = component.getInstance();
			if (cs != null) {
				Map<Class<? extends ComponentSystem>, List<GameObject>> map = Interface.getArchetypesMap();
				if (!map.containsKey(cs.getClass())) {
					map.put(cs.getClass(), new ArrayList<GameObject>());
				}
				map.get(cs.getClass()).add(this);
			}
			component.setGameObject(this);
			components.put(type, component);
			return component;
		}
	}
	
	/**
	 * Helper method to easily get a game object's transform.<br/>
	 * Equivalent to <code>getComponent(Transform.class)</code>
	 * @return transform component or null
	 */
	public Transform getTransform() {
		return getComponent(Transform.class);
	}
	
	/**
	 * Method called when the GameObject have to be painted on the Interface using the Drawer.
	 * @param drawer
	 * @param source
	 * @deprecated Replaced by {@link org.powerhigh.components.MeshRender}
	 */
	@Deprecated
	public void paint(Drawer drawer, Interface source) {}

	public void onEvent(String type, Object... args) {

	}

	public GameObject() {}

	@Override
	public String toString() {
		return getClass().getName() + "[]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	// Transformation compatibility methods
	
	/**
	 * @deprecated The transformation moved from {@link GameObject} to new component system using {@link Transform} component. Use <code>getTransform().size.x</code>
	 */
	@Deprecated(since = "0.9.10", forRemoval = true)
	public int getWidth() {
		return (int) getTransform().size.x;
	}

	/**
	 * @deprecated The transformation moved from {@link GameObject} to new component system using {@link Transform} component. Use <code>getTransform().size.x = ...</code>
	 */
	@Deprecated(since = "0.9.10", forRemoval = true)
	public void setWidth(int width) {
		getTransform().size.x = width;
	}

	/**
	 * @deprecated The transformation moved from {@link GameObject} to new component system using {@link Transform} component. Use <code>getTransform().size.y</code>
	 */
	@Deprecated(since = "0.9.10", forRemoval = true)
	public int getHeight() {
		return (int) getTransform().size.y;
	}
	
	/**
	 * @deprecated The transformation moved from {@link GameObject} to new component system using {@link Transform} component. Use <code>getTransform().size.y = ...</code>
	 */
	@Deprecated(since = "0.9.10", forRemoval = true)
	public void setHeight(int height) {
		getTransform().size.y = height;
	}
	
	/**
	 * @deprecated The transformation moved from {@link GameObject} to new component system using {@link Transform} component. Use <code>getTransform().position.x = ...</code>
	 */
	@Deprecated(since = "0.9.10", forRemoval = true)
	public void setX(int x) {
		getTransform().position.x = x;
	}

	/**
	 * @deprecated The transformation moved from {@link GameObject} to new component system using {@link Transform} component. Use <code>getTransform().position.y = ...</code>
	 */
	@Deprecated(since = "0.9.10", forRemoval = true)
	public void setY(int y) {
		getTransform().position.y = y;
	}
	
	/**
	 * @deprecated The transformation moved from {@link GameObject} to new component system using {@link Transform} component. Use <code>getTransform().position.x</code>
	 */
	@Deprecated(since = "0.9.10", forRemoval = true)
	public int getX() {
		if (getTransform() == null) {
			throw new IllegalStateException("a transform is required");
		}
		return (int) getTransform().position.x;
	}
	
	/**
	 * @deprecated The transformation moved from {@link GameObject} to new component system using {@link Transform} component. Use <code>getTransform().position.y</code>
	 */
	@Deprecated(since = "0.9.10", forRemoval = true)
	public int getY() {
		if (getTransform() == null) {
			throw new IllegalStateException("a transform is required");
		}
		return (int) getTransform().position.y;
	}
	
	/**
	 * @deprecated The transformation moved from {@link GameObject} to new component system using {@link Transform} component. Use <code>getTransform().position = ...</code>
	 */
	@Deprecated(since = "0.9.10", forRemoval = true)
	public void setPosition(int x, int y) {
		setX(x);
		setY(y);
	}
	
	/**
	 * @deprecated The transformation moved from {@link GameObject} to new component system using {@link Transform} component. Use <code>getTransform().size = ...</code>
	 */
	@Deprecated(since = "0.9.10", forRemoval = true)
	public void setSize(Area area) {
		setSize(area.width, area.height);
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	/**
	 * @deprecated The transformation moved from {@link GameObject} to new component system using {@link Transform} component. Use <code>getTransform().size = ...</code>
	 */
	@Deprecated(since = "0.9.10", forRemoval = true)
	public void setSize(int w, int h) {
		if (getTransform() == null) {
			throw new IllegalStateException("a transform is required");
		}
		setWidth(w);
		setHeight(h);
	}

	public Area getSize() {
		return new Area(getWidth(), getHeight());
	}
}

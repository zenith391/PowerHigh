package org.powerhigh.components;

import org.powerhigh.objects.GameObject;

/**
 * Literally anything, the Component is just a superinterface to mark objects that hold data for ComponentSystems
 */
public interface Component {
	
	/**
	 * A component system should always have one instance as it is a singleton.
	 * @return component system corresponding to this component type
	 */
	public ComponentSystem<?> getInstance();
	
	public GameObject getGameObject();
	public void setGameObject(GameObject go);
	
}

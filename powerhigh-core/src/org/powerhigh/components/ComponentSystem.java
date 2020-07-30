package org.powerhigh.components;

import org.powerhigh.objects.GameObject;

/**
 * The System part of E.C.S.
 */
public abstract class ComponentSystem<T extends Component> {
	
	public abstract void process(T[] objects, int off, int len);
	
}

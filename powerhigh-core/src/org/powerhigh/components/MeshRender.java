package org.powerhigh.components;

import org.powerhigh.graphics.IMeshRenderer;

/**
 * Should be used alongside {@link Renderer}
 */
public class MeshRender extends AbstractComponent {

	public IMeshRenderer meshRenderer;
	
	/**
	 * To use only with primitives, sets whether it should be filled or not.
	 */
	public boolean primitiveFilled;
	
	@Override
	public ComponentSystem<?> getInstance() {
		return null;
	}

}

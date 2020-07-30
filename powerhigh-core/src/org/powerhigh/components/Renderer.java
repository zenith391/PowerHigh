package org.powerhigh.components;

import org.powerhigh.graphics.Material;

public class Renderer extends AbstractComponent {
	
	public Material material;

	@Override
	public ComponentSystem<Renderer> getInstance() {
		return RendererSystem.INSTANCE;
	}

}

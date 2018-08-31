package org.powerhigh.ui;

import org.powerhigh.graphics.Texture;
import org.powerhigh.graphics.TextureAtlas;

public abstract class UIModel {

	public abstract TextureAtlas getAtlas();
	public abstract Texture getUI(String id);
	
}

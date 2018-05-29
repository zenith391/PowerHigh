package org.lggl.ui;

import org.lggl.graphics.Texture;
import org.lggl.graphics.TextureAtlas;

public abstract class UIModel {

	public abstract TextureAtlas getAtlas();
	public abstract Texture getUI(String id);
	
}

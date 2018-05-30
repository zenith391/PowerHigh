package org.lggl.graphics;

import java.awt.image.BufferedImage;

public abstract class PostProcessor {
	
	public abstract BufferedImage process(BufferedImage src);

}

package org.lggl.graphics;

import java.awt.image.BufferedImage;

public abstract class BitmapFont {
	
	public static final int START_CODE = 32;
	
	public abstract BufferedImage getBitmap(char c);

}

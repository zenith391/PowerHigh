package org.powerhigh.swing;

import org.powerhigh.utils.Color;

public class SwingUtils {

	public static Color awtColorTophColor(java.awt.Color src) {
		return new Color(src.getRed(), src.getGreen(), src.getBlue(), src.getAlpha());
	}
	
	public static java.awt.Color phColorToAwtColor(Color src) {
		return new java.awt.Color(src.getRed(), src.getGreen(), src.getBlue(), src.getAlpha());
	}
	
}

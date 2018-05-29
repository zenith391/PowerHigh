package org.lggl.graphics;

import java.awt.Dimension;

import org.lggl.utils.Event;

public class ResizeEvent extends Event {
	
	public ResizeEvent(Dimension dim) {
		value = dim;
	}
	
}

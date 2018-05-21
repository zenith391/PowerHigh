package org.lggl.graphics.objects;

import java.awt.Color;

@Deprecated(since = "0.90", forRemoval = true)
/**
 * Replaced by <code>Rectangle</code><br>
 * (Also changed the code of "Block" to just act as a rectangle)
 * Will be removed in LGGL 1.1
 */
public class Block extends Rectangle {
	
	public Block() {
		super();
	}

	public Block(int x, int y, int size, Color c) {
		super(x, y, size, size, c);
	}

}

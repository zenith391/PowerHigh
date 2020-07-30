package org.powerhigh.components;

import org.powerhigh.graphics.Interface;
import org.powerhigh.math.Vector2;
import org.powerhigh.utils.Area;

/**
 * Component containing a game object's transformation: its position, size, rotation and rotation pivot.
 */
public class Transform extends AbstractComponent {

	public Vector2 position = new Vector2();
	public Vector2 size = new Vector2();
	public Vector2 rotationPivot = new Vector2(0.5f, 0.5f);
	
	/**
	 * Rotation in degrees
	 */
	public float rotation = 0;
	
	@Override
	public ComponentSystem<?> getInstance() { // this is pure data
		return null;
	}
	
	public void setPosition(Area dim) {
		position.x = Math.max(dim.getX(), dim.getWidth());
		position.y = Math.max(dim.getY(), dim.getHeight());
	}

	public void setSize(Area dim) {
		size.x = dim.getWidth();
		size.y = dim.getHeight();
	}
	
	public void centerTo(Interface window) {
		position.x = window.getViewport().getWidth() / 2 - (size.x / 2);
		position.y = window.getViewport().getHeight() / 2 - (size.y / 2);
	}

}

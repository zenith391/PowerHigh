package org.powerhigh.objects;

import org.powerhigh.graphics.Drawer;
import org.powerhigh.graphics.Interface;

/**
 * Container without checks for content being out of bound:<br/>
 * basically this means the scene can have objects out of scene bounds. Also automatically
 * scales to scene size
 * @author zenith391
 * @see <code>org.powerhigh.objects.Container</code>
 */
public class Scene extends Container {

	{
		this.checkContent = false;
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public void paint(Drawer drawer, Interface source) {
		super.paint(drawer, source);
		setSize(source.getViewport().width, source.getViewport().height);
	}

}

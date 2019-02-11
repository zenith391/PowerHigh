package org.powerhigh.objects;

/**
 * Container without checks for content being out of bound:<br/>
 * basically this means the scene can have objects out of scene bounds.
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

}

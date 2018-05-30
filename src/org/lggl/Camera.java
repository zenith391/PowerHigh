package org.lggl;

public class Camera {

	private int xOffset, yOffset;
	
	public Camera() {
		this(0, 0);
	}
	
	public Camera(int x, int y) {
		xOffset = x;
		yOffset = y;
	}

	public int getXOffset() {
		return xOffset;
	}

	public void setXOffset(int xOffset) {
		this.xOffset = xOffset;
	}

	public int getYOffset() {
		return yOffset;
	}

	public void setYOffset(int yOffset) {
		this.yOffset = yOffset;
	}

}

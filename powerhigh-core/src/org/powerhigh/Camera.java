package org.powerhigh;

public class Camera {

	private int xOffset, yOffset;
	private double rotation;
	private double scale = 1.0f;
	
	/**
	 * Get camera rotation in degrees.
	 * @return
	 */
	public double getRotation() {
		return rotation;
	}
	
	/**
	 * Set camera rotation in degrees.
	 * @param rotation
	 */
	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

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

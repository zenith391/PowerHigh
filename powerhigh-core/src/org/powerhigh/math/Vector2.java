package org.powerhigh.math;

public class Vector2 {

	public float x, y;
	
	public Vector2() {}
	
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void set(Vector2 vec) {
		this.x = vec.x;
		this.y = vec.y;
	}

	@Override
	public int hashCode() {
		return Float.floatToIntBits(31 * (31 + x) + y);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Vector2))
				return false;
		Vector2 other = (Vector2) obj;
		return x == other.x && y == other.y;
	}
	
	public String toString() {
		return "org.powerhigh.math.Vector2[x=" + x + ", y=" + y + "]";
	}
	
}

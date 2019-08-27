package org.powerhigh.graphics;

/**
 * Class for implementations to accelerate texture writing, reading and showing
 * (by removing the need of a "converted image" buffer)
 */
public abstract class TextureAccelerator {

	public abstract int getRGB(int x, int y);
	public abstract void setRGB(int x, int y, int rgb);
	
	public abstract int getWidth();
	public abstract int getHeight();
	
	/**
	 * Whether the texture is entirely in the GPU or any kind of accelerator.
	 * @return fully accelerated
	 */
	public abstract boolean isFullyAccelerated();
	
	/**
	 * Try accelerating (stocking in GPU) the whole texture.
	 * @return succeeded
	 */
	public abstract boolean tryAccelerate();
	
	/**
	 * Whether the accelerator can lose the texture.
	 * @return can lose
	 */
	public abstract boolean canLose();
	
	/**
	 * Whether the accelerator has lost the texture (and thus it isn't accelerated anymore).
	 * In those cases the texture should be recovered by the implementation.
	 * @return has been lost
	 */
	public abstract boolean isLost();
	
	public boolean supportsFillRect() { return false; }
	public void fillRect(int x, int y, int width, int height, int rgb) {}
	
	public boolean supportsDrawRect() { return false; }
	public void drawRect(int x, int y, int width, int height, int rgb) {}
	
}

package org.powerhigh.utils;

public class BitOps {

	public static int getInt(byte[] b, int off) {
		return ((b[off + 3] & 0xFF)) + 
				((b[off + 2] & 0xFF) << 8) + 
				((b[off + 1] & 0xFF) << 16) + 
				((b[off]) << 24);
	}
	
	public static int getIntBE(byte[] b, int off) {
		return ((b[off] & 0xFF)) + 
				((b[off + 1] & 0xFF) << 8) + 
				((b[off + 2] & 0xFF) << 16) + 
				((b[off + 3]) << 24);
	}

	public static void putInt(byte[] b, int off, int val) {
		b[off + 3] = (byte) (val);
		b[off + 2] = (byte) (val >>> 8);
		b[off + 1] = (byte) (val >>> 16);
		b[off] = (byte) (val >>> 24);
	}
	
	public static void putIntBE(byte[] b, int off, int val) {
		b[off] = (byte) (val);
		b[off + 1] = (byte) (val >>> 8);
		b[off + 2] = (byte) (val >>> 16);
		b[off + 3] = (byte) (val >>> 24);
	}

}

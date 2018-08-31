package org.powerhigh.utils;

import java.beans.XMLDecoder;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;

import org.powerhigh.objects.GameObject;

public class PackInputStream extends FilterInputStream {

	private static final int SIGNATURE1 = 0x1a;
	private static final int SIGNATURE2 = 0x2b;
	
	protected PackInputStream(InputStream in) {
		super(in);
	}
	
	public GameObject readContent() throws IOException {
		if (read() != SIGNATURE1 || read() != SIGNATURE2)
			throw new IOException("Invalid signature");
		@SuppressWarnings("resource") // Same reason than PackInputStream
		XMLDecoder decoder = new XMLDecoder(this);
		return (GameObject) decoder.readObject();
	}
	
}

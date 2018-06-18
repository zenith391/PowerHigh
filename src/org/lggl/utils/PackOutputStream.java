package org.lggl.utils;

import java.beans.XMLEncoder;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.zip.DeflaterOutputStream;

import org.lggl.graphics.objects.GameObject;

/**
 * Game object serialization.
 * Best use with <code>DeflaterOutputStream</code>
 * Trust me, i got it from 1MB to 18KB (thanks ZIP compression :D)
 * @author zenith391
 *
 */
public class PackOutputStream extends FilterOutputStream {
	
	private static final int SIGNATURE1 = 0x1a;
	private static final int SIGNATURE2 = 0x2b;

	public PackOutputStream(OutputStream out) {
		super(out);
	}
	
	public void write(GameObject obj) throws IOException {
		write(SIGNATURE1);
		write(SIGNATURE2);
		@SuppressWarnings("resource") // Not closing since it is a OutputStream
		XMLEncoder encoder = new XMLEncoder(this);
		encoder.writeObject(obj);
		encoder.flush();
	}

}

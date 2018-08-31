package org.powerhigh.utils;

import java.beans.Encoder;
import java.beans.ExceptionListener;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.beans.XMLEncoder;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.zip.DeflaterOutputStream;

import org.powerhigh.graphics.Texture;
import org.powerhigh.objects.GameObject;

/**
 * Game object serialization.
 * Best use with <code>DeflaterOutputStream</code>
 * Trust me, i got it from 1MB to 18KB (thanks ZIP compression :D)
 * @author zenith391
 *
 */
public class PackOutputStream extends OutputStream {
	
	private static final int SIGNATURE1 = 0x1a;
	private static final int SIGNATURE2 = 0x2b;
	
	private OutputStream out;

	public PackOutputStream(OutputStream out, boolean compress) {
		if (compress) {
			this.out = new DeflaterOutputStream(out);
		} else {
			this.out = out;
		}
	}
	
	public void write(GameObject obj) throws IOException {
		write(SIGNATURE1);
		write(SIGNATURE2);
		@SuppressWarnings("resource") // Not closing since it is a OutputStream
		XMLEncoder encoder = new XMLEncoder(this);
		encoder.setExceptionListener(new ExceptionListener() {

			@Override
			public void exceptionThrown(Exception e) {
				e.printStackTrace();
			}
			
		});
		encoder.setPersistenceDelegate(Texture.class, new PersistenceDelegate() {

			@Override
			protected Expression instantiate(Object oldInstance, Encoder out) {
				Texture tex = (Texture) oldInstance;
				Expression exp = new Expression(oldInstance, oldInstance.getClass(), "new", new Object[] {Base64.getEncoder().encodeToString(Texture.imageToBytes(tex.getAWTImage()))});
				return exp;
			}
			
		});
		encoder.writeObject(obj);
		encoder.flush();
	}
	
	public void flush() throws IOException {
		out.flush();
	}
	
	public void close() throws IOException {
		out.close();
	}

	@Override
	public void write(int b) throws IOException {
		out.write(b);
	}
	
	@Override
	public void write(byte[] b) throws IOException {
		out.write(b);
	}
	
	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		out.write(b, off, len);
	}

}

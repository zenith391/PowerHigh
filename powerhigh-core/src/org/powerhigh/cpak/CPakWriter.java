package org.powerhigh.cpak;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPOutputStream;

import org.powerhigh.graphics.Texture;

/**
 * Supported objects:<br/>
 * <ul>
 * <li>Texture</li>
 * <li>Anything serializable</li>
 * </ul>
 * @author zenith391
 *
 */
public class CPakWriter {

	private ObjectOutputStream out;
	
	public static final int GZIP_COMPRESSION = 0;
	public static final int DEFLATE_COMPRESSION = 1;
	public static final int NO_COMPRESSION = 2;
	
	/**
	 * Compressions:
	 * <ol start="0">
	 * <li>GZIP compression</li>
	 * <li>Deflate compression</li>
	 * <li>No compression</li>
	 * </ol>
	 * @param out
	 * @param compression
	 */
	public CPakWriter(OutputStream out, int compression) {
		try {
			// Write header
			out.write((byte) compression);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (compression == 0) {
			try {
				out = new GZIPOutputStream(out, false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (compression == 1) {
			out = new DeflaterOutputStream(out);
		}
		try {
			this.out = new ObjectOutputStream(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeCPakEntries(HashMap<String, Object> entr) throws IOException {
		out.writeInt(entr.size());
		for (String key : entr.keySet()) {
			out.writeUTF(key);
			Object obj = entr.get(key);
			if (obj instanceof Texture) {
				out.write(1);
				Texture tex = (Texture) obj;
				out.writeInt(tex.getWidth());
				out.writeInt(tex.getHeight());
				for (int x = 0; x < tex.getWidth(); x++) {
					for (int y = 0; y < tex.getHeight(); y++) {
						out.writeInt(tex.getRGB(x, y));
					}
				}
			} else {
				out.write(0);
				out.writeObject(obj);
			}
		}
	}
	
	public void close() throws IOException {
		out.close();
	}
	
}

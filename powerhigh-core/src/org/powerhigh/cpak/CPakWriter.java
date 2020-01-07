package org.powerhigh.cpak;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPOutputStream;

import org.powerhigh.graphics.ParticleBlueprint;
import org.powerhigh.graphics.Texture;
import org.powerhigh.utils.Color;

/**
 * Binary format for game data.<br/>
 * Supported objects:<br/>
 * <ul>
 * <li>Texture</li>
 * </ul>
 * @author zenith391
 *
 */
public class CPakWriter {

	private DataOutputStream out;
	
	public static final int GZIP_COMPRESSION = 0;
	public static final int DEFLATE_COMPRESSION = 1;
	public static final int NO_COMPRESSION = 2;
	
	/**
	 * Compressions:
	 * <ol start="0">
	 * <li>GZIP compression</li>
	 * <li>Deflate (zlib) compression</li>
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
		this.out = new DataOutputStream(out);
	}
	
	public void writeHeader(int size) throws IOException {
		out.writeInt(size);
		out.writeInt(1);
	}
	
	public void writeEntry(Object obj) throws IOException {
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
		} else if (obj instanceof Color) {
			out.write(2);
			Color col = (Color) obj;
			out.writeByte(col.getRed());
			out.writeByte(col.getGreen());
			out.writeByte(col.getBlue());
			out.writeByte(col.getAlpha()); // a boolean would actually cost 5 bytes for transparent color and 4 bytes for opaque color, so always saving alpha.
		} else if (obj instanceof ParticleBlueprint) {
			out.write(3);
			ParticleBlueprint pb = (ParticleBlueprint) obj;
			out.writeShort(pb.getMaxLife());
			out.writeInt(pb.getSize());
			if (pb.getTexture() != null) {
				writeEntry(pb.getTexture());
			} else {
				writeEntry(pb.getColor());
			}
			out.writeUTF(pb.getParticleRenderer().getClass().getName());
		}
	}
	
	public void writeEntry(String key, Object obj) throws IOException {
		out.writeUTF(key);
		writeEntry(obj);
	}
	
	public void writeEntries(Map<String, Object> entr) throws IOException {
		writeHeader(entr.size());
		for (String key : entr.keySet()) {
			writeEntry(key, entr.get(key));
		}
	}
	
	public void close() throws IOException {
		out.close();
	}
	
}

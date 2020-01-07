package org.powerhigh.cpak;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

import org.powerhigh.graphics.ParticleBlueprint.ParticleRenderer;
import org.powerhigh.graphics.Texture;
import org.powerhigh.utils.Color;

public class CPakReader {

	HashMap<String, Object> entries = new HashMap<>();
	DataInputStream in;
	
	public CPakReader(InputStream is) {
		try {
			int comp = is.read();
			if (comp == 0) {
				is = new GZIPInputStream(is);
			} else if (comp == 1) {
				is = new InflaterInputStream(is);
			}
			this.in = new DataInputStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public HashMap<String, Object> getEntries() {
		return entries;
	}
	
	public Object readEntry() throws IOException {
		int objectType = in.read();
		if (objectType == 1) { // texture
			int width = in.readInt();
			int height = in.readInt();
			int[][] data = new int[width][height];
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					data[x][y] = in.readInt();
				}
			}
			return new Texture(data);
		} else if (objectType == 2) {
			int r = in.readInt();
			int g = in.readInt();
			int b = in.readInt();
			int a = in.readInt();
			return new Color(r, g, b, a);
		} else if (objectType == 3) {
			short maxLife = in.readShort();
			int size = in.readInt();
			Object o = readEntry();
			String clName = in.readUTF();
			ParticleRenderer pr = null;
			try {
				Class<?> cl = Class.forName(clName);
				pr = (ParticleRenderer) cl.getConstructor().newInstance();
			} catch (ClassNotFoundException e) {
				throw new IOException("Could not find ParticleRenderer class " + clName, e);
			} catch (Exception e) {
				throw new IOException("Could not instantiate ParticleRenderer class " + clName, e);
			}
			
			return null;
		} else {
			throw new IOException("Unsupported object type: " + objectType + ". The file version isn't compatible with the current.");
		}
	}
	
	public void readEntries() throws IOException {
		int size = in.readInt();
		int ver = in.readInt();
		for (int i = 0; i < size; i++) {
			String key = in.readUTF();
			entries.put(key, readEntry());
		}
	}
	
}

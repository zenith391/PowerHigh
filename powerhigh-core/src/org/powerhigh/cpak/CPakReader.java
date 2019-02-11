package org.powerhigh.cpak;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

import org.powerhigh.graphics.Texture;

public class CPakReader {

	HashMap<String, Object> entries = new HashMap<>();
	ObjectInputStream is;
	
	public CPakReader(InputStream is) {
		try {
			int comp = is.read();
			if (comp == 0) {
				is = new GZIPInputStream(is);
			} else if (comp == 1) {
				is = new InflaterInputStream(is);
			}
			this.is = new ObjectInputStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readEntries() throws IOException {
		int size = is.readInt();
		for (int i = 0; i < size; i++) {
			String key = is.readUTF();
			int objectType = is.read();
			if (objectType == 0) {
				try {
					entries.put(key, is.readObject());
				} catch (ClassNotFoundException e) {
					throw new IOException("invalid object", e);
				}
			} else {
				if (objectType == 1) { // texture
					int width = is.readInt();
					int height = is.readInt();
					int[][] data = new int[width][height];
					for (int x = 0; x < width; x++) {
						for (int y = 0; y < height; y++) {
							data[x][y] = is.readInt();
						}
					}
					entries.put(key, new Texture(data));
				}
			}
		}
	}
	
}

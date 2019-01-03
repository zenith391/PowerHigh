package org.powerhigh.cpak;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

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
	
}

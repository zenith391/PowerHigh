package org.lggl;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;

import org.lggl.graphics.BitmapFont;

public class LGGL {

	private static LGGL instance;
	
	static {
		instance = new LGGL();
	}
	
	public static LGGL getContext() {
		return instance;
	}
	
	public BitmapFont createBitmapFont(InputStream img, InputStream desc) {
		return null;
	}

}

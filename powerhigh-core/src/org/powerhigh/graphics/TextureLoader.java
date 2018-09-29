package org.powerhigh.graphics;

import java.io.IOException;
import java.io.InputStream;

public class TextureLoader {

	private static TextureLoaderPlugin plugin;
	
	public static TextureLoaderPlugin getPlugin() {
		return plugin;
	}

	public static void setPlugin(TextureLoaderPlugin plugin) {
		TextureLoader.plugin = plugin;
	}

	public static interface TextureLoaderPlugin {
		
		public Texture load(InputStream input);
		public Texture load(String path);
		
	}
	
	public static Texture getTexture(String path) throws IOException {
		return plugin.load(path);
	}
	
	public static Texture getTexture(InputStream is) throws IOException {
		return plugin.load(is);
	}
	
}

package org.powerhigh.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Properties;

import org.powerhigh.graphics.Texture;
import org.powerhigh.graphics.TextureAtlas;
import org.powerhigh.graphics.TextureLoader;

public class FileUIModel extends UIModel {

	private Properties prop;
	private TextureAtlas atlas;
	private HashMap<String, Texture> textures = new HashMap<>(); // for version 2, in separated
	private int ver;
	
	public FileUIModel(File file) throws FileNotFoundException {
		this(new FileReader(file), file.getParent());
	}
	
	public FileUIModel(Reader file, InputStream img) {
		try {
			prop = new Properties();
			prop.load(file);
			ver = Integer.parseInt(prop.getProperty("version", "1"));
			atlas = TextureAtlas.getFrom(TextureLoader.getTexture(img), Integer.parseInt(prop.getProperty("tileWidth")), Integer.parseInt(prop.getProperty("tileHeight")));
		} catch (Exception e) {
			System.err.println("Error loading FileUIModel of " + file);
			e.printStackTrace();
		}
	}
	
	public FileUIModel(Reader file, String parent) {
		try {
			prop = new Properties();
			prop.load(file);
			ver = Integer.parseInt(prop.getProperty("version", "1"));
			if (ver == 1) {
				atlas = TextureAtlas.getFrom(
						TextureLoader.getTexture(parent + prop.getProperty("file", "skin.png")),
						Integer.parseInt(prop.getProperty("tileWidth")), Integer.parseInt(prop.getProperty("tileHeight")));
			} else {
				boolean separate = Boolean.parseBoolean(prop.getProperty("separated", "false"));
				if (separate) {
					for (Object key : prop.keySet()) {
						if (key.toString().startsWith("skin.")) {
							String k = key.toString().substring(5);
							textures.put(k, TextureLoader.getTexture(parent + prop.getProperty(key.toString())));
						}
					}
				} else {
					atlas = TextureAtlas.getFrom(
							TextureLoader.getTexture(parent + prop.getProperty("file", "skin.png")),
							Integer.parseInt(prop.getProperty("tileWidth")), Integer.parseInt(prop.getProperty("tileHeight")));
				}
			}
		} catch (Exception e) {
			System.err.println("Error loading FileUIModel of " + file);
			e.printStackTrace();
		}
	}
	
	public int getVersion() {
		return ver;
	}
	
	@Override
	public TextureAtlas getAtlas() {
		return atlas;
	}

	@Override
	public Texture getUI(String id) {
		if (textures.containsKey(id)) {
			return textures.get(id);
		}
		String sub = prop.getProperty("skin." + id);
		if (sub == null)
			return null;
		int tx = Integer.parseInt(sub.split(";")[0]);
		int ty = Integer.parseInt(sub.split(";")[1]);
		return atlas.getSubtexture(tx, ty);
	}

}

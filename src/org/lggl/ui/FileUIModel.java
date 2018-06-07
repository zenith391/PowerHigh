package org.lggl.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

import org.lggl.graphics.Texture;
import org.lggl.graphics.TextureAtlas;
import org.lggl.graphics.TextureLoader;

public class FileUIModel extends UIModel {

	private Properties prop;
	private TextureAtlas atlas;
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
			System.out.println(new File(parent + prop.getProperty("file", "ui_skin.png")));
			atlas = TextureAtlas.getFrom(TextureLoader.getTexture(new File(parent + prop.getProperty("file", "ui_skin.png"))), Integer.parseInt(prop.getProperty("tileWidth")), Integer.parseInt(prop.getProperty("tileHeight")));
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
		String sub = prop.getProperty("skin." + id);
		if (sub == null)
			return null;
		int tx = Integer.parseInt(sub.split(";")[0]);
		int ty = Integer.parseInt(sub.split(";")[1]);
		return atlas.getSubtexture(tx, ty);
	}

}

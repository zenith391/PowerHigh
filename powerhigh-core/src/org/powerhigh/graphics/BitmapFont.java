package org.powerhigh.graphics;

public class BitmapFont {

	private Texture[] chars;
	private String encoding;
	
	public BitmapFont(Texture[] chars, String encoding) {
		this.chars = chars;
		this.encoding = encoding;
	}
	
	public String getEncoding() {
		return encoding;
	}
	
	public Texture getChar(int ch) {
		return chars[ch];
	}
	
	public void setChar(int ch, Texture t) {
		chars[ch] = t;
	}
	
	public int getCharsLength() {
		return chars.length;
	}
	
	public static BitmapFont load(String path) {
		Texture[] chars = null;
		String encoding = "UTF-8";
		return new BitmapFont(chars, encoding);
	}
	
}

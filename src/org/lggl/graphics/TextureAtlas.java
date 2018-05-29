package org.lggl.graphics;

public class TextureAtlas {

	private Texture[][] atlas;
	
	public static TextureAtlas getFrom(Texture t, int tw, int th) {
		TextureAtlas atlas = new TextureAtlas();
		atlas.atlas = new Texture[t.getWidth() / tw][t.getHeight() / th];
		for (int y = 0; y < t.getHeight()/th; y++) {
			for (int x = 0; x < t.getWidth()/tw; x++) {
				atlas.atlas[x][y] = new Texture(t.getAWTImage().getSubimage(x * tw, y * th, tw, th));
			}
		}
		return atlas;
	}
	
	public Texture getSubtexture(int tx, int ty) {
		return atlas[tx][ty];
	}
	
}

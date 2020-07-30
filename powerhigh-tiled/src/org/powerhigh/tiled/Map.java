package org.powerhigh.tiled;

import org.powerhigh.graphics.Drawer;

public class Map {

	int[][] tile;
	int width, height;
	Tileset[] tilesets;
	MapOrientation orientation;
	boolean infinite;
	
	/**
	 * Get the {@link Tileset} for tile global ID.
	 * @param gid
	 * @return tileset
	 */
	public Tileset getTileset(int gid) {
		for (Tileset ts : tilesets) {
			if (gid >= ts.firstGlobalId && gid < ts.firstGlobalId+ts.tiles.length) {
				return ts;
			}
		}
		return null;
	}
	
	public int getTileGId(int x, int y) {
		return tile[x][y];
	}
	
	public void setTile(int x, int y, int id) {
		tile[x][y] = id;
	}
	
	public Tile getTile(int x, int y) {
		int gid = getTileGId(x, y);
		Tileset ts = getTileset(gid);
		if (ts != null) {
			return ts.tiles[gid - ts.firstGlobalId];
		} else {
			return null;
		}
	}
	
	public Tileset[] getTilesets() {
		return tilesets;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public MapOrientation getOrientation() {
		return orientation;
	}
	
	public boolean isInfinite() {
		return infinite;
	}
	
	public void draw(Drawer d) {
		
	}
	
}

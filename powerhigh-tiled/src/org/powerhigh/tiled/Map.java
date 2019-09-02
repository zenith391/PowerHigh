package org.powerhigh.tiled;

public class Map {

	int[][] tile;
	Tileset tileset;
	
	public int getTileId(int x, int y) {
		return tile[x][y];
	}
	
	public void setTile(int x, int y, int id) {
		tile[x][y] = id;
	}
	
	public Tile getTile(int x, int y) {
		return tileset.getTile(getTileId(x, y)-1);
	}
	
	public Tileset getTileset() {
		return tileset;
	}
	
	public int getWidth() {
		return tile.length;
	}
	
	public int getHeight() {
		return tile[0].length;
	}
	
}

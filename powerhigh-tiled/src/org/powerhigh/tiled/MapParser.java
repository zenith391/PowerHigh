package org.powerhigh.tiled;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.powerhigh.graphics.TextureLoader;
import org.powerhigh.utils.debug.DebugLogger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Very basic Tiled map parser that only works with CSV maps. More support coming soon
 */
public class MapParser {

	public static Tileset loadTileset(File file) throws Exception {
		Tileset ts = new Tileset();
		ArrayList<Tile> tiles = new ArrayList<>();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
		factory.setIgnoringComments(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(file);
		Element tsNode = (Element) doc.getElementsByTagName("tileset").item(0);
		
		NodeList list = tsNode.getElementsByTagName("tile");
		for (int i = 0; i < list.getLength(); i++) {
			Element elem = (Element) list.item(i);
			int id = Integer.parseInt(elem.getAttribute("id"));
			
			Element imageElem = (Element) elem.getElementsByTagName("image").item(0);
			String source = imageElem.getAttribute("source");
			
			File sourceFile = new File(file.getParent(), source);
			DebugLogger.debug("Tile " + id + ": " + sourceFile);
			Tile tile = new Tile();
			tile.texture = TextureLoader.getTexture(sourceFile.getPath());
			tiles.add(tile);
		}
		
		ts.tiles = tiles.toArray(new Tile[tiles.size()]);
		return ts;
	}
	
	public static Map load(File file) throws Exception {
		Map map = new Map();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
		factory.setIgnoringComments(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(file);
		Element mapNode = (Element) doc.getElementsByTagName("map").item(0);
		
		int width = Integer.parseInt(mapNode.getAttributes().getNamedItem("width").getNodeValue());
		int height = Integer.parseInt(mapNode.getAttributes().getNamedItem("height").getNodeValue());
		
		DebugLogger.debug("Map size: " + width + "x" + height);
		
		Node tileset = mapNode.getElementsByTagName("tileset").item(0);
		File tsSource = new File(file.getCanonicalPath(), tileset.getAttributes().getNamedItem("source").getNodeValue());
		
		DebugLogger.debug("Tileset Path: " + tsSource);
		
		Tileset ts = loadTileset(tsSource);
		map.tile = new int[width][height];
		map.tileset = ts;
		
		Element data = (Element) ((Element) mapNode.getElementsByTagName("layer").item(0)).getElementsByTagName("data").item(0);
		String[] lines = data.getTextContent().substring(1).split("\n");
		for (int y = 0; y < lines.length-1; y++) {
			String[] values = lines[y].split(",");
			for (int x = 0; x < values.length; x++) {
				if (!values[x].isEmpty()) {
					map.tile[x][y] = Integer.parseInt(values[x]);
				}
			}
		}
		
		return map;
	}
	
}

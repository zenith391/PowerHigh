package org.powerhigh.objects;

import org.powerhigh.graphics.Drawer;
import org.powerhigh.graphics.Interface;
import org.powerhigh.utils.Color;

public class Text extends GameObject {
	
	Object font;
	Color color;
	String text;

	public Text(int x, int y, String text, Color color) {
		this(x, y, text, color, null);
	}
	
	public Text(String text) {
		this(0, 0, text);
	}
	
	public Text(int x, int y, String text) {
		this(x, y, text, Color.BLACK);
	}
	
	public Text() {
		this("");
	}
	
	public Text(int x, int y, String text, Color color, Object font) {
		this.x = x;
		this.y = y;
		this.text = text;
		this.color = color;
		this.font = font;
	}

	@Override
	public void paint(Drawer g, Interface source) {
//		width = g.getFontMetrics().stringWidth(text);
//		g.setColor(color);
//		if (!text.contains("\n")) {
//			g.drawString(text, 0, g.getFontMetrics().getHeight());
//			height = g.getFontMetrics().getHeight();
//		} else {
//			int txtY = g.getFontMetrics().getHeight();
//			height = g.getFontMetrics().getHeight() * text.split("\n").length;
//			for (String str : text.split("\n")) {
//				g.drawString(str, 0, txtY);
//				txtY += g.getFontMetrics().getHeight();
//			}
//		}
		
		g.setColor(getColor());
		g.drawText(x, y, text);
		
		// Using canvas for rotation support
	}

	public void setText(String txt) {
		text = txt;
	}

	public Object getFont() {
		return font;
	}

	public void setFont(Object font) {
		this.font = font;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getText() {
		return text;
	}

}

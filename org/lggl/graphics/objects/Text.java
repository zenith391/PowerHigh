package org.lggl.graphics.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import org.lggl.graphics.Window;

public class Text extends GameObject {
	
	Font font;
	Color color;
	String text;

	public Text(int x, int y, String text, Color color) {
		this(x, y, text, color, new Font("Arial", Font.PLAIN, 12));
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
	
	public Text(int x, int y, String text, Color color, Font font) {
		this.x = x;
		this.y = y;
		this.text = text;
		this.color = color;
		this.font = font;
	}

	@Override
	public void paint(Graphics g, Window source) {
		Font old0 = g.getFont();
		if (font != null)
			g.setFont(font);
		width = g.getFontMetrics().stringWidth(text);
		height = g.getFontMetrics().getHeight();
		g.setColor(color);
		if (!text.contains("\n")) {
			g.drawString(text, x, y);
		} else {
			int txtY = y;
			for (String str : text.split("\n")) {
				g.drawString(str, x, txtY);
				txtY += 15;
			}
		}
		g.setFont(old0);
	}

	public void setText(String txt) {
		text = txt;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
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

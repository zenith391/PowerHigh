package org.lggl.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

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
		BufferedImage canvas = new BufferedImage(width > 0 ? width : 100, height > 0 ? height : 100, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = canvas.createGraphics();
		if (font != null)
			g2d.setFont(font);
		width = g2d.getFontMetrics().stringWidth(text);
		g2d.setColor(color);
		if (!text.contains("\n")) {
			g2d.drawString(text, 0, g2d.getFontMetrics().getHeight());
			height = g2d.getFontMetrics().getHeight();
		} else {
			int txtY = g2d.getFontMetrics().getHeight();
			height = g2d.getFontMetrics().getHeight() * text.split("\n").length;
			for (String str : text.split("\n")) {
				g2d.drawString(str, 0, txtY);
				txtY += g2d.getFontMetrics().getHeight();
			}
		}
		g.drawImage(canvas, x, y - g2d.getFontMetrics().getHeight(), null);
		
		// Using canvas for rotation support
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

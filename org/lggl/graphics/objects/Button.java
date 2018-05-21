package org.lggl.graphics.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.lggl.graphics.Texture;
import org.lggl.graphics.TextureAtlas;
import org.lggl.graphics.TextureLoader;
import org.lggl.graphics.Window;
import org.lggl.input.Mouse;

public class Button extends GameObject {

	Color textColor = Color.WHITE;
	Color buttonColor = Color.BLACK;
	Color hoverColor = Color.GRAY;
	Color pressColor = Color.LIGHT_GRAY;
	ArrayList<Runnable> runnables = new ArrayList<Runnable>();
	String buttonText = "";
	private Texture img;
	private Texture pressed;
	private Texture normal;
	
	public Button() {
		setSize(60, 60);
		try {
			normal = TextureAtlas.getFrom(TextureLoader.getTexture(new File("base/base_ui_skin.png")), 64, 64).getSubtexture(0, 0);
			pressed = TextureAtlas.getFrom(TextureLoader.getTexture(new File("base/base_ui_skin.png")), 64, 64).getSubtexture(1, 0);
			img = normal;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Color getForeground() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public Color getColor() {
		return buttonColor;
	}

	public void setTexture(Texture texture) {
		img = texture;
	}

	public void setColor(Color buttonColor) {
		this.buttonColor = buttonColor;
	}

	public String getText() {
		return buttonText;
	}

	public void addAction(Runnable runnable) {
		runnables.add(runnable);
	}
	
	public void removeActions() {
		runnables.removeAll(runnables);
	}
	
	public Runnable[] getActions() {
		return runnables.toArray(new Runnable[runnables.size()]);
	}

	void exeRunnables() {
		for (Runnable run : runnables) {
			run.run();
		}
	}

	public void setText(String buttonText) {
		this.buttonText = buttonText;
	}

	@Override
	public void paint(Graphics g, Window source) {
		Color cl = buttonColor;
		if (isInBounds(Mouse.getX(), Mouse.getY(), getX(), getY(), getWidth(), getHeight())) {
			cl = hoverColor;
		}
		if (img == null) {
			g.setColor(cl);
			g.fillRect(x, y, getWidth(), getHeight());
		} else {
			g.drawImage(img.getAWTImage(), x, y, getWidth(), getHeight(), null);
		}
		g.setColor(textColor);
		g.drawString(buttonText, x + ((getWidth() / 2) - (g.getFontMetrics().stringWidth(buttonText) / 2)), y + ((getHeight() / 2) + (g.getFontMetrics().getHeight() / 2)));
	}

	public Color getPressColor() {
		return pressColor;
	}

	public void setPressColor(Color pressColor) {
		this.pressColor = pressColor;
	}

	public Color getTextColor() {
		return textColor;
	}

	public static boolean isInBounds(double x, double y, double d, double e, double f, double g) {
		boolean cond1 = false;
		boolean cond2 = false;
		for (int i = (int) d; i < f + d; i++) {
			if (x == i) {
				cond1 = true;
			}
			for (int i2 = (int) e; i2 < g + e; i2++) {
				if (y == i2) {
					cond2 = true;
				}
			}
		}
		return cond1 && cond2;
	}
	
	public void onEvent(String type, Object... args) {
		if (type.equals("mouseReleased")) {
			img = normal;
			exeRunnables();
		}
		if (type.equals("mousePressed")) {
			img = pressed;
		}
	}

}

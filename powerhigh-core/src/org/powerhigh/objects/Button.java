package org.powerhigh.objects;

import org.powerhigh.utils.Color;
import java.util.ArrayList;

import org.powerhigh.graphics.Texture;
import org.powerhigh.graphics.Drawer;
import org.powerhigh.graphics.Interface;
import org.powerhigh.input.Mouse;
import org.powerhigh.ui.UISystem;

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
	private Texture hover;
	private boolean p;
	
	public Button() {
		setSize(60, 60);
		normal = UISystem.getUI("button-normal");
		pressed = UISystem.getUI("button-pressed");
		hover = UISystem.getUI("button-hover");
		img = normal;
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
	public void paint(Drawer g, Interface source) {
		Color cl = buttonColor;
		if (isInBounds(Mouse.getX(), Mouse.getY(), getX(), getY(), getWidth(), getHeight())) {
			cl = hoverColor;
			if (hover != null) {
				if (img != hover && img != pressed) {
					img = hover;
				}
			}
		} else {
			if (hover != null) {
				if (img == hover) {
					img = normal;
				}
			}
		}
		if (p == true) {
			cl = pressColor;
		}
		if (img == null) {
			g.setColor(cl);
			g.fillRect(x, y, getWidth(), getHeight());
		} else {
			//g.drawImage(img.getAWTImage(), x, y, getWidth(), getHeight(), null);
		}
		//g.setFont(new Font("Calibri", Font.PLAIN, 13));
		g.setColor(textColor);
		g.drawText(x + 5, y + 15, buttonText);
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
			p = false;
		}
		if (type.equals("mousePressed")) {
			img = pressed;
			p = true;
		}
	}

}

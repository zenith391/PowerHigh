package org.lggl.objects;

import java.awt.Graphics;
import java.util.ArrayList;

import org.lggl.graphics.Window;

public class Container extends GameObject {

	protected ArrayList<GameObject> objects = new ArrayList<GameObject>();
	protected boolean checkContent = true;
	protected GameObject focusedObj;

	public void add(GameObject obj) {
		if (obj == this) {
			throw new IllegalArgumentException(
					"Impossible to add container to itself ! (Cause: " + this + " == " + obj + ")");
		}
		if (obj == null) {
			throw new NullPointerException();
		}
		objects.add(obj);
	}
	
	public void onEvent(String type, Object... args) {
		if (type.equals("mousePressed")) {
			GameObject[] a = objects.toArray(new GameObject[objects.size()]);
			int mx = (int) args[0];
			int my = (int) args[1];
			focusedObj = null;
			for (GameObject b : a) {
				
				if (mx > b.getX() && my > b.getY() && mx < b.getX() + b.getWidth() && my < b.getY() + b.getHeight()) {
					focusedObj = b;
					break;
				}
			}
		}
		if (focusedObj != null)
			focusedObj.onEvent(type, args);
	}
	
	public void setX(int x) {
		int curX = getX();
		for (GameObject obj : objects) {
			int offset = obj.getX() - curX;
			obj.setX(x + offset);
		}
		super.setX(x);
	}
	
	public void setY(int y) {
		int curY = getY();
		for (GameObject obj : objects) {
			int offset = obj.getY() - curY;
			obj.setY(y + offset);
		}
		super.setY(y);
	}

	public void remove(GameObject obj) {
		objects.remove(obj);
	}

	public void paint(Graphics g, Window source) {
		for (GameObject go : objects) {
			if (checkContent) {
				if (go.getY() < getY()) {
					go.setY(getY());
				}
				if (go.getY() > getY() + getHeight()) {
					go.setY(getY());
				}
				if (go.getX() < getX()) {
					go.setX(getX());
				}
				if (go.getX() > getX() + getWidth()) {
					go.setX(getX());
				}
			}
			boolean render = source.shouldRender(go);
			if (render) {
				go.paint(g, source);
			}
		}
	}

	public ArrayList<GameObject> getObjects() {
		return objects;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		try {
			if (objects.get(0) != null) {
				builder.append("Container [objects=");
			}
		} catch (Exception e) {
			builder.append("Container [");
		}
		for (GameObject obj : objects) {
			builder.append(obj.toString() + ";");
		}
		builder.append("]");
		return builder.toString();
	}
}

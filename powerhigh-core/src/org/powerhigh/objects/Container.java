package org.powerhigh.objects;

import java.util.ArrayList;
import java.util.Objects;

import org.powerhigh.graphics.Drawer;
import org.powerhigh.graphics.Interface;

public class Container extends GameObject {

	protected ArrayList<GameObject> objects = new ArrayList<GameObject>();
	protected boolean checkContent = true;
	protected GameObject focusedObj;

	public void add(GameObject obj) {
		if (obj == this) {
			throw new IllegalArgumentException(
					"Cannot add container in itself");
		}
		Objects.requireNonNull(obj, "obj");
		objects.add(obj);
	}

	@Override
	public void onEvent(String type, Object... args) {
		if (type.equals("mousePressed")) {
			GameObject[] a = objects.toArray(new GameObject[objects.size()]);
			int mx = (int) args[0];
			int my = (int) args[1];
			focusedObj = null;
			for (int i = a.length-1; i >= 0; i--) { // in part with rendering
				GameObject b = a[i];
				if (mx > b.getX() && my > b.getY() && mx < b.getX() + b.getWidth() && my < b.getY() + b.getHeight()) {
					focusedObj = b;
					break;
				}
			}
		}
		if (focusedObj != null) {
			focusedObj.onEvent(type, args);
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		for (GameObject obj : objects) {
			obj.dispose();
		}
		objects = null;
		focusedObj = null;
	}

	@Override
	public void setX(int x) {
		int curX = getX();
		for (GameObject obj : objects) {
			int offset = obj.getX() - curX;
			obj.setX(x + offset);
		}
		super.setX(x);
	}

	@Override
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

	@Override
	public void paint(Drawer g, Interface source) {
		for (GameObject go : getObjects()) {
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

	public GameObject[] getObjects() {
		return objects.toArray(new GameObject[objects.size()]);
	}

	public void removeAll() {
		objects = new ArrayList<GameObject>();
		System.gc();
	}

	@Override
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

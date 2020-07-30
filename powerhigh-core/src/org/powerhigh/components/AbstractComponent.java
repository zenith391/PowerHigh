package org.powerhigh.components;

import org.powerhigh.objects.GameObject;

public abstract class AbstractComponent implements Component {
	
	public GameObject gameObject;
	
	public abstract ComponentSystem<?> getInstance();

	@Override
	public GameObject getGameObject() {
		return gameObject;
	}

	@Override
	public void setGameObject(GameObject go) {
		this.gameObject = go;
	}

}

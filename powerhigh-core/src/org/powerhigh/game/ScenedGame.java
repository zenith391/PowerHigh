package org.powerhigh.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.powerhigh.objects.Scene;

public abstract class ScenedGame extends SimpleGame {

	private Map<String, Scene> loadedScenes = new HashMap<>();
	private Scene currentScene;

	public void loadScene(String name, Scene scene) {
		Objects.requireNonNull(name, "name");
		Objects.requireNonNull(scene, "scene");
		if (loadedScenes.containsKey(name)) {
			throw new IllegalStateException("scene arleady loaded");
		}
		loadedScenes.put(name, scene);
	}

	public void setScene(String name) {
		Objects.requireNonNull(name, "name");
		if (!loadedScenes.containsKey(name)) {
			throw new IllegalStateException("no scene with name " + name);
		}
		if (currentScene != null) {
			window.remove(currentScene);
		}
		window.add(loadedScenes.get(name));
		currentScene = loadedScenes.get(name);
	}

	public void sceneUpdate() {
		if (currentScene != null) {
			currentScene.setSize(window.getViewport());
			currentScene.setPosition(0, 0);
		}
	}

	public void unloadScene(String name) {
		loadedScenes.remove(name);
	}

}

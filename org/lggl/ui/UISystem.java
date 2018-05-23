package org.lggl.ui;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.lggl.graphics.Texture;

public class UISystem {

	private static UIModel uiModel;
	
	static {
		if (uiModel == null) {
			try {
				URI uri = UISystem.class.getClassLoader().getResource("org/lggl/ui/base/").toURI();
				uiModel = new FileUIModel(new InputStreamReader(UISystem.class.getClassLoader().getResource("org/lggl/ui/base/base_ui_skin.ui").openStream()), 
						new File(uri).toString() + "\\");
			} catch (IOException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static Texture getUI(String ui) {
		Texture tex = null;
		if (uiModel != null) {
			tex = uiModel.getUI(ui);
		}
		return tex;
	}

	public static UIModel getUIModel() {
		return uiModel;
	}

	public static void setUIModel(UIModel uiModel) {
		UISystem.uiModel = uiModel;
	}
	
}

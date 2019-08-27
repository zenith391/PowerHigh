package org.powerhigh.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.powerhigh.graphics.Texture;
import org.powerhigh.utils.debug.DebugLogger;

public class UISystem {

	private static UIModel uiModel;
	
	static {
		if (uiModel == null) {
			try {
				File base = new File(System.getProperty("powerhigh.ui.folder", "default_ui"));
				if (base.exists()) {
					uiModel = new FileUIModel(new InputStreamReader(new FileInputStream(
							System.getProperty("powerhigh.ui.folder", "default_ui") + "/skin.cfg")), 
							System.getProperty("powerhigh.ui.folder", "default_ui") + "/"); // Special case, inside .jar / project
				} else {
					DebugLogger.logWarn("Cannot find the UI files.");
				}
			} catch (Exception e) {
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

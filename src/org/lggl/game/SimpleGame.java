package org.lggl.game;

import java.awt.Toolkit;

import org.lggl.graphics.ErrorBox;
import org.lggl.graphics.Window;
import org.lggl.utils.debug.DebugLogger;

public abstract class SimpleGame {

	protected Window window = new Window();

	public abstract void update(Window win);

	public abstract void init(Window win);

	private void a() {
		String var0 = System.getProperty("os.name");
		String var1 = System.getProperty("os.version");
		DebugLogger.logInfo("Operating System is " + var0 + "/" + var1);
	}

	protected boolean launched;
	
	public void start() {
		try {
			DebugLogger.logInfo("Preparing game..");
			a();
			// window.show();
			this.init(window);
			window.show();
			while (true) {
				this.update(window);
				Thread.sleep(1000 / 60);
				if (!launched) {
					DebugLogger.logInfo("Game launched");
					launched = true;
				}
			}
		} catch (Throwable t) {
			Toolkit.getDefaultToolkit().beep();
			ErrorBox.create()
			.throwable(t)
			.show(window.getJFrame().getContentPane());
			System.exit(1);
		}
	}
}

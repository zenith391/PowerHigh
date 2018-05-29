package org.lggl.game;

import java.awt.Color;

import javax.swing.SwingUtilities;

import org.jfree.fx.FXGraphics2D;

import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * <h1>NOT READY FOR USAGE</h1>
 *
 */
public class FXContainer extends Application {
	
	private static SimpleGame game;
	private static Canvas canvas;
	private static FXGraphics2D g;
	
	public FXContainer(SimpleGame game) {
		//game.showWin = false;
		FXContainer.game = game;
	}
	
	public FXContainer() {
		// Application instanciation method
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane pane = new BorderPane();
		canvas = new Canvas();
		canvas.setWidth(1280);
		canvas.setHeight(720);
		pane.setCenter(canvas);
		canvas.getGraphicsContext2D().setFill(javafx.scene.paint.Color.BLACK);
		canvas.getGraphicsContext2D().setStroke(javafx.scene.paint.Color.BLACK);
		g = new FXGraphics2D(canvas.getGraphicsContext2D());
		game.window.setCustomGraphics(g);
		
		canvas.getGraphicsContext2D().fillText("LGGL FX", 1, 10);
		Scene scene = new Scene(pane, 620, 480);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void start() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Thread th = new Thread() {
					public void run() {
						game.start();
					}
				};
				th.start();
			}
		});
		Application.launch(FXContainer.class);
	}

}

package org.powerhigh.jfx;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.powerhigh.graphics.Interface;
import org.powerhigh.graphics.TextureLoader;
import org.powerhigh.input.Input;
import org.powerhigh.jfx.input.JFXKeyboard;
import org.powerhigh.jfx.input.JFXMouse;
import org.powerhigh.jfx.input.JFXTexturePlugin;
import org.powerhigh.utils.Area;
import org.powerhigh.utils.Color;
import org.powerhigh.utils.Point;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


// POSSIBILITY: use com.sun.media.jfxmediaimpl.AudioClipProvider.create for sound
public class JFXInterfaceImpl extends Interface {

	public static Stage stage;
	protected static boolean closeRequested;
	private static boolean platformStarted;
	private Object answer;
	public static Canvas gameCanvas;
	private boolean inited;
	private GCDrawer drawer;
	private boolean visible;
	private Color background = Color.BLUE;
	static JFXInterfaceImpl instance;
	private JFXMouse mouse;
	private JFXKeyboard keyboard;
	
	public static class JFXApp extends Application {

		private BorderPane pane;
		private Scene scene;
		
		public JFXApp() {
			
		}
		
		@Override
		public void start(Stage primaryStage) throws Exception {
			pane = new BorderPane();
			scene = new Scene(pane, 620, 480);
			gameCanvas = new Canvas();
			pane.setTop(gameCanvas);
			ExecutorService service = Executors.newSingleThreadExecutor();
			
			// Mouse events
			gameCanvas.setOnMouseMoved((e) -> {
				service.submit(() -> {
					instance.mouse.mouseMoved((int) e.getX(), (int) e.getY(), (int) e.getScreenX(), (int) e.getScreenY());
				});
			});
			gameCanvas.setOnMouseDragged((e) -> {
				service.submit(() -> {
					instance.mouse.mouseDragged(e.getButton().ordinal() - 1, (int) e.getX(), (int) e.getY(), (int) e.getScreenX(), (int) e.getScreenY());
				});
			});
			gameCanvas.setOnMousePressed((e) -> {
				service.submit(() -> {
					instance.mouse.mousePressed(e.getButton().ordinal() - 1, (int) e.getX(), (int) e.getY());
				});
			});
			gameCanvas.setOnMouseReleased((e) -> {
				service.submit(() -> {
					instance.mouse.mouseReleased(e.getButton().ordinal() - 1, (int) e.getX(), (int) e.getY());
				});
			});
			
			// Keyboard events
			scene.setOnKeyPressed((e) -> {
				service.submit(() -> {
					instance.keyboard.keyPressed(e.getCode().getCode());
				});
			});
			scene.setOnKeyReleased((e) -> {
				service.submit(() -> {
					instance.keyboard.keyReleased(e.getCode().getCode());
				});
			});
			scene.setOnKeyTyped((e) -> {
				service.submit(() -> {
					instance.keyboard.keyTyped(e.getCode().getCode());
				});
			});
			
			stage = primaryStage;
			stage.setScene(scene);
			stage.setOnCloseRequest((event) -> {
				closeRequested = true;
			});
			
			//stage.show();
			//System.out.println("start ended");
		}
		
	}
	
	public JFXInterfaceImpl() {
		instance = this;
		TextureLoader.setPlugin(new JFXTexturePlugin());
		Thread t = new Thread(() -> {
			if (!platformStarted) {
				Application.launch(JFXApp.class);
				platformStarted = true;
			} else {
				JFXApp app = new JFXApp();
				Platform.runLater(() -> {
					try {
						//System.out.println("initing..");
						app.init();
						app.start(new Stage());
						//System.out.println("inited :D");
					} catch (Exception e) {
						System.out.println("JavaFX error:");
						e.printStackTrace();
					}
				});
				Platform.requestNextPulse();
				System.out.println("exit");
			}
		});
		t.start();
		try {
			t.join(1000);
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
		drawer = new GCDrawer();
		init();
	}
	
	@Override
	public void setBackground(Color color) {
		background = color;
	}

	@Override
	public Color getBackground() {
		return background;
	}

	@Override
	public void show() {
		if (!inited) {
			inited = true;
			instance.mouse = new JFXMouse(0, 0, instance);
			instance.keyboard = new JFXKeyboard(this);
			Input.setMouseImpl(mouse);
			Input.setKeyboardImpl(instance.keyboard);
		}
		Platform.runLater(() -> {
			if (stage != null)
				stage.show();
		});
		visible = true;
	}

	@Override
	public void hide() {
		Platform.runLater(() -> {
			stage.hide();
		});
	}

	@Override
	public boolean isCloseRequested() {
		return closeRequested;
	}

	@Override
	public boolean isVisible() {
		answer = null;
		Platform.runLater(() -> {
			answer = stage.isShowing();
		});
		while (answer == null) {
			Thread.onSpinWait();
		}
		return (Boolean) answer;
	}

	@Override
	public void setSize(int width, int height) {
		Platform.runLater(() -> {
			stage.setWidth(width);
			stage.setHeight(height);
		});
		System.out.println("Hello World!");
	}
	
	
	@Override
	public void update() {
		if (visible) {
			super.update();
			Platform.runLater(() -> {
				gameCanvas.setTranslateX(getViewport().getX());
				gameCanvas.setTranslateY(getViewport().getY());
				gameCanvas.setWidth(getViewport().getWidth());
				gameCanvas.setHeight(getViewport().getHeight());
				GraphicsContext gc = gameCanvas.getGraphicsContext2D();
				drawer.setGC(gc);
				JFXInterfaceImpl.getRenderer().render(JFXInterfaceImpl.this, drawer);
			});
		}
	}

	@Override
	public Area getSize() {
		answer = null;
		Platform.runLater(() -> {
			answer = new Area((int) stage.getWidth(), (int) stage.getHeight());
		});
		while (answer == null) {
			Thread.onSpinWait();
		}
		return (Area) answer;
	}

	@Override
	public int getWidth() {
		return getSize().getWidth();
	}

	@Override
	public int getHeight() {
		return getSize().getHeight();
	}
	
	@Override
	public void setResizable(boolean resizable) {
		Platform.runLater(() -> {
			stage.setResizable(resizable);
		});
	}
	
	@Override
	public void setTitle(String title) {
		Platform.runLater(() -> {
			stage.setTitle(title);
		});
	}
	
	@Override
	public void setPosition(int x, int y) {
		Platform.runLater(() -> {
			stage.setX(x);
			stage.setY(y);
		});
	}
	
	@Override
	public Point getPosition() {
		answer = null;
		Platform.runLater(() -> {
			answer = new Point((int) stage.getX(), (int) stage.getY());
		});
		while (answer == null) {
			Thread.onSpinWait();
		}
		return (Point) answer;
	}

	@Override
	public void unregister() {
		hide();
		Platform.exit();
	}

}

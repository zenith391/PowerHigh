package test.org.powerhigh;

import java.io.File;
import java.io.IOException;

import org.powerhigh.SizedViewport;
import org.powerhigh.components.Renderer;
import org.powerhigh.components.Transform;
import org.powerhigh.graphics.*;
import org.powerhigh.graphics.ParticleBlueprint.ParticleRenderer;
import org.powerhigh.graphics.renderers.SimpleRenderer;
import org.powerhigh.graphics.renderers.lightning.Lightning;
import org.powerhigh.input.AbstractKeyboard;
import org.powerhigh.input.KeyCodes;
import org.powerhigh.objects.Button;
import org.powerhigh.objects.Particle;
import org.powerhigh.objects.Rectangle;
import org.powerhigh.objects.Sprite;
import org.powerhigh.objects.Text;
import org.powerhigh.swing.audio.WavMusic;
import org.powerhigh.utils.*;
import org.powerhigh.game.AbstractGame;
import org.powerhigh.utils.debug.DebugLogger;

public class PowerHighTest extends AbstractGame {

	private Sprite player;
	private Text fps;
	private ParticleBox box;
	private ParticleBlueprint damageBlueprint;
	private ImplementationSettings impl = new ImplementationSettings(ImplementationSettings.Interface.SWING, ImplementationSettings.Audio.AWT);
	
	@Override
	public void update(Interface win, double delta) {
		delta = 1;
		fps.setText("FPS: " + win.getFPS() + ", Sleep Time: " + win.getEventThread().getSleepTime() + ", Delta: " + win.getEventThread().getDelta());
		handleKeys(win, delta);
		
		Particle p = new Particle(damageBlueprint, 50, 500);
		box.addParticle(p);
		box.windRandom((int)(4d*delta), (int)(5*delta));
		player.getTransform().rotation += 1;
	}
	
	public void exit(Interface win) {
		System.exit(0);
	}

	public void handleKeys(Interface win, double delta) {
		AbstractKeyboard keyboard = win.getInput().getKeyboard();
		int speed = 5;
		Transform t = player.getTransform();
		t.position.x += input.getAxis("Horizontal") * speed;
		t.position.y += input.getAxis("Vertical") * speed;
	}
	
	public void audio() {
		try {
			WavMusic m = new WavMusic(1f, new File("Alonzo - Santana.wav"));
			audio.play(m);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init(Interface win) {
		Rectangle firecamp = new Rectangle(25, 505, 70, 20, Color.LIGHT_GRAY);
		win.add(firecamp);
		win.setViewportManager(new SizedViewport(800, 600));
		player = new Sprite();
		fps = new Text();
		box = new ParticleBox(512);
		ParticleRenderer damageRenderer = new ParticleRenderer() {

			@Override
			public void render(Drawer d, Particle p) {
				int a = p.getLife() % 255;
				if (a < 1) {
					a = 1;
				}
				a -= 255;
				a *= -1;
				Color c = new Color(255, 255, 255, a);
				d.setColor(c);
				int size = p.getBlueprint().getSize();
				float dd = (float) a / 255;
				size *= dd;
				d.fillRect(p.getX(), p.getY(), size, size);
			}
			
		};
		damageBlueprint = new ParticleBlueprint(10, (short)255, damageRenderer);
		player.setSize(128, 128);
		try {
			player.setAnimation(new Animation(new File("Dumb Man.gan")));
			player.getAnimation().start();
		} catch (IOException e) {
			e.printStackTrace();
			player.setSize(128, 128);
		}
		win.setBackground(Color.CYAN);
		win.add(player);
		
		Button bt = new Button();
		bt.setText("Hello World!");
		bt.setX(100);
		bt.setY(200);
		fps.setY(24);
		//fps.setColor(Color.WHITE);
		Renderer r = fps.getComponent(Renderer.class);
		r.material.setColor(Color.WHITE);
		
		bt.setWidth(100);
		bt.setHeight(60);
		bt.addAction(new Runnable() {

			@Override
			public void run() {
				if (bt.getText().equals("Hello World!")) {
					bt.setSize(200, 80);
					bt.setText("Test, again");
				} else {
					bt.setSize(100, 60);
					bt.setText("Hello World!");
				}
			}

		});
		
		win.add(bt);
		win.add(fps);

		win.add(box);
	}

	public static void main(String[] args) {
		PowerHighTest test = new PowerHighTest();
		test.start();
	}

	@Override
	public ImplementationSettings getImplementationSettings() {
		return impl;
	}

}

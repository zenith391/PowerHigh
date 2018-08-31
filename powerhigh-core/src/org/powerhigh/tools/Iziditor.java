package org.powerhigh.tools;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.powerhigh.objects.GameObject;
import org.powerhigh.objects.Rectangle;
import org.powerhigh.objects.Sprite;
import org.powerhigh.objects.Text;

import java.awt.Window.Type;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

/**
 * <h2>WARNING!</h2>
 * Iziditor will move to another project (lggl-iziditor on github) in next release.
 * @author zenith391
 *
 */

@Deprecated(forRemoval = true)
public class Iziditor extends JFrame {

	private JPanel contentPane;
	List<Class<? extends GameObject>> library = new ArrayList<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Iziditor frame = new Iziditor();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Iziditor() {
		library.add(Rectangle.class);
		library.add(Sprite.class);
		library.add(Text.class);
		setTitle("Iziditor - LGGL Editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 620, 480);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);
		
		JMenuItem mntmLoadLevel = new JMenuItem("Load Level..");
		mnFile.add(mntmLoadLevel);
		
		JMenuItem mntmSaveLevelAs = new JMenuItem("Save Level As..");
		mnFile.add(mntmSaveLevelAs);
		
		JMenuItem mntmSaveLevel = new JMenuItem("Save Level");
		mntmSaveLevel.setEnabled(false);
		mnFile.add(mntmSaveLevel);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenu mnLevel = new JMenu("Level");
		menuBar.add(mnLevel);
		
		JMenu mnObjects = new JMenu("Objects");
		mnLevel.add(mnObjects);
		
		JMenuItem mntmLibrary = new JMenuItem("Library..");
		mntmLibrary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Izilib lib = new Izilib(Iziditor.this);
				lib.setVisible(true);
			}
		});
		mnObjects.add(mntmLibrary);
		
		JMenuItem mntmPreview = new JMenuItem("Preview");
		mnLevel.add(mntmPreview);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}

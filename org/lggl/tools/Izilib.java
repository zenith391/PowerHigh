package org.lggl.tools;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListDataListener;

import org.lggl.graphics.ErrorBox;
import org.lggl.graphics.objects.GameObject;
import org.lggl.utils.LGGLException;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListModel;

import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class Izilib extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Iziditor instance;
	private List<Class<? extends GameObject>> nlist;

	/**
	 * Create the dialog.
	 */
	public Izilib(Iziditor inst) {
		setResizable(false);
		instance = inst;
		nlist = instance.library;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JList<String> list = new JList<String>();
		list.setModel(new DefaultListModel<String>() {

			@Override
			public int getSize() {
				return nlist.size();
			}

			@Override
			public String getElementAt(int index) {
				return nlist.get(index).getSimpleName();
			}
		});
		
		JButton btnRemove = new JButton("Remove");
		
		JButton btnAdd = new JButton("Add..");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String c = JOptionPane.showInputDialog(Izilib.this, "Entire class name:", "Select class", JOptionPane.QUESTION_MESSAGE);
				if (c != null) {
					try {
						Class<?> cl = Class.forName(c, false, Izilib.class.getClassLoader());
						if (GameObject.class.isAssignableFrom(cl)) {
							Class<? extends GameObject> gcl = cl.asSubclass(GameObject.class);
							nlist.add(gcl);
							list.revalidate();
							list.repaint();
						} else {
							throw new LGGLException("Class is not a GameObject");
						}
					} catch (Exception e1) {
						new ErrorBox().
							throwable(e1).
							show();
					}
				}
			}
		});
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnAdd)
						.addComponent(btnRemove))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(list, GroupLayout.PREFERRED_SIZE, 323, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(list, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(btnAdd)
							.addGap(9)
							.addComponent(btnRemove)))
					.addContainerGap())
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						instance.library = nlist;
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}

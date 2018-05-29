package org.lggl.graphics;

import java.awt.Component;
import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JOptionPane;

public class ErrorBox {

	private Throwable t;
	
	public static ErrorBox create() {
		return new ErrorBox();
	}
	
	public ErrorBox throwable(Throwable throwable) {
		t = throwable;
		return this;
	}
	
	public void show() {
		show(null);
	}
	
	public void show(Component parent) {
		StringWriter sw = new StringWriter();
		if (t != null)
			t.printStackTrace(new PrintWriter(sw));
		int result = JOptionPane.showConfirmDialog(parent, "Error happened in the JVM.\nWould you like to send an error feedback ?\nError:\n"+sw.toString(), "Error!", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
		if (result == JOptionPane.YES_OPTION) {
			String url = "https://example.com/examplefeedback/examplegame/exempleerror.php";
			JOptionPane.showMessageDialog(parent, "The following procedure will open " + url, "Error Feedback", JOptionPane.INFORMATION_MESSAGE);
			try {
				Desktop.getDesktop().browse(new URL(url).toURI());
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}
	
}

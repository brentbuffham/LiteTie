package litetie;
/*

 */ 

import java.awt.*;
import java.awt.event.*;

import javax.swing.JPopupMenu;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;
import javax.swing.JMenuBar;
import javax.swing.KeyStroke;
import javax.swing.ImageIcon;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class HolePopupMenu implements ActionListener {

	JPopupMenu popup;

	public void init() {	    
		JMenuItem item;

		popup = new JPopupMenu("Edit");

		item = new JMenuItem("Cut");
		item.addActionListener(this);
		popup.add(item);

		item = new JMenuItem("Copy");
		item.addActionListener(this);
		popup.add(item);

		popup.addSeparator();

		item = new JMenuItem("Paste");
		item.addActionListener(this);
		popup.add(item);

	}

	public void processMouseEvent(MouseEvent e) {

		if (e.isPopupTrigger()) { 
			popup.show(e.getComponent(), e.getX(), e.getY());
		}

	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if (command.equals("Cut")) {
			// perform cut operation
		} else if (command.equals("Copy")) {
			// perform copy operation
		} else if (command.equals("Paste")) {
			// perform paste operation
		}
	}
}
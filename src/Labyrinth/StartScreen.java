package Labyrinth;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.event.*;
import javax.swing.*;


@SuppressWarnings("serial")
public class StartScreen extends JFrame implements ActionListener {

	JPanel Labrynth = new JPanel();
	JButton Start = new JButton ("START");
	JLabel gameIcon = new JLabel();
	public StartScreen() {
		panelSetup();
		frameSetup();
	}
	private void panelSetup() {

		Labrynth.setBounds(50, 50, 1250, 500);
		Labrynth.setLayout(null);
		Labrynth.setBackground(Color.green);
		Start.setBounds(575,575,200,50);
		Start.addActionListener(this);
		add(Start); 



		gameIcon.setIcon(new ImageIcon (new ImageIcon("Card Images/GamePIC.PNG").getImage().getScaledInstance(1000, 500, 0)));
		gameIcon.setBounds(175, 75, 1000, 500);
		gameIcon.setLayout(null);
		add(gameIcon);	

	}
	private void frameSetup() {
		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("HELP");
		JMenuItem start = new JMenuItem("START");
		JMenuItem HTP = new JMenuItem("INSTRUCTIONS");
		menu.add(start);
		menu.add(HTP);
		menubar.add(menu);
		menu.setMnemonic(KeyEvent.VK_O);
		
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				Test.t1.start();
			}
		});
		HTP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent eo) {
				new HelpScreen();
			}
		});
		
		setSize(1366,725);
		setLayout(null);
		add(Labrynth);
		setVisible(true);
		getContentPane().setBackground(Color.white);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("LABRYNTH");
		setJMenuBar(menubar);

	}
	public void actionPerformed(ActionEvent arg0) {
		setVisible(false);
		Test.t1.start();
	}
}
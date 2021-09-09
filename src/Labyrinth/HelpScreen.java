package Labyrinth;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HelpScreen extends JFrame implements ActionListener{
	JPanel Help = new JPanel();
	JButton back = new JButton ("BACK");
	JLabel gameIcon = new JLabel();
	public HelpScreen() {
		panelSetup();
		frameSetup();
	}

	
	private void frameSetup() {
		// TODO Auto-generated method stub
		setSize(1366,725);
		setLayout(null);
		add(Help);
		setVisible(true);
		getContentPane().setBackground(Color.white);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("INSTRUCTIONS");
	}


	private void panelSetup() {
		// TODO Auto-generated method stub
		Help.setBounds(50, 50, 1250, 500);
		Help.setLayout(null);
		Help.setBackground(Color.green);
		back.setBounds(575,575,200,50);
		back.addActionListener(this);
		add(back); 



		gameIcon.setIcon(new ImageIcon (new ImageIcon("Card Images/instructions.PNG").getImage().getScaledInstance(1000, 500, 0)));
		gameIcon.setBounds(175, 75, 1000, 475);
		gameIcon.setLayout(null);
		add(gameIcon);	
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		setVisible(false);
		new TempTest();
	}

}

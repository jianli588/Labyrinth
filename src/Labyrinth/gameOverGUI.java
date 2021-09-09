package Labyrinth;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

//After the game ends this screen is displayed
@SuppressWarnings("serial")
public class gameOverGUI extends JFrame implements ActionListener{
	
	private JLabel backgroundImage = new JLabel();
	private JButton exitButton = new JButton("Exit");
	private JButton playAgainButton = new JButton("Play Again");
	private JLabel winner = new JLabel();
	
	
	public gameOverGUI(){
		frameSetup();
	}
	
	private void frameSetup() {
		// TODO Auto-generated method stub
		
		setSize(1280, 720);
		setTitle("Game Over");
		setLayout(null);
		backgroundImage.setSize(1280, 720);
		
		
		winner.setBounds(640, 360, 640, 360);
		winner.setFont(new Font("Magneto", Font.BOLD, 30));
		winner.setOpaque(true);
		if (ArtifLocation.points[0] == 5){
		winner.setText("Player 1 Wins");
		} else if (ArtifLocation.points[1] == 5){
			winner.setText("Player 2 Wins");
		} else if (ArtifLocation.points[2] == 5){
			winner.setText("Player 3 Wins");
		} else if (ArtifLocation.points[3] == 5){
			winner.setText("Player 4 Wins");
		}
		add(winner);
		
		exitButton.setBounds(200, 600, 100, 50);
		exitButton.addActionListener(this);
		exitButton.setFont(new Font("Magneto", Font.BOLD , 15));
		exitButton.setOpaque(true);
		add(exitButton);
		
		
		playAgainButton.setBounds(350, 600, 150, 50);
		playAgainButton.addActionListener(this);
		playAgainButton.setFont(new Font("Magneto", Font.BOLD, 15));
		playAgainButton.setOpaque(true);
		add(playAgainButton);
	
		
		backgroundImage.setIcon(new ImageIcon("playerImages/Turnback Cave1.jpg"));
	    backgroundImage.setLayout(new BorderLayout());
	    backgroundImage.setOpaque(false);
		add(backgroundImage);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		
		if (event.getSource() == exitButton){
			System.exit(EXIT_ON_CLOSE);
		}
		
		if (event.getSource() == playAgainButton){
			setVisible(false);
			dispose();
			new TempTest();
		}	
	}
}

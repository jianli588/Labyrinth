package Labyrinth;

import java.awt.Color;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class GameFrame extends JFrame implements Runnable, ActionListener, MouseListener {

	private boolean running = true;
	private static boolean paused = false;

	private static boolean movePlayer = false;
	private static int direction = 0;

	static long lastTime = System.nanoTime();
	long now;
	final double AmountOfTicks = 120.0;
	long timer = System.currentTimeMillis();
	double ns = 1000000000 / AmountOfTicks;
	static double delta = 0;

	private AI computerPlayer = new AI();

	private final static Object pauseLock = new Object();

	private final int width = 1500;
	private final int height = 1100;

	static int ticksMoved = 0;
	static int PlayerTurn = 0;

	static int locationOfTileAdded = 13;

	private final String name = "Labyrinth";

	private static JLabel[][] labels = new JLabel[9][9];
	static JLabel[][] playerHandLabel = new JLabel[4][6];// labels for
	// putting
	// player's
	// image
	private static JLabel MovableTile = new JLabel();

	private Rectangle[][] labelLocation = new Rectangle[9][9];

	private Rectangle[] topRectangle = new Rectangle[6];
	private Rectangle[] rightRectangle = new Rectangle[6];
	private Rectangle[] leftRectangle = new Rectangle[6];
	private Rectangle[] bottomRectangle = new Rectangle[6];

	private static JButton[] topButton = new JButton[3];
	private static JButton[] bottomButton = new JButton[3];
	private static JButton[] leftButton = new JButton[3];
	private static JButton[] rightButton = new JButton[3];
	private static JButton moveAi = new JButton("move ai");
	private static JButton shiftTiles = new JButton("shift tiles");

	public static boolean[] top = new boolean[3];
	public static boolean[] bottom = new boolean[3];
	public static boolean[] left = new boolean[3];
	public static boolean[] right = new boolean[3];

	private static boolean buttonsClickable = true;

	private JButton EndTurn = new JButton("end turn");
	private JButton rotateRight = new JButton("Rotate Right");
	private JButton rotateLeft = new JButton("Rotate Left");

	private JPanel gamePanel = new JPanel();

	private JPanel playerHand[] = new JPanel[4];

	static Rectangle[] playerAvatarRectangle = new Rectangle[4];
	static JLabel[] playerAvatar = new JLabel[4];

	static Border border = BorderFactory.createLineBorder(Color.BLUE, 5);

	static Player players = new Player();

	JMenuBar menubar = new JMenuBar();
	JMenu menu = new JMenu("INSTRUCTIONS");
	JMenuItem HTP = new JMenuItem("INSTRUCTIONS");

	static void update() {
		
		
	}
	
	private void tick() {// function that gets run each iteration

		if (locationOfTileAdded < 13) {
			tilesShift();
			ticksMoved--;
		}
		if (movePlayer) {
			moveAvatar(PlayerTurn, direction);
			ticksMoved--;
		}

		if (ticksMoved <= 0) {

			ArtifLocation.point(PlayerTurn);
			redrawHand();
			for (int a = 0; a < 4; a++) {
				if (ArtifLocation.points[a] == 5) {
					setVisible(false);
					new gameOverGUI();
					System.out.println(ArtifLocation.points[a]);

				}
			}
			CreatingBoardObject.changeBoard(locationOfTileAdded);

			Player.player1.setCurrentX((playerAvatar[0].getX() - 100) / 75);
			Player.player1.setCurrentY((playerAvatar[0].getY() - 100) / 75);
			playerAvatar[0].setBounds(Player.player1.getCurrentX() * 75 + 100, Player.player1.getCurrentY() * 75 + 100,
					32, 32);
			Player.checkBoard(0);

			Player.player2.setCurrentX((playerAvatar[1].getX() - 100) / 75);
			Player.player2.setCurrentY((playerAvatar[1].getY() - 100) / 75);
			playerAvatar[1].setBounds(Player.player2.getCurrentX() * 75 + 100, Player.player2.getCurrentY() * 75 + 100,
					32, 32);
			Player.checkBoard(1);

			Player.player3.setCurrentX((playerAvatar[2].getX() - 100) / 75);
			Player.player3.setCurrentY((playerAvatar[2].getY() - 100) / 75);
			playerAvatar[2].setBounds(Player.player3.getCurrentX() * 75 + 100, Player.player3.getCurrentY() * 75 + 100,
					32, 32);
			Player.checkBoard(2);

			Player.player4.setCurrentX((playerAvatar[3].getX() - 100) / 75);
			Player.player4.setCurrentY((playerAvatar[3].getY() - 100) / 75);
			playerAvatar[3].setBounds(Player.player4.getCurrentX() * 75 + 100, Player.player4.getCurrentY() * 75 + 100,
					32, 32);
			Player.checkBoard(3);

			movePlayer = false;

			locationOfTileAdded = 13;

			changeBorder();

			for (int i = 0; i < labels.length; i++) {
				for (int k = 0; k < labels[i].length; k++) {
					setLabels(i, k);
				}
			}
			setExtraTile();

			pause();

		}

	}

	public void pause() {// https://stackoverflow.com/questions/16758346/how-pause-and-then-resume-a-thread
		// you may want to throw an IllegalStateException if !running
		paused = true;
	}

	public static void resume() {// https://stackoverflow.com/questions/16758346/how-pause-and-then-resume-a-thread
		synchronized (pauseLock) {
			paused = false;
			delta = 0;
			lastTime = System.nanoTime();
			pauseLock.notifyAll(); // Unblocks thread
		}
	}

	public synchronized void run() { // code from RealTutsGML Youtube Channel
		// (https://www.youtube.com/watch?v=TNVHWROwYuM&t=546s)

		while (running) {
			synchronized (pauseLock) {
				if (!running) { // may have changed while waiting to
					// synchronize on pauseLock
					break;
				}
				if (paused) {
					try {
						synchronized (pauseLock) {

							pauseLock.wait(); // will cause this Thread to block
							// until
							// another thread calls pauseLock.notifyAll()
							// Note that calling wait() will
							// relinquish the synchronized lock that this
							// thread holds on pauseLock so another thread
							// can acquire the lock to call notifyAll()
							// (link with explanation below this code)

						}
					} catch (InterruptedException ex) {
						break;
					}
					if (!running) { // running might have changed since we
						// paused
						break;

					}
				}

				now = System.nanoTime();
				delta += (now - lastTime) / ns;
				lastTime = now;

				if (delta >= 1) {
					tick();
					delta--;
				}
			}
		}

	}

	public GameFrame() {

		CreatingBoardObject.randomizeBoard();

		Initialize();
		this.setName(name);
		this.setBounds(0, 0, width, height);
		setLayout(null);
		add(shiftTiles);
		add(moveAi);
		add(gamePanel);
		add(EndTurn);
		add(MovableTile);
		add(rotateLeft);
		add(rotateRight);
		add(menubar);
		setJMenuBar(menubar);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private void redrawHand() {

		for (int i = 0; i < 4; i++) {
			for (int k = 0; k < 4; k++) {
				playerHandLabel[i][k].setIcon(Card.getHand(i, k));
			}
		}

	}

	private void Initialize() {// create the objects to be put on to the game
		// frame

		// button to move ai
		moveAi.setBounds(1200, 850,100,100);
		moveAi.setVisible(true);
		moveAi.addMouseListener(this);
		
		shiftTiles.setBounds(1300, 850,100,100);
		shiftTiles.setVisible(true);
		shiftTiles.addMouseListener(this);
		
		// button to end turn
		EndTurn.setBounds(1200, 700, 200, 100);
		EndTurn.setVisible(true);
		EndTurn.addMouseListener(this);

		rotateLeft.setBounds(1200, 550, 100, 100);
		rotateLeft.setVisible(true);
		rotateLeft.addMouseListener(this);

		rotateRight.setBounds(1300, 550, 100, 100);
		rotateRight.setVisible(true);
		rotateRight.addMouseListener(this);

		// displays the tile that is movable

		gamePanel.setBounds(200, 175, 675, 675);
		gamePanel.setLayout(null);
		gamePanel.setVisible(true);
		gamePanel.setBackground(new Color(0, 0, 0));
		gamePanel.setOpaque(true);

		// initialize items associated with player's hand
		playerHand[0] = new JPanel();
		playerHand[1] = new JPanel();
		playerHand[2] = new JPanel();
		playerHand[3] = new JPanel();

		playerHand[0].setBounds(250, 50, 720, 120);// top
		playerHand[1].setBounds(250, 900, 720, 120);// bottom
		playerHand[2].setBounds(50, 200, 100, 720);// left
		playerHand[3].setBounds(950, 200, 120, 720);// right

		for (int i = 0; i < 4; i++) {
			playerHand[i].setLayout(null);
			playerHand[i].setVisible(true);
			playerHand[i].setOpaque(true);
			add(playerHand[i]);

		}

		// insert the player cards rectangle

		for (int i = 0; i < 6; i++) {

			topRectangle[i] = new Rectangle(120 * i, 0, 100, 120);
			bottomRectangle[i] = new Rectangle(120 * i, 0, 100, 100);
			rightRectangle[i] = new Rectangle(0, 120 * i, 100, 100);
			leftRectangle[i] = new Rectangle(0, 120 * i, 100, 100);

		}

		Card.shuffle();

		// add in buttons for putting in java items
		for (int i = 0; i < 3; i++) {

			InsertButtons(leftButton, i, 0, 150 * i + 150);
			InsertButtons(rightButton, i, 600, 150 * i + 150);
			InsertButtons(topButton, i, 150 * i + 150, 0);
			InsertButtons(bottomButton, i, 150 * i + 150, 600);

		}
		enableButtons();
		enableOrDisableButtons(true);

		CreatingBoardObject.randomizeBoard(); // initialize labels on game board

		Player.getPlayer1();
		Player.getPlayer2();
		Player.getPlayer3();
		Player.getPlayer4();

		Player.checkBoard(PlayerTurn);

		for (int i = 0; i < labels.length; i++) {
			for (int k = 0; k < labels[i].length; k++) {

				labels[i][k] = new JLabel();
				labels[i][k].setVisible(true);
				labels[i][k].setOpaque(true);
				labels[i][k].addMouseListener(this);

				labelLocation[i][k] = new Rectangle(75 * i, 75 * k, 75, 75);
				setLabels(i, k);
				gamePanel.add(labels[i][k]);
				labels[i][k].setVisible(true);
				// gamePanel.setComponentZOrder(labels[i][k], 1);
			}

		}
		changeBorder();

		MovableTile = new JLabel(new ImageIcon(
				new ImageIcon(CreatingBoardObject.getExtraTile()).getImage().getScaledInstance(300, 300, 0)));

		MovableTile.setBounds(1150, 200, 300, 300);
		MovableTile.setVisible(true);
		MovableTile.setBackground(new Color(0, 0, 0));
		MovableTile.setOpaque(true);

		// for (int i =0; i<7; i++) {
		// for (int k =0; k<7; k++) {
		// players.getPlayerAllowedLocations(PlayerTurn, i, k);
		// }
		// }

		for (int i = 0; i < 4; i++) {
			for (int k = 0; k < 5; k++) {

				playerHandLabel[i][k] = new JLabel(Card.getHand(i, k));

			}
			playerHandLabel[i][5] = new JLabel(new ImageIcon(
					new ImageIcon(Player.getPlayerImageIcon(i)).getImage().getScaledInstance(100, 100, 0)));
		}

		setPlayerHand();

		playerAvatar[0] = new JLabel(
				new ImageIcon(new ImageIcon(Player.getPlayerImageIcon(0)).getImage().getScaledInstance(32, 32, 0)));
		playerAvatarRectangle[0] = new Rectangle((Player.player1.getCurrentX() + 1) * 75 + 25,
				(Player.player1.getCurrentY() + 1) * 75 + 25, 32, 32);

		playerAvatar[0].setBounds(playerAvatarRectangle[0]);

		playerAvatar[0].setVisible(true);
		gamePanel.add(playerAvatar[0]);
		gamePanel.setComponentZOrder(playerAvatar[0], 0);

		playerAvatar[1] = new JLabel(
				new ImageIcon(new ImageIcon(Player.getPlayerImageIcon(1)).getImage().getScaledInstance(32, 32, 0)));
		playerAvatarRectangle[1] = new Rectangle((Player.player2.getCurrentX() + 1) * 75 + 25,
				(Player.player2.getCurrentY() + 1) * 75 + 25, 32, 32);

		playerAvatar[1].setBounds(playerAvatarRectangle[1]);

		playerAvatar[1].setVisible(true);
		gamePanel.add(playerAvatar[1]);
		gamePanel.setComponentZOrder(playerAvatar[1], 0);

		playerAvatar[2] = new JLabel(
				new ImageIcon(new ImageIcon(Player.getPlayerImageIcon(2)).getImage().getScaledInstance(32, 32, 0)));
		playerAvatarRectangle[2] = new Rectangle((Player.player3.getCurrentX() + 1) * 75 + 25,
				(Player.player3.getCurrentY() + 1) * 75 + 25, 32, 32);

		playerAvatar[2].setBounds(playerAvatarRectangle[2]);

		playerAvatar[2].setVisible(true);
		gamePanel.add(playerAvatar[2]);
		gamePanel.setComponentZOrder(playerAvatar[2], 0);

		playerAvatar[3] = new JLabel(
				new ImageIcon(new ImageIcon(Player.getPlayerImageIcon(3)).getImage().getScaledInstance(32, 32, 0)));
		playerAvatarRectangle[3] = new Rectangle((Player.player4.getCurrentX() + 1) * 75 + 25,
				(Player.player4.getCurrentY() + 1) * 75 + 25, 32, 32);

		playerAvatar[3].setBounds(playerAvatarRectangle[3]);

		playerAvatar[3].setVisible(true);
		gamePanel.add(playerAvatar[3]);
		gamePanel.setComponentZOrder(playerAvatar[3], 0);

	}

	private void setPlayerHand() {// move the player's card

		playerHand[0].removeAll();
		playerHand[1].removeAll();
		playerHand[2].removeAll();
		playerHand[3].removeAll();

		for (int k = 0; k < 6; k++) {

			playerHandLabel[PlayerTurn % 4][k].setBounds(bottomRectangle[k]);
			playerHandLabel[PlayerTurn % 4][k].setVisible(true);
			playerHand[1].add(playerHandLabel[PlayerTurn % 4][k]);

			playerHandLabel[(PlayerTurn + 1) % 4][k].setBounds(leftRectangle[k]);
			playerHandLabel[(PlayerTurn + 1) % 4][k].setVisible(true);
			playerHand[2].add(playerHandLabel[(PlayerTurn + 1) % 4][k]);

			playerHandLabel[(PlayerTurn + 2) % 4][k].setBounds(topRectangle[5 - k]);
			playerHandLabel[(PlayerTurn + 2) % 4][k].setVisible(true);
			playerHand[0].add(playerHandLabel[(PlayerTurn + 2) % 4][k]);

			playerHandLabel[(PlayerTurn + 3) % 4][k].setBounds(rightRectangle[5 - k]);
			playerHandLabel[(PlayerTurn + 3) % 4][k].setVisible(true);
			playerHand[3].add(playerHandLabel[(PlayerTurn + 3) % 4][k]);

		}

		this.repaint();

	}

	private void InsertButtons(JButton[] buttonArray, int i, int XCord, int YCord) {

		buttonArray[i] = new JButton("");
		buttonArray[i].addActionListener(this);
		buttonArray[i].setBounds(XCord, YCord, 75, 75);
		buttonArray[i].setContentAreaFilled(true);
		buttonArray[i].setBackground(new Color(212, 175, 55));
		buttonArray[i].setVisible(true);
		gamePanel.add(buttonArray[i]);
		// gamePanel.setComponentZOrder(buttonArray[i], 0);
	}

	private void setLabels(int i, int k) {
		//

		labelLocation[i][k].setLocation(75 * i, 75 * k);
		labels[i][k].setBounds(labelLocation[i][k]);

		if (i == 0 || i == 8 || k == 0 || k == 8) {
			labels[i][k].setIcon(null);
			labels[i][k].setBackground(new Color(0, 0, 0));
		} else {
			labels[i][k].setIcon(CreatingBoardObject.tileImage(i - 1, k - 1));

		}

	}

	static void setExtraTile() {
		MovableTile.setIcon(new ImageIcon(
				new ImageIcon(CreatingBoardObject.getExtraTile()).getImage().getScaledInstance(300, 300, 0)));
	}

	static void changeBorder() {
		for (int i = 1; i < 8; i++) {
			for (int k = 1; k < 8; k++) {
				if (Player.getPlayerAllowedLocations(PlayerTurn)[i - 1][k - 1]) {
					labels[i][k].setBorder(border);
				} else {
					labels[i][k].setBorder(null);
				}
			}
		}
	}

	static void disableBorder() {
		for (int i = 1; i < 8; i++) {
			for (int k = 1; k < 8; k++) {
				labels[i][k].setBorder(null);
			}
		}
	}

	static void enableOrDisableButtons(boolean x) {

		if (!x) {
			for (int i = 0; i < 3; i++) {

				leftButton[i].setVisible(x);
				rightButton[i].setVisible(x);
				topButton[i].setVisible(x);
				bottomButton[i].setVisible(x);
			}
		} else if (x) {
			for (int i = 0; i < 3; i++) {
				if (left[i]) {
					leftButton[i].setVisible(x);
				}
				if (right[i]) {
					rightButton[i].setVisible(x);
				}
				if (top[i]) {

					topButton[i].setVisible(x);
				}
				if (bottom[i]) {

					bottomButton[i].setVisible(x);
				}
			}
		}
	}

	static void enableButtons() {

		for (int i = 0; i < 3; i++) {
			top[i] = true;
			bottom[i] = true;
			left[i] = true;
			right[i] = true;
		}
	}

	public void actionPerformed(ActionEvent e) {

		// when the insert buttons are clicked, a copy of the extraTile object
		// is made
		// the image is than resized and used to change the imageIcon of the
		// JLabel
		// under the button
		if (buttonsClickable) {
			for (int i = 0; i < 3; i++) {
				if (e.getSource() == leftButton[i]) {

					labels[0][i * 2 + 2].setIcon((new ImageIcon(new ImageIcon(CreatingBoardObject.getExtraTile())
							.getImage().getScaledInstance(75, 75, 0))));

					enableButtons();
					right[i] = false;
					locationOfTileAdded = i + 1;
					enableOrDisableButtons(false);
					disableBorder();
					ticksMoved = 75;
					resume();
					break;

				} else if (e.getSource() == rightButton[i] && right[i] == true) {

					labels[8][i * 2 + 2].setIcon((new ImageIcon(new ImageIcon(CreatingBoardObject.getExtraTile())
							.getImage().getScaledInstance(75, 75, 0))));

					locationOfTileAdded = i + 4;

					enableButtons();
					left[i] = false;
					enableOrDisableButtons(false);
					disableBorder();
					ticksMoved = 75;
					resume();
					break;

				} else if (e.getSource() == topButton[i] && top[i] == true) {

					labels[i * 2 + 2][0].setIcon((new ImageIcon(new ImageIcon(CreatingBoardObject.getExtraTile())
							.getImage().getScaledInstance(75, 75, 0))));

					locationOfTileAdded = i + 7;

					enableButtons();
					bottom[i] = false;

					enableOrDisableButtons(false);
					disableBorder();
					ticksMoved = 75;
					resume();
					break;

				} else if (e.getSource() == bottomButton[i] && bottom[i] == true) {

					labels[i * 2 + 2][8].setIcon((new ImageIcon(new ImageIcon(CreatingBoardObject.getExtraTile())
							.getImage().getScaledInstance(75, 75, 0))));

					enableButtons();
					top[i] = false;

					locationOfTileAdded = i + 10;
					enableOrDisableButtons(false);
					disableBorder();
					ticksMoved = 75;
					resume();
					break;
				}

			}
		}
	}

	public void mouseClicked(MouseEvent e) {// does nothing
		// TODO Auto-generated method stub
	}

	public void mouseEntered(MouseEvent e) {// does nothing

	}

	public void mouseExited(MouseEvent e) {// does nothing

	}

	public void mousePressed(MouseEvent e) {

		// TODO Auto-generated method stub

		if(e.getSource() == moveAi) {
			computerPlayer.MoveAI(PlayerTurn);
			Player.checkBoard(PlayerTurn);
			changeBorder();
			setPlayerHand();
			Player.checkBoard(0);
			Player.checkBoard(1);
			Player.checkBoard(2);
			Player.checkBoard(3);
			redrawHand();
		}
		
		if(e.getSource() == shiftTiles) {
			
			computerPlayer.insert(PlayerTurn);

			
			
		}
		if (e.getSource() == EndTurn) {// end turn button

			if (paused) {

				

				PlayerTurn++;

				if (PlayerTurn == 4) {
					PlayerTurn = 0;
				}

				changeBorder();
				enableOrDisableButtons(true);
				setPlayerHand();
				resume();

				changeBorder();

			}

			// images rotate through changing the
			// object held inside the extra tile
		} else if (e.getSource() == rotateLeft) {// ratating left

			CreatingBoardObject.rotateLeft();
			setExtraTile();
		} else if (e.getSource() == rotateRight) {// rotating right,
			CreatingBoardObject.rotateRight();
			setExtraTile();
		}
		if (ticksMoved == 0) {
			for (int i = 1; i < 8; i++) {
				for (int k = 1; k < 8; k++) {
					if (e.getSource() == labels[i][k]) {

						if (Player.getPlayerAllowedLocations(PlayerTurn)[i - 1][k - 1]) {
							// Player.setLocation(i,k);

							disableBorder();
							playerShift(i, k);
							Player.player1.setCurrentX(i - 1);
							Player.player1.setCurrentY(k - 1);
							Player.player2.setCurrentX(i - 1);
							Player.player2.setCurrentY(k - 1);
							Player.player3.setCurrentX(i - 1);
							Player.player3.setCurrentY(k - 1);
							Player.player4.setCurrentX(i - 1);
							Player.player4.setCurrentY(k - 1);

							break;
						}
					}
				}

			}
		}
	}

	public void mouseReleased(MouseEvent e) {// does nothing

	}

	private void tilesShift() {// moves the board tiles according to the
		// location
		switch (locationOfTileAdded) {

		// case 1 to 3 are for left side buttons
		case 1:
			for (int i = 0; i < 9; i++) {
				labelLocation[i][2].setLocation((int) (labelLocation[i][2].getX() + 1),
						(int) (labelLocation[i][2].getY()));
				labels[i][2].setBounds(labelLocation[i][2]);

			}

			if (Player.player1.getCurrentY() == 1) {
				moveAvatar(0, 1);
			}
			if (Player.player2.getCurrentY() == 1) {
				moveAvatar(1, 1);
			}
			if (Player.player3.getCurrentY() == 1) {
				moveAvatar(2, 1);
			}
			if (Player.player4.getCurrentY() == 1) {
				moveAvatar(3, 1);
			}

			break;

		case 2:
			for (int i = 0; i < 9; i++) {
				labelLocation[i][4].setLocation((int) (labelLocation[i][4].getX() + 1),
						(int) (labelLocation[i][4].getY()));
				labels[i][4].setBounds(labelLocation[i][4]);
			}

			if (Player.player1.getCurrentY() == 3) {
				moveAvatar(0, 1);
			}
			if (Player.player2.getCurrentY() == 3) {
				moveAvatar(1, 1);
			}
			if (Player.player3.getCurrentY() == 3) {
				moveAvatar(2, 1);
			}
			if (Player.player4.getCurrentY() == 3) {
				moveAvatar(3, 1);
			}

			break;

		case 3:
			for (int i = 0; i < 9; i++) {
				labelLocation[i][6].setLocation((int) (labelLocation[i][6].getX() + 1),
						(int) (labelLocation[i][6].getY()));
				labels[i][6].setBounds(labelLocation[i][6]);
			}

			if (Player.player1.getCurrentY() == 5) {
				moveAvatar(0, 1);
			}
			if (Player.player2.getCurrentY() == 5) {
				moveAvatar(1, 1);
			}
			if (Player.player3.getCurrentY() == 5) {
				moveAvatar(2, 1);
			}
			if (Player.player4.getCurrentY() == 5) {
				moveAvatar(3, 1);
			}

			break;

		// case 4 to 6 are for right side buttons
		case 4:
			for (int i = 0; i < 9; i++) {
				labelLocation[i][2].setLocation((int) (labelLocation[i][2].getX() - 1),
						(int) (labelLocation[i][2].getY()));
				labels[i][2].setBounds(labelLocation[i][2]);
			}

			if (Player.player1.getCurrentY() == 1) {
				moveAvatar(0, 2);
			}
			if (Player.player2.getCurrentY() == 1) {
				moveAvatar(1, 2);
			}
			if (Player.player3.getCurrentY() == 1) {
				moveAvatar(2, 2);
			}
			if (Player.player4.getCurrentY() == 1) {
				moveAvatar(3, 2);
			}

			break;

		case 5:
			for (int i = 0; i < 9; i++) {
				labelLocation[i][4].setLocation((int) (labelLocation[i][4].getX() - 1),
						(int) (labelLocation[i][4].getY()));
				labels[i][4].setBounds(labelLocation[i][4]);

			}

			if (Player.player1.getCurrentY() == 3) {
				moveAvatar(0, 2);
			}
			if (Player.player2.getCurrentY() == 3) {
				moveAvatar(1, 2);
			}
			if (Player.player3.getCurrentY() == 3) {
				moveAvatar(2, 2);
			}
			if (Player.player4.getCurrentY() == 3) {
				moveAvatar(3, 2);
			}

			break;

		case 6:
			for (int i = 0; i < 9; i++) {
				labelLocation[i][6].setLocation((int) (labelLocation[i][6].getX() - 1),
						(int) (labelLocation[i][6].getY()));
				labels[i][6].setBounds(labelLocation[i][6]);
			}

			if (Player.player1.getCurrentY() == 5) {
				moveAvatar(0, 2);
			}
			if (Player.player2.getCurrentY() == 5) {
				moveAvatar(1, 2);
			}
			if (Player.player3.getCurrentY() == 5) {
				moveAvatar(2, 2);
			}
			if (Player.player4.getCurrentY() == 5) {
				moveAvatar(3, 2);
			}

			break;

		// case 7 to 9 are for buttons on the top
		case 7:
			for (int i = 0; i < 9; i++) {
				labelLocation[2][i].setLocation((int) (labelLocation[2][i].getX()),
						(int) (labelLocation[2][i].getY()) + 1);
				labels[2][i].setBounds(labelLocation[2][i]);
			}

			if (Player.player1.getCurrentX() == 1) {
				moveAvatar(0, 3);
			}
			if (Player.player2.getCurrentX() == 1) {
				moveAvatar(1, 3);
			}
			if (Player.player3.getCurrentX() == 1) {
				moveAvatar(2, 3);
			}
			if (Player.player4.getCurrentX() == 1) {
				moveAvatar(3, 3);
			}

			break;

		case 8:
			for (int i = 0; i < 9; i++) {
				labelLocation[4][i].setLocation((int) (labelLocation[4][i].getX()),
						(int) (labelLocation[4][i].getY()) + 1);
				labels[4][i].setBounds(labelLocation[4][i]);

			}

			if (Player.player1.getCurrentX() == 3) {
				moveAvatar(0, 3);
			}
			if (Player.player2.getCurrentX() == 3) {
				moveAvatar(1, 3);
			}
			if (Player.player3.getCurrentX() == 3) {
				moveAvatar(2, 3);
			}
			if (Player.player4.getCurrentX() == 3) {
				moveAvatar(3, 3);
			}

			break;

		case 9:
			for (int i = 0; i < 9; i++) {
				labelLocation[6][i].setLocation((int) (labelLocation[6][i].getX()),
						(int) (labelLocation[6][i].getY()) + 1);
				labels[6][i].setBounds(labelLocation[6][i]);
			}

			if (Player.player1.getCurrentX() == 5) {
				moveAvatar(0, 3);
			}
			if (Player.player2.getCurrentX() == 5) {
				moveAvatar(1, 3);
			}
			if (Player.player3.getCurrentX() == 5) {
				moveAvatar(2, 3);
			}
			if (Player.player4.getCurrentX() == 5) {
				moveAvatar(3, 3);
			}

			break;

		// case 10 to 11 are for buttons on the bottom
		case 10:
			for (int i = 0; i < 9; i++) {
				labelLocation[2][i].setLocation((int) (labelLocation[2][i].getX()),
						(int) (labelLocation[2][i].getY()) - 1);
				labels[2][i].setBounds(labelLocation[2][i]);
			}

			if (Player.player1.getCurrentX() == 1) {
				moveAvatar(0, 4);
			}
			if (Player.player2.getCurrentX() == 1) {
				moveAvatar(1, 4);
			}
			if (Player.player3.getCurrentX() == 1) {
				moveAvatar(2, 4);
			}
			if (Player.player4.getCurrentX() == 1) {
				moveAvatar(3, 4);
			}

			break;

		case 11:
			for (int i = 0; i < 9; i++) {
				labelLocation[4][i].setLocation((int) (labelLocation[4][i].getX()),
						(int) (labelLocation[4][i].getY()) - 1);
				labels[4][i].setBounds(labelLocation[4][i]);

			}

			if (Player.player1.getCurrentX() == 3) {
				moveAvatar(0, 4);
			}
			if (Player.player2.getCurrentX() == 3) {
				moveAvatar(1, 4);
			}
			if (Player.player3.getCurrentX() == 3) {
				moveAvatar(2, 4);
			}
			if (Player.player4.getCurrentX() == 3) {
				moveAvatar(3, 4);
			}

			break;

		case 12:
			for (int i = 0; i < 9; i++) {
				labelLocation[6][i].setLocation((int) (labelLocation[6][i].getX()),
						(int) (labelLocation[6][i].getY()) - 1);
				labels[6][i].setBounds(labelLocation[6][i]);
			}

			if (Player.player1.getCurrentX() == 5) {
				moveAvatar(0, 4);
			}
			if (Player.player2.getCurrentX() == 5) {
				moveAvatar(1, 4);
			}
			if (Player.player3.getCurrentX() == 5) {
				moveAvatar(2, 4);
			}
			if (Player.player4.getCurrentX() == 5) {
				moveAvatar(3, 4);
			}

			break;

		default: // does nothing

		}// closes switch

	}

	private void moveAvatar(int PlayerNum, int way) {

		switch (way) {
		case 1:// moves right
			playerAvatarRectangle[PlayerNum].setLocation((int) (playerAvatarRectangle[PlayerNum].getX() + 1),
					(int) (playerAvatarRectangle[PlayerNum].getY()));

			break;
		case 2:// moves left
			playerAvatarRectangle[PlayerNum].setLocation((int) (playerAvatarRectangle[PlayerNum].getX() - 1),
					(int) (playerAvatarRectangle[PlayerNum].getY()));

			break;
		case 3:// moves down
			playerAvatarRectangle[PlayerNum].setLocation((int) (playerAvatarRectangle[PlayerNum].getX()),
					(int) (playerAvatarRectangle[PlayerNum].getY()) + 1);

			break;
		case 4:// moves up
			playerAvatarRectangle[PlayerNum].setLocation((int) (playerAvatarRectangle[PlayerNum].getX()),
					(int) (playerAvatarRectangle[PlayerNum].getY()) - 1);

			break;
		default:// does nothing
		}
		if (playerAvatarRectangle[PlayerNum].getX() <= 75) {

			playerAvatarRectangle[PlayerNum].setLocation((int) (600 + playerAvatarRectangle[PlayerNum].getX() - 75),
					(int) playerAvatarRectangle[PlayerNum].getY());
		} else if (playerAvatarRectangle[PlayerNum].getY() <= 75) {
			playerAvatarRectangle[PlayerNum].setLocation((int) playerAvatarRectangle[PlayerNum].getX(),
					(int) (600 + playerAvatarRectangle[PlayerNum].getY() - 75));
		} else if (playerAvatarRectangle[PlayerNum].getX() >= 600) {
			playerAvatarRectangle[PlayerNum].setLocation((int) (600 - playerAvatarRectangle[PlayerNum].getX() + 75),
					(int) playerAvatarRectangle[PlayerNum].getY());
		} else if (playerAvatarRectangle[PlayerNum].getY() >= 600) {
			playerAvatarRectangle[PlayerNum].setLocation((int) playerAvatarRectangle[PlayerNum].getX(),
					(int) (600 - playerAvatarRectangle[PlayerNum].getY() + 75));
		}
		playerAvatar[PlayerNum].setBounds(playerAvatarRectangle[PlayerNum]);
	}

	public static void playerShift(int xCord, int yCord) {
		int xMoved = 0;
		int yMoved = 0;

		if (PlayerTurn == 0) {

			xMoved = xCord - Player.player1.getCurrentX() - 1;
			yMoved = yCord - Player.player1.getCurrentY() - 1;

			ticksMoved = Math.abs((xMoved + yMoved)) * 75;
			movePlayer = true;
			resume();

		} else if (PlayerTurn == 1) {

			xMoved = xCord - Player.player2.getCurrentX() - 1;
			yMoved = yCord - Player.player2.getCurrentY() - 1;

			ticksMoved = Math.abs((xMoved + yMoved)) * 75;
			movePlayer = true;
			resume();

		} else if (PlayerTurn == 2) {

			xMoved = xCord - Player.player3.getCurrentX() - 1;
			yMoved = yCord - Player.player3.getCurrentY() - 1;

			ticksMoved = Math.abs((xMoved + yMoved)) * 75;
			movePlayer = true;
			resume();

		} else if (PlayerTurn == 3) {

			xMoved = xCord - Player.player4.getCurrentX() - 1;
			yMoved = yCord - Player.player4.getCurrentY() - 1;

		}
		if (xMoved > 0) {
			direction = 1;
		} else if (xMoved < 0) {

			direction = 2;
		} else if (yMoved > 0) {

			direction = 3;
		} else if (yMoved < 0) {

			direction = 4;
		} else {

		}

		ticksMoved = Math.abs((xMoved + yMoved)) * 75;
		movePlayer = true;
		resume();

	}

}

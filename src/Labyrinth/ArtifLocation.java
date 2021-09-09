package Labyrinth;

import javax.swing.ImageIcon;

public class ArtifLocation {
	public static int[] points = new int[4];

	public static void point(int playerTurn) {
		// System.out.print("John is Hot");

		switch (playerTurn) {
		case (0):

			for (int a = 0; a < 5; a++) {

				if (CreatingBoardObject.Board[Player.player1.getCurrentX()][Player.player1.getCurrentY()].getNum()
						.equals(Card.temp1[0][a].getNum())) {
					Card.hands[0][a] = new ImageIcon(
							new ImageIcon("Card Images/founds.PNG").getImage().getScaledInstance(1000, 600, 0));
					points[0] = points[0] + 1;
					Card.temp1[0][a].setNum("25");
					System.out.println("player One points" +"     :" + points[0]);
				}
			}
			break;
		case (1):

			for (int a = 0; a < 5; a++) {

				if (CreatingBoardObject.Board[Player.player2.getCurrentX()][Player.player2.getCurrentY()].getNum()
						.equals(Card.temp1[1][a].getNum())) {
					Card.hands[1][a] = new ImageIcon(
							new ImageIcon("Card Images/founds.PNG").getImage().getScaledInstance(1000, 600, 0));
					points[1] = points[1] + 1;
					Card.temp1[1][a].setNum("25");
					System.out.println("player two points" +"     :" + points[1]);
				}
			}
			break;
		case (2):

			for (int a = 0; a < 5; a++) {

				if (CreatingBoardObject.Board[Player.player3.getCurrentX()][Player.player3.getCurrentY()].getNum()
						.equals(Card.temp1[2][a].getNum())) {
					Card.hands[2][a] = new ImageIcon(
							new ImageIcon("Card Images/founds.PNG").getImage().getScaledInstance(1000, 600, 0));
					points[2] = points[2] + 1;
					Card.temp1[2][a].setNum("25");
					System.out.println("player Three points" +"     :" + points[1]);
				}
			}
			break;
		case (3):

			for (int a = 0; a < 5; a++) {

				if (CreatingBoardObject.Board[Player.player4.getCurrentX()][Player.player4.getCurrentY()].getNum()
						.equals(Card.temp1[3][a].getNum())) {
					Card.hands[3][a] = new ImageIcon(
							new ImageIcon("Card Images/founds.PNG").getImage().getScaledInstance(1000, 600, 0));
					points[3] = points[3] + 1;
					Card.temp1[3][a].setNum("25");
					System.out.println("player Four points" +"     :" + points[3]);
				}
			}
			break;
		}

	}
}

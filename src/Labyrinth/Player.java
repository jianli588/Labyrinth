package Labyrinth;
import javax.swing.ImageIcon;

// if currentPlayer == player then allow them to click
public class Player {

	private static boolean[][][] allowedLocations = new boolean[4][7][7];
																	
	 static PlayerObject player1 = new PlayerObject("playerImages/red_guy.png", 0, 0 ,0);
	 static PlayerObject player2 = new PlayerObject("playerImages/blue_guy.png", 1, 0, 6);
	 static PlayerObject player3 = new PlayerObject("playerImages/yellow_guy.png", 2, 6, 6);
	 static PlayerObject player4 = new PlayerObject("playerImages/green_guy.png", 3, 6, 0);												
		
		

	 	public static PlayerObject getPlayer1() {
			return player1;
		}

		public static void setPlayer1(PlayerObject player1) {
			Player.player1 = player1;
		}

		public static PlayerObject getPlayer2() {
			return player2;
		}

		public static void setPlayer2(PlayerObject player2) {
			Player.player2 = player2;
		}

		public static PlayerObject getPlayer3() {
			return player3;
		}

		public static void setPlayer3(PlayerObject player3) {
			Player.player3 = player3;
		}

		public static PlayerObject getPlayer4() {
			return player4;
		}

		public static void setPlayer4(PlayerObject player4) {
			Player.player4 = player4;
		}

	static void checkBoard(int player) {

		for (int i = 0; i < 7; i++) {
			for (int k = 0; k < 7; k++) {
				allowedLocations[player][i][k] = false;
			}
		}
		
		switch (player){
		
		case(0):
		checkUp(allowedLocations[player], player1.getCurrentX(), player1.getCurrentY());
		checkDown(allowedLocations[player], player1.getCurrentX(), player1.getCurrentY());
		checkLeft(allowedLocations[player], player1.getCurrentX(), player1.getCurrentY());
		checkRight(allowedLocations[player], player1.getCurrentX(), player1.getCurrentY());
		break;
		
		case(1):
		checkUp(allowedLocations[player], player2.getCurrentX(), player2.getCurrentY());
		checkDown(allowedLocations[player], player2.getCurrentX(), player2.getCurrentY());
		checkLeft(allowedLocations[player], player2.getCurrentX(), player2.getCurrentY());
		checkRight(allowedLocations[player], player2.getCurrentX(), player2.getCurrentY());
		break;
		
		case(2):
		checkUp(allowedLocations[player], player3.getCurrentX(), player3.getCurrentY());
		checkDown(allowedLocations[player], player3.getCurrentX(), player3.getCurrentY());
		checkLeft(allowedLocations[player], player3.getCurrentX(), player3.getCurrentY());
		checkRight(allowedLocations[player], player3.getCurrentX(), player3.getCurrentY());
		break;
		
		case(3):
		checkUp(allowedLocations[player], player4.getCurrentX(), player4.getCurrentY());
		checkDown(allowedLocations[player], player4.getCurrentX(), player4.getCurrentY());
		checkLeft(allowedLocations[player], player4.getCurrentX(), player4.getCurrentY());
		checkRight(allowedLocations[player], player4.getCurrentX(), player4.getCurrentY());
		break;
		}
	}

	public void artiflocation() {
		
	}
	public static boolean[][] getPlayerAllowedLocations(int i) {
		checkBoard(i);
		return allowedLocations[i];
	}

	public boolean[][] getP2AllowedLocations() {
		checkBoard(1);
		return allowedLocations[1];
	}

	public boolean[][] getP3AllowedLocations() {
		checkBoard(2);
		return allowedLocations[2];
	}

	public boolean[][] getP4AllowedLocations() {
		checkBoard(3);
		return allowedLocations[3];
	}

	public static void checkUp(boolean[][] playerBoard, int xCord, int yCord) {

		if (yCord == 0) {
			return;
		}

		if (!CreatingBoardObject.isUp(xCord, yCord)) {

			return;
		}
		if (!CreatingBoardObject.isDown(xCord, yCord - 1)) {

			return;
		} else {

			playerBoard[xCord][yCord - 1] = true;
			checkUp(playerBoard, xCord, yCord - 1);
			//checkLeft(playerBoard, xCord, yCord - 1);
			//checkRight(playerBoard, xCord, yCord - 1);
			
		}
	}

	public static void checkDown(boolean[][] playerBoard, int xCord, int yCord) {

		if (yCord == 6) {
			return;
		}

		if (!CreatingBoardObject.isDown(xCord, yCord)) {

			return;
		}
		if (!CreatingBoardObject.isUp(xCord, yCord + 1)) {

			return;

		} else {

			playerBoard[xCord][yCord + 1] = true;

			checkDown(playerBoard, xCord, yCord + 1);
			//checkLeft(playerBoard, xCord, yCord + 1);
			//checkRight(playerBoard, xCord, yCord + 1);
		}
	}

	public static void checkRight(boolean[][] playerBoard, int xCord, int yCord) {

		if (xCord == 6) {
			return;
		}

		if (!CreatingBoardObject.isRight(xCord, yCord)) {

			return;
		}

		if (!CreatingBoardObject.isLeft(xCord + 1, yCord)) {

			return;
		} else {

			playerBoard[xCord + 1][yCord] = true;
			checkRight(playerBoard, xCord + 1, yCord);
			//checkUp(playerBoard, xCord + 1, yCord);
			//checkDown(playerBoard, xCord + 1, yCord);
		}
	}

	public static void checkLeft(boolean[][] playerBoard, int xCord, int yCord) {

		if (xCord == 0) {
			return;
		}

		
		if (!CreatingBoardObject.isLeft(xCord, yCord)) {

			return;
		}
		if (!CreatingBoardObject.isRight(xCord - 1, yCord)) {

			return;
		} else {

			playerBoard[xCord - 1][yCord] = true;
			checkLeft(playerBoard, xCord - 1, yCord);
			//checkUp(playerBoard, xCord - 1, yCord);
			//checkDown(playerBoard, xCord - 1, yCord);
		}
	}

	public static String getPlayerImageIcon(int i) {
		// TODO Auto-generated method stub
		switch(i){
		
		case 0:
		return player1.getfileLocation();
		
		case 1:
		return player2.getfileLocation();
		
		case 2:
		return player3.getfileLocation();
		
		case 3:
		return player4.getfileLocation();
		
		}
		return null;
		
	}
	
	
}
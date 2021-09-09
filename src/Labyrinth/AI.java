package Labyrinth;

public class AI {

	private static boolean[][] movableTiles = new boolean[7][7];// array of walkable tile location

	static boolean[][] playerOne = new boolean[7][7];// array of walkable tile location
	static boolean[][] playerTwo = new boolean[7][7];// array of walkable tile location
	static boolean[][] playerThree = new boolean[7][7];// array of walkable tile location
	static boolean[][] playerFour = new boolean[7][7];// array of walkable tile location

	static boolean FoundTreasure = false;// check if a tresure can be found in the current walkable locations

	static int bestXcord = 0;// the best xcord out of 49 the ai can move to before inserting
	static int bestYcord = 0;// the best ycord out of 49 the ai can move to before inserting
	static int bestInsertLocation = 0; //// the best insert location out of 11 the ai can insert in
	static int bestRotation = 0; // the best rotational number the ai can insert from

	static Integer temp = 0; // a temporary number to store information

	static int bestNumber = -10000;// set the best number to the lowest possible

	private static TileObject[][] TemporaryBoard = new TileObject[7][7];// the temporary board that gets changed
	
	private static int[][] ratingsForBoard = new int[12][4];// array that rates the board

	private static PlayerObject[] players = new PlayerObject[4];// player object with temporary locations when finding
																// best route

	private static int[] numOfFreeTiles = new int[4];// array for the number of tiles available

	public static void initializePlayer() {
		// create player objects
		players[0] = new PlayerObject(0, 0, 0);
		players[1] = new PlayerObject(1, 0, 0);
		players[2] = new PlayerObject(2, 0, 0);
		players[3] = new PlayerObject(3, 0, 0);
	}

	private int blockPlayer(int player, int xCord, int yCord) {// to block players
		int temp = 0;

		switch (player + 1) {

		case 1:// for player 1

			temp = temp + findTreasure(player + 1, xCord, yCord) / 2;
			temp = temp + findTreasure(player + 2, xCord, yCord) / 4;
			temp = temp + findTreasure(player + 3, xCord, yCord) / 4;
			return -temp;

		case 2:// for player 2

			temp = temp + findTreasure(player + 1, xCord, yCord) / 2;
			temp = temp + findTreasure(player + 2, xCord, yCord) / 4;
			temp = temp + findTreasure(player - 1, xCord, yCord) / 4;
			return -temp;

		case 3:// for player 3

			temp = temp + findTreasure(player + 1, xCord, yCord) / 2;
			temp = temp + findTreasure(player - 2, xCord, yCord) / 4;
			temp = temp + findTreasure(player + 1, xCord, yCord) / 4;
			return -temp;

		case 4:// for player 4

			temp = temp + findTreasure(player - 1, xCord, yCord) / 2;
			temp = temp + findTreasure(player - 2, xCord, yCord) / 2;
			temp = temp + findTreasure(player - 3, xCord, yCord) / 4;
			return -temp;

		}

		return -temp;
	}

	public void insert(int player) {

		for (int i = 0; i < 7; i++) { // set all walkable tiles to default
			for (int k = 0; k < 7; k++) {

				movableTiles[i][k] = false;

			}
		}

		switch (player) {// creates the movable tiles for player one, used to check the available
							// treasures currently
		case (0):
			checkUp(CreatingBoardObject.Board, movableTiles, Player.player1.getCurrentX(), Player.player1.getCurrentY(),
					player);
			checkDown(CreatingBoardObject.Board, movableTiles, Player.player1.getCurrentX(),
					Player.player1.getCurrentY(), player);
			checkLeft(CreatingBoardObject.Board, movableTiles, Player.player1.getCurrentX(),
					Player.player1.getCurrentY(), player);
			checkRight(CreatingBoardObject.Board, movableTiles, Player.player1.getCurrentX(),
					Player.player1.getCurrentY(), player);
			break;

		case (1):// for player two
			checkUp(CreatingBoardObject.Board, movableTiles, Player.player2.getCurrentX(), Player.player2.getCurrentY(),
					player);
			checkDown(CreatingBoardObject.Board, movableTiles, Player.player2.getCurrentX(),
					Player.player2.getCurrentY(), player);
			checkLeft(CreatingBoardObject.Board, movableTiles, Player.player2.getCurrentX(),
					Player.player2.getCurrentY(), player);
			checkRight(CreatingBoardObject.Board, movableTiles, Player.player2.getCurrentX(),
					Player.player2.getCurrentY(), player);
			break;

		case (2):// for player three
			checkUp(CreatingBoardObject.Board, movableTiles, Player.player3.getCurrentX(), Player.player3.getCurrentY(),
					player);
			checkDown(CreatingBoardObject.Board, movableTiles, Player.player3.getCurrentX(),
					Player.player3.getCurrentY(), player);
			checkLeft(CreatingBoardObject.Board, movableTiles, Player.player3.getCurrentX(),
					Player.player3.getCurrentY(), player);
			checkRight(CreatingBoardObject.Board, movableTiles, Player.player3.getCurrentX(),
					Player.player3.getCurrentY(), player);
			break;

		case (3): // for player four
			checkUp(CreatingBoardObject.Board, movableTiles, Player.player4.getCurrentX(), Player.player4.getCurrentY(),
					player);
			checkDown(CreatingBoardObject.Board, movableTiles, Player.player4.getCurrentX(),
					Player.player4.getCurrentY(), player);
			checkLeft(CreatingBoardObject.Board, movableTiles, Player.player4.getCurrentX(),
					Player.player4.getCurrentY(), player);
			checkRight(CreatingBoardObject.Board, movableTiles, Player.player4.getCurrentX(),
					Player.player4.getCurrentY(), player);
			break;

		}

		insertTiles(player);// insert the tiles

		for (int i = 0; i < bestRotation; i++) {
			CreatingBoardObject.rotateLeft();
		}

		// move in the tiles based on the best locations
		GameFrame.locationOfTileAdded = bestInsertLocation + 1;
		GameFrame.enableOrDisableButtons(false);
		GameFrame.disableBorder();
		GameFrame.ticksMoved = 75;
		GameFrame.resume();

	}

	public void MoveAI(int player) {

		
		// disable the entire movable tiles
		for (int i = 0; i < 7; i++) {
			for (int k = 0; k < 7; k++) {

				movableTiles[i][k] = false;

			}
		}

		switch (player) {// check movable tiles
		
		
		case (0):// for player 1
			
			checkUp(CreatingBoardObject.Board, movableTiles, Player.player1.getCurrentX(), Player.player1.getCurrentY(),
					player);
			checkDown(CreatingBoardObject.Board, movableTiles, Player.player1.getCurrentX(),
					Player.player1.getCurrentY(), player);
			checkLeft(CreatingBoardObject.Board, movableTiles, Player.player1.getCurrentX(),
					Player.player1.getCurrentY(), player);
			checkRight(CreatingBoardObject.Board, movableTiles, Player.player1.getCurrentX(),
					Player.player1.getCurrentY(), player);
			break;

		case (1):// for player 2
			
			checkUp(CreatingBoardObject.Board, movableTiles, Player.player2.getCurrentX(), Player.player2.getCurrentY(),
					player);
			checkDown(CreatingBoardObject.Board, movableTiles, Player.player2.getCurrentX(),
					Player.player2.getCurrentY(), player);
			checkLeft(CreatingBoardObject.Board, movableTiles, Player.player2.getCurrentX(),
					Player.player2.getCurrentY(), player);
			checkRight(CreatingBoardObject.Board, movableTiles, Player.player2.getCurrentX(),
					Player.player2.getCurrentY(), player);
			break;

		case (2):// for player 3
			checkUp(CreatingBoardObject.Board, movableTiles, Player.player3.getCurrentX(), Player.player3.getCurrentY(),
					player);
			checkDown(CreatingBoardObject.Board, movableTiles, Player.player3.getCurrentX(),
					Player.player3.getCurrentY(), player);
			checkLeft(CreatingBoardObject.Board, movableTiles, Player.player3.getCurrentX(),
					Player.player3.getCurrentY(), player);
			checkRight(CreatingBoardObject.Board, movableTiles, Player.player3.getCurrentX(),
					Player.player3.getCurrentY(), player);
			break;

		case (3):// for player 4
			checkUp(CreatingBoardObject.Board, movableTiles, Player.player4.getCurrentX(), Player.player4.getCurrentY(),
					player);
			checkDown(CreatingBoardObject.Board, movableTiles, Player.player4.getCurrentX(),
					Player.player4.getCurrentY(), player);
			checkLeft(CreatingBoardObject.Board, movableTiles, Player.player4.getCurrentX(),
					Player.player4.getCurrentY(), player);
			checkRight(CreatingBoardObject.Board, movableTiles, Player.player4.getCurrentX(),
					Player.player4.getCurrentY(), player);
			break;

		}

		// print movable tiles and check treasure for each tile
		for (int i = 0; i < 7; i++) {
			for (int k = 0; k < 7; k++) {

				if (movableTiles[k][i]) {
					System.out.print("X");
					if (checkTreasure(CreatingBoardObject.Board, k, i, player)) {
					}
				} else {
					System.out.print("O");
				}

			}
			System.out.println();
		}

	}

	private void checkWalkableLocations() {

		checkUp(TemporaryBoard, playerOne, players[0].getCurrentX(), players[0].getCurrentY(), 0);
		checkDown(TemporaryBoard, playerOne, players[0].getCurrentX(), players[0].getCurrentY(), 0);
		checkRight(TemporaryBoard, playerOne, players[0].getCurrentX(), players[0].getCurrentY(), 0);
		checkLeft(TemporaryBoard, playerOne, players[0].getCurrentX(), players[0].getCurrentY(), 0);

		checkUp(TemporaryBoard, playerTwo, players[1].getCurrentX(), players[1].getCurrentY(), 1);
		checkDown(TemporaryBoard, playerTwo, players[1].getCurrentX(), players[1].getCurrentY(), 1);
		checkRight(TemporaryBoard, playerTwo, players[1].getCurrentX(), players[1].getCurrentY(), 1);
		checkLeft(TemporaryBoard, playerTwo, players[1].getCurrentX(), players[1].getCurrentY(), 1);

		checkUp(TemporaryBoard, playerThree, players[2].getCurrentX(), players[2].getCurrentY(), 2);
		checkDown(TemporaryBoard, playerThree, players[2].getCurrentX(), players[2].getCurrentY(), 2);
		checkRight(TemporaryBoard, playerThree, players[2].getCurrentX(), players[2].getCurrentY(), 2);
		checkLeft(TemporaryBoard, playerThree, players[2].getCurrentX(), players[2].getCurrentY(), 2);

		checkUp(TemporaryBoard, playerFour, players[3].getCurrentX(), players[3].getCurrentY(), 2);
		checkDown(TemporaryBoard, playerFour, players[3].getCurrentX(), players[3].getCurrentY(), 2);
		checkRight(TemporaryBoard, playerFour, players[3].getCurrentX(), players[3].getCurrentY(), 2);
		checkLeft(TemporaryBoard, playerFour, players[3].getCurrentX(), players[3].getCurrentY(), 2);

	}

	private void insertTiles(int player) {

		bestNumber = -10000;// reset the best number
		
		// set the player location
		players[0].setCurrentX(Player.player1.getCurrentX());
		players[0].setCurrentY(Player.player1.getCurrentY());

		players[1].setCurrentX(Player.player2.getCurrentX());
		players[1].setCurrentY(Player.player2.getCurrentY());

		players[2].setCurrentX(Player.player3.getCurrentX());
		players[2].setCurrentY(Player.player3.getCurrentY());

		players[3].setCurrentX(Player.player4.getCurrentX());
		players[3].setCurrentY(Player.player4.getCurrentY());

		
		// set current tile as movable
		movableTiles[players[player].getCurrentX()][players[player].getCurrentY()] = true;

		
		// print the current board
		for (int i = 0; i < 7; i++) {
			for (int k = 0; k < 7; k++) {
				if (movableTiles[i][k]) {
					System.out.print("X");
				} else {
					System.out.print("O");
				}

			}
			System.out.println();
		}

		// loop for each movable tile currently
		for (int xCordToMoveTo = 0; xCordToMoveTo < 7; xCordToMoveTo++) {
			for (int yCordToMoveTo = 0; yCordToMoveTo < 7; yCordToMoveTo++) {

				if (movableTiles[yCordToMoveTo][xCordToMoveTo]) {

					// check the insert location for each rotation
					for (int numberOfRotations = 0; numberOfRotations < 4; numberOfRotations++) {
						for (int insertLocations = 0; insertLocations < 12; insertLocations++) {

							// reset the ratings for board
							ratingsForBoard[insertLocations][numberOfRotations] = 0;

							
							for (int i = 0; i < 7; i++) {
								for (int k = 0; k < 7; k++) {
									//
									TemporaryBoard[i][k] = CreatingBoardObject.Board[i][k];// creates an copy of the
																							// board
									// reset the movable tiles for the players
									playerOne[i][k] = false;
									playerTwo[i][k] = false;
									playerThree[i][k] = false;
									playerFour[i][k] = false;

								}
							}

//							players[0].setCurrentX(Player.player1.getCurrentX());
//							players[0].setCurrentY(Player.player1.getCurrentY());
//
//							players[1].setCurrentX(Player.player2.getCurrentX());
//							players[1].setCurrentY(Player.player2.getCurrentY());
//
//							players[2].setCurrentX(Player.player3.getCurrentX());
//							players[2].setCurrentY(Player.player3.getCurrentY());
//
//							players[3].setCurrentX(Player.player4.getCurrentX());
//							players[3].setCurrentY(Player.player4.getCurrentY());

							
							// set the player to a walkable tile,
							players[player].setCurrentX(xCordToMoveTo);
							players[player].setCurrentY(yCordToMoveTo);

							
							//insert the tiles
							changeBoard(insertLocations);

							// check walkable tiles
							checkWalkableLocations();

							
							int temp = ratingsForBoard[insertLocations][numberOfRotations];
							
							
							//rates each board
							for (int i = 0; i < 7; i++) {
								for (int k = 0; k < 7; k++) {

									//temp = temp + findTreasure(player, k, i);
									temp = temp + blockPlayer(player, k, i);

									if (player == 0) {
										if (playerOne[i][k] && !movableTiles[k][i]) {

											temp = temp + 1;
										}
									}
									else if (player == 1) {
										if (playerTwo[i][k] && !movableTiles[k][i]) {

											temp = temp + 1;
										}
									}
									else if (player == 2) {
										if (playerThree[i][k] && !movableTiles[k][i]) {

											temp = temp + 1;
										}
									}
									else if (player == 3) {
										if (playerFour[i][k] && !movableTiles[k][i]) {

											temp = temp + 1;
										}
									}

								}
							}
							// check if number is better than the last one
							if (temp > bestNumber) {
								bestNumber = temp;
								bestXcord = xCordToMoveTo;
								bestYcord = yCordToMoveTo;
								bestInsertLocation = insertLocations;
								bestRotation = numberOfRotations;
							}

//							System.out.println(yCordToMoveTo + "   " + xCordToMoveTo + "   " + numberOfRotations + "   "
//									+ insertLocations + ":" + temp);

						}

						CreatingBoardObject.rotateLeft();
						GameFrame.setExtraTile();

					}

				}

			}

		}
		System.out.println(
				bestYcord + "   " + bestXcord + "   " + bestRotation + "   " + bestInsertLocation + ":" + bestNumber);

		if (player == 0) {
			
			// moves the player to the best coordinates
			Player.player1.setCurrentX(bestYcord);
			Player.player1.setCurrentY(bestXcord);

			GameFrame.playerAvatarRectangle[player].setBounds(Player.player1.getCurrentX() * 75 + 100,
					Player.player1.getCurrentY() * 75 + 100, 32, 32);
			GameFrame.playerAvatar[player].setBounds(GameFrame.playerAvatarRectangle[player]);
		} else if (player == 1) {
			
			// moves the player to the best coordinates
			Player.player2.setCurrentX(bestYcord);
			Player.player2.setCurrentY(bestXcord);

			GameFrame.playerAvatarRectangle[player].setBounds(Player.player2.getCurrentX() * 75 + 100,
					Player.player2.getCurrentY() * 75 + 100, 32, 32);
			GameFrame.playerAvatar[player].setBounds(GameFrame.playerAvatarRectangle[player]);
		} else if (player == 2) {
			
			// moves the player to the best coordinates
			Player.player3.setCurrentX(bestYcord);
			Player.player3.setCurrentY(bestXcord);

			GameFrame.playerAvatarRectangle[player].setBounds(Player.player3.getCurrentX() * 75 + 100,
					Player.player3.getCurrentY() * 75 + 100, 32, 32);
			GameFrame.playerAvatar[player].setBounds(GameFrame.playerAvatarRectangle[player]);
			
			
		} else if (player == 3) {
			
			// moves the player to the best coordinates
			Player.player4.setCurrentX(bestYcord);
			Player.player4.setCurrentY(bestXcord);

			
			GameFrame.playerAvatarRectangle[player].setBounds(Player.player4.getCurrentX() * 75 + 100,
					Player.player4.getCurrentY() * 75 + 100, 32, 32);
			GameFrame.playerAvatar[player].setBounds(GameFrame.playerAvatarRectangle[player]);

		}

		switch (bestInsertLocation + 1) {// to disable the buttons depending on the insert location

		case 1:

			GameFrame.enableButtons();
			GameFrame.right[0] = false;

			break;

		case 2:

			GameFrame.enableButtons();
			GameFrame.right[1] = false;

			break;

		case 3:
			GameFrame.enableButtons();
			GameFrame.right[2] = false;

			break;

		case 4:
			GameFrame.enableButtons();
			GameFrame.left[0] = false;

			break;

		case 5:
			GameFrame.enableButtons();
			GameFrame.left[1] = false;

			break;

		case 6:
			GameFrame.enableButtons();
			GameFrame.left[2] = false;

			break;

		case 7:
			GameFrame.enableButtons();
			GameFrame.bottom[0] = false;

			break;

		case 8:
			GameFrame.enableButtons();
			GameFrame.bottom[1] = false;

			break;

		case 9:
			GameFrame.enableButtons();
			GameFrame.bottom[2] = false;

			break;

		case 10:
			GameFrame.enableButtons();
			GameFrame.top[0] = false;

			break;

		case 11:

			GameFrame.enableButtons();
			GameFrame.top[1] = false;

			break;

		case 12:

			GameFrame.enableButtons();
			GameFrame.top[2] = false;

			break;
		}

	}

	
	// method to find the treasure for the temporary board
	private static int findTreasure(int player, int xCord, int yCord) {

		temp = 0;

		switch (player) {
		case 0:// check player one points for temporary board
			if (playerOne[yCord][xCord]) {
				for (int y = 0; y < 5; y++) {
					// check if a treasure can be found
					if (TemporaryBoard[xCord][yCord].getNum().equals(Card.temp1[player][y].getNum())) {
						temp = temp + 100;
						System.out.println("playerOne");
						System.out.println(players[0].getCurrentY() + " " + players[0].getCurrentX());
						// additional points if 4 treasures is already found
						if (ArtifLocation.points[0] == 4) {
							temp = temp + 900;

						}

					}
				}

			}

			return temp;
		case 1:// check player two points for temporary board
			if (playerTwo[yCord][xCord]) {
				for (int y = 0; y < 5; y++) {
					// check if a treasure can be found
					if (TemporaryBoard[xCord][yCord].getNum().equals(Card.temp1[player][y].getNum())) {
						temp = temp + 100;
						System.out.println("playerTwo");
						System.out.println(players[1].getCurrentY() + " " + players[1].getCurrentX());
						// additional points if 4 treasures is already found
						if (ArtifLocation.points[1] == 4) {
							temp = temp + 900;
						}
						
					}

				}
			}

			return temp;

		case 2:// check player three points for temporary board
			if (playerThree[yCord][xCord]) {
				for (int y = 0; y < 5; y++) {
					// check if a treasure can be found
					if (TemporaryBoard[xCord][yCord].getNum().equals(Card.temp1[player][y].getNum())) {
						temp = temp + 100;
						System.out.println("playerThree");
						System.out.println(players[2].getCurrentY() + " " + players[2].getCurrentX());
						// additional points if 4 treasures is already found
						if (ArtifLocation.points[player] == 4) {
							temp = temp + 900;
						}
						
					}

				}
			}

			return temp;

		case 3:// check player four points for temporary board
			if (playerFour[yCord][xCord]) {
				for (int y = 0; y < 5; y++) {
					// check if a treasure can be found
					if (TemporaryBoard[xCord][yCord].getNum().equals(Card.temp1[player][y].getNum())) {
						temp = temp + 100;
						System.out.println("playerFour:" + y);
						System.out.println(players[3].getCurrentY() + " " + players[3].getCurrentX());
						// additional points if 4 treasures is already found
						if (ArtifLocation.points[3] == 4) {
							temp = temp + 900;
						}
						
					}

				}
			}

			return temp;
		}

		return temp;

	}

	// check if player can find treasure currently
	private static boolean checkTreasure(TileObject[][] board, int xCord, int yCord, int player) {

		if (player == 0) {

			for (int y = 0; y < 5; y++) {
				if (board[xCord][yCord].getNum().equals(Card.temp1[0][y].getNum())) {// if treasure is found
					Player.player1.setCurrentX(xCord);// change the player coordinate
					Player.player1.setCurrentY(yCord);

					
					// move the player on the map
					GameFrame.playerAvatarRectangle[0].setBounds(Player.player1.getCurrentX() * 75 + 100,
							Player.player1.getCurrentY() * 75 + 100, 32, 32);
					GameFrame.playerAvatar[0].setBounds(GameFrame.playerAvatarRectangle[0]);

					
					// check the points for the player
					ArtifLocation.point(0);
					Player.checkBoard(player);
					GameFrame.changeBorder();
					return true;
				}
			}

		} else if (player == 1) {

			for (int y = 0; y < 5; y++) {
				if (board[xCord][yCord].getNum().equals(Card.temp1[1][y].getNum())) {
					Player.player2.setCurrentX(xCord);// change the player coordinate
					Player.player2.setCurrentY(yCord);

					// move the player on the map
					GameFrame.playerAvatarRectangle[1].setBounds(Player.player2.getCurrentX() * 75 + 100,
							Player.player2.getCurrentY() * 75 + 100, 32, 32);
					GameFrame.playerAvatar[1].setBounds(GameFrame.playerAvatarRectangle[1]);
					
					// check the points for the player
					ArtifLocation.point(1);
					Player.checkBoard(player);
					GameFrame.changeBorder();

					return true;
				}
			}

		} else if (player == 2) {
			for (int y = 0; y < 5; y++) {
				if (board[xCord][yCord].getNum().equals(Card.temp1[2][y].getNum())) {
					Player.player3.setCurrentX(xCord);// change the player coordinate
					Player.player3.setCurrentY(yCord);
					
					// move the player on the map
					GameFrame.playerAvatarRectangle[2].setBounds(Player.player3.getCurrentX() * 75 + 100,
							Player.player3.getCurrentY() * 75 + 100, 32, 32);
					GameFrame.playerAvatar[2].setBounds(GameFrame.playerAvatarRectangle[2]);
					
					// check the points for the player
					ArtifLocation.point(2);
					Player.checkBoard(player);
					GameFrame.changeBorder();

					return true;
				}
			}

		} else if (player == 3) {
			for (int y = 0; y < 5; y++) {
				if (board[xCord][yCord].getNum().equals(Card.temp1[3][y].getNum())) {
					Player.player4.setCurrentX(xCord);// change the player coordinate
					Player.player4.setCurrentY(yCord);
					
					// move the player on the map
					GameFrame.playerAvatarRectangle[3].setBounds(Player.player4.getCurrentX() * 75 + 100,
							Player.player4.getCurrentY() * 75 + 100, 32, 32);
					GameFrame.playerAvatar[3].setBounds(GameFrame.playerAvatarRectangle[3]);
					
					// check the points for the player
					ArtifLocation.point(3);
					Player.checkBoard(player);
					GameFrame.changeBorder();

					return true;
				}
			}
		}
		return false;

	}
	
	
	// recurssion that checks the board to move up checks if player can move up
	public void checkUp(TileObject[][] tileBoard, boolean[][] board, int xCord, int yCord, int player) {
		
		// stop recussion if on the top of board
		if (yCord == 0) {
			return;
		}

		// stop recurssion if player current cord is enclosed top
		if (!tileBoard[xCord][yCord].isUp()) {

			return;
		}
		
		// stop recursion if top tile is enclosed bottom
		if (!tileBoard[xCord][yCord - 1].isDown()) {

			return;
		}
		
		// stop if top tile has already been walked on
		if (board[xCord][yCord - 1]) {
			return;

		} else {

			board[xCord][yCord - 1] = true;
			checkUp(tileBoard, board, xCord, yCord - 1, player);
			checkLeft(tileBoard, board, xCord, yCord - 1, player);
			checkRight(tileBoard, board, xCord, yCord - 1, player);

		}
	}

	// // recurssion that checks the board to move down checks if player can move down
	public void checkDown(TileObject[][] tileBoard, boolean[][] board, int xCord, int yCord, int player) {

		
		// stop recurssion if on the bottom
		if (yCord == 6) {
			return;
		}

		// stop recurssion if bottom is enclosed
		if (!CreatingBoardObject.isDown(xCord, yCord)) {

			return;
		}
		
		// stop recurssion if the tile on the bottom is enclosed top
		if (!CreatingBoardObject.isUp(xCord, yCord + 1)) {

			return;

		}
		
		// stop recurssion if the tile is already walked on
		if (board[xCord][yCord + 1]) {
			return;

		} else {

			board[xCord][yCord + 1] = true;

			checkDown(tileBoard, board, xCord, yCord + 1, player);
			checkLeft(tileBoard, board, xCord, yCord + 1, player);
			checkRight(tileBoard, board, xCord, yCord + 1, player);
		}
	}
	// recurssion that checks the board to move right, checks if player can move right
	public void checkRight(TileObject[][] tileBoard, boolean[][] board, int xCord, int yCord, int player) {

		
		// if the player is on the right side of the board
		if (xCord == 6) {
			return;
		}
		

		// if the tile is enclosed on the right
		if (!CreatingBoardObject.isRight(xCord, yCord)) {

			return;
		}

		// if the tile on the right is enclosed on the left
		if (!CreatingBoardObject.isLeft(xCord + 1, yCord)) {

			return;
		}
		
		// stop if the tile on the right has already been walked on 
		if (board[xCord + 1][yCord]) {
			return;

		} else {
			
			
			board[xCord + 1][yCord] = true;
			checkRight(tileBoard, board, xCord + 1, yCord, player);
			checkUp(tileBoard, board, xCord + 1, yCord, player);
			checkDown(tileBoard, board, xCord + 1, yCord, player);
		}
	}

	// recurssion that checks the board to move left checks if player can move left
	public void checkLeft(TileObject[][] tileBoard, boolean[][] board, int xCord, int yCord, int player) {

		
		// if the player is on the left most tile
		if (xCord == 0) {
			return;
		}

		// if the tile is enclosed on the left
		if (!tileBoard[xCord][yCord].isLeft()) {

			return;
		}
		
		// if the tile on the left is enclosed on the right
		if (!CreatingBoardObject.isRight(xCord - 1, yCord)) {

			return;
		}
		
		// in the tile on the left is walked on
		if (board[xCord - 1][yCord]) {
			return;
		} else {
			
			
			board[xCord - 1][yCord] = true;
			checkLeft(tileBoard, board, xCord - 1, yCord, player);
			checkUp(tileBoard, board, xCord - 1, yCord, player);
			checkDown(tileBoard, board, xCord - 1, yCord, player);
		}
	}

	public static void changeBoard(int insertLocation) {// create temporary boards

		switch (insertLocation + 1) {

		// left insert locations
		case 1:

			for (int col = 6; col > 0; col--) {
				TemporaryBoard[col][1] = TemporaryBoard[col - 1][1];
			}
			TemporaryBoard[0][1] = CreatingBoardObject.extraTile;

			for (int i = 0; i < 4; i++) {
				if (players[i].getCurrentX() == 1) {
					players[i].setCurrentY(players[i].getCurrentY() + 1);
					if (players[i].getCurrentY() == 7) {
						players[i].setCurrentY(0);
					}
				}
			}

			if (!GameFrame.left[0]) {
				System.out.println("asd");
				for (int i = 0; i < 4; i++) {
					ratingsForBoard[0][i] -= 10000;
					System.out.println(ratingsForBoard[3][i]);
				}
			}

			break;

		case 2:

			for (int col = 6; col > 0; col--) {
				TemporaryBoard[col][3] = TemporaryBoard[col - 1][3];
			}
			TemporaryBoard[0][3] = CreatingBoardObject.extraTile;

			for (int i = 0; i < 4; i++) {
				if (players[i].getCurrentX() == 3) {
					players[i].setCurrentY(players[i].getCurrentY() + 1);
					if (players[i].getCurrentY() == 7) {
						players[i].setCurrentY(0);
					}
				}
			}

			if (!GameFrame.left[1]) {
				for (int i = 0; i < 4; i++) {
					ratingsForBoard[1][i] -= 10000;
				}
			}

			break;
		case 3:

			for (int col = 6; col > 0; col--) {
				TemporaryBoard[col][5] = TemporaryBoard[col - 1][5];
			}
			TemporaryBoard[0][5] = CreatingBoardObject.extraTile;

			for (int i = 0; i < 4; i++) {
				if (players[i].getCurrentX() == 5) {
					players[i].setCurrentY(players[i].getCurrentY() + 1);
					if (players[i].getCurrentY() == 7) {
						players[i].setCurrentY(0);
					}
				}
			}

			if (!GameFrame.left[2]) {
				for (int i = 0; i < 4; i++) {
					ratingsForBoard[2][i] -= 10000;
				}
			}

			break;

		// right insert locations
		case 4:

			for (int col = 0; col < 6; col++) {
				TemporaryBoard[col][1] = TemporaryBoard[col + 1][1];
			}
			TemporaryBoard[6][1] = CreatingBoardObject.extraTile;

			for (int i = 0; i < 4; i++) {
				if (players[i].getCurrentX() == 1) {
					players[i].setCurrentY(players[i].getCurrentY() - 1);

					if (players[i].getCurrentY() == -1) {
						players[i].setCurrentY(6);
					}
				}
			}

			if (!GameFrame.right[0]) {
				for (int i = 0; i < 4; i++) {
					ratingsForBoard[3][i] -= 10000;
				}

			}
			break;
		case 5:

			for (int col = 0; col < 6; col++) {
				TemporaryBoard[col][3] = TemporaryBoard[col + 1][3];
			}
			TemporaryBoard[6][3] = CreatingBoardObject.extraTile;

			for (int i = 0; i < 4; i++) {
				if (players[i].getCurrentX() == 3) {
					players[i].setCurrentY(players[i].getCurrentY() - 1);

					if (players[i].getCurrentY() == -1) {
						players[i].setCurrentY(6);
					}
				}
			}

			if (!GameFrame.right[1]) {
				for (int i = 0; i < 4; i++) {
					ratingsForBoard[4][i] -= 10000;
				}
			}

			break;

		case 6:

			for (int col = 0; col < 6; col++) {
				TemporaryBoard[col][5] = TemporaryBoard[col + 1][5];
			}
			TemporaryBoard[6][5] = CreatingBoardObject.extraTile;

			for (int i = 0; i < 4; i++) {
				if (players[i].getCurrentX() == 5) {
					players[i].setCurrentY(players[i].getCurrentY() - 1);

					if (players[i].getCurrentY() == -1) {
						players[i].setCurrentY(6);
					}
				}
			}
			if (!GameFrame.right[2]) {
				for (int i = 0; i < 4; i++) {
					ratingsForBoard[5][i] -= 10000;
				}
			}

			break;

		// top insert locations
		case 7:

			for (int row = 6; row > 0; row--) {

				TemporaryBoard[1][row] = TemporaryBoard[1][row - 1];

			}
			TemporaryBoard[1][0] = CreatingBoardObject.extraTile;

			for (int i = 0; i < 4; i++) {
				if (players[i].getCurrentY() == 1) {
					players[i].setCurrentX(players[i].getCurrentX() + 1);

					if (players[i].getCurrentX() == 7) {
						players[i].setCurrentX(0);
					}

				}
			}
			if (!GameFrame.top[0]) {
				for (int i = 0; i < 4; i++) {
					ratingsForBoard[6][i] -= 10000;
				}
			}

			break;

		case 8:

			for (int row = 6; row > 0; row--) {
				TemporaryBoard[3][row] = TemporaryBoard[3][row - 1];
			}
			TemporaryBoard[3][0] = CreatingBoardObject.extraTile;

			for (int i = 0; i < 4; i++) {
				if (players[i].getCurrentY() == 3) {
					players[i].setCurrentX(players[i].getCurrentX() + 1);

					if (players[i].getCurrentX() == 7) {
						players[i].setCurrentX(0);
					}

				}
			}
			if (!GameFrame.top[1]) {
				for (int i = 0; i < 4; i++) {
					ratingsForBoard[7][i] -= 10000;
				}
			}

			break;
		case 9:

			for (int row = 6; row > 0; row--) {
				TemporaryBoard[5][row] = TemporaryBoard[5][row - 1];
			}
			TemporaryBoard[5][0] = CreatingBoardObject.extraTile;

			for (int i = 0; i < 4; i++) {
				if (players[i].getCurrentY() == 5) {
					players[i].setCurrentX(players[i].getCurrentX() + 1);

					if (players[i].getCurrentX() == 7) {
						players[i].setCurrentX(0);
					}
				}
			}

			if (!GameFrame.top[2]) {
				for (int i = 0; i < 4; i++) {
					ratingsForBoard[8][i] -= 10000;
				}
			}

			break;

		// bottom insert locations
		case 10:

			for (int row = 0; row < 6; row++) {
				TemporaryBoard[1][row] = TemporaryBoard[1][row + 1];
			}
			TemporaryBoard[1][6] = CreatingBoardObject.extraTile;

			for (int i = 0; i < 4; i++) {
				if (players[i].getCurrentY() == 1) {
					players[i].setCurrentX(players[i].getCurrentX() - 1);

					if (players[i].getCurrentX() == -1) {
						players[i].setCurrentX(6);
					}
				}
			}

			if (!GameFrame.bottom[0]) {
				for (int i = 0; i < 4; i++) {
					ratingsForBoard[9][i] -= 10000;
				}
			}

			break;
		case 11:

			for (int row = 0; row < 6; row++) {
				TemporaryBoard[3][row] = TemporaryBoard[3][row + 1];
			}
			TemporaryBoard[3][6] = CreatingBoardObject.extraTile;

			for (int i = 0; i < 4; i++) {
				if (players[i].getCurrentY() == 3) {
					players[i].setCurrentX(players[i].getCurrentX() - 1);

					if (players[i].getCurrentX() == -1) {
						players[i].setCurrentX(6);
					}
				}
			}
			if (!GameFrame.bottom[1]) {
				for (int i = 0; i < 4; i++) {
					ratingsForBoard[10][i] -= 10000;
				}
			}

			break;

		case 12:

			for (int row = 0; row < 6; row++) {
				TemporaryBoard[5][row] = TemporaryBoard[5][row + 1];
			}
			TemporaryBoard[5][6] = CreatingBoardObject.extraTile;

			for (int i = 0; i < 4; i++) {
				if (players[i].getCurrentY() == 5) {
					players[i].setCurrentX(players[i].getCurrentX() - 1);

					if (players[i].getCurrentX() == -1) {
						players[i].setCurrentX(6);
					}
				}
			}
			if (!GameFrame.bottom[2]) {
				for (int i = 0; i < 4; i++) {
					ratingsForBoard[11][i] -= 10000;
				}
			}

			break;

		default:// does nothing

		}

		GameFrame.enableOrDisableButtons(true);
//		switch (insertLocation + 1) {
//
//		// left insert locations
//		case 1:
//
//			for (int col = 6; col > 0; col--) {
//				TemporaryBoard[col][1] = TemporaryBoard[col - 1][1];
//			}
//			TemporaryBoard[0][1] = CreatingBoardObject.extraTile;
//
//			for (int i = 0; i < 4; i++) {
//				if (players[i].getCurrentY() == 1) {
//					players[i].setCurrentX(players[i].getCurrentX() + 1);
//					if (players[i].getCurrentX() == 7) {
//						players[i].setCurrentX(0);
//					}
//				}
//			}
//
//			if (!GameFrame.left[0]) {
//				for (int i = 0; i < 4; i++) {
//					ratingsForBoard[0][i] -= 10000;
//				}
//			}
//
//			break;
//
//		case 2:
//
//			for (int col = 6; col > 0; col--) {
//				TemporaryBoard[col][3] = TemporaryBoard[col - 1][3];
//			}
//			TemporaryBoard[0][3] = CreatingBoardObject.extraTile;
//
//			for (int i = 0; i < 4; i++) {
//				if (players[i].getCurrentY() == 3) {
//					players[i].setCurrentX(players[i].getCurrentX() + 1);
//					if (players[i].getCurrentX() == 7) {
//						players[i].setCurrentX(0);
//					}
//				}
//			}
//
//			if (!GameFrame.left[1]) {
//				for (int i = 0; i < 4; i++) {
//					ratingsForBoard[1][i] -= 10000;
//				}
//			}
//			break;
//		case 3:
//
//			for (int col = 6; col > 0; col--) {
//				TemporaryBoard[col][5] = TemporaryBoard[col - 1][5];
//			}
//			TemporaryBoard[0][5] = CreatingBoardObject.extraTile;
//
//			for (int i = 0; i < 4; i++) {
//				if (players[i].getCurrentY() == 5) {
//					players[i].setCurrentX(players[i].getCurrentX() + 1);
//					if (players[i].getCurrentX() == 7) {
//						players[i].setCurrentX(0);
//					}
//				}
//			}
//
//			if (!GameFrame.left[2]) {
//				for (int i = 0; i < 4; i++) {
//					ratingsForBoard[2][i] -= 10000;
//				}
//			}
//
//			break;
//
//		// right insert locations
//		case 4:
//
//			for (int col = 0; col < 6; col++) {
//				TemporaryBoard[col][1] = TemporaryBoard[col + 1][1];
//			}
//			TemporaryBoard[6][1] = CreatingBoardObject.extraTile;
//
//			for (int i = 0; i < 4; i++) {
//				if (players[i].getCurrentY() == 1) {
//					players[i].setCurrentX(players[i].getCurrentX() - 1);
//
//					if (players[i].getCurrentX() == -1) {
//						players[i].setCurrentX(6);
//					}
//				}
//			}
//
//			if (!GameFrame.right[0]) {
//				for (int i = 0; i < 4; i++) {
//					ratingsForBoard[3][i] -= 10000;
//				}
//			}
//
//			break;
//		case 5:
//
//			for (int col = 0; col < 6; col++) {
//				TemporaryBoard[col][3] = TemporaryBoard[col + 1][3];
//			}
//			TemporaryBoard[6][3] = CreatingBoardObject.extraTile;
//
//			for (int i = 0; i < 4; i++) {
//				if (players[i].getCurrentY() == 3) {
//					players[i].setCurrentX(players[i].getCurrentX() - 1);
//
//					if (players[i].getCurrentX() == -1) {
//						players[i].setCurrentX(6);
//					}
//				}
//			}
//
//			if (!GameFrame.right[1]) {
//				for (int i = 0; i < 4; i++) {
//					ratingsForBoard[4][i] -= 10000;
//				}
//			}
//			break;
//
//		case 6:
//
//			for (int col = 0; col < 6; col++) {
//				TemporaryBoard[col][5] = TemporaryBoard[col + 1][5];
//			}
//			TemporaryBoard[6][5] = CreatingBoardObject.extraTile;
//
//			for (int i = 0; i < 4; i++) {
//				if (players[i].getCurrentY() == 5) {
//					players[i].setCurrentX(players[i].getCurrentX() - 1);
//
//					if (players[i].getCurrentX() == -1) {
//						players[i].setCurrentX(6);
//					}
//				}
//			}
//			if (!GameFrame.right[2]) {
//				for (int i = 0; i < 4; i++) {
//					ratingsForBoard[5][i] -= 10000;
//				}
//			}
//
//			break;
//
//		// top insert locations
//		case 7:
//
//			for (int row = 6; row > 0; row--) {
//
//				TemporaryBoard[1][row] = TemporaryBoard[1][row - 1];
//
//			}
//			TemporaryBoard[1][0] = CreatingBoardObject.extraTile;
//
//			for (int i = 0; i < 4; i++) {
//				if (players[i].getCurrentX() == 1) {
//					players[i].setCurrentY(players[i].getCurrentY() + 1);
//
//					if (players[i].getCurrentY() == 7) {
//						players[i].setCurrentY(0);
//					}
//
//				}
//			}
//			if (!GameFrame.top[0]) {
//				for (int i = 0; i < 4; i++) {
//					ratingsForBoard[6][i] -= 10000;
//				}
//			}
//
//			break;
//
//		case 8:
//
//			for (int row = 6; row > 0; row--) {
//				TemporaryBoard[3][row] = TemporaryBoard[3][row - 1];
//			}
//			TemporaryBoard[3][0] = CreatingBoardObject.extraTile;
//
//			for (int i = 0; i < 4; i++) {
//				if (players[i].getCurrentX() == 3) {
//					players[i].setCurrentY(players[i].getCurrentY() + 1);
//
//					if (players[i].getCurrentY() == 7) {
//						players[i].setCurrentY(0);
//					}
//
//				}
//			}
//			if (!GameFrame.top[1]) {
//				for (int i = 0; i < 4; i++) {
//					ratingsForBoard[7][i] -= 10000;
//				}
//			}
//
//			break;
//		case 9:
//
//			for (int row = 6; row > 0; row--) {
//				TemporaryBoard[5][row] = TemporaryBoard[5][row - 1];
//			}
//			TemporaryBoard[5][0] = CreatingBoardObject.extraTile;
//
//			for (int i = 0; i < 4; i++) {
//				if (players[i].getCurrentX() == 5) {
//					players[i].setCurrentY(players[i].getCurrentY() + 1);
//
//					if (players[i].getCurrentY() == 7) {
//						players[i].setCurrentY(0);
//					}
//				}
//			}
//
//			if (!GameFrame.top[2]) {
//				for (int i = 0; i < 4; i++) {
//					ratingsForBoard[8][i] -= 10000;
//				}
//			}
//			break;
//
//		// bottom insert locations
//		case 10:
//
//			for (int row = 0; row < 6; row++) {
//				TemporaryBoard[1][row] = TemporaryBoard[1][row + 1];
//			}
//			TemporaryBoard[1][6] = CreatingBoardObject.extraTile;
//
//			for (int i = 0; i < 4; i++) {
//				if (players[i].getCurrentX() == 1) {
//					players[i].setCurrentY(players[i].getCurrentY() - 1);
//
//					if (players[i].getCurrentY() == -1) {
//						players[i].setCurrentY(6);
//					}
//				}
//			}
//
//			if (!GameFrame.bottom[0]) {
//				for (int i = 0; i < 4; i++) {
//					ratingsForBoard[9][i] -= 10000;
//				}
//			}
//
//			break;
//		case 11:
//
//			for (int row = 0; row < 6; row++) {
//				TemporaryBoard[3][row] = TemporaryBoard[3][row + 1];
//			}
//			TemporaryBoard[3][6] = CreatingBoardObject.extraTile;
//
//			for (int i = 0; i < 4; i++) {
//				if (players[i].getCurrentX() == 3) {
//					players[i].setCurrentY(players[i].getCurrentY() - 1);
//
//					if (players[i].getCurrentY() == -1) {
//						players[i].setCurrentY(6);
//					}
//				}
//			}
//			if (!GameFrame.bottom[1]) {
//				for (int i = 0; i < 4; i++) {
//					ratingsForBoard[10][i] -= 10000;
//				}
//			}
//			break;
//	
//		case 12:
//
//			for (int row = 0; row < 6; row++) {
//				TemporaryBoard[5][row] = TemporaryBoard[5][row + 1];
//			}
//			TemporaryBoard[5][6] = CreatingBoardObject.extraTile;
//
//			for (int i = 0; i < 4; i++) {
//				if (players[i].getCurrentX() == 5) {
//					players[i].setCurrentY(players[i].getCurrentY() - 1);
//
//					if (players[i].getCurrentY() == -1) {
//						players[i].setCurrentY(6);
//					}
//				}
//			}
//			if (!GameFrame.bottom[2]) {
//				for (int i = 0; i < 4; i++) {
//					ratingsForBoard[11][i] -= 10000;
//				}
//			}
//			break;
//
//		default:// does nothing
//	}
		System.out.println("the board has been changed");
	}

}

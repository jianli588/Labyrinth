package Labyrinth;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;

public abstract class CreatingBoardObject {

	static TileObject[][] Board = new TileObject[7][7];

	// put all the image and direction movable of tiles
	private static TileObject[][] board_items = new TileObject[12][4];

	// insert the tiles without pattern
	private static TileObject[] L_road = new TileObject[4];
	private static TileObject[] I_road = new TileObject[4];

	private static TileObject temporary;
	public static TileObject extraTile;

	public static void randomizeBoard() {

		// the tiles of type "T"
		board_items[0][0] = new TileObject(false, true, true, true, "Board Tile/Bat0.png", "9", 1);
		board_items[0][1] = new TileObject(true, false, true, true, "Board Tile/Bat1.png", "9", 2);
		board_items[0][2] = new TileObject(true, true, false, true, "Board Tile/Bat2.png", "9", 3);
		board_items[0][3] = new TileObject(true, true, true, false, "Board Tile/Bat3.png", "9", 4);

		board_items[1][0] = new TileObject(false, true, true, true, "Board Tile/Dragon0.png", "21", 11);
		board_items[1][1] = new TileObject(true, false, true, true, "Board Tile/Dragon1.png", "21", 12);
		board_items[1][2] = new TileObject(true, true, false, true, "Board Tile/Dragon2.png", "21", 13);
		board_items[1][3] = new TileObject(true, true, true, false, "Board Tile/Dragon3.png", "21", 14);

		board_items[2][0] = new TileObject(false, true, true, true, "Board Tile/GhostBottle0.png", "13", 21);
		board_items[2][1] = new TileObject(true, false, true, true, "Board Tile/GhostBottle1.png", "13", 22);
		board_items[2][2] = new TileObject(true, true, false, true, "Board Tile/GhostBottle2.png", "13", 23);
		board_items[2][3] = new TileObject(true, true, true, false, "Board Tile/GhostBottle3.png", "13", 24);

		board_items[3][0] = new TileObject(false, true, true, true, "Board Tile/GhostWaving0.png", "14", 31);
		board_items[3][1] = new TileObject(true, false, true, true, "Board Tile/GhostWaving1.png", "14", 32);
		board_items[3][2] = new TileObject(true, true, false, true, "Board Tile/GhostWaving2.png", "14", 33);
		board_items[3][3] = new TileObject(true, true, true, false, "Board Tile/GhostWaving3.png", "14", 34);

		board_items[4][0] = new TileObject(false, true, true, true, "Board Tile/LadyPig0.png", "1", 41);
		board_items[4][1] = new TileObject(true, false, true, true, "Board Tile/LadyPig1.png", "1", 41);
		board_items[4][2] = new TileObject(true, true, false, true, "Board Tile/LadyPig2.png", "1", 43);
		board_items[4][3] = new TileObject(true, true, true, false, "Board Tile/LadyPig3.png", "1", 44);

		board_items[5][0] = new TileObject(false, true, true, true, "Board Tile/Sorceress0.png", "11", 51);
		board_items[5][1] = new TileObject(true, false, true, true, "Board Tile/Sorceress1.png", "11", 52);
		board_items[5][2] = new TileObject(true, true, false, true, "Board Tile/Sorceress2.png", "11", 53);
		board_items[5][3] = new TileObject(true, true, true, false, "Board Tile/Sorceress3.png", "11", 54);

		// -------------------------------------------------------------------------
		// the tiles of type "L"
		board_items[6][0] = new TileObject(true, true, false, false, "Board Tile/Moth0.png", "17", 61);
		board_items[6][1] = new TileObject(false, true, true, false, "Board Tile/Moth1.png", "17", 62);
		board_items[6][2] = new TileObject(false, false, true, true, "Board Tile/Moth2.png", "17", 63);
		board_items[6][3] = new TileObject(true, false, false, true, "Board Tile/Moth3.png", "17", 64);

		board_items[7][0] = new TileObject(true, true, false, false, "Board Tile/Owl0.png", "23", 71);
		board_items[7][1] = new TileObject(false, true, true, false, "Board Tile/Owl1.png", "23", 72);
		board_items[7][2] = new TileObject(false, false, true, true, "Board Tile/Owl2.png", "23", 73);
		board_items[7][3] = new TileObject(true, false, false, true, "Board Tile/Owl3.png", "23", 74);

		board_items[8][0] = new TileObject(true, true, false, false, "Board Tile/Rat0.png", "8", 81);
		board_items[8][1] = new TileObject(false, true, true, false, "Board Tile/Rat1.png", "8", 82);
		board_items[8][2] = new TileObject(false, false, true, true, "Board Tile/Rat2.png", "8", 83);
		board_items[8][3] = new TileObject(true, false, false, true, "Board Tile/Rat3.png", "8", 84);

		board_items[9][0] = new TileObject(true, true, false, false, "Board Tile/Scarab0.png", "2", 91);
		board_items[9][1] = new TileObject(false, true, true, false, "Board Tile/Scarab1.png", "2", 92);
		board_items[9][2] = new TileObject(false, false, true, true, "Board Tile/Scarab2.png", "2", 93);
		board_items[9][3] = new TileObject(true, false, false, true, "Board Tile/Scarab3.png", "2", 94);

		board_items[10][0] = new TileObject(true, true, false, false, "Board Tile/Lizard0.png", "22", 101);
		board_items[10][1] = new TileObject(false, true, true, false, "Board Tile/Lizard1.png", "22", 102);
		board_items[10][2] = new TileObject(false, false, true, true, "Board Tile/Lizard2.png", "22", 103);
		board_items[10][3] = new TileObject(true, false, false, true, "Board Tile/Lizard3.png", "22", 104);

		board_items[11][0] = new TileObject(true, true, false, false, "Board Tile/Spider0.png", "10", 111);
		board_items[11][1] = new TileObject(false, true, true, false, "Board Tile/Spider1.png", "10", 112);
		board_items[11][2] = new TileObject(false, false, true, true, "Board Tile/Spider2.png", "10", 113);
		board_items[11][3] = new TileObject(true, false, false, true, "Board Tile/Spider3.png", "10", 114);

		// the road tile in type "L"
		L_road[0] = new TileObject(true, true, false, false, "Board Tile/L0.png", "L", 121);
		L_road[1] = new TileObject(false, true, true, false, "Board Tile/L1.png", "L", 122);
		L_road[2] = new TileObject(false, false, true, true, "Board Tile/L2.png", "L", 123);
		L_road[3] = new TileObject(true, false, false, true, "Board Tile/L3.png", "L", 124);

		// the road tile in type "I"
		I_road[0] = new TileObject(true, false, true, false, "Board Tile/I0.png", "I", 131);
		I_road[1] = new TileObject(false, true, false, true, "Board Tile/I1.png", "I", 132);
		I_road[2] = new TileObject(true, false, true, false, "Board Tile/I2.png", "I", 133);
		I_road[3] = new TileObject(false, true, false, true, "Board Tile/I3.png", "I", 134);

		//
		//
		ArrayList<TileObject> MovableTiles = new ArrayList<TileObject>();

		// randomly choose tiles with items on it in type "T” and “L”
		for (int i = 0; i < 12; i++) {
			int x = (int) (Math.random() * 4);
			MovableTiles.add(board_items[i][x]);
		}
		// randomly choose tiles L's direction 9 times
		for (int i = 0; i < 9; i++) {
			int x = (int) (Math.random() * 4);
			MovableTiles.add(L_road[x]);
		}

		// randomly choose tiles I's direction 12 times
		for (int i = 0; i < 13; i++) {
			int x = (int) (Math.random() * 4);
			MovableTiles.add(I_road[x]);
		}

		// mix the tiles which are already selected from 4 directions
		Collections.shuffle(MovableTiles);

		// ================================================================================================
		// put all the unmovable tiles in their location on the board

		getBoard()[0][0] = new TileObject(true, true, true, true, "Board tile/L1.png", "L");
		getBoard()[0][2] = new TileObject(false, true, true, true, "Board Tile/Book0.jpg", "19");
		getBoard()[0][4] = new TileObject(false, true, true, true, "Board Tile/Coin0.jpg", "7");
		getBoard()[0][6] = new TileObject(true, true, true, true, "Board tile/L0.png", "L");
		getBoard()[2][0] = new TileObject(true, true, true, false, "Board Tile/Map3.jpg", "12");
		getBoard()[2][2] = new TileObject(true, true, true, false, "Board Tile/Crown3.jpg", "20");
		getBoard()[2][4] = new TileObject(false, true, true, true, "Board Tile/Key0.jpg", "4");
		getBoard()[2][6] = new TileObject(true, false, true, true, "Board Tile/Skull1.jpg", "18");
		getBoard()[4][0] = new TileObject(true, true, true, false, "Board Tile/Ring3.jpg", "24");
		getBoard()[4][2] = new TileObject(true, true, false, true, "Board Tile/Radio2.jpg", "16");
		getBoard()[4][4] = new TileObject(true, false, true, true, "Board Tile/Emerald1.jpg", "3");
		getBoard()[4][6] = new TileObject(true, false, true, true, "Board Tile/Sword1.jpg", "6");
		getBoard()[6][0] = new TileObject(true, true, true, true, "Board Tile/L2.png", "L");
		getBoard()[6][2] = new TileObject(true, true, false, true, "Board Tile/Candlestick2.jpg", "15");
		getBoard()[6][4] = new TileObject(true, true, false, true, "Board Tile/Aglnet2.jpg", "5");
		getBoard()[6][6] = new TileObject(true, true, true, true, "Board Tile/L3.png", "L");

		// put the other movable into the board

		int index = 0;

		for (int row = 0; row < 7; row++) {
			for (int col = 0; col < 7; col++) {
				if (row == 1 || row == 3 || row == 5 || col == 1 || col == 3 || col == 5) {
					getBoard()[row][col] = MovableTiles.get(index);

					index++;
				} // insert the tile
				System.out.print("|" + getBoard()[row][col].getImageIdentification());
			}
			System.out.println();
		}

		setExtraTile(MovableTiles.get(index));
	}

	public static boolean isUp(int x, int y) {

		return getBoard()[x][y].isUp();

	}

	public static boolean isDown(int x, int y) {

		return getBoard()[x][y].isDown();

	}

	public static boolean isLeft(int x, int y) {

		return getBoard()[x][y].isLeft();

	}

	public static boolean isRight(int x, int y) {

		return getBoard()[x][y].isRight();

	}

	public static ImageIcon tileImage(int i, int k) {
		return getBoard()[i][k].getImage();
	}

	public static void changeBoard(int insertLocation) {

		switch (insertLocation) {
		case 1:

			temporary = getBoard()[6][1];
			for (int col = 6; col > 0; col--) {
				getBoard()[col][1] = getBoard()[col - 1][1];
			}
			getBoard()[0][1] = extraTile;
			setExtraTile(temporary);
			break;

		case 2:

			temporary = getBoard()[6][3];
			for (int col = 6; col > 0; col--) {
				getBoard()[col][3] = getBoard()[col - 1][3];
			}
			getBoard()[0][3] = extraTile;
			setExtraTile(temporary);
			break;
		case 3:

			temporary = getBoard()[6][5];
			for (int col = 6; col > 0; col--) {
				getBoard()[col][5] = getBoard()[col - 1][5];
			}
			getBoard()[0][5] = extraTile;
			setExtraTile(temporary);
			break;
		case 4:

			temporary = getBoard()[0][1];
			for (int col = 0; col < 6; col++) {
				getBoard()[col][1] = getBoard()[col + 1][1];
			}
			getBoard()[6][1] = extraTile;
			setExtraTile(temporary);
			break;
		case 5:

			temporary = getBoard()[0][3];
			for (int col = 0; col < 6; col++) {
				getBoard()[col][3] = getBoard()[col + 1][3];
			}
			getBoard()[6][3] = extraTile;
			setExtraTile(temporary);
			break;

		case 6:

			temporary = getBoard()[0][5];
			for (int col = 0; col < 6; col++) {
				getBoard()[col][5] = getBoard()[col + 1][5];
			}
			getBoard()[6][5] = extraTile;
			setExtraTile(temporary);
			break;

		case 7:
			temporary = getBoard()[1][6];
			for (int row = 6; row > 0; row--) {

				getBoard()[1][row] = getBoard()[1][row - 1];

			}
			getBoard()[1][0] = extraTile;
			setExtraTile(temporary);
			break;

		case 8:

			temporary = getBoard()[3][6];
			for (int row = 6; row > 0; row--) {
				getBoard()[3][row] = getBoard()[3][row - 1];
			}
			getBoard()[3][0] = extraTile;
			setExtraTile(temporary);
			break;
		case 9:

			temporary = getBoard()[5][6];
			for (int row = 6; row > 0; row--) {
				getBoard()[5][row] = getBoard()[5][row - 1];
			}
			getBoard()[5][0] = extraTile;
			setExtraTile(temporary);
			break;
		case 10:

			temporary = getBoard()[1][0];
			for (int row = 0; row < 6; row++) {
				getBoard()[1][row] = getBoard()[1][row + 1];
			}
			getBoard()[1][6] = extraTile;
			setExtraTile(temporary);
			break;
		case 11:

			temporary = getBoard()[3][0];
			for (int row = 0; row < 6; row++) {
				getBoard()[3][row] = getBoard()[3][row + 1];
			}
			getBoard()[3][6] = extraTile;
			setExtraTile(temporary);
			break;

		case 12:

			temporary = getBoard()[5][0];
			for (int row = 0; row < 6; row++) {
				getBoard()[5][row] = getBoard()[5][row + 1];
			}
			getBoard()[5][6] = extraTile;
			setExtraTile(temporary);
			break;

		default:// does nothing
		}
	}

	public static String getExtraTile() {
		return extraTile.getFileLocation();
	}

	public static void setExtraTile(TileObject extraTile) {
		CreatingBoardObject.extraTile = extraTile;
	}

	public static void rotateRight() {
		// TODO Auto-generated method stub

		int temp = extraTile.getImageIdentification() + 1;
		if (temp % 10 == 5) {
			temp -= 4;
		}
		for (int i = 0; i < 4; i++) {
			for (int k = 0; k < 12; k++) {
				if (temp == board_items[k][i].getImageIdentification()) {
					extraTile = board_items[k][i];
					return;
				}
			}
			if (temp == L_road[i].getImageIdentification()) {
				extraTile = L_road[i];
				return;
			} else if (temp == I_road[i].getImageIdentification()) {
				extraTile = I_road[i];
				return;
			}
		}

	}

	public static void rotateLeft() {

		// TODO Auto-generated method stub
		int temp = extraTile.getImageIdentification() - 1;
		if (temp % 10 == 0) {
			temp += 4;
		}

		for (int i = 0; i < 4; i++) {
			for (int k = 0; k < 12; k++) {
				if (temp == board_items[k][i].getImageIdentification()) {
					extraTile = board_items[k][i];
					return;
				}
			}
			if (temp == L_road[i].getImageIdentification()) {
				extraTile = L_road[i];
				return;
			} else if (temp == I_road[i].getImageIdentification()) {
				extraTile = I_road[i];
				return;
			}

		}

	}

	

	public static TileObject[][] getBoard() { 
		return Board;
	}
	


	public static void setBoard(TileObject[][] board) {
		Board = board;
	}
}
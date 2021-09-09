package Labyrinth;
import java.util.*;
import javax.swing.*;

/*
 * 0: north
 * 1: east
 * 2: south
 * 3: west
 */

public class Tile {

	// create a list of the tiles after randomly choose the direction
	static ArrayList<ImageIcon> tileList = new ArrayList<ImageIcon>();

	// set the number of tiled of road we need, 12 I and 9 L
	int n_I = 12;
	int n_L = 9;

	// create a image list of tiles with items on it
	static ImageIcon[][] T_item = new ImageIcon[6][4];
	{

		// add images
		T_item[0][0] = new ImageIcon("Image/Bat0");
		T_item[0][1] = new ImageIcon("Image/Bat1");
		T_item[0][2] = new ImageIcon("Image/Bat2");
		T_item[0][3] = new ImageIcon("Image/Bat3");

		T_item[1][0] = new ImageIcon("Image/Dragon0");
		T_item[1][1] = new ImageIcon("Image/Dragon1");
		T_item[1][2] = new ImageIcon("Image/Dragon2");
		T_item[1][3] = new ImageIcon("Image/Dragon3");

		T_item[2][0] = new ImageIcon("Image/GhostBottle0");
		T_item[2][1] = new ImageIcon("Image/GhostBottle1");
		T_item[2][2] = new ImageIcon("Image/GhostBottle2");
		T_item[2][3] = new ImageIcon("Image/GhostBottle3");

		T_item[3][0] = new ImageIcon("Image/GhostWaving0");
		T_item[3][1] = new ImageIcon("Image/GhostWaving1");
		T_item[3][2] = new ImageIcon("Image/GhostWaving2");
		T_item[3][3] = new ImageIcon("Image/GhostWaving3");

		T_item[4][0] = new ImageIcon("Image/LadyPig0");
		T_item[4][1] = new ImageIcon("Image/LadyPig1");
		T_item[4][2] = new ImageIcon("Image/LadyPig2");
		T_item[4][3] = new ImageIcon("Image/LadyPig3");

		T_item[5][0] = new ImageIcon("Image/Sorceress0");
		T_item[5][1] = new ImageIcon("Image/Sorceress1");
		T_item[5][2] = new ImageIcon("Image/Sorceress2");
		T_item[5][3] = new ImageIcon("Image/Sorceress3");
	}

	static ImageIcon[][] L_item = new ImageIcon[6][4];
	{
		L_item[0][0] = new ImageIcon("Image/Moth0");
		L_item[0][1] = new ImageIcon("Image/Moth1");
		L_item[0][2] = new ImageIcon("Image/Moth2");
		L_item[0][3] = new ImageIcon("Image/Moth3");

		L_item[1][0] = new ImageIcon("Image/Owl0");
		L_item[1][1] = new ImageIcon("Image/Owl1");
		L_item[1][2] = new ImageIcon("Image/Owl2");
		L_item[1][3] = new ImageIcon("Image/Owl3");

		L_item[2][0] = new ImageIcon("Image/Rat0");
		L_item[2][1] = new ImageIcon("Image/Rat1");
		L_item[2][2] = new ImageIcon("Image/Rat2");
		L_item[2][3] = new ImageIcon("Image/Rat3");

		L_item[3][0] = new ImageIcon("Image/Scarab0");
		L_item[3][1] = new ImageIcon("Image/Scarab1");
		L_item[3][2] = new ImageIcon("Image/Scarab2");
		L_item[3][3] = new ImageIcon("Image/Scarab3");

		L_item[4][0] = new ImageIcon("Image/Lizard0");
		L_item[4][1] = new ImageIcon("Image/Lizard1");
		L_item[4][2] = new ImageIcon("Image/Lizard2");
		L_item[4][3] = new ImageIcon("Image/Lizard3");

		L_item[5][0] = new ImageIcon("Image/Spider0");
		L_item[5][1] = new ImageIcon("Image/Spider1");
		L_item[5][2] = new ImageIcon("Image/Spider2");
		L_item[5][3] = new ImageIcon("Image/Spider3");
	}

	// create a image list of tiles of road
	ImageIcon[][] I_road = new ImageIcon[1][4];
	{ // add images
		I_road[0][0] = new ImageIcon("Image/I0");
		I_road[0][1] = new ImageIcon("Image/I1");
		I_road[0][2] = new ImageIcon("Image/I2");
		I_road[0][3] = new ImageIcon("Image/I3");
	}

	ImageIcon[][] L_road = new ImageIcon[1][4];
	{
		L_road[1][0] = new ImageIcon("Image/L0");
		L_road[1][1] = new ImageIcon("Image/L1");
		L_road[1][2] = new ImageIcon("Image/L2");
		L_road[1][3] = new ImageIcon("Image/L3");
	}

	// randomly select the tiles' direction
	public void selecttDirection() {

		// randomly choose tiles with items on it in type "T"
		for (int i = 0; i < 6; i++) {
			int x = (int) (Math.random() * 4);
			tileList.add(T_item[i][x]);
		}

		// randomly choose tiles with items on it in type "L"
		for (int i = 0; i < 6; i++) {
			int x = (int) (Math.random() * 4);
			tileList.add(L_item[i][x]);
		}

		// randomly choose tiles I's direction 12 times
		for (int i = 0; i < 12; i++) {
			int x = (int) (Math.random() * 4);
			tileList.add(I_road[0][x]);
		}

		// randomly choose tiles L's direction 9 times
		for (int i = 0; i < 9; i++) {
			int x = (int) (Math.random() * 4);
			tileList.add(L_road[1][x]);
		}
	}

	public void setMoveable() {

		// T*4 + I*4 + L*4 ( three types, four directions)
		Boolean[][] moveable_t = new Boolean[12][4];

		// road with type "T"
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 4; j++) {
				if (j == 0) {
					moveable_t[i][0] = false;
					moveable_t[i][1] = true;
					moveable_t[i][2] = true;
					moveable_t[i][3] = true;
				}

				else if (j == 1) {
					moveable_t[i][0] = true;
					moveable_t[i][1] = false;
					moveable_t[i][2] = true;
					moveable_t[i][3] = true;
				}

				else if (j == 2) {
					moveable_t[i][0] = true;
					moveable_t[i][1] = true;
					moveable_t[i][2] = false;
					moveable_t[i][3] = true;
				}

				else if (j == 3) {
					moveable_t[i][0] = true;
					moveable_t[i][1] = true;
					moveable_t[i][2] = true;
					moveable_t[i][3] = false;
				}
			}
		}

		// road with type "L"
		for (int i = 6; i < 12; i++) {
			for (int j = 0; j < 4; j++) {
				if (j == 0) {
					moveable_t[i][0] = true;
					moveable_t[i][1] = true;
					moveable_t[i][2] = false;
					moveable_t[i][3] = false;
				}

				else if (j == 1) {
					moveable_t[i][0] = false;
					moveable_t[i][1] = true;
					moveable_t[i][2] = true;
					moveable_t[i][3] = false;
				}

				else if (j == 2) {
					moveable_t[i][0] = false;
					moveable_t[i][1] = false;
					moveable_t[i][2] = true;
					moveable_t[i][3] = true;
				}

				else if (j == 3) {
					moveable_t[i][0] = true;
					moveable_t[i][1] = false;
					moveable_t[i][2] = false;
					moveable_t[i][3] = true;
				}
			}
		}
	}
}
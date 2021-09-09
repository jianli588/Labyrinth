package Labyrinth;
import java.util.ArrayList;

import javax.swing.ImageIcon;

/*
 * Author: Eva Chen
 *
 */

public class TileObject {// class for creating objects that define the tiles from each class

	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	private String num;
	private String fileLocation;
	private int ImageIdentification;	
	private ImageIcon image;
	
	// constructor
	public TileObject(boolean up, boolean right, boolean down, boolean left, String fileLocation, String num, int ImageIdentification) {// constructor for movable pieces
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
		this.fileLocation = fileLocation;
		image = new ImageIcon(new ImageIcon(fileLocation).getImage().getScaledInstance(75, 75, 0));
		this.num = num;
		this.ImageIdentification = ImageIdentification;
	}
	
	public TileObject(boolean up, boolean right, boolean down, boolean left, String fileLocation, String num) {// constructor for unmovable pieces
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
		image = new ImageIcon(new ImageIcon(fileLocation).getImage().getScaledInstance(75, 75, 0));
		this.num = num;
	}


	// setters and getters
	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}
	
	public ImageIcon getImage() {
		return image;
	}

	public void setImage(String fileLocation, int dimension) {
		this.image = new ImageIcon(new ImageIcon(fileLocation).getImage().getScaledInstance(dimension, dimension, 0));
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public int getImageIdentification() {
		return ImageIdentification;
	}

	public void setImageIdentification(int imageIdentification) {
		this.ImageIdentification = imageIdentification;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

}

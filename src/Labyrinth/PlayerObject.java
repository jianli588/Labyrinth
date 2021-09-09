package Labyrinth;
import javax.swing.ImageIcon;

public class PlayerObject {

	private String fileLocation;

	private int currentX;
	private int currentY;
	private int player;

	
	public PlayerObject(int player , int x, int y) {
		setPlayer(player);
		currentX = x;
		currentY = y;
	}
	
	
	public PlayerObject(String fileLocation, int player, int x, int y) {

		this.fileLocation = fileLocation;
		setPlayer(player);
		currentX = x;
		currentY = y;

	}

	public int getCurrentX() {
		return currentX;
	}

	public void setCurrentX(int currentX) {
		this.currentX = currentX;
	}

	public int getCurrentY() {
		return currentY;
	}

	public void setCurrentY(int currentY) {
		this.currentY = currentY;
	}

	public void setPlayer(int player) {

		if (player >= 0 && player <= 3) {
			this.player = player;
		} else
			System.out.println("Specify a valid player number");
	}

	public int getPlayer() {
		return player;
	}

	public String getfileLocation() {
		return fileLocation;
	}
}
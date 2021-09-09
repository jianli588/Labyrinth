package Labyrinth;
public class PlayerLocation {

	private static int x;
	private static int y;
	
	
	//Getters and Setters
	public static int getX() {
		return x;
	}

	public void setX(int x) {
		PlayerLocation.x = x;
	}

	public static int getY() {
		return y;
	}

	public void setY(int y) {
		PlayerLocation.y = y;
	}

	//Construct
	public PlayerLocation(int row, int col) {
		
		super();
		PlayerLocation.x = row;
		PlayerLocation.y = col;
	}
}
	
	


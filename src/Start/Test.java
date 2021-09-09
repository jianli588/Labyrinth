package Labyrinth;


public class Test {

	 static Thread t1 = new Thread(new GameFrame());

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		AI.initializePlayer();
		t1.start();


		
	}
}
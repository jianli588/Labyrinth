package Labyrinth;

import java.util.*;

import javax.swing.ImageIcon;

public class Card {

	public static ImageIcon[][] hands = new ImageIcon[4][5];	
	public static CardObject[][] temp1 = new CardObject[4][5];

	public static ImageIcon getHand(int i, int k) {
		return hands[i][k];
	}

	// Creates all the Card Objects so that the BoardClass knows each card's values
	public static void shuffle() {
		 CardObject[] cardBoard = new CardObject[24];


		cardBoard[0] = new CardObject("Card Images/1.PNG", "1");

		cardBoard[1] = new CardObject("Card Images/2.PNG", "2");

		cardBoard[2] = new CardObject("Card Images/3.PNG", "3");

		cardBoard[3] = new CardObject("Card Images/4.PNG", "4");

		cardBoard[4] = new CardObject("Card Images/5.PNG", "5");

		cardBoard[5] = new CardObject("Card Images/6.PNG", "6");

		cardBoard[6] = new CardObject("Card Images/7.PNG", "7");

		cardBoard[7] = new CardObject("Card Images/8.PNG", "8");

		cardBoard[8] = new CardObject("Card Images/9.PNG", "9");

		cardBoard[9] = new CardObject("Card Images/10.PNG", "10");

		cardBoard[10] = new CardObject("Card Images/11.PNG", "11");

		cardBoard[11] = new CardObject("Card Images/12.PNG", "12");

		cardBoard[12] = new CardObject("Card Images/13.PNG", "13");

		cardBoard[13] = new CardObject("Card Images/14.PNG", "14");

		cardBoard[14] = new CardObject("Card Images/15.PNG", "15");

		cardBoard[15] = new CardObject("Card Images/16.PNG", "16");

		cardBoard[16] = new CardObject("Card Images/17.PNG", "17");

		cardBoard[17] = new CardObject("Card Images/18.PNG", "18");

		cardBoard[18] = new CardObject("Card Images/19.PNG", "19");

		cardBoard[19] = new CardObject("Card Images/20.PNG", "20");

		cardBoard[20] = new CardObject("Card Images/21.PNG", "21");

		cardBoard[21] = new CardObject("Card Images/22.PNG", "22");

		cardBoard[22] = new CardObject("Card Images/23.PNG", "23");

		cardBoard[23] = new CardObject("Card Images/24.PNG", "24");


		ArrayList<CardObject> ArtifactCard = new ArrayList<CardObject>(Arrays.asList(cardBoard));

		
		Collections.shuffle(ArtifactCard);

		int temp = 0;

		for (int handNum = 0; handNum < 4; handNum++) {
			for (int imageNum = 0; imageNum < 5; imageNum++) {
				hands[handNum][imageNum] = new ImageIcon(
						new ImageIcon(ArtifactCard.get(temp).getFileLocation()).getImage().getScaledInstance(100, 100, 0));
				temp++;
				temp1[handNum][imageNum] = new CardObject(ArtifactCard.get((imageNum) + handNum*5).getFileLocation(),ArtifactCard.get(handNum*5 + (imageNum)).getNum());
			}
		}
		
	}
}
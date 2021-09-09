package Labyrinth;
public class CardObject {
	
	//Allows other classes to access the fileLocation of any card
	private String fileLocation;

	//Allows other classes to access the Artifact# of any card
	private String num;

	//Constructs the Card Objects
	public CardObject(String fileLocation, String num) {
		this.fileLocation = fileLocation;
		this.num = num;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	} 

}
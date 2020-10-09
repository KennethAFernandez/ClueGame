package clueGame;

// Holds infomation about rooms
@SuppressWarnings("unused")
public class Room {

	
	private String name;
	BoardCell centerCell;
	BoardCell labelCell;
	
	
	public Room() {
	}
	
	public BoardCell getLabelCell() {
		return labelCell;
	}

	public String getName() {
		return name;
	}

	public BoardCell getCenterCell() {
		return centerCell;
	}

}

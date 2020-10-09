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

	public BoardCell getName() {
		return labelCell;
	}

	public BoardCell getCenterCell() {
		return centerCell;
	}

}

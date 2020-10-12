package clueGame;

// Holds infomation about rooms
@SuppressWarnings("unused")
public class Room {

	
	private String name;
	private int row, col;
	BoardCell centerCell;
	BoardCell labelCell;
	
	public Room() {}
	
	public Room(String name) {
		this.name = name;
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

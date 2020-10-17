package clueGame;

// Holds infomation about rooms
@SuppressWarnings("unused")
public class Room {

	
	private String name;
	private int row, col;
	private boolean label;
	private boolean center;
	BoardCell centerCell;
	BoardCell labelCell;
	
	public Room() {}
	
	public Room(String name) {
		this.name = name;
	}
	// returns the labelcell
	public BoardCell getLabelCell() {
		return labelCell;
	}
	//getter
	public String getName() {
		return name;
	}
	//getter for the center cell
	public BoardCell getCenterCell() {
		return centerCell;
	}
	//sets label cell
	public void setLabel(BoardCell cell) {
		labelCell = cell;
	}
	// sets center cell
	public void setCenter(BoardCell cell) {
		centerCell = cell;
	}
	public void setName(String name) {
		this.name = name;
	}

}

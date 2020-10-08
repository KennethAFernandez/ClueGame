package clueGame;


// Information on a specific cell
public class BoardCell {

	private String name;
	public BoardCell() {
	}

	public Object[] getDoorDirection() {
		return null;
	}

	public boolean isDoorway() {
		return true;
	}

	public boolean isLabel() {
		return false;
	}

	public boolean isRoomCenter() {
		return false;
	}

	public char getSecretPassage() {
		return 0;
	}

	public Object getName() {
		return name;
	}

}

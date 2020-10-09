package clueGame;

import java.util.Set;

// Information on a specific cell
@SuppressWarnings("unused")
public class BoardCell {

	
	private int row, col;
	private char initial;
	DoorDirection doorDirection;
	private boolean roomLabel;
	private boolean roomCenter;
	private char secretPassage;
	Set<BoardCell> adjList;
	
	
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
		return secretPassage;
	}

	public Object getName() {
		return null;
	}
	
	public char getInitial() {
		return initial;
	}
}

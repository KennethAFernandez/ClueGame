package clueGame;

import java.util.HashSet;
import java.util.Set;

// Information on a specific cell
@SuppressWarnings("unused")
public class BoardCell {

	
	private int row, col;
	private char initial;
	private boolean roomLabel;
	private boolean roomCenter;
	private boolean isRoom;
	private boolean isDoorway;
	private boolean isOccupied;
	private boolean walkway;
	private char secretPassage;
	Set<BoardCell> adjList;
	DoorDirection doorDirection;
	Room room;
	
	
	public BoardCell(int row, int col) {
		this.row = row;
		this.col = col;
		adjList = new HashSet<BoardCell>();
	}
	
	
	public void addAdj(BoardCell cell) {
		adjList.add(cell);
	}
	public Set<BoardCell> getAdjList(){
		return adjList;
	}
	public char getInitial() {
		return initial;
	}
	public boolean getOccupied() {
		return isOccupied;
	}
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	public char getSecretPassage() {
		return secretPassage;
	}
	
	
	
	
	public void setOccupied(boolean check) {
		this.isOccupied = check;
	}
	public void setDoorDirection(DoorDirection dd) {
		this.doorDirection = dd;
	}
	public void setRoom(boolean check) {
		this.isRoom = check;
	}
	public void setLabel() {
		this.roomLabel = true;
		
	}
	public void setCenter() {
		this.roomCenter = true;
		
		
	}
	public void setDoor() {
		this.isDoorway = true;
	}
	public void setInitital(char initial) {
		this.initial = initial;
	}
	public void setSecretPassage(char pass) {
		this.secretPassage = pass;
	}
	
	
	
	public boolean isRoom() {
		return isRoom;
	}
	public boolean isOccupied() {
		return isOccupied;
	}
	public boolean isDoorway() {
		return isDoorway;
	}
	public boolean isRoomLabel() {
		return roomLabel;
	}
	public boolean isRoomCenter() {
		return roomCenter;
	}
	public boolean isLabel() {
		return roomLabel;
	}
	public boolean isWalkway() {
		return walkway;
	}

	
	

}
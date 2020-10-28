package clueGame;

import java.util.HashSet;
import java.util.Set;

// Information on a specific cell
@SuppressWarnings("unused")
public class BoardCell {

	// ints to hold row/ col values
	// char to hold cell's initial
	private int row, col;
	private char initial;
	// booleans to help define cell
	private boolean roomLabel;
	private boolean roomCenter;
	private boolean isRoom;
	private boolean isDoorway;
	private boolean isOccupied;
	private boolean isUnused;
	private boolean isPassage;
	private boolean walkway;
	private char secretPassage;
	// set to hold adj list
	// doorDirection tells which way door is facing
	Set<BoardCell> adjList;
	DoorDirection doorDirection;
	
	
	public BoardCell(int row, int col) {
		this.row = row;
		this.col = col;
		adjList = new HashSet<BoardCell>();
	}
	// addsd to cells adjList
	public void addAdj(BoardCell cell) {
		adjList.add(cell);
	}
	//getters for adjlist, initial, if occupied, dd, and secretpassage
	public Set<BoardCell> getAdjList(){
		return adjList;
	}
	public Set<BoardCell> getAdjList(int row, int col){
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
	
	
	
	
	//setters for occupied, dd, label, center, secret passage
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
	public void setWalkway() {
		this.walkway = true;
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
		this.isPassage = true;
	}
	public void setUnused() {
		isUnused = false;
	}

	// returns a boolean to help with formatting and movement
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
	public boolean isPassage() {
		return isPassage;
	}
	public boolean isCellUnused() {
		return isUnused;
	}

	
	

}
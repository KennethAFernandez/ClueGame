// Authors: Kenneth Fernandez and Asher Rubin
package experiment;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unused")
public class TestBoardCell {
	
	// Constants and booleans
	private int row;
	private int col;
	private boolean isRoom, isOccupied;
	
	// Set to store each adjList for each cell
	Set<TestBoardCell> adjList;
	
	public TestBoardCell() {}
	
	
	// Constructor passing row and col
	public TestBoardCell(int row, int col) {
		super();
		this.adjList = new HashSet<TestBoardCell>();
		this.row = row;
		this.col = col;
	}
	
	
	// Returns the adjacency list for the cell
	public Set<TestBoardCell> getAdjList() {
		return adjList;
	}
	
	
	// Setter for indicating a cell is part of a room
	public void setRoom(boolean check) {
		this.isRoom = check;
	}
	
	
	// Indicates if a cell is part of a room
	public boolean isRoom() {
		return isRoom;
	}
	
	
	// Setter for indicating a cell is occupied
	public void setOccupied(boolean check) {
		this.isOccupied = check;
	}
	
	
	// Indicates if cell is occupied by another player
	public boolean getOccupied() {
		return isOccupied;
	}

}

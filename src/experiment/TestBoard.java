// Authors: Kenneth Fernandez and Asher Rubin
package experiment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unused")
public class TestBoard {


	// Map to store adj list, Set to store moves, Set to store already visited spaces
	// Grid which stores board cell in a grid
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	private TestBoardCell[][] grid;
	private boolean hasVisitedStartingCell = false;
	

	// Constants to intialize board size
	final static int COLS = 4;
	final static int ROWS = 4;


	// Constructor that sets up the board
	public TestBoard() {
		grid = new TestBoardCell[ROWS][COLS];
		for(int i = 0; i < ROWS; i++) {
			for(int j=0; j < COLS; j++) {
				grid[i][j] = new TestBoardCell(i,j);
			}
		}

		for(int i=0; i<ROWS; i++) {
			for(int j=0; j<COLS; j++) {
				adjacencies(i, j);
			}
		}
	}


	// Calculates adjacencies 
	public void adjacencies(int row, int col) {
		TestBoardCell cell = getCell(row, col); 
		if(validate(row + 1, col)) {
			cell.addAdj(getCell(row + 1, col));
		}
		if(validate(row - 1, col)) {
			cell.addAdj(getCell(row - 1, col));
		}
		if(validate(row, col + 1)) {
			cell.addAdj(getCell(row, col + 1));
		}
		if(validate(row, col - 1)) {
			cell.addAdj(getCell(row, col - 1));
		}
	}


	// Returns boolean if the given cell coords are valid
	public boolean validate(int row, int col) {
		return row >= 0 && row < ROWS && col >= 0 && col < COLS;
	}


	// Calculates legal targets for a move from startCell to length path
	public void calcTargets(TestBoardCell startCell, int path) {
		targets = new HashSet<TestBoardCell>();
		visited = new HashSet<TestBoardCell>();
		visited.add(startCell);
		findTargets(startCell, path);
	}

	
	public void findTargets(TestBoardCell startCell, int path) {
		if(!hasVisitedStartingCell) {
			visited.add(startCell);
			hasVisitedStartingCell = true;
		}
		for(TestBoardCell cell : startCell.getAdjList()) {
			if(visited.contains(cell)) {
				break;
			} else {
				visited.add(cell);
				if(path == 1) {
					targets.add(cell);
				} else {
					findTargets(cell, path - 1);
				}
				visited.remove(cell);
			}
		}
	}


	// Returns the target set
	public Set<TestBoardCell> getTargets(){ 
		return targets;
	}


	// Returns the cell at the given coords
	public TestBoardCell getCell(int row, int col) {
		return grid[row][col];
	}
}



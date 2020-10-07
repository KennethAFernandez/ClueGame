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
	private Map<TestBoardCell, Set<TestBoardCell>> adjMtx;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	private TestBoardCell[][] grid;


	// Constants to intialize board size
	final static int COLS = 4;
	final static int ROWS = 4;

	
	// Constructor that sets up the board
	public TestBoard() {
		grid = new TestBoardCell[ROWS][COLS];
		adjMtx = new HashMap<TestBoardCell, Set<TestBoardCell>>();
		for(int i = 0; i < ROWS; i++) {
			for(int j=0; j < COLS; j++) {
				grid[i][j] = new TestBoardCell(i,j);
				adjMtx.put(new TestBoardCell(i, j), new HashSet<TestBoardCell>());
			}
		}
		
	}


	// Calculates adjacencies 
	public void adjacencies() {
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++) {
				TestBoardCell cell = grid[i][j];
				if(validate(i + 1, j)) {
					cell = new TestBoardCell(i + 1, j);
					cell.adjList.add(cell);				
				}
				if(validate(i - 1, j)) {
					cell = new TestBoardCell(i - 1, j);
					cell.adjList.add(cell);
				}
				if(validate(i, j + 1)) {
					cell = new TestBoardCell(i, j + 1);
					cell.adjList.add(cell);
				}
				if(validate(i, j - 1)) {
					cell = new TestBoardCell(i, j - 1);
					cell.adjList.add(cell);
				}
			}
		}
	}
	
//		for(int i = 0; i < ROWS; i++) {
//			for (int j = 0; j < COLS; j++) {
//				TestBoardCell cell = board.getCell(i, j);
//				if(validate(i + 1, j)) {
//					adjMtx.get(cell).add(board.getCell(i + 1, j));
//				}
//				if(validate(i - 1, j)) {
//					adjMtx.get(cell).add(board.getCell(i - 1, j));
//				}
//				if(validate(i, j + 1)) {
//					adjMtx.get(cell).add(board.getCell(i, j + 1));
//				}
//				if(validate(i, j - 1)) {
//					adjMtx.get(cell).add(board.getCell(i, j - 1));
//				}
//			}
//		}
//	}




			// Returns boolean if the given cell coords are valid
			public boolean validate(int row, int col) {
				return row >= 0 && row < ROWS && col >= 0 && col < COLS;
			}


			// Calculates legal targets for a move from startCell to length path
			public void calcTargets(TestBoardCell startCell, int path) { 
				targets = new HashSet<TestBoardCell>();
				visited = new HashSet<TestBoardCell>();
				for(TestBoardCell cell : startCell.getAdjList()) {
					if(visited.contains(cell)) {
						break;
					} else {
						visited.add(cell);
						if(path == 1) {
							targets.add(cell);
						} else {
							calcTargets(cell, path - 1);
						}
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

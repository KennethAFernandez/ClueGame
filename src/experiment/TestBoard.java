// Authors: Kenneth Fernandez and Asher Rubin
package experiment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestBoard {
	
	private Map<TestBoardCell, Set<TestBoardCell>> adjMtx;
	private List<TestBoardCell> visited = new ArrayList<TestBoardCell>();
	private int path;

	public TestBoard() {
		adjMtx = new HashMap<TestBoardCell, Set<TestBoardCell>>();
	}

	public void calcTargets(TestBoardCell startCell, int path) { // calculations
		this.path = path; // setting path for later
		for(TestBoardCell cell : startCell.getAdjList()) { // for all the cells in adj list
			for(TestBoardCell visit : visited) { // check to make sure said cell is not in visited
				if(visit == cell) { // if it is
					break; // break from loop
				} else {  // else add it
					visited.add(cell);
					if(path == 1) {
						adjMtx.put(cell, startCell.getAdjList());
					} else {
						calcTargets(cell, path-1); // path more than one recursion with path-1
					}
				}
				adjMtx.remove(cell); // then remove
			}
		}
	}
	
	public boolean getPath() { // gets the path length 
		return path <= 6;
	}

	public Set<TestBoardCell> getTargets(){ 
		Set<TestBoardCell> test = new HashSet<TestBoardCell>(); // test
		return test;
	}

	public TestBoardCell getCell(int row, int col) {
		TestBoardCell test = new TestBoardCell(4, 4); // test
		return test;
	}
}

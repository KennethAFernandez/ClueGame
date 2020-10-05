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

	public void calcTargets(TestBoardCell startCell, int path) {
		this.path = path;
		for(TestBoardCell cell : startCell.getAdjList()) {
			for(TestBoardCell visit : visited) {
				if(visit == cell) {
					break;
				} else {
					visited.add(cell);
					if(path == 1) {
						adjMtx.put(cell, startCell.getAdjList());
					} else {
						calcTargets(cell, path-1);
					}
				}
				adjMtx.remove(cell);
			}
		}
	}
	
	public boolean getPath() {
		return path <= 6;
	}

	public Set<TestBoardCell> getTargets(){ 
		Set<TestBoardCell> test = new HashSet<TestBoardCell>();
		return test;
	}

	public TestBoardCell getCell(int row, int col) {
		TestBoardCell test = new TestBoardCell(4, 4);
		return test;
	}
}

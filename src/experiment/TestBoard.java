package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TestBoard {
	@SuppressWarnings("unused")
	private Map<TestBoardCell, Set<TestBoardCell>> adjMtx;
	
	public TestBoard() {
		adjMtx = new HashMap<TestBoardCell, Set<TestBoardCell>>();
	}
	
	public void calcTargets(TestBoardCell startCell, int path) {
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

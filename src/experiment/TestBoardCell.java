package experiment;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unused")
public class TestBoardCell {
	
	private int row, col;
	
	public TestBoardCell() {}
	public TestBoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}
	
	public Set<TestBoardCell> getAdjList() {	
		Set<TestBoardCell> test = new HashSet<TestBoardCell>();
		return test;
	}
	
	public void setRoom(boolean check) {
	}
	
	public boolean isRoom() {
		return false;
	}
	
	public void setOccupied(boolean check) {
	}
	
	public boolean getOccupied() {
		return false;
	}

}

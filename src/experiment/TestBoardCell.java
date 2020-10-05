package experiment;

import java.util.Map;
import java.util.Set;

@SuppressWarnings("unused")
public class TestBoardCell {
	private int row, column;
	
	public TestBoardCell(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public Set<TestBoardCell> getAdjList() {	
		return null;
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

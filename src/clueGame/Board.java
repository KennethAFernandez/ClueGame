package clueGame;

import java.util.HashMap;
import java.util.Map;

// Holds game board
@SuppressWarnings("unused")
public class Board {

	
	private BoardCell[][] grid;
	private int numRows;
	private int numCols;
	private String layoutConfigFile;
	private String setupConfigFile;
	Map<Character, Room> roomMap = new HashMap<Character, Room>();
	
	 // variable and methods used for singleton pattern
	private static Board theInstance = new Board();
	
	// constructor is private to ensure only one can be created
	private Board() {
		super() ;
	}
	
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	
	// initialize the board (since we are using singleton pattern)
	public void initialize() {
	}
	
	public void setConfigFiles(String string, String string2) {
		
	}
	
	public void loadSetupConfig() {
		
	}
	
	public void loadLayoutConfig() {
		
	}
	
	public Room getRoom(char c) {
		Room room = new Room();
		return room;
	}
	

	public int getNumRows() {
		return 0;
	}
	
	public BoardCell getCell(int i, int j) {
		BoardCell cell = new BoardCell();
		return cell;
	}
	
	public int getNumColumns() {
		return 0;
	}
	
	public Room getRoom(BoardCell cell) {
		return getRoom(cell.getInitial());
	}
	
	public int getAmountRooms() {
		return 0;
	}
	public int getAmountDoors(){
		return 0;
	}

	
}

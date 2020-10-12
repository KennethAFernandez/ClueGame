package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

@SuppressWarnings("unused")

public class Board {

	private int numRows;
	private int numCols;
	private int numDoors;
	private int numRooms;
	private String layoutConfigFile;
	private String setupConfigFile;
	private BoardCell[][] grid;
	Map<Character, Room> roomMap;
	Set<BoardCell> targets;
	Set<BoardCell> visited;

	private static Board theInstance = new Board();
	private Board() {
		super() ;
	}
	public static Board getInstance() {
		return theInstance;
	}


	public void initialize() {
		try {
			setConfigValues();
			loadSetupConfig();
			loadLayoutConfig();
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public void setConfigFiles(String string, String string2) {
		this.layoutConfigFile = "data/"+string;
		this.setupConfigFile = "data/"+string2;
	}
	
	@SuppressWarnings("resource")
	public void setConfigValues() throws FileNotFoundException, BadConfigFormatException {
		
		FileReader reader = new FileReader(layoutConfigFile);
		Scanner scanner = new Scanner(reader);
		String currLine;
		String[] values = null;
		char key;
		int cols = 0;
		int rows = 0;
		boolean firstIter = true;
		
		while(scanner.hasNext()) {
			currLine = scanner.nextLine();
			values = currLine.split("[\\,\\s]+");
			cols = values.length;
			rows++;
		}
			
		numRows = rows;
		numCols = cols;
		grid = new BoardCell[numRows][numCols];
		
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				grid[i][j] = new BoardCell(i, j);
			}
		}
			
	}
	
	@SuppressWarnings("resource")
	public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException {
		roomMap = new HashMap<Character, Room>();
		FileReader reader = new FileReader(setupConfigFile);
		Scanner scanner = new Scanner(reader);
		String currLine;
		String[] values;
		char key;

		while(scanner.hasNext()) {
			currLine = scanner.nextLine();
			values = currLine.split("[\\,\\s]+");
			if(values[0] == "//" || values[0] == "space") {
				continue;
			}
			
			if(values[0] == "Room") {
				key = values[2].charAt(0);
				if(!Character.isLetter(key)) {
					throw new BadConfigFormatException("Bad format (KEY) " + setupConfigFile);
				} 
				Room room = new Room(values[1]);
				roomMap.put(key, room);
			}
		}
	}
	public void loadLayoutConfig() throws BadConfigFormatException, FileNotFoundException{

	}


	public void adjacencies(int row, int col) {
		BoardCell cell = getCell(row, col); 
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
	public boolean validate(int row, int col) {
		return row >= 0 && row < numRows && col >= 0 && col < numCols;
	}


	public void calcTargets(BoardCell startCell, int path) {
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		visited.add(startCell);
		findTargets(startCell, path);
	}
	public void findTargets(BoardCell startCell, int path) {
		for(BoardCell cell : startCell.getAdjList()) {
			if(!visited.contains(startCell)) {
				visited.add(startCell);
				if(path == 1 || cell.isDoorway() == true) {
					targets.add(cell);
				} else {
					findTargets(cell, path -1);
				}
				visited.remove(cell);
			}
		}
	}
	public Set<BoardCell> getTargets(){
		return targets;
	}


	public Room getRoom(char c) {
		return roomMap.get(c);
	}
	public BoardCell getCell(int i, int j) {
		return grid[i][j];
	}
	public Room getRoom(BoardCell cell) {
		return getRoom(cell.getInitial());
	}
	public int getNumColumns() {
		return numCols;
	}
	public int getNumRows() {
		return numRows;
	}
	public int getAmountRooms() {
		return roomMap.size();
	}
	public int getAmountDoors(){
		return numDoors;
	}

}

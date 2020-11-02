package clueGame;

import java.awt.Color;
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

	// Integers to hold the initial setup values
	private int numRows;
	private int numCols;
	private int numDoors;
	private int numRooms;
	
	
	// Strings to hold the file names
	private String layoutConfigFile;
	private String setupConfigFile;
	
	
	// Array to hold the board of BoardCells
	private BoardCell[][] grid;
	
	
	// Maps to hold all the rooms, secret passages and room centers
	Map<Character, Room> roomMap;
	Map<Character, BoardCell> passageMap;
	Map<Character, BoardCell> centerMap;
	Map<String, Color> players;
	
	
	// sets to hold targets, and vistited cells
	Set<BoardCell> targets;
	Set<BoardCell> visited;
	Set<String> weapons;

	
	// singleton method
	private static Board theInstance = new Board();
	private Board() {super();}
	public static Board getInstance() {return theInstance;}
	
	
	// function to try and run the config files
	// try/catch for file not found exceptions, or bad format exceptions
	public void initialize() {
		try {
			setConfigValues();
			loadSetupConfig();
			loadLayoutConfig();
			adjacencies();
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void deal() {
		
	}
	
	

	
	// calculates the adjacencies
	// checks if walkway, then if doorway, than if it is a room center
	public void adjacencies() {
		for(int i=0; i<numRows; i++) {
			for(int j=0; j<numCols; j++) {
				char temp = getCell(i, j).getInitial();
				BoardCell cell = getCell(i, j);
				if(cell.isWalkway()) {
					if(validateCellBounds(i+1, j) && getCell(i+1, j).isWalkway()) {
						cell.addAdj(getCell(i+1, j));
					}
					if(validateCellBounds(i-1, j) && getCell(i-1, j).isWalkway()) {
						cell.addAdj(getCell(i-1, j));
					}
					if(validateCellBounds(i, j+1) && getCell(i, j+1).isWalkway()) {
						cell.addAdj(getCell(i, j+1));
					}
					if(validateCellBounds(i, j-1) && getCell(i, j-1).isWalkway()) {
						cell.addAdj(getCell(i, j-1));
					}
				} 
				
				if(cell.isDoorway()) {
					DoorDirection tmp = cell.getDoorDirection();
					Character roomLoc ='X';
					if(tmp == DoorDirection.UP) {
						roomLoc = getCell(i-1, j).getInitial();
					}
					if(tmp == DoorDirection.DOWN) {
						roomLoc = getCell(i+1, j).getInitial();
					}
					if(tmp == DoorDirection.RIGHT) {
						roomLoc = getCell(i, j+1).getInitial();
					}
					if(tmp == DoorDirection.LEFT) {
						roomLoc = getCell(i, j-1).getInitial();
					}
					cell.addAdj(centerMap.get(roomLoc));
					centerMap.get(roomLoc).addAdj(cell);
				}
				
				if(cell.isRoomCenter()) {
					if(passageMap.containsKey(cell.getInitial())) {
						Character tmp = (passageMap.get(cell.getInitial())).getInitial();
						cell.addAdj(centerMap.get(tmp));
					}						
				}				
			}
		}
	}
	
	
	// helper method to adjacencies method that validates a cell
	// cell is valid if it is in the bounds of the board
	public boolean validateCellBounds(int row, int col) {
		return (row >= 0 && row < numRows && col >= 0 && col < numCols);
	}

	
	// creates sets and recursively calls findTargets()
	// in order to calculate legal moves
	public void calcTargets(BoardCell startCell, int path) {
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		visited.add(startCell);
		findTargets(startCell, path);
	}
	
	
	// calculates valid targets for a given cell and path
	public void findTargets(BoardCell startCell, int path) {
		for (BoardCell cell : startCell.getAdjList()) {
			if (!visited.contains(cell)) {
				if(cell.isOccupied() && !cell.isRoom()) {continue;}
				visited.add(cell);
				if ((path == 1 || cell.isRoom() == true) && (cell.isOccupied() == false || cell.isRoom())) {
					targets.add(cell);
				} else {
					findTargets(cell, path - 1);
				}
				visited.remove(cell);
			}
		}
	}


	// sets the file names with proper path "data"
	public void setConfigFiles(String string, String string2) {
		this.layoutConfigFile = "data/" + string;
		this.setupConfigFile = "data/" + string2;
	}
	

	// method we created to get the number of rows and columns 
	// before we iterate through the files for specifics
	public void setConfigValues() throws FileNotFoundException, BadConfigFormatException {
		FileReader reader = new FileReader(layoutConfigFile);
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(reader);
		String currLine;
		String[] values = null;		
		char key;
		int cols = 0;
		int rows = 0;
		boolean firstIter = true;
		
		while (scanner.hasNext()) {
			currLine = scanner.nextLine();
			values = currLine.split("[\\,\\s]+");
			cols = values.length;
			rows++;
		}
		
		numRows = rows;
		numCols = cols;
		grid = new BoardCell[numRows][numCols];
		
		// creates grid based of off number of rows and columns
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				grid[i][j] = new BoardCell(i, j);

			}
		}
	}

	
	// loads the given legend and creates the room map
	public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException {
		roomMap = new HashMap<Character, Room>();		
		players = new HashMap<String, Color>();
		weapons = new HashSet<String>();
		FileReader reader = new FileReader(setupConfigFile);
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(reader);
		String currLine;
		String[] values;		
		char key;
		Card newCard;
		Color color = null;
		Player player;

		while (scanner.hasNext()) {
			currLine = scanner.nextLine();
			values = currLine.split(", ");
			String name = values[0];
					
			if(name.equals("Room") || name.equals("Space")) {
				key = values[2].charAt(0);
				if (!(Character.isLetter(key))) {
					throw new BadConfigFormatException("Bad format: Inappropriate value for room initial in legend " + setupConfigFile);
				}
				Room room = new Room(values[1]);
				roomMap.put(key, room);
				newCard = new Card(name, CardType.ROOM);
			}
			if(name.equals("Player")) {				
				switch(values[2]) {
				case "Yellow":
					color = Color.YELLOW;
					return;
				case "Red":
					color = Color.RED;
					return;
				case "Green":
					color = Color.GREEN;
					return;
				case "Blue":
					color = Color.BLUE;
					return;
				case "White":
					color = Color.WHITE;
					return;
				case "Black":
					color = Color.BLACK;
					return;
				}
				newCard = new Card(name, CardType.PERSON, color);
				players.put(name, color);
			
			} else if (name.equals("Weapon")) {
				newCard = new Card(name, CardType.WEAPON);
			} else {
				continue;
			}
		}
	}

	
	// goes through the csv file and checks for doors, door direction
	// label cells, center cells using switch statement
	public void loadLayoutConfig() throws BadConfigFormatException, FileNotFoundException{
		passageMap = new HashMap<Character, BoardCell>();
		centerMap = new HashMap<Character, BoardCell>();
		FileReader reader = new FileReader(layoutConfigFile);
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(reader);
		String currLine;
		String[] values;
		int cols = 0; 
		int rows = 0;
		while(scanner.hasNext()) {
			currLine = scanner.nextLine();
			values = currLine.split(",");
			cols = values.length;
			//throwing badConfigForamtException in case of number columns is inconsistent
			if(numCols!= cols) {
				throw new BadConfigFormatException("Error with config files");
			}
			for(int i = 0; i < cols; ++i) {
				grid[rows][i] = getCell(rows, i);
				char location = values[i].charAt(0);
				if(location == 'X') {
					grid[rows][i].setUnused();
					grid[rows][i].setInitital('X');
					grid[rows][i].setRoom(true);
					continue;
				}
				grid[rows][i].setInitital(location);
				if(location == 'W') {
					grid[rows][i].setWalkway();
				}
				else if(roomMap.containsKey(location)) {
					grid[rows][i].setInitital(location);
					grid[rows][i].setRoom(true);
				}
				if(values[i].length() == 2) {
					//populates maps within the Board class to aid in populating the adjacency lists
					char tmp = values[i].charAt(1);
					specialCell(tmp, rows, i, location);
				}
			}
			rows++;
		}					
	}
	// Helper function for LoadLayoutConfig
	private void specialCell(char tmp, int rows, int i, char location) {
		switch (tmp) {
		case '>':
			grid[rows][i].setDoor();
			grid[rows][i].setDoorDirection(DoorDirection.RIGHT);
			return;
		case '<':
			grid[rows][i].setDoor();
			grid[rows][i].setDoorDirection(DoorDirection.LEFT);
			return;
		case '^':
			grid[rows][i].setDoor();
			grid[rows][i].setDoorDirection(DoorDirection.UP);
			return;
		case 'v':
			grid[rows][i].setDoor();
			grid[rows][i].setDoorDirection(DoorDirection.DOWN);
			return;
		case '#':
			grid[rows][i].setLabel();
			roomMap.get(location).setLabel(grid[rows][i]);
			return;
		case '*':
			grid[rows][i].setCenter();
			roomMap.get(location).setCenter(grid[rows][i]);
			centerMap.put(location, grid[rows][i]);
			return;
		default:
			grid[rows][i].setSecretPassage(tmp);
			passageMap.put(tmp, grid[rows][i]);
			return;
		}
	}
	
	
	public Map<String, Color> getPlayers(){
		return players;
	}
	
	public Set<String> getWeapons(){
		return weapons;
	}
	
	// returns set of targets
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	// returns room based on key from room map
	public Room getRoom(char c) {
		return roomMap.get(c);
	}

	// returns BoardCell from grid array
	public BoardCell getCell(int i, int j) {
		return grid[i][j];
	}

	// returns room from the room map
	public Room getRoom(BoardCell cell) {
		return getRoom(cell.getInitial());
	}

	// returns the number of columns
	public int getNumColumns() {
		return numCols;
	}

	// returns the number of rows
	public int getNumRows() {
		return numRows;
	}

	// returns the amount of rooms
	public int getAmountRooms() {
		return roomMap.size();
	}

	// returns a adjacency list for any given cell
	public Set<BoardCell> getAdjList(int i, int j) {
		return getCell(i, j).getAdjList();
	}
}

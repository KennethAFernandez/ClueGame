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

	// Integers to hold the setup values
	private int numRows;
	private int numCols;
	private int numDoors;
	private int numRooms;
	// Strings to hold the file names
	private String layoutConfigFile;
	private String setupConfigFile;
	// Array to hold the board 
	private BoardCell[][] grid;
	// Mpas to hold all the rooms, secret passages and room centers
	Map<Character, Room> roomMap;
	Map<Character, BoardCell> passageMap;
	Map<Character, BoardCell> centerMap;
	// sets to hold targets, and vistited cells
	Set<BoardCell> targets;
	Set<BoardCell> visited;

	private static Board theInstance = new Board();

	private Board() {
		super();
	}

	public static Board getInstance() {
		return theInstance;
	}

	// function to try and run the config files
	// catch any file not found exceptions, or bad format exceptions
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

	// calculates the adjacencies
	// checks if walkway, then if doorway, than if it is a room center
	public void adjacencies() {
		for(int i=0; i<numRows; i++) {
			for(int j=0; j<numCols; j++) {
				char temp = getCell(i, j).getInitial();
				BoardCell cell = getCell(i, j);
				if(cell.isWalkway()) {
					if(validate(i+1, j) && getCell(i+1, j).isWalkway()) {
						cell.addAdj(getCell(i+1, j));
					}
					if(validate(i-1, j) && getCell(i-1, j).isWalkway()) {
						cell.addAdj(getCell(i-1, j));
					}
					if(validate(i, j+1) && getCell(i, j+1).isWalkway()) {
						cell.addAdj(getCell(i, j+1));
					}
					if(validate(i, j-1) && getCell(i, j-1).isWalkway()) {
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


	// helper func to adjacencies that validates a cell
	public boolean validate(int row, int col) {
		return (row >= 0 && row < numRows && col >= 0 && col < numCols);
	}

	// creates sets and recursively calls findTargets()
	public void calcTargets(BoardCell startCell, int path) {
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		visited.add(startCell);
		findTargets(startCell, path);
	}

	// calculates targets for a given cell and path
	public void findTargets(BoardCell startCell, int path) {
		for (BoardCell cell : startCell.getAdjList()) {
			if (!visited.contains(cell)) {
				if(cell.isOccupied() && !cell.isRoom()) {continue;}
				visited.add(cell);
				if (path == 1 || cell.isRoom() == true) {
					if(cell.isOccupied() == false || cell.isRoom()) {						
						targets.add(cell);
					}
				} else {
					findTargets(cell, path - 1);
				}
				visited.remove(cell);
			}
		}
	}

	// getters
	public Set<BoardCell> getTargets() {
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

	public int getAmountDoors() {
		return numDoors;
	}

	public Set<BoardCell> getAdjList(int i, int j) {
		return getCell(i, j).getAdjList();
	}

	// sets the file names
	public void setConfigFiles(String string, String string2) {
		this.layoutConfigFile = "data/" + string;
		this.setupConfigFile = "data/" + string2;
	}

	// method we created to get the number of rows and columns
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

		while (scanner.hasNext()) {
			currLine = scanner.nextLine();
			values = currLine.split("[\\,\\s]+");
			cols = values.length;
			rows++;
		}

		numRows = rows;
		numCols = cols;
		grid = new BoardCell[numRows][numCols];
		// creates grid
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				grid[i][j] = new BoardCell(i, j);

			}
		}
	}

	// loads the given legend and creates the room map
	@SuppressWarnings("resource")
	public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException {
		roomMap = new HashMap<Character, Room>();
		FileReader reader = new FileReader(setupConfigFile);
		Scanner scanner = new Scanner(reader);
		String currLine;
		String[] values;
		char key;

		while (scanner.hasNext()) {
			currLine = scanner.nextLine();
			values = currLine.split(", ");
			if (values[0].equals("//")) {
				continue;
			}
			// adding to the room map
			if (values[0].equals("Room") || values[0].equals("Space")) {
				key = values[2].charAt(0);
				if (!(Character.isLetter(key))) {
					throw new BadConfigFormatException("Bad format (KEY) " + setupConfigFile);
				}
				Room room = new Room(values[1]);
				roomMap.put(key, room);
			}
		}
	}

	// goes through the csv file and checks for doors, door direction
	// label cells, center cells using switch statement
	@SuppressWarnings("resource")
	public void loadLayoutConfig() throws BadConfigFormatException, FileNotFoundException{
		passageMap = new HashMap<Character, BoardCell>();
		centerMap = new HashMap<Character, BoardCell>();
		
		FileReader reader = new FileReader(layoutConfigFile);
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
					//switch statement based on the second character
					char tmp = values[i].charAt(1);
					switch (tmp) {
					case '>':
						grid[rows][i].setDoor();
						grid[rows][i].setDoorDirection(DoorDirection.RIGHT);
						continue;
					case '<':
						grid[rows][i].setDoor();
						grid[rows][i].setDoorDirection(DoorDirection.LEFT);
						continue;
					case '^':
						grid[rows][i].setDoor();
						grid[rows][i].setDoorDirection(DoorDirection.UP);
						continue;
					case 'v':
						grid[rows][i].setDoor();
						grid[rows][i].setDoorDirection(DoorDirection.DOWN);
						continue;
					case '#':
						grid[rows][i].setLabel();
						roomMap.get(location).setLabel(grid[rows][i]);
						continue;
					case '*':
						grid[rows][i].setCenter();
						roomMap.get(location).setCenter(grid[rows][i]);
						centerMap.put(location, grid[rows][i]);
						continue;	
					default:
						grid[rows][i].setSecretPassage(tmp);
						passageMap.put(tmp, grid[rows][i]);
						continue;
					}
				}
			}
			rows++;
		}					
	}
}

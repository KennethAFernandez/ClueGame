package clueGame;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
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
	Map<String, Player> players;

	// Array lists to hold all objects of card types
	ArrayList<Card> deck;
	ArrayList<Card> weapons;
	ArrayList<Card> rooms;
	ArrayList<Card> gameCharacters; // for the characters in the game; not calling this "players" for clarity.
	Solution solution;


	// sets to hold targets, and vistited cells
	Set<BoardCell> targets;
	Set<BoardCell> visited;


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
			deal();
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}


	//Checking an accusation, return true if accusation matches solution
	// I cut the if statement condidtional in pieces so it was easier to read and you didn't 
	// have to scroll to the right forever. 
	public boolean checkAccusation(Card person, Card location, Card weapon) {
		if(solution.getPerson().getCardName().equals(person.getCardName()) && 
				solution.getRoom().getCardName().equals(location.getCardName()) && 
				solution.getWeapon().getCardName().equals(weapon.getCardName())) {
			return true;
		} else {
			return false;
		}
	}


	//Handle a suggestion made
	public Card handleSuggestion(Card person, Card room, Card weapon, Player suggestor) {
		ArrayList<Card> suggestionList = new ArrayList<Card>();
		suggestionList.add(person);
		suggestionList.add(room);
		suggestionList.add(weapon);
		Card result = null;
		for(Map.Entry<String, Player> entry: players.entrySet()) {
			if(entry.getValue() == suggestor) {continue;}
			for(int suggestionIterator = 0; suggestionIterator < 3; ++suggestionIterator) {
				for(int handIterator = 0; handIterator < 3; ++handIterator) {
					if(entry.getValue().getHand().get(handIterator).getCardName().equals(suggestionList.get(suggestionIterator).getCardName())) {
						result = suggestionList.get(suggestionIterator);
						break;
					}
				}
			}
			if(result != null) {return result;}
		} 
		return null;
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


	// loads the given legend, creates the room map, and the cards
	public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException {
		deck = new ArrayList<Card>();
		roomMap = new HashMap<Character, Room>();		
		players = new HashMap<String, Player>();
		weapons = new ArrayList<Card>();
		rooms = new ArrayList<Card>();
		gameCharacters = new ArrayList<Card>();
		FileReader reader = new FileReader(setupConfigFile);
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(reader);
		String currLine;
		String[] values;		
		char key;
		boolean firstIter = true;

		Color color = null;		

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
				if(name.equals("Room")) {
					Card newCard;
					newCard = new Card(values[1], CardType.ROOM);
					deck.add(newCard);
					rooms.add(newCard);
				}
			}

			// switch statement and corresponding else if to create cards and 
			// array list of all card types. If the conditional is met then 
			// we create a new card and add it to the deck and corresponding
			// array list
			if(name.equals("Player")) {		
				switch(values[2]) {
				case "Yellow":
					color = Color.YELLOW;
					break;
				case "Red":
					color = Color.RED;
					break;
				case "Green":
					color = Color.GREEN;
					break;
				case "Blue":
					color = Color.BLUE;
					break;
				case "White":
					color = Color.WHITE;
					break;
				case "Black":
					color = Color.BLACK;
					break;
				}

				Card newCard;
				newCard = new Card(values[1], CardType.PERSON);
				deck.add(newCard);
				gameCharacters.add(newCard);
				// creates player class (uses first "person")
				if(firstIter == true) {
					Player player = new HumanPlayer(values[1], color, Integer.parseInt(values[3]), Integer.parseInt(values[4]));
					players.put(values[1], player);
					firstIter = false;
				}else {					
					Player player = new ComputerPlayer(values[1], color, Integer.parseInt(values[3]), Integer.parseInt(values[4]));
					players.put(values[1], player);
				}

			} else if (name.equals("Weapon")) {
				Card newCard;
				newCard = new Card(values[1], CardType.WEAPON);
				deck.add(newCard);
				weapons.add(newCard);
			} else {
				continue;
			}
		}


	}

	// Shuffles the array lists of all card types while creating solution
	// Then removes solutions so we can ouput the deal out the rest of the cards
	// Then after they are removed, we go through out all of players and deal
	// out cards while checking for any out of bounds exception which would occur	
	// from a bad setup file.
	public void shuffle() {
		Collections.shuffle(rooms);
		Collections.shuffle(weapons);
		Collections.shuffle(gameCharacters);
	}
	public void deal() throws BadConfigFormatException {
		solution = new Solution(gameCharacters.get(0),rooms.get(0), weapons.get(0));
		deck.remove(rooms.get(0));
		deck.remove(weapons.get(0));
		deck.remove(gameCharacters.get(0));
		Collections.shuffle(deck);
		int counter = 0;
		for(Map.Entry<String, Player> entry: players.entrySet()) {	
			if(counter > deck.size()-3) {
				throw new BadConfigFormatException("Out of bounds exception: Bad Config in setup file");
			}else {
				entry.getValue().updateHand(deck.get(counter));
				entry.getValue().updateHand(deck.get(counter+1));
				entry.getValue().updateHand(deck.get(counter+2));
			}
			counter+=3;
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


	public ArrayList<Card> getDeck(){
		return deck;
	}
	public void addDeckCards(Card card) {
		deck.add(card);
	}
	public Map<String, Player> getPlayers(){
		return players;
	}
	
	public void setPlayers(Map<String, Player> input) {
		players = input;
	}

	public ArrayList<Card> getWeapons(){
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

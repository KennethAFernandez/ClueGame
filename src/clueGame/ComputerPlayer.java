package clueGame;

import java.awt.Color;

public class ComputerPlayer extends Player{

	public ComputerPlayer(String name, Color color, int row, int col) {
		super(name, color, row, col);
	}
	
	
	// Computer player creates a suggestion includes room, a weapon and a player
	// Room must match location of player, pass board cell as a parameter
	public Solution createSuggestion() {
		return null;
	}
	
	
	// Computer player selcts a move target 
	public BoardCell selectTargets() {
		return null;
	}
	

}

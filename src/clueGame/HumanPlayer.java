package clueGame;

import java.awt.Color;
import java.util.Set;

public class HumanPlayer extends Player{

	public HumanPlayer(String name, Color color, int row, int col) {
		super(name, color, row, col);
	}

	@Override
	public void createSuggestion(BoardCell boardCell) {
		
	}

	@Override
	public Card getSuggPerson() {
		return null;
	}

	@Override
	public Card getSuggWeapon() {
		return null;
	}

	@Override
	public String getSuggRoom() {
		return null;
	}

	@Override
	public BoardCell selectTargets(Set<BoardCell> set) {
		return null;
	}

}

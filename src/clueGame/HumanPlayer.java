package clueGame;

import java.awt.Color;
import java.util.Set;

public class HumanPlayer extends Player{

	public HumanPlayer(String name, Color color, int row, int col) {
		super(name, color, row, col);
	}

	@Override
	public void createSuggestion(BoardCell boardCell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Card getSuggPerson() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Card getSuggWeapon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSuggRoom() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoardCell selectTargets(Set<BoardCell> set) {
		// TODO Auto-generated method stub
		return null;
	}

}

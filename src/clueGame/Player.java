package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Player {

	private String name;
	private Color color;
	private int row, column;
	private BoardCell location;
	private boolean humanComputer;
	private ArrayList<Card> hand = new ArrayList<Card>();
	
	public Player(String name, Color color) {
		this.name = name;
		this.color = color;
	}
	
	public Player() {
		
	}
	public void updateHand(Card card) {
		
	}

}

package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Player {

	// variables to hold name of the player, corresponding color,
	// location and said players hand
	private String name;
	private Color color;
	private int row, column;
	private BoardCell location;
	private ArrayList<Card> hand = new ArrayList<Card>();
	
	// constructor to set correct variables
	public Player(String name, Color color, int row, int column) {
		this.setName(name);
		this.setColor(color);
		this.setRow(row);
		this.setColumn(column);
		this.location = Board.getInstance().getCell(row, column);
	}
	
	// adds cards to array list hand of players
	public void updateHand(Card card) {
		hand.add(card);
	}
	
	// returns each player's hand
	public ArrayList<Card> getHand() {
		return hand;
	}

	// corresponding setters and getters
	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	public BoardCell getLocation() {
		return location;
	}

	public void setLocation(BoardCell location) {
		this.location = location;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

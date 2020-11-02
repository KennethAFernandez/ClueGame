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
	
	public Player(String name, Color color, int row, int column) {
		this.name = name;
		this.setColor(color);
		this.setRow(row);
		this.setColumn(column);
		this.location = Board.getInstance().getCell(row, column);
	}
	
	public void updateHand(Card card) {
		
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

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

}

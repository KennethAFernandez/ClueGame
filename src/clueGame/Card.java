package clueGame;

import java.awt.Color;

public class Card extends Player{

	String cardName;
	CardType cardType;
	
	public Card(String name, CardType cardType, Color color) {
		super(name, color);
		this.cardName = name;
		this.cardType =  cardType;
	}
	
	public Card(String name, CardType cardType) {
		super();
		this.cardName = name;
		this.cardType = cardType;
	}
	
	public boolean equals(Card target) {
		return false;
	}
	
	public CardType getCardType() {
		return cardType;
	}
	
	public String getCardName() {
		return cardName;
	}
	
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	public void setCardName(String name) {
		this.cardName = name;
	}
}

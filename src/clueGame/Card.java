package clueGame;

public class Card{

	private String cardName;
	private CardType cardType;
	
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

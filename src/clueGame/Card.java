package clueGame;

public class Card{

	// variables to hold card name and card type
	private String cardName;
	private CardType cardType;


	// constructor to set name and card type
	public Card(String name, CardType cardType) {
		super();
		this.cardName = name;
		this.cardType = cardType;
	}

	public boolean equals(Card target) {
		if(target.cardName == cardName && target.getCardType() == cardType) {
			if(getClass() == target.getClass()) {
				return true;
			}
		} else {
			return false;
		}
		return false;
	}

	// returns the card type
	public CardType getCardType() {
		return cardType;
	}
	// returns teh card name
	public String getCardName() {
		return cardName;
	}
	// sets card type
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}
	// sets card name
	public void setCardName(String name) {
		this.cardName = name;
	}
}

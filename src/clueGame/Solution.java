package clueGame;

public class Solution {

	// card variables to hold solution cards for all three types
	Card person;
	Card room;
	Card Weapon;
	
	
	// constructor to set proper solution cards
	public Solution(Card person, Card room, Card weapon) {
		this.person = person;
		this.room = room;
		this.Weapon = weapon;
	}
	
	// corresponding getters and setters
	public Card getPerson() {
		return person;
	}

	public void setPerson(Card person) {
		this.person = person;
	}

	public Card getRoom() {
		return room;
	}

	public void setRoom(Card room) {
		this.room = room;
	}

	public Card getWeapon() {
		return Weapon;
	}

	public void setWeapon(Card weapon) {
		Weapon = weapon;
	}

}

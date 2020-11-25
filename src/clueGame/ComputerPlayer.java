package clueGame;

import java.awt.Color;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JOptionPane;


public class ComputerPlayer extends Player{


	private Card suggPerson;
	private Card suggWeapon;
	private Card suggRoom;
	Card disprove;
	private String roomString;

	boolean hasAccusation = false;

	public ComputerPlayer(String name, Color color, int row, int col) {
		super(name, color, row, col);
	}

	// default
	public ComputerPlayer() {
		this("TestName", Color.YELLOW, 6, 9);
	}



	// Computer player creates a suggestion includes room, a weapon and a player
	// Room must match location of player, pass board cell as a parameter
	@Override
	public void createSuggestion(BoardCell boardCell) {

		ArrayList<Card> peoples = new ArrayList<Card>();
		ArrayList<Card> weapons = new ArrayList<Card>();

		for(Card c: Board.getInstance().getDeck()) {
			if(!seen.contains(c) && !getHand().contains(c) && c.getCardName() != this.getName()) {
				if(c.getCardType().equals(CardType.PERSON)) {
					peoples.add(c);
				} else if (c.getCardType() == CardType.WEAPON){
					weapons.add(c);
				}
			}
		}

		Random randNum = new Random();
		this.suggPerson = peoples.get(randNum.nextInt(peoples.size()));
		this.suggWeapon = weapons.get(randNum.nextInt(weapons.size()));
		
		//System.out.println(suggPerson.getCardName() + " " + suggWeapon.getCardName());
		char initialRoom = (boardCell.getInitial());
		// switch statement to aid in getting location.
		switch(initialRoom) {
		case 'M':
			this.roomString = "Main Hall";
			break;
		case 'K':
			this.roomString = "Cooking Room";
			break;
		case 'R':
			this.roomString = "Relaxing Room";
			break;
		case 'P':
			this.roomString = "Ping Pong Room";
			break;
		case 'L':
			this.roomString = "Library";
			break;
		case 'D':
			this.roomString = "Dining Room";
			break;
		case 'T':
			this.roomString = "TV Room";
			break;
		case 'S':
			this.roomString = "School";
			break;
		case 'C':
			this.roomString = "Closet";
			break;
		case 'W':
			this.roomString = "Walkway";
			break;
		default:
			this.roomString = "Unused";
			break;
		}
		
		for(Card c: Board.getInstance().rooms) {
			if(c.getCardName().equals(roomString)) {
				
				suggRoom = c;
				break;
			}
		}
	}


	// Computer player selcts a move target 
	@Override
	public BoardCell selectTargets(Set<BoardCell> targets) {
		if(targets.size() == 0) {
			return this.getLocation();
		}
		this.getLocation().setOccupied(false);

		if(hasAccusation) {
			boolean result =Board.getInstance().checkAccusation(suggPerson, suggRoom, suggWeapon);
			if(result) {
				JButton ok = new JButton();
				JOptionPane.showMessageDialog(ok, "Computer Player wins!");
				System.exit(0);
			}
		}

		for(BoardCell targetCheck: targets) {
			if(targetCheck.isRoom() && !getSeen().contains(Board.getInstance().getCardFromRoomInitial(targetCheck.getInitial()))) {
				for(Card c: Board.getInstance().rooms) {
					if(c.getCardName() == Board.getInstance().getRoom(targetCheck.getInitial()).getName()) {
						this.updateSeen(c);
						break;
					}
				}
				
				if(targetCheck.isRoom()) {
					createSuggestion(targetCheck);
					Card newCard = Board.getInstance().handleSuggestion(suggPerson, suggRoom, suggWeapon, this);
					if(newCard != null) {
						updateSeen(newCard);
					} else {
						hasAccusation = true;
					}
				}
				return targetCheck;
			}
		}
		int idx = new Random().nextInt(targets.size());
		for(BoardCell findTarget: targets) {
			if(idx-- == 0) {
				findTarget.setOccupied(true);
				return findTarget;
			}
		}
		return null;
	}

	// getters and Setters for Suggesetions
	public Card getSuggPerson() {
		return suggPerson;
	}

	public void setSuggPerson(Card suggPerson) {
		this.suggPerson = suggPerson;
	}

	public Card getSuggWeapon() {
		return suggWeapon;
	}

	public void setSuggWeapon(Card suggWeapon) {
		this.suggWeapon = suggWeapon;
	}

	public String getSuggRoom() {
		return roomString;
	}

	public void setSuggRoom(String suggRoom) {
		this.roomString = suggRoom;
	}

	public void createSuggestion() {

	}
}

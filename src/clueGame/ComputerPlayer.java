package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public class ComputerPlayer extends Player{


	private Card suggPerson;
	private Card suggWeapon;
	private String suggRoom;
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
			if( !seen.contains(c) && !getHand().contains(c)) {
				if(c.getCardType().equals(CardType.PERSON)) {
					peoples.add(c);
				} else if (c.getCardType() == CardType.WEAPON){
					weapons.add(c);
				}
			}
		}

		Random randNum = new Random();
		this.setSuggPerson(peoples.get(randNum.nextInt(peoples.size())));
		this.setSuggWeapon(weapons.get(randNum.nextInt(peoples.size())));
		char initialRoom = (boardCell.getInitial());
		// switch statement to aid in getting location.
		switch(initialRoom) {
		case 'M':
			System.out.println(1);
			this.suggRoom = "Main Hall";
			break;
		case 'K':
			this.suggRoom = "Cooking Room";
			break;
		case 'R':
			this.suggRoom = "Relaxing Room";
			break;
		case 'P':
			this.suggRoom = "Ping Pong Room";
			break;
		case 'L':
			this.suggRoom = "Library";
			break;
		case 'D':
			this.suggRoom = "Dining Room";
			break;
		case 'T':
			this.suggRoom = "TV Room";
			break;
		case 'S':
			this.suggRoom = "School";
			break;
		case 'C':
			this.suggRoom = "Closet";
			break;
		case 'W':
			this.suggRoom = "Walkway";
			break;
		default:
			this.suggRoom = "Unused";
			break;
		}

	}


	// Computer player selcts a move target 
	@Override
	public BoardCell selectTargets(Set<BoardCell> targets) {
		for(BoardCell targetCheck: targets) {
			if(targetCheck.isRoom() && !getSeen().toString().contains(Board.getInstance().getRoom(targetCheck.getInitial()).getName())) {
				return targetCheck;
			}
		}
		Random rngen = new Random();
		int randTarget = rngen.nextInt(targets.size());
		BoardCell[] targetArray = targets.toArray(new BoardCell[targets.size()]); // since sets are not indexed, it must be converted into array, which adds linear complexity.
		return targetArray[randTarget];
		
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
		return suggRoom;
	}


	public void setSuggRoom(String suggRoom) {
		this.suggRoom = suggRoom;
	}

	
	public void createSuggestion() {
		// TODO Auto-generated method stub
		
	}




}

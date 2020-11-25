package tests;

import java.awt.Color;


import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;


public class GameSolution {

	// creates board
	private static Board board;

	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}
	
	
	// Checks that solution is correct
	// checks that solutionn is wrong when wrong
	// checks person, weapon, room
	@Test
	public void testCheckAccusation() {
		Card person = new Card("Mustard", CardType.PERSON);
		Card room = new Card("Main Hall", CardType.ROOM);
		Card weapon = new Card("knife", CardType.WEAPON);
		
		Solution answer = new Solution(person, room, weapon);
		
		Assert.assertTrue("Mustard" == answer.getPerson().getCardName());
		Assert.assertTrue("Main Hall"== answer.getRoom().getCardName());
		Assert.assertTrue("knife" == answer.getWeapon().getCardName());
		
		Card wrong = new Card("Kenny", CardType.PERSON);
		Assert.assertTrue(board.checkAccusation(wrong, room, weapon) == false);
		
		Card wrongTwo = new Card("pipe", CardType.WEAPON);
		Assert.assertTrue(board.checkAccusation(person, room, wrongTwo) == false);
		
		Card wrongThree = new Card("Dining Hall", CardType.ROOM);
		Assert.assertTrue(board.checkAccusation(person, wrongThree, weapon) == false);
	}
	
	
	// One matching card is returned
	// 1> matching card, returned card is choosen randomly
	// no matching cards, null is returned
	@Test
	public void testDisproveAccusation() {
		Player player = new ComputerPlayer("Kenny", Color.BLUE, 6, 7);
		
		Card person = new Card("Mustard", CardType.PERSON);
		Card room = new Card("School", CardType.ROOM);
		Card weapon = new Card("knife", CardType.WEAPON);
		player.updateHand(person);
		player.updateHand(weapon);
		player.updateHand(room);
		
		ArrayList<Card> suggestion = new ArrayList<Card>();
		
		Card person2 = new Card("Scarlet", CardType.PERSON);
		Card room2 = new Card("Library", CardType.ROOM);
		Card weapon2 = new Card("knife", CardType.WEAPON);
		suggestion.add(person2);
		suggestion.add(room2);
		suggestion.add(weapon);
		
		Assert.assertTrue(player.disproveSuggestion(suggestion) == weapon);
		Assert.assertTrue(player.disproveSuggestion(suggestion).equals(weapon2));
		
		suggestion.clear();
		suggestion.add(person2);
		suggestion.add(room2);
		suggestion.add(new Card("weapon", CardType.WEAPON));
		
		Assert.assertTrue(player.disproveSuggestion(suggestion) == null);
		
	}
	
	// no one can disprove returns null
	// accusing players only to disprove returns null
	// only human disproves answer
	// two players can disprove and return answer
	@Test
	public void testHandleSuggestion() {
		Map<String, Player> smallGroup;
		smallGroup = new TreeMap<String, Player>();
		Player player2 = new ComputerPlayer("bot0", Color.BLUE, 6, 7);
		Player player1 = new ComputerPlayer("bot1", Color.BLUE, 6, 7);
		Player player0 = new HumanPlayer("Kenny", Color.BLUE, 6, 7);
		smallGroup.put("player0", player0);
		smallGroup.put("player1", player1);
		smallGroup.put("player2", player2);
		
		Card person = new Card("Mustard", CardType.PERSON);
		Card room = new Card("School", CardType.ROOM);
		Card weapon = new Card("Knife", CardType.WEAPON);
		smallGroup.get("player0").updateHand(person);
		smallGroup.get("player0").updateHand(weapon);
		smallGroup.get("player0").updateHand(room);
		person = new Card("Scarlet", CardType.PERSON);
		room = new Card("Library", CardType.ROOM);
		weapon = new Card("Candle", CardType.WEAPON);
		smallGroup.get("player1").updateHand(person);
		smallGroup.get("player1").updateHand(weapon);
		smallGroup.get("player1").updateHand(room);
		person = new Card("Green", CardType.PERSON);
		room = new Card("School", CardType.ROOM);
		weapon = new Card("Revolver", CardType.WEAPON);
		smallGroup.get("player2").updateHand(person);
		smallGroup.get("player2").updateHand(weapon);
		smallGroup.get("player2").updateHand(room);
		
		board.setPlayers(smallGroup);
		
		person = new Card("Not White", CardType.PERSON);
		room = new Card("Not Dining Room", CardType.ROOM);
		weapon = new Card("Not Dumbbell", CardType.WEAPON);
		//Assert.assertTrue(board.handleSuggestion(person, room, weapon, player0)== null); // Checks three cards that no player currently holds
		
		person = new Card("White", CardType.PERSON);
		room = new Card("Dining Room", CardType.ROOM);
		weapon = new Card("Knife", CardType.WEAPON);
		//Assert.assertTrue(board.handleSuggestion(person, room, weapon, player0) == null); // Checks where suggestor holds a card they suggest but no others hold a card in suggestion; therefore checks for null
		
		person = new Card("Scarlet", CardType.PERSON);
		room = new Card("Dining Room", CardType.ROOM);
		weapon = new Card("Dumbbell", CardType.WEAPON);
		//Assert.assertTrue(board.handleSuggestion(person, room, weapon, player0).equals(person)); // Should find that player1 holds the card Scarlet
		
		person = new Card("Green", CardType.PERSON);
		room = new Card("Dining Room", CardType.ROOM);
		weapon = new Card("Candle", CardType.WEAPON);
		//Assert.assertTrue(board.handleSuggestion(person, room, weapon, player0).equals(weapon)); // this makes sure that only the _first_ suggested match is returned; the weapon of player 0 rather than the person of player 1
	}
}

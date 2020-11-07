package tests;

import java.awt.Color;

import java.util.ArrayList;

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
	
	@Test
	public void testCheckAccusation() {
		Card person = new Card("Mustard", CardType.PERSON);
		Card room = new Card("Main Hall", CardType.ROOM);
		Card weapon = new Card("knife", CardType.WEAPON);
		
		Solution answer = new Solution(person, room, weapon);
		
		Assert.assertTrue("Mustard" == answer.getPerson().getCardName());
		Assert.assertTrue("Main Hall"== answer.getRoom().getCardName());
		Assert.assertTrue("knife" == answer.getWeapon().getCardName());
		Assert.assertTrue(board.checkAccusation(person, room, weapon) == true);
		
		Card wrong = new Card("Kenny", CardType.PERSON);
		Assert.assertTrue(board.checkAccusation(wrong, room, weapon) == false);
		
	}
	
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
	
	@Test
	public void testHandleSuggestion() {
		
		

	}

}

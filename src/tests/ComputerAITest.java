package tests;

import java.awt.Color;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class ComputerAITest {

	// creates board
	private static Board board;
	// cards are static for use in tests
	public static Card mustardCard;
	public static Card scarletCard;
	public static Card greenCard;
	public static Card peacockCard;
	public static Card whiteCard;
	public static Card plumCard;
	public static Card knife;
	public static Card relaxingRoom;
	
	

	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
		mustardCard = new Card("Mustard", CardType.PERSON);
		scarletCard = new Card("Scarlet", CardType.PERSON);
		greenCard = new Card("Green", CardType.PERSON);
		peacockCard = new Card("Peacock", CardType.PERSON);
		whiteCard = new Card("White", CardType.PERSON);
		plumCard = new Card("Plum", CardType.PERSON);
		knife = new Card("knife", CardType.WEAPON);
		relaxingRoom = new Card("Relaxing Room", CardType.ROOM);
		
	}
	
	
	// no rooms -> randomly selected
	// seen -> dont select it
	// not seen -> select it
	// randomly if each target seen
	@Test
	public void testSelectTargets() {
		// if no rooms in list, select randomly
		Player player = new ComputerPlayer();
		board.calcTargets(player.getLocation(), 1);
		Set<BoardCell> targets = board.getTargets();
		BoardCell initialCell = player.getLocation();
		BoardCell cell = player.selectTargets(targets);
		
		Assert.assertTrue(cell != initialCell);
		
		Player playerTwo = new ComputerPlayer("Name", Color.BLACK, 6, 5);
		playerTwo.updateSeen(relaxingRoom);
		board.calcTargets(player.getLocation(), 3);
		Set<BoardCell> targetsTwo = board.getTargets();
		BoardCell cellTwo = player.selectTargets(targetsTwo);
		Assert.assertTrue(cellTwo.getInitial() != 'R');
	}
	
	// Room matches current location
	// one weapon -> selected
	// one person -> selected
	// no persons seen -> random
	// no weapons seen -> random
	// multiple of either -> random
	@Test
	public void testCreateSuggestion() {
		
		// Test only suggestion if only one possible choice
		Player computerPlayerOne = new ComputerPlayer();
		computerPlayerOne.getHand().clear();
		computerPlayerOne.getSeen().clear();
		board.getDeck().clear();
		computerPlayerOne.updateSeen(scarletCard);
		computerPlayerOne.updateSeen(greenCard);
		computerPlayerOne.updateSeen(peacockCard);
		computerPlayerOne.updateSeen(whiteCard);
		board.addDeckCards(scarletCard);
		board.addDeckCards(greenCard);
		board.addDeckCards(peacockCard);
		board.addDeckCards(whiteCard);
		
		board.addDeckCards(plumCard);
		board.addDeckCards(knife);
		
		computerPlayerOne.createSuggestion(computerPlayerOne.getLocation());
		// if only one weapon seen, its selected, if not then it is randomally chosen.
		// if only one person seen, its selected, if not then it is randomally chosen.
		Assert.assertEquals("Plum", computerPlayerOne.getSuggPerson().getCardName());
		Assert.assertEquals("knife", computerPlayerOne.getSuggWeapon().getCardName());
	
	}
	
	@Test
	public void testSuggLocation() {
		
		//Room matches current location on board
		Player computerPlayerOne = new ComputerPlayer();
		computerPlayerOne.createSuggestion(computerPlayerOne.getLocation());
		String location = computerPlayerOne.getSuggRoom();
		Assert.assertTrue(location == "Walkway");
		//Room matches current location on board
		Player computerPlayerTwo = new ComputerPlayer("Test", Color.BLACK, 11, 3);
		computerPlayerTwo.createSuggestion(computerPlayerTwo.getLocation());
		String locationTWO = computerPlayerTwo.getSuggRoom();
		Assert.assertTrue(locationTWO == "Library");
	}
	
}

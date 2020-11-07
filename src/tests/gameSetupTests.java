package tests;

import static org.junit.Assert.assertEquals;


import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;

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

public class gameSetupTests {
	
	
	// constants to check, easily changeable if need be
	public static final int NUM_ROOMS = 9;
	public static final int NUM_PLAYER_CARDS = 6;
	public static final int NUM_WEAPON_CARDS = 6;
	public static final int NUM_DECK_CARDS = 21;
	// creates board
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}
	
	// checks for the correct number of players
	@Test
	public void loadNumPlayers() {
		Map<String, Player> players = board.getPlayers();
		assertEquals(NUM_PLAYER_CARDS, players.size());
	}
	
	// checks for the correct number of weapons
	@Test
	public void loadNumWeapons() {
		ArrayList<Card> weapons = board.getWeapons();
		assertEquals(NUM_WEAPON_CARDS, weapons.size());
	}
	
	// checks that the deck is the correct size after removal of solution cards
	@Test
	public void loadNumDeck() {
		ArrayList<Card> deck = board.getDeck();
		assertEquals(NUM_DECK_CARDS, deck.size() +3);
	}
	
	// confirms all players are where they need to be
	@Test
	public void testPlayerNames() {
		Map<String, Player> players = board.getPlayers();
		assertEquals(NUM_PLAYER_CARDS, players.size());
		Assert.assertTrue(players.containsKey("Mustard"));
		Assert.assertTrue(players.containsKey("Scarlet"));
		Assert.assertTrue(players.containsKey("Green"));
		Assert.assertTrue(players.containsKey("Peacock"));
		Assert.assertTrue(players.containsKey("White"));
		Assert.assertTrue(players.containsKey("Plum"));
	}
	
	// confirms all players correspond to their given colors
	@Test
	public void testPlayerColors() {
		Map<String, Player> players = board.getPlayers();
		Assert.assertEquals(Color.YELLOW, players.get("Mustard").getColor());
		Assert.assertEquals(Color.RED, players.get("Scarlet").getColor());
		Assert.assertEquals(Color.GREEN, players.get("Green").getColor());
		Assert.assertEquals(Color.BLUE, players.get("Peacock").getColor());
		Assert.assertEquals(Color.WHITE, players.get("White").getColor());
		Assert.assertEquals(Color.BLACK, players.get("Plum").getColor());
	}
	
	// tests that the code seperates human players from computer players
	@Test
	public void testHumanPlayer() {
		Map<String, Player> players = board.getPlayers();
		Assert.assertEquals(HumanPlayer.class, players.get("Mustard").getClass());
		Assert.assertEquals(ComputerPlayer.class, players.get("Scarlet").getClass());
		Assert.assertEquals(ComputerPlayer.class, players.get("Green").getClass());
		Assert.assertEquals(ComputerPlayer.class, players.get("Peacock").getClass());
		Assert.assertEquals(ComputerPlayer.class, players.get("White").getClass());
		Assert.assertEquals(ComputerPlayer.class, players.get("Plum").getClass());
	}
	
	// test that weapons is populated and of card type weapon
	@Test
	public void testWeapons() {
		ArrayList<Card> weapons = board.getWeapons();
		Assert.assertTrue(weapons != null);
		Assert.assertTrue(weapons.get(1).getCardType() == CardType.WEAPON);
	}
	
	// tests that all players are mapped the the correct location
	@Test
	public void testLocations() {
		Map<String, Player> players = board.getPlayers();
		Assert.assertEquals(board.getCell(6, 5), players.get("Mustard").getLocation()); 
		Assert.assertEquals(board.getCell(6, 15), players.get("Scarlet").getLocation()); 
		Assert.assertEquals(board.getCell(15, 15), players.get("Green").getLocation()); 
		Assert.assertEquals(board.getCell(15, 8), players.get("Peacock").getLocation()); 
		Assert.assertEquals(board.getCell(11, 9), players.get("White").getLocation()); 
		Assert.assertEquals(board.getCell(11, 14), players.get("Plum").getLocation()); 
	}
	
	// test all of the players hands after the cards are dealt
	@Test
	public void testHand() {
		Map<String, Player> players = board.getPlayers();
		ArrayList<Card> hand = players.get("Mustard").getHand();
		Assert.assertNotEquals(null, hand);
		ArrayList<Card> handTwo = players.get("Scarlet").getHand();
		Assert.assertNotEquals(null, handTwo);
		ArrayList<Card> handThree = players.get("Green").getHand();
		Assert.assertNotEquals(null, handThree);
		ArrayList<Card> handFour = players.get("Peacock").getHand();
		Assert.assertNotEquals(null, handFour);
		ArrayList<Card> handFive = players.get("White").getHand();
		Assert.assertNotEquals(null, handFive);
		ArrayList<Card> handSix = players.get("Plum").getHand();
		Assert.assertNotEquals(null, handSix);				
	}
	
	// test that the solution class works
	@Test
	public void testSolution() {
		Card person = new Card("Test", CardType.PERSON);
		Card room = new Card("Room", CardType.ROOM);
		Card weapon = new Card("Weapon", CardType.WEAPON);
		Solution answers = new Solution(person, room, weapon);
		Assert.assertTrue(answers != null);
		Assert.assertTrue(answers.getPerson().getCardType() == CardType.PERSON);
		Assert.assertTrue(answers.getRoom().getCardType() == CardType.ROOM);
		Assert.assertTrue(answers.getWeapon().getCardType() == CardType.WEAPON);
	}

}

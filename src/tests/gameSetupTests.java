package tests;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import clueGame.Board;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;

public class gameSetupTests {
		
	public static final int NUM_ROOMS = 9;
	public static final int NUM_PLAYER_CARDS = 6;
	public static final int NUM_WEAPON_CARDS = 6;
	public static final int NUM_DECK_CARDS = 21;
	
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}
	
	@Test
	public void checkNumPlayers() {
		Map<String, Player> players = board.getPlayers();
		assertEquals(NUM_PLAYER_CARDS, players.size());
	}
	@Test
	public void checkNumWeapons() {
		Set<String> weapons = board.getWeapons();
		assertEquals(NUM_WEAPON_CARDS, weapons.size());
	}
	@Test
	public void checkNumDeck() {
		ArrayList<Card> deck = board.getDeck();
		assertEquals(NUM_DECK_CARDS, deck.size());
	}
	
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
	
	@Test
	public void testWeapons() {
		Set<String> weapons = board.getWeapons();
		Assert.assertEquals(NUM_WEAPON_CARDS, weapons.size());
		Assert.assertTrue(weapons.contains("knife"));
		Assert.assertTrue(weapons.contains("rope"));
		Assert.assertTrue(weapons.contains("dumbbell"));
		Assert.assertTrue(weapons.contains("poison"));
		Assert.assertTrue(weapons.contains("pipe"));
		Assert.assertTrue(weapons.contains("revolver"));
	}
	
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
}

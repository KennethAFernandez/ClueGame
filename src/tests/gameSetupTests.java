package tests;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import clueGame.Board;

public class gameSetupTests {
	
	
	public static final int NUM_ROOMS = 9;
	public static final int NUM_PLAYER_CARDS = 6;
	public static final int NUM_WEAPON_CARDS = 9;
	public static final int NUM_DECK_CARDS = 21;
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}
	
	@Test
	public void testPlayerNames() {
		Map<String, Color> players = board.getPlayers();
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
		Map<String, Color> players = board.getPlayers();
		Assert.assertEquals(Color.YELLOW, players.get("Mustard"));
		Assert.assertEquals(Color.RED, players.get("Scarlet"));
		Assert.assertEquals(Color.GREEN, players.get("Green"));
		Assert.assertEquals(Color.BLUE, players.get("Peacock"));
		Assert.assertEquals(Color.WHITE, players.get("White"));
		Assert.assertEquals(Color.BLACK, players.get("Plum"));
	}
	
	@Test
	public void testDeckSize() {
		int size = board.getPlayers().size() + board.getWeapons().size();
		Assert.assertEquals(NUM_DECK_CARDS, size);
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

}

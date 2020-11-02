package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;


public class BoardAdjTargetTest {

	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		board.initialize();
	}

	
	// test locations with only walkways as adj Locations

	@Test
	public void testLocWalkways() {
		Set<BoardCell> testList = board.getAdjList(6, 9);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(6, 8)));
		assertTrue(testList.contains(board.getCell(6,10)));
		testList = board.getAdjList(5, 8);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(5, 7)));
		assertTrue(testList.contains(board.getCell(6, 8)));
		assertTrue(testList.contains(board.getCell(5, 9)));
		
	}
	
	

	// locations within rooms not center (empty adj List)
	@Test
	public void testNonCenterAdjList() {
		Set<BoardCell> testList = board.getAdjList(5, 21);
		assertEquals(0, testList.size());
		assertFalse(testList.contains(board.getCell(5, 22)));
	}

	// testing edge locations
	@Test
	public void testEdgeLocs() {
		BoardCell cell = board.getCell(6, 0);
		board.calcTargets(cell, 1);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getCell(5, 0)));
		Assert.assertTrue(targets.contains(board.getCell(7, 0)));
		cell = board.getCell(0, 5);
		board.calcTargets(cell, 1);
		targets = board.getTargets();
		Assert.assertFalse(targets.contains(board.getCell(0, 6)));
		Assert.assertTrue(targets.contains(board.getCell(1, 5)));
	}

	// testing locations next to a room that is not a doorway
	@Test
	public void testLocNextToRoom() {
		BoardCell cell = board.getCell(8,8);
		board.calcTargets(cell, 1);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertFalse(targets.contains(board.getCell(8, 7)));
		Assert.assertFalse(targets.contains(board.getCell(9, 7)));
	}

	// testing locations that are doorways
	@Test
	public void testDoorways() {
		Set<BoardCell> testList = board.getAdjList(15,4);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(15,3)));
		assertTrue(testList.contains(board.getCell(16,4)));
	}
	// testing locations that allow passage through door
	@Test
	public void testPassage() {
		Set<BoardCell> testList = board.getAdjList(5, 4);
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertTrue(testList.contains(board.getCell(6, 4)));
	}


	// along walkways, test various cells
	@Test
	public void testTargetsVarious() {
		BoardCell cell = board.getCell(9, 15);
		board.calcTargets(cell, 2);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(5, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(8, 16)));
		Assert.assertTrue(targets.contains(board.getCell(8, 14)));
		Assert.assertTrue(targets.contains(board.getCell(11, 15)));
		Assert.assertTrue(targets.contains(board.getCell(7, 15)));
	}
	// test if user can enter room
	@Test
	public void testEnterRoom() {
		BoardCell cell = board.getCell(8,16);
		board.calcTargets(cell, 2);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getCell(11, 20)));

	}
	// test targets does not include places it should not
	@Test
	public void testTargetsNoPassage() {
		BoardCell cell = board.getCell(5,11);
		board.calcTargets(cell, 2);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertFalse(targets.contains(board.getCell(4, 13)));
	}
	// test targets should include places
	@Test
	public void testTargetsPassage() {
		Set<BoardCell> testList = board.getAdjList(20, 19);
		assertTrue(testList.contains(board.getCell(3,2)));		
	}
	// test occupied and blocking 
	@Test
	public void testBlocking() {
		board.getCell(14, 15).setOccupied(true);
		BoardCell cell = board.getCell(14,14);
		board.calcTargets(cell, 1);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertFalse(targets.contains(board.getCell(14,15)));
	}
}

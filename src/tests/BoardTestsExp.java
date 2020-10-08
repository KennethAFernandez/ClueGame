// Authors: Kenneth Fernandez and Asher Rubin
package tests;
import java.util.Set;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import experiment.TestBoard;
import experiment.TestBoardCell;

public class BoardTestsExp {
	TestBoard board;

	@BeforeEach
	public void setUp() { // given code
		board = new TestBoard(); // creates new board
	}
	
	@Test
	public void testAdjacency() { // tests adjacency, given code
		TestBoardCell cell = board.getCell(0,0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(0, 1)));
		Assert.assertEquals(2, testList.size());
	}
	@Test
	public void topLeftCorner() {  // test top left corner [0,0]
		TestBoardCell cell = board.getCell(1,0); 
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(0, 0))); 
	}
	@Test 
	public void bottomRightCorner() { // checks bottom right corner 
		TestBoardCell cell = board.getCell(3,2);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(3, 3)));
	}
	@Test
	public void rightEdge() { // checks right edge [0,2]
		TestBoardCell cell = board.getCell(0,3);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(0, 2)));
		Assert.assertTrue(testList.contains(board.getCell(1, 3)));
	}
	@Test
	public void leftEdge() { //checks left edge [3,0]
		TestBoardCell cell = board.getCell(2,0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(3, 0)));
	}
	@Test
	public void testMiddleSquareOfGrid() { // tests middle of grid [2,2]
		TestBoardCell cell = board.getCell(2, 1);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(2, 2)));
	}
	
	
	
	@Test
	public void testTargetsNormal() { // given code to test movement
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
	}
	@Test
	public void testRoom() { // tests room application
		board.getCell(1, 0).setRoom(true); // sets the room
		TestBoardCell cell = board.getCell(0, 0); // gets the cell
		board.calcTargets(cell, 1); // calculate moves if path is one
		Set<TestBoardCell> targets = board.getTargets(); 
		Assert.assertTrue(targets.contains(board.getCell(1, 0))); // if the room is in the list of moveables
	}
	@Test 
	public void testOccupied() { // tests occupied application
		TestBoardCell cell = board.getCell(0,0);
		board.getCell(0, 1).setOccupied(true); // sets a room as occupied
		board.calcTargets(cell, 1);
		Assert.assertEquals(true, board.getCell(0, 1).getOccupied()); // checks if room is considered occupied
	}
	@Test
	public void testTargetsMixed() { // given code to test occipied and room
		board.getCell(0, 2).setOccupied(true);
		board.getCell(1, 2).setRoom(true);
		TestBoardCell cell = board.getCell(0, 3);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
	}


	@Test
	public void multipleSteps() {  // tests multiple steps (2)
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets(); // just checks that all moves are in the set
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
	}
}

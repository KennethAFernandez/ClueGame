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
	public void setUp() {
		board = new TestBoard();
	}
	
	@Test
	public void testAdjacency() { 
		TestBoardCell cell = board.getCell(0,0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(0, 1)));
		Assert.assertEquals(2, testList.size());
	}
	@Test
	public void topLeftCorner() { 
		TestBoardCell cell = board.getCell(0,0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(0, 0)));
	}
	@Test 
	public void bottomRightCorner() { 
		TestBoardCell cell = board.getCell(0,0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(3, 3)));
	}
	@Test
	public void rightEdge() { 
		TestBoardCell cell = board.getCell(0,3);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(0, 2)));
		Assert.assertTrue(testList.contains(board.getCell(1, 3)));
	}
	@Test
	public void leftEdge() { 
		TestBoardCell cell = board.getCell(3,0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(2, 0)));
		Assert.assertTrue(testList.contains(board.getCell(3, 0)));
	}
	@Test
	public void testMiddleSquareOfGrid() { 
		TestBoardCell cell = board.getCell(0, 0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(2, 2)));
	}
	
	
	
	@Test
	public void testTargetsNormal() { 
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
	public void testRoom() {
		board.getCell(1, 0).setRoom(true);
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 1);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
	}
	@Test 
	public void testOccupied() { 
		TestBoardCell cell = board.getCell(0,0);
		board.getCell(0, 1).setOccupied(true);
		board.calcTargets(cell, 1);
		Assert.assertEquals(true, board.getCell(0, 1).getOccupied());
	}
	@Test
	public void testTargetsMixed() {
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
	public void noMoreThanSix() { 
		TestBoardCell cell = board.getCell(0, 3);
		board.calcTargets(cell, 7);
		Assert.assertEquals(true, board.getPath());
	}
	@Test
	public void multipleSteps() { 
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));	
	}
}

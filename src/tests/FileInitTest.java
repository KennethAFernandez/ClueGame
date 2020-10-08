package tests;
import static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.Room;


@SuppressWarnings("unused")
public class FileInitTest {

	public static final int LEGEND_SIZE = 13;
	public static final int NUM_ROWS = 25;
	public static final int NUM_COLUMNS = 24;

	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}
	
	
	// Test number of rooms
	@Test
	public void testNumRooms() {
		Assert.assertEquals(9, board.getAmountRooms());
	}
	
	
	// Test number of rows
	@Test
	public void testRows(){
		Assert.assertEquals(25, board.getNumRows());
	}
	
	
	// Test number of cols
	@Test
	public void testCols() {
		Assert.assertEquals(24, board.getNumColumns());
	}

	
	// Verify at least one direction of each, verify cells w/o door is false
	@Test
	public void testDirectionsDoors() {
		BoardCell cell = board.getCell(0, 0);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		cell = board.getCell(0, 0);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		cell = board.getCell(0, 0);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		cell = board.getCell(0, 0);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
	}
	
	
	// Test correct number of doors were loaded in
	@Test
	public void testLoadingDoors() {
		BoardCell cell = board.getCell(0, 0);
		Assert.assertEquals(9, board.getAmountDoors());
		cell = board.getCell(0, 0);
		assertFalse(cell.isDoorway());
	}
	
	
	// Check some cells, and that they hve correct initial
	@Test
	public void testInitials() {
		Assert.assertEquals('W', board.getCell(0, 6).getName());
		Assert.assertEquals('R', board.getCell(0, 0).getName());
		Assert.assertEquals('C', board.getCell(1, 8).getName());
		Assert.assertEquals('P', board.getCell(2, 12).getName());
		Assert.assertEquals('K', board.getCell(0, 18).getName());
		Assert.assertEquals('L', board.getCell(9, 0).getName());
	}
	
	
	// Check that rooms have the proper cetner cell and label cell
	@Test
	public void testCenterAndLabel() {
		Assert.assertEquals("Main Hall", board.getRoom('M').getName());
		Assert.assertEquals("School", board.getRoom('S').getName());
		Assert.assertEquals("Closet", board.getRoom('C').getName());
	}
}

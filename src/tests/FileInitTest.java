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

	// Constants to test with
	public static final int LEGEND_SIZE = 13;
	public static final int NUM_ROWS = 25;
	public static final int NUM_COLUMNS = 24;

	// One instance of board
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
		Assert.assertEquals(11, board.getAmountRooms());
	}
	
	
	// Test number of rows
	@Test
	public void testRows(){
		Assert.assertEquals(24, board.getNumRows());
	}
	
	
	// Test number of cols
	@Test
	public void testCols() {
		Assert.assertEquals(25, board.getNumColumns());
	}

	
	// Verify at least one direction of each, verify cells w/o door is false
	@Test
	public void testDirectionsDoors() {
		BoardCell cell = board.getCell(5,4);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		cell = board.getCell(5,7);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		cell = board.getCell(5,13);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		cell = board.getCell(17,7);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		
		cell = board.getCell(0, 0);
		assertFalse(cell.isDoorway());
	}
	
	
	// Test correct number of doors were loaded in, and that a cell with no door isn't constituted as false
	@Test
	public void testLoadingDoors() {
		int counter = 0;
		for(int i = 0; i < board.getNumRows(); i++) {
			for(int j = 0; j< board.getNumColumns(); j++) {
				BoardCell cell = board.getCell(i, j);
				if(cell.isDoorway())
					counter++;
			}
		}
		Assert.assertEquals(9, counter);
	}
	
	
	// Check some cells, and that they hve correct initial
	@Test
	public void testInitials() {
		Assert.assertEquals('W', board.getCell(0, 5).getInitial());
		Assert.assertEquals('R', board.getCell(0, 0).getInitial());
		Assert.assertEquals('C', board.getCell(0, 7).getInitial());
		Assert.assertEquals('P', board.getCell(1, 11).getInitial());
		Assert.assertEquals('K', board.getCell(0, 17).getInitial());
		Assert.assertEquals('L', board.getCell(8, 0).getInitial());
	}
	
	
	// Check that rooms have the proper cetner cell and label cell
	@Test
	public void testCenterAndLabel() {
		Assert.assertEquals("Main Hall", board.getRoom('M').getName());
		Assert.assertEquals("Cooking Room", board.getRoom('K').getName());
		Assert.assertEquals("Relaxing Room", board.getRoom('R').getName());
		Assert.assertEquals("Ping Pong Room", board.getRoom('P').getName());
		Assert.assertEquals("Library", board.getRoom('L').getName());
		Assert.assertEquals("Dining Room", board.getRoom('D').getName());
		Assert.assertEquals("TV Room", board.getRoom('T').getName());
		Assert.assertEquals("School", board.getRoom('S').getName());
		Assert.assertEquals("Closet", board.getRoom('C').getName());

	}
}

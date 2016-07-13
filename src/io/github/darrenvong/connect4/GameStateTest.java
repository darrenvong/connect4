package io.github.darrenvong.connect4;

import static org.junit.Assert.*;
import org.junit.*;


/**
 * @author Ka (Darren) Vong
 */

public class GameStateTest {

	//Instance variable representing the current game state of a Connect 4 game
	Connect4GameState state;
	
	@Before
	/**
	 * Creates a new copy of the game state and starts the game (initialise all the slot
	 * on the game board to empty) before the start of each tests
	 */
	public void setUp() {
		state = new GameState();
		state.startGame();
	}

	@Test
	/**
	 * Positive test for the getCounterAt() method to see if it works as expected
	 */
	public void testGetCounterAt() {
		int result = state.getCounterAt(2, 1);
		assertEquals(Connect4GameState.EMPTY, result);
	}
	
	@Test(expected=IllegalRowException.class)
	/**
	 * Tests whether an IllegalRowException will be correctly thrown when 
	 * attempting to get a counter at a high (out of bound) index
	 */
	public void testIllegalHighRowGetCounter() {
		state.getCounterAt(2, 6);
	}
	
	@Test(expected=IllegalRowException.class)
	/**
	 * Tests whether an IllegalRowException will be correctly thrown when 
	 * attempting to get a counter at a low (out of bound) index
	 */
	public void testIllegalLowRowGetCounter() {
		state.getCounterAt(2, -1);
	}
	
	@Test(expected=IllegalColumnException.class)
	/**
	 * Tests whether an IllegalColumnException will be correctly thrown when 
	 * attempting to get a counter at a low (out of bound) index
	 */
	public void testIllegalLowColGetCounter() {
		state.getCounterAt(-1, 1);
	}
	
	@Test(expected=IllegalColumnException.class)
	/**
	 * Tests whether an IllegalColumnException will be correctly thrown when 
	 * attempting to get a counter at a high (out of bound) index
	 */
	public void testIllegalHighColGetCounter() {
		state.getCounterAt(7, 1);
	}
	
	@Test
	/**
	 * Tests the move() method and see if it works as expected
	 */
	public void testMove() throws ColumnFullException {
		state.move(3);
		int result = state.getCounterAt(3, 0);
		assertEquals(Connect4GameState.RED, result);
	}
	
	@Test(expected=IllegalColumnException.class)
	/**
	 * Tests whether an IllegalColumnException will be correctly thrown if a move attempt
	 * to a low, out of bound index is made
	 */
	public void testLowColIllegalMove() throws IllegalColumnException, ColumnFullException {
		state.move(-4);
	}
	
	@Test(expected=IllegalColumnException.class)
	/**
	 * Tests whether an IllegalColumnException will be correctly thrown if a move attempt
	 * to a high, out of bound index is made
	 */
	public void testHighColIllegalMove() throws IllegalColumnException, ColumnFullException {
		state.move(54);
	}
	
	@Test(expected=ColumnFullException.class)
	/**
	 * Tests whether a ColumnFullException will be correctly thrown if a move attempt
	 * to a fully filled up column is made
	 */
	public void testFullColMove() throws ColumnFullException {
		for (int i=0; i<7; i++) {
			state.move(3);
		}
	}
	
	@Test
	/**
	 * Tests whether the whoseTurn() is working correctly. In this test, the constant YELLOW
	 * should be returned since RED has just made a move
	 */
	public void testYellowTurn() throws ColumnFullException {
		state.move(3);
		assertEquals(Connect4GameState.YELLOW, state.whoseTurn());
	}
	
	@Test
	/**
	 * Tests whether the whoseTurn() is working correctly. In this test, the constant RED
	 * should be returned since YELLOW has just made the last move
	 * @throws Exception when an illegal move is made (the specific exception thrown here
	 * is not the main interest in this test)
	 */
	public void testRedTurn() throws Exception {
		state.move(3);
		state.move(5);
		assertEquals(Connect4GameState.RED, state.whoseTurn());
	}
	
	@Test
	/**
	 * Tests whether the getWinner() method can correctly find a 4-in-a-row 
	 * horizontal RED winner
	 */
	public void testHorizontalRedWinner() throws Exception {
		for (int i=0, j=3; i<=7 && j<7; i++) {
			if (i%2 == 0) {
				state.move(j);
				j++;
			}
			else
				state.move(0);
		}
		assertEquals(Connect4GameState.RED, state.getWinner());
	}
	
	@Test
	/**
	 * Tests whether the getWinner() method can correctly find a 4-in-a-row
	 * horizontal YELLOW winner
	 */
	public void testHorizontalYellowWinner() throws Exception {
		for (int i=0, j=3; i<=9 && j<7; i++) {
			if (i%2 == 1) {
				state.move(j);
				if (i<7)
					j++;
			}
			else if (i >= 6)
				state.move(0);
			else
				state.move(j);
		}
		assertEquals(Connect4GameState.YELLOW, state.getWinner());
	}
	
	@Test
	/**
	 * Tests whether the getWinner() method can correctly find a 4-in-a-row
	 * vertical RED winner
	 */
	public void testVerticalRedWinner() throws Exception {
		for (int i=0; i<7; i++) {
			if (i%2 == 0)
				state.move(5);
			else
				state.move(4);
		}
		assertEquals(Connect4GameState.RED, state.getWinner());
	}
	
	@Test
	/**
	 * Tests whether the getWinner() method can correctly find a 4-in-a-row
	 * vertical YELLOW winner
	 */
	public void testVerticalYellowWinner() throws Exception {
		for (int i=0; i<=7; i++) {
			if (i%2 == 1 || i == 0)
				state.move(1);
			else
				state.move(0);
		}
		assertEquals(Connect4GameState.YELLOW, state.getWinner());
	}
	
	@Test
	/**
	 * Tests whether the getWinner() method can correctly find a 4-in-a-row 
	 * upward diagonal RED winner
	 */
	public void testUpDRedWinner() throws Exception {
		int[] colMoves = {0, 2, 2, 3, 0, 3, 3, 4, 0, 4, 0, 4, 4, 5, 0, 5, 1, 5, 1, 5, 5};
		for (int moves : colMoves)
			state.move(moves);
		assertEquals(Connect4GameState.RED, state.getWinner());
	}
	
	@Test
	/**
	 * Tests whether the getWinner() method can correctly find a 4-in-a-row 
	 * upward diagonal YELLOW winner
	 */
	public void testUpDYellowWinner() throws Exception {
		int[] colMoves = {4, 3, 5, 4, 5, 5, 6, 3, 6, 3, 6, 6};
		for (int moves : colMoves)
			state.move(moves);
		assertEquals(Connect4GameState.YELLOW, state.getWinner());
	}
	
	@Test
	/**
	 * Tests whether the getWinner() method can correctly find a 4-in-a-row 
	 * downward diagonal RED winner
	 */
	public void testDownDRedWinner() throws Exception {
		int[] colMoves = {6, 5, 5, 4, 0, 4, 4, 3, 0, 3, 0, 3, 3};
		for (int moves : colMoves)
			state.move(moves);
		assertEquals(Connect4GameState.RED, state.getWinner());
	}
	
	@Test
	/**
	 * Tests whether the getWinner() method can correctly find a 4-in-a-row 
	 * downward diagonal YELLOW winner
	 */
	public void testDownDYellowWinner() throws Exception {
		int[] colMoves = {4, 4, 3, 5, 3, 3, 2, 5, 2, 5, 2, 2, 1, 5, 1, 5, 1, 5, 1, 1};
		for (int moves : colMoves)
			state.move(moves);
		assertEquals(Connect4GameState.YELLOW, state.getWinner());
	}
	
	/* The following group of tests examine whether the counter counts properly. For instance,
	* it may count 4 in a row merely because there are 4 counters in the row but
	* not necessarily next to each other */
	@Test
	/**
	 * Tests whether the existence of a fake horizontal 4-in-a-row consisting of 
	 * 4 reds in same row but with a yellow counter in-between would cause 
	 * the getWinner() method to fail
	 */
	public void testFakeHorizontal4InARow() throws Exception {
		int[] colMoves = {1, 2, 3, 0, 4, 6, 5};
		for (int moves : colMoves)
			state.move(moves);
		assertEquals(Connect4GameState.EMPTY, state.getWinner());
	}
	@Test
	/**
	 * Tests whether a fake horizontal 4-in-a-row composing of 2 groups of 2 reds 
	 * in bottom row (although not together) would cause the getWinner() to fail
	 */
	public void testFakeHorizontal4InARow2() throws Exception {
		int[] colMoves = {2, 0, 3, 4, 5, 1, 6};
		for (int moves : colMoves)
			state.move(moves);
		assertEquals(Connect4GameState.EMPTY, state.getWinner());
	}
	@Test
	/**
	 * Tests whether a fake vertical 4-in-a-row at centre of board - 
	 * composing of 2 groups of 2 reds in same col would cause getWinner() to fail
	 */
	public void testFakeVertical4InARow() throws Exception {
		for (int i=0; i<7; i++) {
			if (i == 1 || i == 5)
				state.move(2);
			else
				state.move(3);
		}
		assertEquals(Connect4GameState.EMPTY, state.getWinner());
	}
	@Test
	/**
	 * Tests whether a fake diagonal starting from row 0, col 2
	 * (look up and forward/rightward) would cause getWinner() to fail
	 */
	public void testFakeDiagonal4InARow() throws Exception {
		int[] colMoves = {2, 3, 3, 4, 4, 5, 4, 2, 6, 5, 5, 5, 6, 6, 3, 6, 6};
		for (int moves : colMoves)
			state.move(moves);
		assertEquals(Connect4GameState.EMPTY, state.getWinner());
	}
	@Test
	/**
	 * Tests whether a fake diagonal 4-in-a-row starting from row 0, col 5
	 * (look up and backward/leftward) would cause getWinner() to fail
	 */
	public void testFakeDiagonal4InARow2() throws Exception {
		int[] colMoves = {5, 4, 4, 3, 3, 3, 2, 2, 2, 1, 2, 1, 1, 0, 1, 0, 1};
		for (int moves : colMoves)
			state.move(moves);
		assertEquals(Connect4GameState.EMPTY, state.getWinner());
	}
	
	@Test
	/**
	 * Tests whether the gameOver() method works as intended. In this case, game should
	 * be over since red wins
	 */
	public void testGameOver() throws Exception {
		int[] colMoves = {6, 5, 5, 4, 0, 4, 4, 3, 0, 3, 0, 3, 3};
		for (int moves : colMoves)
			state.move(moves);
		
		assertTrue(state.gameOver());
		assertEquals("Red wins", Connect4GameState.RED, state.getWinner());
	}
	
	@Test
	/**
	 * Tests whether isColumnFull() works properly. Here it shouldn't be full
	 * (i.e. returning false) since the game just started and no moves have been made
	 */
	public void testisColFull() throws IllegalColumnException {
		assertFalse(state.isColumnFull(3));
	}
	
	@Test(expected=IllegalColumnException.class)
	/**
	 * Tests whether an IllegalColumnException is thrown correctly when a column of low,
	 * out of bound index is checked 
	 */
	public void testillegalLowIsColFull() throws IllegalColumnException {
		state.isColumnFull(-1);
	}
	
	@Test(expected=IllegalColumnException.class)
	/**
	 * Tests whether an IllegalColumnException is thrown correctly when a column of high,
	 * out of bound index is checked 
	 */
	public void testillegalHighIsColFull() throws IllegalColumnException {
		state.isColumnFull(7);
	}
	
	@Test
	/**
	 * Tests whether the gameOver(), isBoardFull() and getWinner() methods work 
	 * in the case of a draw (i.e. full game board). gameOver() should return true,
	 * getWinner() should return EMPTY and isBoardFull() should return true
	 * @throws Exception when an illegal move is made (the specific exception thrown here is not
	 * the main interest in this test)
	 */
	public void testGameDraw() throws Exception {
		int[] colMoves = {3, 4, 2, 0, 3, 6, 5, 1, 6, 1, 2, 0, 0, 4, 1, 5,
				5, 2, 4, 3, 2, 6, 3, 1, 6, 5, 4, 0, 0, 3, 1, 0, 2, 1, 4,
				4, 5, 5, 3, 2, 6, 6};
		for (int moves : colMoves)
			state.move(moves);
		//Expecting it to be game over (as board is full) and therefore a draw
		assertTrue(state.gameOver());
		assertTrue(state.isBoardFull());
		assertEquals(Connect4GameState.EMPTY, state.getWinner());
	}
	
	@Test
	/**
	 * Positive test - the game only just started so board shouldn't be full
	 */
	public void testEmptyBoard() {
		assertFalse(state.isBoardFull());
	}
	
	@Test
	/**
	 * Tests whether the game state has been copied properly by the copy() method
	 */
	public void testCopy() throws Exception {
		state.move(3);
		state.move(4);
		Connect4GameState gameCopy = state.copy();
		assertEquals("Red moved here", Connect4GameState.RED, gameCopy.getCounterAt(3, 0));
		assertEquals("Yellow moved here", Connect4GameState.YELLOW, gameCopy.getCounterAt(4, 0));
	}
	
	@Test
	/**
	 * Positive test - making a copy of game board at the start of the game should return
	 * EMPTY on all 42 possible slots
	 */
	public void testCopyPositive() {
		Connect4GameState gameCopy = state.copy();
		for (int row=0; row<Connect4GameState.NUM_ROWS; row++) {
			for (int col=0; col<Connect4GameState.NUM_COLS; col++) {
				assertEquals(Connect4GameState.EMPTY, gameCopy.getCounterAt(col, row));
			}
		}
	}
	
	@Test
	/**
	 * Tests whether making a move at the game state copy would affect the main game state.
	 * In other words, this would happen if a shallow copy has been made
	 */
	public void testDeepCopy() throws Exception {
		state.move(3);
		Connect4GameState gameCopy = state.copy();
		gameCopy.move(4);
		assertEquals(Connect4GameState.EMPTY, state.getCounterAt(4, 0));
	}

}

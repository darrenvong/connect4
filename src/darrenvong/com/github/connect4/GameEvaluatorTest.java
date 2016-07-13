package darrenvong.com.github.connect4;

import static org.junit.Assert.*;

import org.junit.*;
 
import static darrenvong.com.github.connect4.Connect4GameState.*;

public class GameEvaluatorTest {

	Connect4GameState gs;
	@Before
	public void setUp() {
		gs = new GameState();
		gs.startGame();
	}
	
	@Test
	public void testNumInARowLineFound() throws Exception {
		int[] colMoves = {3, 0, 3};
		for (int moves: colMoves)
			gs.move(moves);
		GameEvaluator ge = new GameEvaluator(gs);
		int[][] data = ge.inspectGameState();
		
		assertEquals("14 1-in-a-row but got diff value", 14, data[RED][0]);
		assertEquals("1 2-in-a-row but got diff value", 1, data[RED][1]);
		assertEquals("0 3-in-a-row but got diff value", 0, data[RED][2]);
		assertEquals("2 1-in-a-row but got diff value", 2, data[YELLOW][0]);
		assertEquals("0 2-in-a-row but got diff value", 0, data[YELLOW][1]);
		assertEquals("0 3-in-a-row but got diff value", 0, data[YELLOW][2]);
	}
	
	@Test
	//Testing the method against a more complicated game state
	public void testNumInARowLineFound2() throws Exception {
		int[] colMoves = {0, 3, 3, 4, 2, 5, 6, 4, 4, 3, 5, 3, 5, 5,
				1, 4, 2, 3, 3, 5, 0, 1};
		for (int moves: colMoves)
			gs.move(moves);
		GameEvaluator ge = new GameEvaluator(gs);
		int[][] data = ge.inspectGameState();
		
		assertEquals("9 1-in-a-row but got diff value", 9, data[RED][0]);
		assertEquals("2 2-in-a-row but got diff value", 2, data[RED][1]);
		assertEquals("0 3-in-a-row but got diff value", 0, data[RED][2]);
		assertEquals("8 1-in-a-row but got diff value", 8, data[YELLOW][0]);
		assertEquals("5 2-in-a-row but got diff value", 5, data[YELLOW][1]);
		assertEquals("4 3-in-a-row but got diff value", 4, data[YELLOW][2]);
	}
	
	@Test
	public void testEvaluationFunctionYellowTurn() throws Exception {
		String msg = "14 1-in-a-row, 1 2-in-a-row for red and 2 1-in-a-row for yellow "+
				"should give -22, but different score obtained";
		int[] colMoves = {3, 0, 3};
		for (int moves: colMoves)
			gs.move(moves);
		GameEvaluator ge = new GameEvaluator(gs);
		assertEquals(msg, -22, ge.evaluateGameState(YELLOW));
	}
	
	@Test
	public void testEvaluationFunctionRedTurn() throws Exception {
		String msg = "2 1-in-a-row for red, 6 1-in-a-row for yellow gives -4, but "+
				"different score obtained";
		int[] colMoves = {6, 3};
		for (int moves: colMoves)
			gs.move(moves);
		GameEvaluator ge = new GameEvaluator(gs);
		assertEquals(msg, -4, ge.evaluateGameState(RED));
	}
	
	@Test
	public void testBlockWinningLineScoreRedTurn() throws Exception {
		String msg = "When Max is red, should return higher score when successfully "+
				"blocked a yellow win, yet this is not the case";
		int[] colMoves = {4, 3, 5, 4, 5, 5, 6, 6, 6, 2};
		for (int moves: colMoves)
			gs.move(moves);
		Connect4GameState blockWinState = gs.copy();
		blockWinState.move(6);
		gs.move(4);
		GameEvaluator ge = new GameEvaluator(gs);
		GameEvaluator geBlocked = new GameEvaluator(blockWinState);
		assertTrue(msg,
				geBlocked.evaluateGameState(RED) > ge.evaluateGameState(RED));
	}

	@Test
	public void testBlockWinningLineScoreYellowTurn() throws Exception {
		String msg = "When Max is yellow, should return higher score when successfully "+
				"blocked a red win, yet this is not the case";
		int[] colMoves = {3, 4, 2, 3, 1};
		for (int moves: colMoves)
			gs.move(moves);
		Connect4GameState blockWinState = gs.copy();
		blockWinState.move(0);
		gs.move(3);
		GameEvaluator ge = new GameEvaluator(gs);
		GameEvaluator geBlocked = new GameEvaluator(blockWinState);
		assertTrue(msg,
				geBlocked.evaluateGameState(YELLOW) > ge.evaluateGameState(YELLOW));
	}

}

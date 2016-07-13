package darrenvong.com.github.connect4;

import static org.junit.Assert.*;

import org.junit.*;

import static darrenvong.com.github.connect4.Connect4GameState.*;

public class OneMoveLookAheadPlayerTest {

	Connect4GameState gs;
	OneMoveLookAheadPlayer alice;
	
	@Before
	public void setUp() {
		gs = new GameState();
		gs.startGame();
		alice = new OneMoveLookAheadPlayer();
	}

	@Test
	public void testChooseMove() {
		String msg = "Should choose col 3, but different col chosen instead";
		assertEquals(msg, 3, alice.chooseMove(gs));
	}
	
	@Test
	public void testEvaluationFunctionYellowTurn() throws Exception {
		String msg = "Expects a score of 429, yet different score returned";
		int[] colMoves = {0, 3, 3, 4, 2, 5, 6, 4, 4, 3, 5, 3, 5, 5,
				1, 4, 2, 3, 3, 5, 0, 1};
		for (int moves: colMoves)
			gs.move(moves);
		assertEquals(msg, 429, alice.evaluateGameState(gs, YELLOW));
	}
	
	@Test
	public void testEvaluationFunctionRedTurn() throws Exception {
		String msg = "Expects large negative score (less than -1000) when Alice is "+
				"not blocking a winning line, yet this is not the case";
		int[] colMoves = {3, 3, 3, 4, 2, 2, 5, 1, 5, 1, 3};
		for (int moves: colMoves)
			gs.move(moves);
		assertTrue(msg, alice.evaluateGameState(gs, RED) < -1000);
	}
	
	@Test
	public void testBlockWinningLineRedTurn() throws Exception {
		String msg = "Score returned should be higher when successfully blocked "+
				"opponent's winning line, yet this is not the case";
		int[] colMoves = {3, 3, 3, 4, 2, 2, 5, 1, 5, 1};
		for (int moves: colMoves)
			gs.move(moves);
		Connect4GameState blockWinner = gs.copy();
		blockWinner.move(4);
		gs.move(3);
		assertTrue(msg,
				alice.evaluateGameState(blockWinner, RED) > alice.evaluateGameState(gs, RED));
	}
	
	@Test
	public void testBlockWinningLineYellowTurn() throws Exception {
		String msg = "Score returned should be higher when successfully blocked "+
				"opponent's winning line, yet this is not the case";
		int[] colMoves = {3, 4, 5, 4, 5, 3, 5};
		for (int moves: colMoves)
			gs.move(moves);
		Connect4GameState blockWinner = gs.copy();
		blockWinner.move(5);
		gs.move(6);
		assertTrue(msg,
				alice.evaluateGameState(blockWinner, YELLOW) > alice.evaluateGameState(gs, YELLOW));
	}
	
	@Test
	public void testWinningMoveRed() throws Exception {
		String msg = "Winning red move available, yet 1000000 is not the score returned";
		int[] colMoves = {3, 3, 4, 4, 5, 5, 6};
		for (int moves: colMoves)
			gs.move(moves);
		assertEquals(msg, 1000000, alice.evaluateGameState(gs, RED));
	}
	
	@Test
	public void testWinningMoveYellow() throws Exception {
		String msg = "Winning yellow move available, yet 1000000 is not the score returned";
		int[] colMoves = {3, 3, 0, 3, 0, 3, 2, 3};
		for (int moves: colMoves)
			gs.move(moves);
		assertEquals(msg, 1000000, alice.evaluateGameState(gs, YELLOW));
	}

}

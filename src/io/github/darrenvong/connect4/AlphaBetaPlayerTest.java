package io.github.darrenvong.connect4;

import static org.junit.Assert.*;

import org.junit.*;

import static io.github.darrenvong.connect4.Connect4GameState.*;
import static java.lang.Integer.*;

public class AlphaBetaPlayerTest {

	Connect4GameState gs;
	AlphaBetaPlayer eva;
	
	@Before
	public void setUp() {
		gs = new GameState();
		gs.startGame();
		eva = new AlphaBetaPlayer();
	}

	@Test
	public void alphabetaRedTurn() {
		String msg = "score should be -3, like minimax has returned, yet diff score found";
		assertEquals(msg, -3, eva.alphabeta(gs, MIN_VALUE, MAX_VALUE, 2, RED, true));
	}
	
	@Test
	public void alphabetaYellowTurn() throws Exception {
		String msg = "score should be -20 like minimax has returned, yet diff score found";
		gs.move(3);
		assertEquals(msg, -20, eva.alphabeta(gs, MIN_VALUE, MAX_VALUE, 2, YELLOW, true));
	}
	
	@Test
	public void testMiniMaxWinningMoveRed() throws Exception {
		String msg = "Winning red move available, yet 1000000 is not the score returned";
		int[] colMoves = {3, 4, 3, 4, 3, 4};
		for (int moves: colMoves)
			gs.move(moves);
		assertEquals(msg, 1000000, eva.alphabeta(gs, MIN_VALUE, MAX_VALUE, 2, RED, true));
	}
	
	@Test
	public void testMiniMaxWinningMoveYellow() throws Exception {
		String msg = "Winning yellow move available, yet 1000000 is "+
				"not the score returned";
		int[] colMoves = {4, 3, 5, 4, 5, 5, 6, 6, 6};
		for (int moves: colMoves)
			gs.move(moves);
		assertEquals(msg, 1000000, eva.alphabeta(gs, MIN_VALUE, MAX_VALUE, 2, YELLOW, true));
	}
	
	@Test
	public void testAlphaBetaComplexState() throws Exception {
		String msg = "score should be 426 in yellow's perspective, yet diff score returned";
		int[] colMoves = {0, 3, 3, 4, 2, 5, 6, 4, 4, 3, 5, 3, 5, 5,
				1, 4, 2, 3, 3, 5, 0, 1};
		for (int moves: colMoves)
			gs.move(moves);
		assertEquals(msg, 426, eva.alphabeta(gs, MIN_VALUE, MAX_VALUE, 4, YELLOW, true));
	}


}

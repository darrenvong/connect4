package darrenvong.com.github.connect4;

import static org.junit.Assert.*;
import org.junit.*;

import static darrenvong.com.github.connect4.Connect4GameState.*;

public class MinimaxTest {

	Connect4GameState gs;
	MinimaxPlayer max;
	
	@Before
	public void setUp() {
		gs = new GameState();
		gs.startGame();
		max = new MinimaxPlayer();
	}
	
	@Test
	public void testMiniMaxRedTurn() {
		String msg = "score should be -3, yet different score has been returned";
		assertEquals(msg, -3, max.minimax(gs, 2, true, RED));
	}
	
	@Test
	public void testMiniMaxYellowTurn() throws Exception {
		String msg = "score should be -20, yet different score returned";
		gs.move(3);
		assertEquals(msg, -20, max.minimax(gs, 2, true, YELLOW));
	}
	
	@Test
	public void testMiniMaxWinningMoveRed() throws Exception {
		String msg = "Winning red move available, yet 1000000 is not the score returned";
		int[] colMoves = {3, 4, 3, 4, 3, 4};
		for (int moves: colMoves)
			gs.move(moves);
		assertEquals(msg, 1000000, max.minimax(gs, 2, true, RED));
	}
	
	@Test
	public void testMiniMaxWinningMoveYellow() throws Exception {
		String msg = "Winning yellow move available, yet 1000000 is "+
				"not the score returned";
		int[] colMoves = {4, 3, 5, 4, 5, 5, 6, 6, 6};
		for (int moves: colMoves)
			gs.move(moves);
		assertEquals(msg, 1000000, max.minimax(gs, 2, true, YELLOW));
	}
	
	@Test
	public void testMiniMaxComplexState() throws Exception {
		String msg = "score should be 429 in yellow's perspective, yet diff score returned";
		int[] colMoves = {0, 3, 3, 4, 2, 5, 6, 4, 4, 3, 5, 3, 5, 5,
				1, 4, 2, 3, 3, 5, 0, 1};
		for (int moves: colMoves)
			gs.move(moves);
		assertEquals(msg, 426, max.minimax(gs, 4, true, YELLOW));
	}
}

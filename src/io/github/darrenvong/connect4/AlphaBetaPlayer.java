package io.github.darrenvong.connect4;

import static java.lang.Integer.*;
import static io.github.darrenvong.connect4.Connect4GameState.*;

/**
 * @author Ka (Darren) Vong
 */

public class AlphaBetaPlayer extends Connect4Player {

	private static final int NUM_MOVES_LOOK_AHEAD = 6;
	
	private int bestMove;
	private GameEvaluator gameEvaluator;
	
	/**
	 * Creates an instance of the minimax player which uses alpha=beta pruning
	 */
	public AlphaBetaPlayer() {
		bestMove = 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void makeMove(Connect4GameState gameState) {
		int myCounter = gameState.whoseTurn();
		alphabeta(gameState, MIN_VALUE, MAX_VALUE, NUM_MOVES_LOOK_AHEAD, myCounter, true);
		try {
			gameState.move(bestMove);
			System.out.println("Eva dropped a counter in column "+bestMove);
		} catch (ColumnFullException e) {
			throw new RuntimeException();
		}

	}
	
	/**
	 * Chooses the best move by maximising the minimum gain given by opponent. This is
	 * developing on minimax by pruning off redundant child game nodes
	 * @param gs the game state to be copied and tried on
	 * @param alpha best score found so far by this player (always the maximising player)
	 * @param beta best score found so far by opponent (the minimising player)
	 * @param depth the number of moves it is looking ahead
	 * @param myCounter the colour of the counter this player is using
	 * @param origin true if method is called from original depth, false otherwise
	 * @return the optimal score for the best move
	 */
	public int alphabeta(Connect4GameState gs, int alpha, int beta, int depth,
			int myCounter, boolean origin) {
		if (depth == 0 || gs.gameOver()) {
			gameEvaluator = new GameEvaluator(gs);
			return gameEvaluator.evaluateGameState(myCounter);
		}
		else if (gs.whoseTurn() == myCounter) {
			//Maximising
			for (int col=0; col<NUM_COLS; col++) {
				Connect4GameState gsCopy = gs.copy();
				try {
					gsCopy.move(col);
					int currentScore = alphabeta(gsCopy, alpha, beta, depth-1,
							myCounter, false);
					if (currentScore > alpha) {
						alpha = currentScore;
						if (origin)
							bestMove = col;
					}
					if (beta <= alpha)
						return beta;
				} catch (ColumnFullException e) {
					continue;
				}
			}
			return alpha;
		}
		else {
			//minimising
			for (int col=0; col<NUM_COLS; col++) {
				Connect4GameState gsCopy = gs.copy();
				try {
					gsCopy.move(col);
					int currentScore = alphabeta(gsCopy, alpha, beta, depth-1,
							myCounter, false);
					if (currentScore < beta)
						beta = currentScore;
					if (beta <= alpha)
						return alpha;
				} catch (ColumnFullException e) {
					continue;
				}
			}
			return beta;
		}
	}
}

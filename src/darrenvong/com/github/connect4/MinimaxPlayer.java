package darrenvong.com.github.connect4;

import static darrenvong.com.github.connect4.Connect4GameState.*;

/**
 * @author Ka (Darren) Vong
 */

public class MinimaxPlayer extends Connect4Player {
	
	private static final int NUM_MOVES_LOOK_AHEAD = 4;
	
	private int bestMove;
	private GameEvaluator gameEvaluator;
	
	/**
	 * Creates an instance of the minimax player
	 */
	public MinimaxPlayer() {
		this.bestMove = 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void makeMove(Connect4GameState gameState) {
		int myCounter = gameState.whoseTurn();
		minimax(gameState, NUM_MOVES_LOOK_AHEAD, true, myCounter);
		try {
			gameState.move(bestMove);
			System.out.println("Max dropped a counter in column "+bestMove);
		} catch (ColumnFullException e) {
			throw new RuntimeException();
		}

	}

	/**
	 * Chooses the best move by maximising the worst moves (i.e. the minimum)
	 * given by the opponent
	 * @param gs the game state to be copied and tried on
	 * @param depth the number of moves it is looking ahead
	 * @param origin true if method is called from original depth, false otherwise
	 * @param myCounter the colour of the counter this player is using
	 * @return the optimal score for the best move
	 */
	public int minimax(Connect4GameState gs, int depth, boolean origin, int myCounter) {
		if (depth == 0 || gs.gameOver()) {
			gameEvaluator = new GameEvaluator(gs);
			return gameEvaluator.evaluateGameState(myCounter);
		}
		else if (gs.whoseTurn() == myCounter) {
			//Maximising
			int bestScore = Integer.MIN_VALUE;
			for (int col=0; col<NUM_COLS; col++) {
				Connect4GameState gsCopy = gs.copy();
				try {
					gsCopy.move(col);
					int currentScore = minimax(gsCopy, depth-1, false, myCounter);
					if (currentScore > bestScore) {
						bestScore = currentScore;
						if (origin)
							bestMove = col;
					}
				} catch (ColumnFullException e) {
					continue;
				}
			}
			return bestScore;
		}
		else if (gs.whoseTurn() == 1-myCounter) {
			//Minimising
			int bestScore = Integer.MAX_VALUE;
			for (int col=0; col<NUM_COLS; col++) {
				Connect4GameState gsCopy = gs.copy();
				try {
					gsCopy.move(col);
					int currentScore = minimax(gsCopy, depth-1, false, myCounter);
					if (currentScore < bestScore)
						bestScore = currentScore;
				} catch (ColumnFullException e) {
					continue;
				}
			}
			return bestScore;
		}
		//Never actually executed
		return 0;
	}
}

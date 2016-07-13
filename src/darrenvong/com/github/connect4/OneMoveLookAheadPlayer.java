package darrenvong.com.github.connect4;

import static darrenvong.com.github.connect4.Connect4GameState.*;

/**
 * @author Ka Vong
 */

public class OneMoveLookAheadPlayer extends Connect4Player {

	/**
	 * {@inheritDoc}
	 */
	public void makeMove(Connect4GameState gameState) {
		try {
			int move = chooseMove(gameState);
			gameState.move(move);
			System.out.println("Alice dropped a counter in column "+move);
		} catch (ColumnFullException e) {
			throw new RuntimeException();
		}

	}
	
	/**
	 * Chooses the next move by trying out all possible moves in each column
	 * @param gs the state of the game to be copied and tried on
	 * @return column number of the best move
	 */
	public int chooseMove(Connect4GameState gs) {
		int bestMove = 0;
		int bestScore = Integer.MIN_VALUE;
		int myCounter = gs.whoseTurn();
		for (int col=0; col<NUM_COLS; col++) {
			Connect4GameState gsCopy = gs.copy();
			try {
				gsCopy.move(col);
				int currentScore = evaluateGameState(gsCopy, myCounter);
				if (currentScore > bestScore) {
					bestScore = currentScore;
					bestMove = col;
				}
			} catch (ColumnFullException e) {
				continue;
			}
		}
		return bestMove;
	}
	
	/**
	 * The evaluation function which scores the goodness of the given game state
	 * based on the difference between opponent's 1-, 2- and 3-in-a-row lines
	 * and those of this player
	 * @param gsCopy the game state copy to be evaluated
	 * @param myCounter the colour of the counter this player is using
	 * @return a score which measures the goodness of the given game state copy
	 */
	public int evaluateGameState(Connect4GameState gsCopy, int myCounter) {
		int score = 0;
		int[][] numInARowData = new GameEvaluator(gsCopy).inspectGameState();
		if (gsCopy.getWinner() == myCounter) {
			score += 1000000;
			return score;
		}
		else if (gsCopy.getWinner() == 1-myCounter) {
			score -= 1000000;
			return score;
		}
		//Calculates score based on 1-, 2- and 3-in-a-row lines
		for (int counter=RED; counter<=YELLOW; counter++) {
			for (int xInARow = 0, scoreWeighting = 1; xInARow<(NUM_IN_A_ROW_TO_WIN)-1;
					xInARow++, scoreWeighting *= 10) {
				if (myCounter == counter)
					score += (numInARowData[counter][xInARow]*scoreWeighting);
                else if (xInARow == NUM_IN_A_ROW_TO_WIN-2)
                    //Placing more emphasis on the damaging effect of opponent's 3-in-a-row
                    score -= (numInARowData[counter][xInARow]*(10*scoreWeighting));
				else
					score -= (numInARowData[counter][xInARow]*scoreWeighting);
			}
		}
		return score;
	}
}

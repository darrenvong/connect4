package darrenvong.com.github.connect4;

import static java.lang.Math.*;

/**
 * @author Ka (Darren) Vong
 */

public class RandomPlayer extends Connect4Player {

	private int colMove;
	
	/**
	 * Constructor for creating a random, computer controlled player
	 */
	public RandomPlayer() {
		this.colMove = 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void makeMove(Connect4GameState gameState) {
		boolean validInput = false;
		while (!validInput) {
			colMove = (int)(6*random());
			if (!gameState.isColumnFull(colMove))
				validInput = true;
		}
		try {
			gameState.move(colMove);
			if ((1-gameState.whoseTurn()) == Connect4GameState.RED)
				System.out.println("Computer dropped a red counter in column "+colMove);
			else
				System.out.println("Computer dropped a yellow counter in column "+colMove);
		}
		//This should never be executed
		catch (ColumnFullException e) {
			throw new RuntimeException(e);
		}
	}
}

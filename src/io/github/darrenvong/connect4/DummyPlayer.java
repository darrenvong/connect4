package io.github.darrenvong.connect4;


/**
 * @author Ka (Darren) Vong
 */

public class DummyPlayer extends Connect4Player {
	
	/**
	 * Constructor for creating a dummy player used to even the number of
	 * competitors in the tournament
	 */
	public DummyPlayer() {}
	
	/**
	 * Since this is a dummy player, it does not actually make a move.
	 */
	public void makeMove(Connect4GameState gameState) {
		return;
	}
}

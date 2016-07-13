package darrenvong.com.github.connect4;

import darrenvong.com.github.connect4.io.*;

/**
 * @author Ka Vong
 */

public class KeyboardPlayer extends Connect4Player {

	private EasyReader keyboard;
	private int colMove;
	
	/**
	 * Constructor for creating a player which is controlled by keyboard inputs
	 */
	public KeyboardPlayer() {
		this.keyboard = new EasyReader();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void makeMove(Connect4GameState gameState) {
		boolean validInput = false;
		while (!validInput) {
			try {
				System.out.println("Please enter a column number, 0 to 6: ");
				colMove = Integer.valueOf(keyboard.readString());
				gameState.move(colMove);
				validInput = true;
			}
			catch (ColumnFullException e) {
				System.out.println("The column you have entered is full.");
			}
			catch (IllegalColumnException e) {
				System.out.println("The column you have entered is out of range.");
			}
			//Deals with exception thrown when user types in a non-numerical input
			catch (NumberFormatException e) {
				System.out.println("The column number you have entered is invalid.");
			}
		}
	}
}

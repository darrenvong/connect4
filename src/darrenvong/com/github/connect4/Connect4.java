package darrenvong.com.github.connect4;


/**
 * @author Ka (Darren) Vong
 */

public class Connect4 {

	//Instance variables representing the state and players in a Connect 4 game
	private Connect4GameState state;
	private Connect4Player red, yellow;
	
	/**
	 * Constructs a Connect 4 game object which manages the main game
	 * @param red The connect 4 player who is assigned to a red counter
	 * @param yellow THe connect 4 player who is assigned to a yellow counter
	 */
	public Connect4(Connect4Player red, Connect4Player yellow) {
		this.red = red;
		this.yellow = yellow;
		state = new GameState();
	}
	
	/**
	 * Initialises and plays the Connect 4 game until it is game over
	 */
	public void play() {
		state.startGame();
		while (!state.gameOver()) {
			draw();
			if (state.whoseTurn() == Connect4GameState.RED)
				red.makeMove(state);
			else
				yellow.makeMove(state);
		}
		draw();
		if (getWinner() == Connect4GameState.RED)
			System.out.println("R wins\n");
		else if (getWinner() == Connect4GameState.YELLOW)
			System.out.println("Y wins\n");
		else
			System.out.println("It's a draw!\n");
	}
	
	int getWinner() {
		return state.getWinner();
	}
	
	/**
	 * Draws the latest Connect 4 game board to the console
	 */
	private void draw() {
		for (int row=(Connect4GameState.NUM_ROWS)-1; row>=0; row--) {
			System.out.print("|  ");
			for (int col=0; col<Connect4GameState.NUM_COLS; col++) {
				if (state.getCounterAt(col, row) == Connect4GameState.EMPTY)
					System.out.print("   ");
				else if (state.getCounterAt(col, row) == Connect4GameState.RED)
					System.out.print("R  ");
				else
					System.out.print("Y  ");
			}
			System.out.println("|");
		}
		System.out.println(" ----------------------");
		System.out.println("   0  1  2  3  4  5  6\n");
	}
}

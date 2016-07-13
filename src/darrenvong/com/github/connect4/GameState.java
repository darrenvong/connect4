package darrenvong.com.github.connect4;


/**
 * @author Ka (Darren) Vong
 */

public class GameState extends Connect4GameState {

	//Instance variables representing the state of a current game
	private int[][] gameBoard;
	private int lastRow, lastCol, numEmpty;
	
	/**
	 * Constructor for creating an instance of the current Connect4 game state
	 */
	public GameState() {
		this.gameBoard = new int[NUM_ROWS][NUM_COLS];
		this.numEmpty = NUM_ROWS*NUM_COLS;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void startGame() {
		for (int row=0; row<NUM_ROWS; row++) {
			for (int col=0; col<NUM_COLS; col++)
				gameBoard[row][col] = EMPTY;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void move(int col) throws ColumnFullException,
			IllegalColumnException {
		if (col<0 || col>6)
			throw new IllegalColumnException(col);
		if (isColumnFull(col))
			throw new ColumnFullException(col);
		
		for (int row=0; row<NUM_ROWS; row++) {
			if (getCounterAt(col, row) == EMPTY) {
				gameBoard[5-row][col] = whoseTurn();
				lastRow = row;
				lastCol = col;
				numEmpty--;
				return;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public int whoseTurn() {
		//An even number of remaining empty slots denotes that it's red's turn
		if (numEmpty%2 == 0)
			return RED;
		else
			return YELLOW;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getCounterAt(int col, int row) throws IllegalColumnException,
			IllegalRowException {
		if (col<0 || col>6)
			throw new IllegalColumnException(col);
		if (row<0 || row>5)
			throw new IllegalRowException(row);
		return gameBoard[5-row][col];
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isBoardFull() {
		if (numEmpty == 0)
			return true;
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isColumnFull(int col) throws IllegalColumnException {
		if (col<0 || col>6)
			throw new IllegalColumnException(col);
		for (int row=NUM_ROWS-1; row>=0; row--) {
			if (gameBoard[row][col] == EMPTY)
				return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getWinner() {
		if (checkHorizontalWinner())
			//Returns the player who has made the LAST move
			return 1-whoseTurn();
		
		if (checkVerticalWinner())
			return 1-whoseTurn();
		
		//Checks from the starting locations of only the diagonals where
		//a 4-in-a-row winner is possible
		for (int row=3; row<NUM_ROWS; row++) {
			if (row == 5) {
				for (int col=0; col<=3; col++) {
					if (checkUpDiagonalWinner(row, col))
						return 1-whoseTurn();
				}
			}
			else if (checkUpDiagonalWinner(row, 0))
				return 1-whoseTurn();
		}
		for (int row=3; row<NUM_ROWS; row++) {
			if (row == 5) {
				for (int col=6; col>=3; col--) {
					if (checkDownDiagonalWinner(row, col))
						return 1-whoseTurn();
				}
			}
			else if (checkDownDiagonalWinner(row, 6))
				return 1-whoseTurn();
		}
		return EMPTY;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean gameOver() {
		if (getWinner() != EMPTY)
			return true;
		else if (numEmpty == 0)
			return true;
		else
			return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public Connect4GameState copy() {
		GameState gameStateCopy = new GameState();
		gameStateCopy.gameBoard = gameBoardCopy();
		gameStateCopy.lastCol = this.lastCol;
		gameStateCopy.lastRow = this.lastRow;
		gameStateCopy.numEmpty = this.numEmpty;
		return gameStateCopy;
	}

	/**
	 * Copies the game board (2D array) within the current Connect4GameState instance
	 * @return a new and deep copy of the game board represented by a 2D array
	 */
	private int[][] gameBoardCopy() {
		int[][] boardCopy = new int[NUM_ROWS][NUM_COLS];
		for (int row=0; row<NUM_ROWS; row++) {
			for (int col=0; col<NUM_COLS; col++) {
				boardCopy[5-row][col] = getCounterAt(col, row);
			}
		}
		return boardCopy;
	}
	/**
	 * Checks for a horizontal 4-in-a-row winner based on the row of the last move
	 * @return true if a winner is found, false otherwise
	 */
	private boolean checkHorizontalWinner() {
		int counter = 0; //keeps track of the longest consecutive counter chain found so far
		for (int c=0; c<NUM_COLS; c++) {
			if ( gameBoard[5-lastRow][c] == (1-whoseTurn()) )
				counter++;
			//resets counter if empty or different colour to player of last move is found
			else
				counter = 0;
			
			if (counter == NUM_IN_A_ROW_TO_WIN)
				return true;
		}
		return false;
	}
	
	/**
	 * Checks for a vertical 4-in-a-row winner based on the column of the last move
	 * @return true if a winner is found, false otherwise
	 */
	private boolean checkVerticalWinner() {
		int counter = 0;
		for (int r=NUM_ROWS-1; r>=0; r--) {
			if ( gameBoard[r][lastCol] == (1-whoseTurn()) )
				counter++;
			else
				counter = 0;
			if (counter == NUM_IN_A_ROW_TO_WIN)
				return true;
		}
		return false;
	}
	
	/**
	 * Checks upward diagonal slopes (/) where a 4-in-a-row winner is possible.
	 * The row number here is based on the internal representation of the game board
	 * (i.e. top left is (0, 0))
	 * @param r the row where such upward diagonal starts
	 * @param c the column where such upward diagonal starts
	 * @return true if a winner is found, false otherwise
	 */
	private boolean checkUpDiagonalWinner(int r, int c) {
		int counter = 0;
		for (int checker=0; (r-checker>=0) && (c+checker<=6); checker++) {
			if ( gameBoard[r-checker][c+checker] == (1-whoseTurn()) )
				counter++;
			else
				counter = 0;
			if (counter == NUM_IN_A_ROW_TO_WIN)
				return true;
		}
		return false;
	}
	
	/**
	 * Checks downward diagonal slopes (\) where a 4-in-a-row winner is possible.
	 * The row number here is based on the internal representation of the game board
	 * (i.e. top left is (0, 0))
	 * @param r the row where such downward diagonal starts
	 * @param c the column where such downward diagonal starts
	 * @return true if a winner is found, false otherwise
	 */
	private boolean checkDownDiagonalWinner(int r, int c) {
		int counter = 0;
		for (int checker=0; (r-checker>=0) && (c-checker>=0); checker++) {
			if ( gameBoard[r-checker][c-checker] == (1-whoseTurn()) )
				counter++;
			else
				counter = 0;
			if (counter == NUM_IN_A_ROW_TO_WIN)
				return true;
		}
		return false;
	}
}

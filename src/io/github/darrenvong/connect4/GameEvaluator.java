package io.github.darrenvong.connect4;

import java.util.*;

import static io.github.darrenvong.connect4.Connect4GameState.*;

/**
 * @author Ka (Darren) Vong
 * 
 * A class to be used in conjunction with the evaluation function of my AI
 * Connect 4 players
 */

public class GameEvaluator {
	
	//Pattern data of x-in-a-row lines, where x is between 1-3, in a group of 4
	private static Integer[][] threeInARowPattern = {{RED, RED, RED, EMPTY}, {RED, RED, EMPTY, RED},
			{RED, EMPTY, RED, RED}, {EMPTY, RED, RED, RED},
			{YELLOW, YELLOW, YELLOW, EMPTY}, {YELLOW, YELLOW, EMPTY, YELLOW},
			{YELLOW, EMPTY, YELLOW, YELLOW}, {EMPTY, YELLOW, YELLOW, YELLOW}};
	private static Integer[][] twoInARowPattern = {{RED, RED, EMPTY, EMPTY}, {RED, EMPTY, RED, EMPTY},
			{RED, EMPTY, EMPTY, RED}, {EMPTY, RED, RED, EMPTY}, {EMPTY, RED, EMPTY, RED},
			{EMPTY, EMPTY, RED, RED}, {YELLOW, YELLOW, EMPTY, EMPTY}, {YELLOW, EMPTY, YELLOW, EMPTY},
			{YELLOW, EMPTY, EMPTY, YELLOW}, {EMPTY, YELLOW, YELLOW, EMPTY},
			{EMPTY, YELLOW, EMPTY, YELLOW}, {EMPTY, EMPTY, YELLOW, YELLOW}};
	private static Integer[][] oneInARowPattern = {{EMPTY, EMPTY, EMPTY, RED}, {EMPTY, EMPTY, RED, EMPTY},
		{EMPTY, RED, EMPTY, EMPTY}, {RED, EMPTY, EMPTY, EMPTY},
		{EMPTY, EMPTY, EMPTY, YELLOW}, {EMPTY, EMPTY, YELLOW, EMPTY},
		{EMPTY, YELLOW, EMPTY, EMPTY}, {YELLOW, EMPTY, EMPTY, EMPTY}};
	
	private Connect4GameState gs;
	private int[][] numInARowData;
	
	/**
	 * Creates a game inspector which analyses the number of x-in-a-row lines
	 * for both players in the game
	 * @param gs the state of the game to be analysed
	 */
	public GameEvaluator(Connect4GameState gs) {
		this.gs = gs;
		numInARowData = new int[2][NUM_IN_A_ROW_TO_WIN-1];
	}
	
	/**
	 * Inspects and counts the number of x-in-a-row lines for both players by
	 * checking against the pattern data
	 * @return an array with the corresponding number of x-in-a-row lines both players have
	 */
	public int[][] inspectGameState() {
		//horizontal 2- and 3-in-a-row
		for (int r=0; r<NUM_ROWS; r++) {
			for (int c=0; c<=3; c++) {
				List<Integer> linePattern = new ArrayList<>();
				for (int i=0; i<NUM_IN_A_ROW_TO_WIN; i++)
					linePattern.add(gs.getCounterAt(c+i, r));
				checkHorizontal(linePattern, threeInARowPattern, 3);
				checkHorizontal(linePattern, twoInARowPattern, 2);
				checkHorizontal(linePattern, oneInARowPattern, 1);
			}
		}
		//vertical 2- and 3-in-a-row
		for (int c=0; c<NUM_COLS; c++) {
			for (int r=0; r<=2; r++) {
				List<Integer> linePattern = new ArrayList<>();
				for (int i=0; i<NUM_IN_A_ROW_TO_WIN; i++)
					linePattern.add(gs.getCounterAt(c, r+i));
				checkVertical(linePattern, threeInARowPattern, 3);
				checkVertical(linePattern, twoInARowPattern, 2);
				checkVertical(linePattern, oneInARowPattern, 1);
			}
		}
		//Forward diagonal 2- and 3-in-a-row
		for (int r=2; r>=0; r--) {
			if (r == 0) {
				for (int c=0; c<4; c++)
					checkForwardDiagonal(c, r);
			}
			else
				checkForwardDiagonal(0, r);
		}
		//Backward diagonal 2- and 3-in-a-row
		for (int r=2; r>=0; r--) {
			if (r == 0) {
				for (int c=6; c>=3; c--)
					checkBackwardDiagonal(c, r);
			}
			else
				checkBackwardDiagonal(6, r);
		}
		return numInARowData;
	}
	
	/**
	 * The evaluation function which scores the goodness of the given game state
	 * based on the difference between opponent's 1-, 2- and 3-in-a-row lines
	 * and those of this player
	 * @param myCounter the colour of the counter this player is using
	 * @return a score which measures the goodness of the given game state copy
	 */
	public int evaluateGameState(int myCounter) {
		int score = 0;
		int[][] numInARowData = inspectGameState();
		if (gs.getWinner() == myCounter) {
			score += 1000000;
			return score;
		}
		else if (gs.getWinner() == 1-myCounter) {
			score -= 1000000;
			return score;
		}
		//Calculates score based on 1-, 2- and 3-in-a-row lines
		for (int counter=RED; counter<=YELLOW; counter++) {
			for (int xInARow = 0, scoreWeighting = 1; xInARow<(NUM_IN_A_ROW_TO_WIN)-1;
				xInARow++, scoreWeighting *= 10) {
				if (myCounter == counter)
					score += (numInARowData[counter][xInARow]*scoreWeighting);
				else
					score -= (numInARowData[counter][xInARow]*scoreWeighting);
			}
		}
		return score;
	}
	
	/**
	 * Checks whether a given line pattern matches the pattern data
	 * @param pattern the pattern to be checked
	 * @param patternData the pattern to be checked against
	 * @param numInARow the x-in-a-row data to be updated
	 */
	private void checkPatternMatch(List<Integer> pattern, Integer[][] patternData,
							int numInARow) {
		int colour = RED;
		for (int i=0; i<patternData.length; i++) {
			List<Integer> dataToList = Arrays.asList(patternData[i]);
			if (pattern.equals(dataToList)) {
				numInARowData[colour][numInARow-1] ++;
				return;
			}
			if (i == (patternData.length/2)-1)
				colour++;
		}
	}
	
	/**
	 * Extract group of 4 line patterns from a diagonal of size greater than
	 * or equal to 4, then check if they match the pattern data
	 * @param aDiagonal diagonal containing groups of 4 line patterns
	 * @param patternData the pattern to be checked against
	 * @param numInARow the x-in-a-row data to be updated
	 */
	private void extractPattern(List<Integer> aDiagonal, Integer[][] patternData,
									int numInARow) {
		while (aDiagonal.size()>4) {
			List<Integer> linePattern = aDiagonal.subList(0, 4);
			checkPatternMatch(linePattern, patternData, numInARow);
			aDiagonal.remove(0);
		}
		if (aDiagonal.size() == 4)
			checkPatternMatch(aDiagonal, patternData, numInARow);
	}
	
	/**
	 * Returns a deep copy of a list containing a diagonal of size
	 * greater than or equal to 4
	 * @param aDiagonal the diagonal to be copied
	 * @return a deep copy of the list containing the diagonal
	 */
	private List<Integer> copyDiagonalList(List<Integer> aDiagonal) {
		List<Integer> copy = new ArrayList<>();
		for (Integer space : aDiagonal) {
			copy.add(space);
		}
		return copy;
	}
	
	/**
	 * Checks for any x-in-a-row horizontal lines formed by both players
	 * @param pattern the pattern to be checked
	 * @param patternData the pattern to be checked against
	 * @param numInARow the x-in-a-row data to be updated
	 */
	private void checkHorizontal(List<Integer> pattern, Integer[][] patternData,
									int numInARow) {
		checkPatternMatch(pattern, patternData, numInARow);
	}
	
	/**
	 * Checks for any x-in-a-row vertical lines formed by both players
	 * @param pattern the pattern to be checked
	 * @param patternData the pattern to be checked against
	 * @param numInARow the x-in-a-row data to be updated
	 */
	private void checkVertical(List<Integer> pattern, Integer[][] patternData,
								int numInARow) {
		checkPatternMatch(pattern, patternData, numInARow);
	}
	
	/**
	 * Checks for any x-in-a-row forward diagonal lines (/) formed by both players
	 * @param pattern the pattern to be checked
	 * @param patternData the pattern to be checked against
	 * @param numInARow the x-in-a-row data to be updated
	 */
	private void checkForwardDiagonal(int c, int r) {
		List<Integer> aDiagonal = new ArrayList<>();
		for (int delta=0; (r+delta<NUM_ROWS) && (c+delta<NUM_COLS); delta++) {
			aDiagonal.add(gs.getCounterAt(c+delta, r+delta));
		}
		//Extracting the pattern by trimming from the bigger list of data obtained
		extractPattern(copyDiagonalList(aDiagonal), threeInARowPattern, 3);
		extractPattern(copyDiagonalList(aDiagonal), twoInARowPattern, 2);
		extractPattern(copyDiagonalList(aDiagonal), oneInARowPattern, 1);
	}
	
	/**
	 * Checks for any x-in-a-row backward diagonal lines (\) formed by both players
	 * @param pattern the pattern to be checked
	 * @param patternData the pattern to be checked against
	 * @param numInARow the x-in-a-row data to be updated
	 */
	private void checkBackwardDiagonal(int c, int r) {
		List<Integer> aDiagonal = new ArrayList<>();
		for (int delta=0; (r+delta<NUM_ROWS) && (c-delta>=0); delta++) {
			aDiagonal.add(gs.getCounterAt(c-delta, r+delta));
		}
		extractPattern(copyDiagonalList(aDiagonal), threeInARowPattern, 3);
		extractPattern(copyDiagonalList(aDiagonal), twoInARowPattern, 2);
		extractPattern(copyDiagonalList(aDiagonal), oneInARowPattern, 1);
	}
}

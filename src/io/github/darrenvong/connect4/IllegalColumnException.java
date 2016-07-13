package io.github.darrenvong.connect4;

@SuppressWarnings("serial")
public class IllegalColumnException extends RuntimeException {

	/**
	 * Constructs a new IllegalColumnException
	 * @param col the illegal column number that was supplied
	 */
	public IllegalColumnException(int col) {
		super("Column "+col+" is an illegal column number");		
	}	
}

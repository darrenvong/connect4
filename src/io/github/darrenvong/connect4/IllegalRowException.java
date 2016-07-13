package io.github.darrenvong.connect4;

@SuppressWarnings("serial")
public class IllegalRowException extends RuntimeException {

	/**
	 * Constructs a new IllegalRowException
	 * @param col the illegal row number that was supplied
	 */
	public IllegalRowException(int row) {
		super("Row "+row+" is an illegal row number");		
	}	

}

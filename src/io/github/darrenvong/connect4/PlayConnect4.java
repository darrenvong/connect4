package io.github.darrenvong.connect4;


/**
 * @author Ka (Darren) Vong
 */

public class PlayConnect4 {

	public static void main(String[] args) {
		
		Connect4Player red = new AlphaBetaPlayer();
		Connect4Player yellow = new KeyboardPlayer();
		
		Connect4 game = new Connect4(red, yellow);
		game.play();
	}
	
}

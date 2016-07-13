package darrenvong.com.github.connect4;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ka Vong
 */

public class PlayTournament {
	
	public static void main(String[] args) {
		Connect4Tournament tournament = new DarrenTournament();
		Map<String, Connect4Player> competitors = new HashMap<>();
		/*------ Enter the competitors here ------ */
		competitors.put("OneMoveLookAheadPlayer", new OneMoveLookAheadPlayer());
		competitors.put("MinimaxPlayer", new MinimaxPlayer());
		competitors.put("RandomPlayer", new RandomPlayer());
		competitors.put("AlphaBetaPlayer", new AlphaBetaPlayer());
		/* --------------------------------------- */
		tournament.runTournament(competitors);

	}

}

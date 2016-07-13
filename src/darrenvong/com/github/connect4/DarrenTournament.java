package darrenvong.com.github.connect4;

import java.util.*;


/**
 * @author Ka (Darren) Vong
 */

public class DarrenTournament extends Connect4Tournament {
	
	private List<PlayerRanking> playerRankings;
	
	/**
	 * Creates a new Connect 4 tournament
	 */
	public DarrenTournament() {
		this.playerRankings = new ArrayList<PlayerRanking>();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void runTournament(Map<String, Connect4Player> competitors) {
		if (competitors.size() == 0 || competitors.size() == 1) {
			System.out.println("Please supply enough competitors in your program code "+
					"before running this method again.");
			return;
		}
		//Adds in a dummy player to even the number of competitors for ease
		//of scheduling later
		if (competitors.size()%2 == 1)
			competitors.put("DummyPlayer", new DummyPlayer());
		
		for (String playerNames : competitors.keySet()) {
			playerRankings.add(new PlayerRanking(competitors.get(playerNames)));
		}
		//Fixing the first player and shuffling other players one place to the right
		//n-1 times, where n is the number of the competitors
		for (int round=0; round<competitors.size()-1; round++) {
			playerRankings.add(1, playerRankings.get(playerRankings.size()-1));
			playerRankings.remove(playerRankings.size()-1);
			for (int i=0; i<=(competitors.size()-2); i += 2) {
				playConnect4(playerRankings.get(i), playerRankings.get(i+1));
				playConnect4(playerRankings.get(i+1), playerRankings.get(i));
			}
		}
		
		if (competitors.get("DummyPlayer") != null) {
			ListIterator<PlayerRanking> iterator = playerRankings.listIterator();
			while (iterator.hasNext()) {
				PlayerRanking playerRank = iterator.next();
				if (playerRank.getName().equalsIgnoreCase("DummyPlayer")) {
					iterator.remove();
				}
			}
		}
		printTable();
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<Connect4Ranking> getRankings() {
		List<Connect4Ranking> sortedRankings = new ArrayList<Connect4Ranking>();
		sortedRankings.addAll(playerRankings);
		Collections.sort(sortedRankings, new Comparator<Connect4Ranking>() {
			
			public int compare(Connect4Ranking playerA, Connect4Ranking playerB) {
				if (playerB.getPoints()-playerA.getPoints() != 0)
					return playerB.getPoints()-playerA.getPoints();
				else
					return playerA.getName().compareTo(playerB.getName());
			}
		});
		
		return sortedRankings;
	}

	/**
	 * {@inheritDoc}
	 */
	public void printTable() {
		List<Connect4Ranking> results = getRankings();
		System.out.println("Tournament Results");
		System.out.format("%-10s%-25s%-15s", "Rank", "Player", "Points");
		System.out.println();
		for (int i=0; i<results.size(); i++) {
			System.out.format("%-10d%-25s%-15d", i+1, results.get(i).getName(),
					results.get(i).getPoints());
			System.out.println();
		}
	}
	
	/**
	 * Starts a game of Connect 4 for the tournament
	 * @param red The player who is starting the game
	 * @param yellow The player who plays second
	 */
	public void playConnect4(PlayerRanking red, PlayerRanking yellow) {
		if (red.getName().equalsIgnoreCase("DummyPlayer") ||
				yellow.getName().equalsIgnoreCase("DummyPlayer"))
			return;
		Connect4 game = new Connect4(red.getPlayer(), yellow.getPlayer());
		game.play();
		if (game.getWinner() == Connect4GameState.RED)
			red.addPoints(3);
		else if (game.getWinner() == Connect4GameState.YELLOW)
			yellow.addPoints(3);
		else {
			red.addPoints(1);
			yellow.addPoints(1);
		}
	}
}

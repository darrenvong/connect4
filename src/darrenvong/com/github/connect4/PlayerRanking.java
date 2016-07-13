package darrenvong.com.github.connect4;


public class PlayerRanking extends Connect4Ranking {
	
	private Connect4Player player;
	private int points;
	
	public PlayerRanking(Connect4Player player) {
		this.player = player;
	}
	
	public String getName() {
		return player.getClass().getSimpleName();
	}

	public int getPoints() {
		return points;
	}
	
	public void addPoints(int pointAward) {
		points += pointAward;
	}
	
	public Connect4Player getPlayer() {
		return player;
	}

}

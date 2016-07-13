package darrenvong.com.github.connect4;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.*;

/**
 * @author Ka (Darren) Vong
 */

public class DarrenTournamentTest {

	Connect4Tournament tournament;
	Map<String, Connect4Player> competitors;
	
	@Before
	public void setUp() {
		tournament = new DarrenTournament();
		competitors = new HashMap<>();
	}

	@Test
	public void testOddNumberPlayers() {
		String msg = "Odd number of players, though should be made even";
		competitors.put("RandomPlayer", new RandomPlayer());
		competitors.put("AnotherPlayer", new RandomPlayer());
		competitors.put("Player3", new RandomPlayer());
		tournament.runTournament(competitors);
		int expectNoRemainder = 0;
		assertEquals(msg, expectNoRemainder, competitors.size()%2);
	}
	
	@Test
	public void testDummyPlayerAdded() {
		String msg = "DummyPlayer should be added to the end to make "+
				"player number even, but no player is found";
		competitors.put("RandomPlayer", new RandomPlayer());
		competitors.put("Player2", new RandomPlayer());
		competitors.put("player3", new RandomPlayer());
		tournament.runTournament(competitors);
		assertNotNull(msg, competitors.get("DummyPlayer"));
	}
	
	@Test
	public void testDummyPlayerUsed() {
		String msg = "Only DummyPlayer should be used to make player number "+
				"even, yet player of different class found";
		competitors.put("RandomPlayer", new RandomPlayer());
		competitors.put("Player2", new RandomPlayer());
		competitors.put("player3", new RandomPlayer());
		tournament.runTournament(competitors);
		assertEquals(msg, "DummyPlayer",
				competitors.get("DummyPlayer").getClass().getSimpleName());
	}
	
	@Test
	public void testNoDummyPlayer() {
		String msg = "Even number of players, yet DummyPlayer is found when not needed";
		competitors.put("RandomPlayer", new RandomPlayer());
		competitors.put("RandomPlayer2", new RandomPlayer());
		assertNull(msg, competitors.get("DummyPlayer"));
	}
	
	@Test
	public void testGetRankings() {
		competitors.put("RandomPlayer", new RandomPlayer());
		competitors.put("Player2", new RandomPlayer());
		competitors.put("player3", new RandomPlayer());
		tournament.runTournament(competitors);
		List<Connect4Ranking> sortedList = tournament.getRankings();
		for (int i=0; i<sortedList.size(); i++) {
			try {
				if (sortedList.get(i).getPoints()<sortedList.get(i+1).getPoints())
					fail("Player with lower points appear earlier in the list");
			} catch (IndexOutOfBoundsException e) {
				break;
			}
		}
	}

}

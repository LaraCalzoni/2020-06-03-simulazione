package it.polito.tdp.PremierLeague.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.PremierLeague.model.Action;
import it.polito.tdp.PremierLeague.model.Player;

public class PremierLeagueDAO {
	
	
	
	public void listAllPlayers(Map <Integer, Player> idMap){
		String sql = "SELECT * FROM Players";
		//List<Player> result = new ArrayList<Player>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				if(!idMap.containsKey(res.getInt("PlayerID"))) {
				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
				
				//result.add(player);
				idMap.put(player.getPlayerID(), player);
			}
			}
			conn.close();
			//return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			//return null;
		}
	}
	
	public void listAllActions(Map<Integer, Action> idMapAzioni){
		String sql = "SELECT * FROM Actions";
		List<Action> result = new ArrayList<Action>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				if(!idMapAzioni.containsKey(res.getInt("MatchID"))) {
				Action action = new Action(res.getInt("PlayerID"),res.getInt("MatchID"),res.getInt("TeamID"),res.getInt("Starts"),res.getInt("Goals"),
						res.getInt("TimePlayed"),res.getInt("RedCards"),res.getInt("YellowCards"),res.getInt("TotalSuccessfulPassesAll"),res.getInt("totalUnsuccessfulPassesAll"),
						res.getInt("Assists"),res.getInt("TotalFoulsConceded"),res.getInt("Offsides"));
				
				idMapAzioni.put(res.getInt("MatchID"), action);
				//result.add(action);
			}
			}
			conn.close();
			//return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			//return null;
		}
	}
	
	public List <Player> getVertici(int goal, Map <Integer, Player> idMap){
		String sql = "SELECT p.PlayerID AS giocatore, AVG(a.Goals) AS media "
				+ "FROM players p, actions a "
				+ "WHERE p.PlayerID = a.PlayerID "
				+ "GROUP BY a.PlayerID "
				+ "HAVING AVG(a.Goals)> ?";
		
		List<Player> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, goal);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player p = idMap.get(res.getInt("giocatore"));
				result.add(p);
			
				
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List <Adiacenza> getAdiacenze(Map <Integer, Player> idMap,Map <Integer, Action> idMapAzioni, int goal){
		
		String sql = "SELECT a1.PlayerID, a2.PlayerID, AVG(a1.Goals) , AVG(a2.Goals) "
				+ "FROM players p1, actions a1, players p2, actions a2 "
				+ "WHERE p1.PlayerID = a1.PlayerID AND p2.PlayerID = a2.PlayerID AND a1.TeamID> a2.TeamID "
				+ "AND a1.MatchID=a2.MatchID AND a1.`Starts`= 1 AND a2.`Starts`=1 AND p1.PlayerID>p2.PlayerID "
				+ "GROUP BY a1.PlayerID, a2.PlayerID "
				+ "HAVING AVG(a1.Goals) > 0.5 AND AVG(a2.Goals)>= AVG(a1.Goals)";
		
		List<Adiacenza> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, goal);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player p = idMap.get(res.getInt("giocatore"));
				result.add(p);
			
				
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}

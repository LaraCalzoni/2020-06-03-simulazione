package it.polito.tdp.PremierLeague.model;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {

	private SimpleDirectedWeightedGraph <Player, DefaultWeightedEdge> grafo;
	private PremierLeagueDAO dao;
	private Map <Integer, Player> idMap;
	private Map <Integer, Action> idMapAzioni;
	
	public Model() {
		dao = new PremierLeagueDAO ();
		idMap = new HashMap<>();
		idMapAzioni = new HashMap<>();
		dao.listAllPlayers(idMap);
		dao.listAllActions(idMapAzioni);
		
		
	}
	
	public void creaGrafo(int goal) {
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		 //aggiungo vertici
		Graphs.addAllVertices(grafo,dao.getVertici(goal, idMap));
		
		//aggiungo archi
		
		
		
		
		
		
		
		
	}
	
	
}

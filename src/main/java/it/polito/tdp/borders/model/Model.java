package it.polito.tdp.borders.model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private BordersDAO dao;
	private Map<Integer, Country> idMap;
	private Graph<Country, DefaultEdge> grafo;

	public Model() {
		dao = new BordersDAO();
		idMap = new HashMap<>();
	}
	
	public void creaGrafo(int anno) {
		grafo = new SimpleGraph<>(DefaultEdge.class);
		dao.loadAllCountries(idMap);
		List<Border> borders = dao.getCountryPairs(anno, idMap);
		
		for(Border b: borders) {
			if(!grafo.containsVertex(b.getState1()))
				grafo.addVertex(b.getState1());
			if(!grafo.containsVertex(b.getState2()))
				grafo.addVertex(b.getState2());
			grafo.addEdge(b.getState1(), b.getState2());
		}
	}
	
	public int nArchi() {
		return grafo.edgeSet().size();
	}
	
	public int nVertici() {
		return grafo.vertexSet().size();
	}

	public Graph<Country, DefaultEdge> getGrafo() {
		return grafo;
	}
	
	public int getComponentiConnesse() {
		 ConnectivityInspector<Country, DefaultEdge> g = new ConnectivityInspector<>(grafo);
		 return g.connectedSets().size();
	}
	
	public List<Country> visita(Country source){
		List<Country> visita = new ArrayList<>();
		GraphIterator<Country, DefaultEdge> dfv = new BreadthFirstIterator<>(grafo, source);
		while(dfv.hasNext()) {
			visita.add(dfv.next());
		}
		return visita;
	}

}
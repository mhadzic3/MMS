package MMS.Seminarski.algoritmi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import MMS.Seminarski.graf.Cvor;
import MMS.Seminarski.graf.Grana;
import edu.uci.ics.jung.graph.Graph;

public class Dijkstra extends Algoritam {
	public Dijkstra(Graph<Cvor, Grana> graf, Cvor izvor) {
		super();
		
		this.Dijkstra_start(graf, izvor);
		this.setGraphAtributes();
	}
	
	private void Dijkstra_start(Graph<Cvor, Grana> graf, Cvor izvor) {
		if(!this.testGraph(graf)) {
			return;
		}
		
		Algoritam.resetGraph(graf);
		
		this.addGraphAtributes(graf);
		
		izvor.setPosjecen(true);
		List<Cvor> test = new ArrayList<Cvor>(graf.getVertices());
		
		while(!test.isEmpty()) {
			List<Cvor> cvorovi = new ArrayList<Cvor>(graf.getVertices());
			cvorovi.removeIf(i->!i.isPosjecen() || i.getOznacen()==1);
			cvorovi.sort(Comparator.comparingDouble(Cvor::getPotencijal));
			
			Cvor cvor = cvorovi.isEmpty() ? null : cvorovi.get(0);
			if(cvor == null) {
				return;
			}
			else {
				cvor.setOznacen(1);
				if(cvor.getGrana() != null) {
					cvor.getGrana().setOznacena(1);
				}
			}

			List<Grana> grane = new ArrayList<Grana>(graf.getOutEdges(cvor));
			for(Grana g:grane) {
				Cvor c = graf.getOpposite(cvor, g);
				if(!c.isPosjecen() || c.getPotencijal() > cvor.getPotencijal() + g.getTezina()) {
					c.setPotencijal(cvor.getPotencijal() + g.getTezina());
					c.setGrana(g);
					c.setCvor(cvor);
				}
				c.setPosjecen(true);
			}
			
			test.removeIf(i->i.getOznacen()==1);
			
			this.addGraphAtributes(graf);
		}
	}
	
	private boolean testGraph(Graph<Cvor, Grana> graf) {
		return !this.testNegativeEdges(graf);
	}
}

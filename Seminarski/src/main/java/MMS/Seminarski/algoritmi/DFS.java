package MMS.Seminarski.algoritmi;

import java.util.ArrayList;
import java.util.List;

import MMS.Seminarski.graf.Cvor;
import MMS.Seminarski.graf.Grana;
import edu.uci.ics.jung.graph.Graph;

public class DFS extends Algoritam {
	public DFS(Graph<Cvor, Grana> graf, Cvor izvor) {
		super();
		
		this.DFS_start(graf, izvor);
		this.setGraphAtributes();
	}
	
	private void DFS_start(Graph<Cvor, Grana> graf, Cvor izvor) {
		Algoritam.resetGraph(graf);
		
		this.addGraphAtributes(graf);
		
		izvor.setRedoslijed(Cvor.getBrojac());
		Cvor.setBrojac(Cvor.getBrojac() + 1);
		
		DFS_rek(graf, izvor);
	}
	
	private void DFS_rek(Graph<Cvor, Grana> graf, Cvor izvor) {
		izvor.setOznacen(1);
		
		this.addGraphAtributes(graf);
		
		List<Grana> grane = new ArrayList<Grana>(graf.getOutEdges(izvor));
		for(Grana g:grane) {
			Cvor c = graf.getOpposite(izvor, g);
			if(c.getOznacen() == 0) {
				g.setOznacena(1);
				c.setRedoslijed(Cvor.getBrojac());
				Cvor.setBrojac(Cvor.getBrojac() + 1);
				
				DFS_rek(graf, c);
			}
		}
	}
}

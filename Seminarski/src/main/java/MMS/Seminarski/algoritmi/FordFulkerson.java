package MMS.Seminarski.algoritmi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import MMS.Seminarski.graf.Cvor;
import MMS.Seminarski.graf.Grana;
import edu.uci.ics.jung.graph.Graph;

public class FordFulkerson extends Algoritam{
	public FordFulkerson(Graph<Cvor, Grana> graf) {
		super();
		
		this.FordFulkerson_start(graf);
		this.setGraphAtributes();
	}
	
	private void FordFulkerson_start(Graph<Cvor, Grana> graf) {
		Algoritam.resetGraph(graf);
		
		this.addGraphAtributes(graf);
		
		List<Cvor> cvorovi = new ArrayList<Cvor>(graf.getVertices());
		Cvor izvor = null, ponor = null;
		
		for(Cvor c:cvorovi) {
			if(graf.getInEdges(c).size()==0 && izvor==null) {
				izvor = c;
			}
			else if(graf.getOutEdges(c).size()==0 && ponor==null) {
				ponor = c;
			}
			else if(graf.getInEdges(c).size()==0 || graf.getOutEdges(c).size()==0){
				System.out.println("Mreza ima vise od jednog izvora ili ponora.");
				return;
			}
		}
		
		List<Grana> lanac = BFS_lanac(graf, izvor, ponor);
				
		while(!lanac.isEmpty()) {
			lanac.sort(Comparator.comparingDouble(Grana::getRezerva));
			
			double delta = lanac.get(0).getRezerva();
			
			for(Grana g:lanac) {
				g.setProtok(g.getProtok() + (g.isSmjer() ? delta : -delta));
				g.setOznacena(1);
			}
			
			this.addGraphAtributes(graf);
			
			resetGraphFF(graf);
			
			lanac = BFS_lanac(graf, izvor, ponor);
		}
	}
	
	private static List<Grana> BFS_lanac(Graph<Cvor, Grana> graf, Cvor izvor, Cvor ponor) {
		List<Cvor> cvorovi = new ArrayList<Cvor>();
		cvorovi.add(izvor);
		
		BFS_rek(graf, cvorovi);
		
		List<Grana> lanac = new ArrayList<Grana>();
		
		Cvor cvor = ponor;
		
		while(cvor.getCvor() != null) {
			lanac.add(cvor.getGrana());
			cvor = cvor.getCvor();
		}
		
		return lanac;
	}
	
	private static void BFS_rek(Graph<Cvor, Grana> graf, List<Cvor> cvorovi) {
		if(cvorovi.isEmpty()) {
			return;
		}
		
		for(Cvor c:cvorovi) {
			c.setOznacen(1);
			c.setRedoslijed(Cvor.getBrojac());
		}
		
		Cvor.setBrojac(Cvor.getBrojac() + 1);
		
		List<Cvor> cvorovi2 = new ArrayList<Cvor>();
		for(Cvor c:cvorovi) {
			List<Grana> grane = new ArrayList<Grana>(graf.getIncidentEdges(c));
			for(Grana g:grane) {
				Cvor c2 = graf.getOpposite(c, g);
				if(c2.getOznacen() == 0 && 
						(c2 == graf.getDest(g) && g.getProtok() < g.getTezina() || 
						 c2 == graf.getSource(g) && g.getProtok() > 0.0)) {
					c2.setCvor(c);
					c2.setGrana(g);
					cvorovi2.add(c2);
					
					g.setSmjer(c2 == graf.getDest(g) ? true : false);
				}
			}
		}
		
		BFS_rek(graf, cvorovi2);
	}
	
	private static void resetGraphFF(Graph<Cvor, Grana> graf) {
		Cvor.setBrojac(1);
		
		for(Cvor c:graf.getVertices()) {
			c.setGrana(null);
			c.setCvor(null);
			
			c.setRedoslijed(0);
			
			c.setOznacen(0);
		}
		
		for(Grana g:graf.getEdges()) {
			g.setOznacena(0);
		}
	}
}

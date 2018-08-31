package MMS.Seminarski.algoritmi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import MMS.Seminarski.graf.Cvor;
import MMS.Seminarski.graf.Grana;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;

public class FordFulkerson extends Algoritam{
	public FordFulkerson(Graph<Cvor, Grana> graf) {
		super();
		
		this.FordFulkerson_start(graf);
		this.setGraphAtributes();
	}
	
	private void FordFulkerson_start(Graph<Cvor, Grana> graf) {
		List<Cvor> izvorList = new ArrayList<Cvor>(), ponorList = new ArrayList<Cvor>();
		
		if(!this.testGraph(graf, izvorList, ponorList)) {
			return;
		}
		
		Cvor izvor = izvorList.get(0), ponor = ponorList.get(0);
		
		Algoritam.resetGraph(graf);
		
		this.addGraphAtributes(graf);
		
		List<Grana> lanac = BFS_lanac(graf, izvor, ponor);
				
		while(!lanac.isEmpty()) {
			lanac.sort(Comparator.comparingDouble(Grana::getRezerva));
			
			for(Grana g:lanac) {
				g.setOznacena(1);
			}
			
			this.addGraphAtributes(graf);
			
			lanac.get(0).setOznacena(3);
			
			this.addGraphAtributes(graf);
			
			double delta = lanac.get(0).getRezerva();
			for(Grana g:lanac) {
				g.setProtok(g.getProtok() + (g.isSmjer() ? delta : -delta));
			}
			
			this.addGraphAtributes(graf);
			
			resetGraphFF(graf);
			
			lanac = BFS_lanac(graf, izvor, ponor);
		}
	}
	
	private List<Grana> BFS_lanac(Graph<Cvor, Grana> graf, Cvor izvor, Cvor ponor) {
		List<Cvor> cvorovi = new ArrayList<Cvor>();
		cvorovi.add(izvor);
		
		BFS_rek(graf, cvorovi, ponor);
		
		List<Grana> lanac = new ArrayList<Grana>();
		
		Cvor cvor = ponor;
		
		while(cvor.getCvor() != null) {
			cvor.setOznacen(1);
			lanac.add(cvor.getGrana());
			cvor = cvor.getCvor();
			if(cvor.getCvor() == null) {
				cvor.setOznacen(1);
			}
		}
		
		return lanac;
	}
	
	private void BFS_rek(Graph<Cvor, Grana> graf, List<Cvor> cvorovi, Cvor ponor) {
		if(cvorovi.isEmpty()) {
			return;
		}
		
		boolean kraj = false;
		for(Cvor c:cvorovi) {
			if(c == ponor) {
				kraj = true;
			}
			
			c.setOznacen(2);
			c.setRedoslijed(Cvor.getBrojac());
		}
		Cvor.setBrojac(Cvor.getBrojac() + 1);
		
		this.addGraphAtributes(graf);
		
		if(kraj) {
			return;
		}
				
		List<Cvor> cvorovi2 = new ArrayList<Cvor>();
		
		for(Cvor c:cvorovi) {
			List<Grana> grane = new ArrayList<Grana>(graf.getIncidentEdges(c));
			for(Grana g:grane) {
				Cvor c2 = graf.getOpposite(c, g);
				if(c2.getOznacen() == 0 && 
						(c2 == graf.getDest(g) && g.getProtok() < g.getTezina() || 
						 c2 == graf.getSource(g) && g.getProtok() > 0.0)) {
					
					g.setOznacena(2);
					
					if(!cvorovi2.contains(c2)) {
						g.setSmjer(c2 == graf.getDest(g) ? true : false);
						
						c2.setCvor(c);
						c2.setGrana(g);
						
						cvorovi2.add(c2);
					}
				}
			}
		}
		
		BFS_rek(graf, cvorovi2, ponor);
	}
	
	private void resetGraphFF(Graph<Cvor, Grana> graf) {
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
	
	private boolean testGraph(Graph<Cvor, Grana> graf, List<Cvor> izvor, List<Cvor> ponor) {
		if(graf.getDefaultEdgeType() == EdgeType.DIRECTED && !this.testNegativeEdges(graf)) {
			boolean postojiIzvor = false, postojiPonor = false;
			
			for(Cvor c:graf.getVertices()) {
				if(!postojiIzvor && graf.getInEdges(c).size() == 0) {
					izvor.add(c);
					postojiIzvor = true;
				}
				else if(!postojiPonor && graf.getOutEdges(c).size() == 0) {
					ponor.add(c);
					postojiPonor = true;
				}
				else if(graf.getInEdges(c).size()==0 || graf.getOutEdges(c).size()==0){
					return false;
				}
			}
			
			return izvor.size() == 1 && ponor.size() == 1;
		}
		
		return false;
	}
}

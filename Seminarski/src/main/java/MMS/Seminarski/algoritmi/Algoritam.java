package MMS.Seminarski.algoritmi;

import java.util.ArrayList;
import java.util.List;

import MMS.Seminarski.graf.Cvor;
import MMS.Seminarski.graf.Grana;
import edu.uci.ics.jung.graph.Graph;

public abstract class Algoritam {
	private List<List<Cvor>> cvoroviKorak;
	private List<List<Grana>> graneKorak;
	private int korak;
	
	public Algoritam() {
		this.setCvoroviKorak(new ArrayList<List<Cvor>>());
		this.setGraneKorak(new ArrayList<List<Grana>>());
		this.setKorak(0);
	}
	
	public void addGraphAtributes(Graph<Cvor, Grana> graf) {
		List<Cvor> cvorovi = new ArrayList<Cvor>();
		List<Grana> grane = new ArrayList<Grana>();
		
		for(Cvor c:graf.getVertices()) {
			cvorovi.add(c.getClone());
		}
		
		for(Grana g:graf.getEdges()) {
			grane.add(g.getClone());
		}
		
		this.getCvoroviKorak().add(cvorovi);
		this.getGraneKorak().add(grane);
	}
	
	public boolean nextStep() {
		if(this.getKorak() < this.getCvoroviKorak().size() - 1) {
			this.setKorak(this.getKorak() + 1);
			this.setGraphAtributes();
			
			return true;
		}
		
		return false;
	}
	
	public boolean previousStep() {
		if(this.getKorak() > 0) {
			this.setKorak(this.getKorak() - 1);
			this.setGraphAtributes();
			
			return true;
		}
		
		return false;
	}
	
	public void setGraphAtributes() {
		List<Cvor> cvorovi = this.getCvoroviKorak().get(this.getKorak());
		List<Grana> grane = this.getGraneKorak().get(this.getKorak());
		
		for(Cvor c:cvorovi) {
			c.setGraphAtributes();
		}
		
		for(Grana g:grane) {
			g.setGraphAtributes();
		}
	}

	public List<List<Cvor>> getCvoroviKorak() {
		return cvoroviKorak;
	}

	public void setCvoroviKorak(List<List<Cvor>> cvoroviKorak) {
		this.cvoroviKorak = cvoroviKorak;
	}

	public List<List<Grana>> getGraneKorak() {
		return graneKorak;
	}

	public void setGraneKorak(List<List<Grana>> graneKorak) {
		this.graneKorak = graneKorak;
	}

	public int getKorak() {
		return korak;
	}

	public void setKorak(int korak) {
		this.korak = korak;
	}
	
	public static void resetGraph(Graph<Cvor, Grana> graf) {
		Cvor.setBrojac(1);
		
		for(Cvor c:graf.getVertices()) {
			c.setPosjecen(false);
			c.setPotencijal(0.0);
			c.setGrana(null);
			c.setCvor(null);
			
			c.setRedoslijed(0);
			
			c.setOznacen(0);
		}
		
		for(Grana g:graf.getEdges()) {
			g.setProtok(0.0);
			g.setSmjer(false);
			
			g.setOznacena(0);
		}
	}
}

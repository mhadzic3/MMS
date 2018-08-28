package MMS.Seminarski.graf;

import java.awt.Color;
import java.awt.Paint;

public class Grana {
	private int id;
	private String naziv;
	
	private double tezina;
	
	// ford-fulkerson atributes
	private double protok;
	private boolean smjer;
	
	// view atributes
	private int oznacena;
	private Grana grafGrana;
	
	public Grana() {}
	
	public Grana(int id, double tezina) {
		super();
		this.setId(id);
		this.setNaziv("G" + this.getId());
		
		this.setTezina(tezina);
		this.setProtok(0.0);
		this.setSmjer(false);
		
		this.setOznacena(0);
	}
	
	public Grana getClone() {
		Grana grana = new Grana();
		
		grana.setOznacena(this.getOznacena());
		grana.setProtok(this.getProtok());
		grana.setSmjer(this.isSmjer());
		grana.setTezina(this.getTezina());
		grana.setGrafGrana(this);
		
		return grana;
	}
	
	public void setGraphAtributes() {
		this.getGrafGrana().setOznacena(this.getOznacena());
		this.getGrafGrana().setProtok(this.getProtok());
		this.getGrafGrana().setSmjer(this.isSmjer());
		this.getGrafGrana().setTezina(this.getTezina());
	}
	
	public double getRezerva() {
		return this.isSmjer() ? this.getTezina() - this.getProtok() : this.getProtok();
	}
	
	public Paint getPaint() {
		if(this.getOznacena() == 1) {
			return Color.GREEN;
		}
		else if(this.getOznacena() == 2) {
			return Color.BLUE;
		}
		else if(this.getOznacena() == 3) {
			return Color.RED;
		}
		else {
			return Color.BLACK;
		}
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	public double getTezina() {
		return tezina;
	}

	public void setTezina(double tezina) {
		this.tezina = tezina;
	}

	public double getProtok() {
		return protok;
	}

	public void setProtok(double protok) {
		this.protok = protok;
	}

	public boolean isSmjer() {
		return smjer;
	}

	public void setSmjer(boolean smjer) {
		this.smjer = smjer;
	}

	public int getOznacena() {
		return oznacena;
	}

	public void setOznacena(int oznacena) {
		this.oznacena = oznacena;
	}

	public Grana getGrafGrana() {
		return grafGrana;
	}

	public void setGrafGrana(Grana grafGrana) {
		this.grafGrana = grafGrana;
	}

	public String toString() {
		return getNaziv();
	}
}

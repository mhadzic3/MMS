package MMS.Seminarski.graf;

import java.awt.Color;
import java.awt.Paint;

public class Cvor {
	private int id;
	private String naziv;
	private double x;
	private double y;
	
	// dijkstra atributes
	private boolean posjecen;
	private double potencijal;
	private Grana grana;
	private Cvor cvor;
	
	// dfs and bfs atributes
	private int redoslijed;
	private static int brojac = 0;
	
	// view atributes
	private int oznacen;
	private Cvor grafCvor;
	
	public Cvor() {}

	public Cvor(int id) {
		super();
		this.setId(id);
		this.setNaziv("C" + this.getId());
		
		this.setPosjecen(false);
		this.setPotencijal(0.0);
		this.setGrana(null);
		this.setCvor(null);
		
		this.setRedoslijed(0);
		
		this.setOznacen(0);
	}
	
	public Cvor getClone() {
		Cvor cvor = new Cvor();
		
		cvor.setOznacen(this.getOznacen());
		cvor.setPotencijal(this.getPotencijal());
		cvor.setPosjecen(this.isPosjecen());
		cvor.setRedoslijed(this.getRedoslijed());
		cvor.setCvor(this.getCvor());
		cvor.setGrana(this.getGrana());
		cvor.setGrafCvor(this);
		
		return cvor;
	}
	
	public void setGraphAtributes() {
		this.getGrafCvor().setOznacen(this.getOznacen());
		this.getGrafCvor().setPotencijal(this.getPotencijal());
		this.getGrafCvor().setPosjecen(this.isPosjecen());
		this.getGrafCvor().setRedoslijed(this.getRedoslijed());
		this.getGrafCvor().setCvor(this.getCvor());
		this.getGrafCvor().setGrana(this.getGrana());
	}
	
	public Paint getPaint() {
		if(this.getOznacen() == 1) {
			return Color.GREEN;
		}
		else if(this.getOznacen() == 2) {
			return Color.BLUE;
		}
		else {
			return Color.RED;
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

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public boolean isPosjecen() {
		return posjecen;
	}

	public void setPosjecen(boolean posjecen) {
		this.posjecen = posjecen;
	}

	public double getPotencijal() {
		return potencijal;
	}

	public void setPotencijal(double potencijal) {
		this.potencijal = potencijal;
	}

	public Grana getGrana() {
		return grana;
	}

	public void setGrana(Grana grana) {
		this.grana = grana;
	}

	public Cvor getCvor() {
		return cvor;
	}

	public void setCvor(Cvor cvor) {
		this.cvor = cvor;
	}

	public int getRedoslijed() {
		return redoslijed;
	}

	public void setRedoslijed(int redoslijed) {
		this.redoslijed = redoslijed;
	}
	
	public static int getBrojac() {
		return brojac;
	}

	public static void setBrojac(int brojac) {
		Cvor.brojac = brojac;
	}

	public int getOznacen() {
		return oznacen;
	}

	public void setOznacen(int oznacen) {
		this.oznacen = oznacen;
	}
	
	public Cvor getGrafCvor() {
		return grafCvor;
	}

	public void setGrafCvor(Cvor grafCvor) {
		this.grafCvor = grafCvor;
	}

	public String toString() {
		return this.getNaziv();
	}
}

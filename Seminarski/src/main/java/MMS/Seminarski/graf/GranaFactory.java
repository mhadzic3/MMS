package MMS.Seminarski.graf;

import org.apache.commons.collections15.Factory;

public class GranaFactory implements Factory<Grana>{
	private static final double pocetnaTezina = 10;
	
	private static int brojac = 1;
	
	public Grana create() {
		return new Grana(brojac++, pocetnaTezina);
	}
	
	public Grana create(int id, double tezina) {
		return new Grana(id, tezina);
	}

	public static int getBrojac() {
		return brojac;
	}

	public static void setBrojac(int brojac) {
		GranaFactory.brojac = brojac;
	}
}

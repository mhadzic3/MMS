package MMS.Seminarski.graf;

import org.apache.commons.collections15.Factory;

public class CvorFactory implements Factory<Cvor>{
	private static int brojac = 1;
	
	public Cvor create() {
		return new Cvor(brojac++);
	}

	public static int getBrojac() {
		return brojac;
	}

	public static void setBrojac(int brojac) {
		CvorFactory.brojac = brojac;
	}
}

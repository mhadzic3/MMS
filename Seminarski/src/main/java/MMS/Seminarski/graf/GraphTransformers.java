package MMS.Seminarski.graf;

import java.awt.Paint;

import org.apache.commons.collections15.Transformer;

public class GraphTransformers {
	public static Transformer<Cvor, Paint> getVertexPaint(){
		Transformer<Cvor, Paint> vertexPaint = new Transformer<Cvor, Paint>() {
            public Paint transform(Cvor cvor) {
                return cvor.getPaint();
            }
        }; 
        
        return vertexPaint;
	}
	
	public static Transformer<Grana, Paint> getEdgePaint(){
		Transformer<Grana, Paint> edgePaint = new Transformer<Grana, Paint>() {
            public Paint transform(Grana grana) {
                return grana.getPaint();
            }
        }; 
        
        return edgePaint;
	}
	
	public static Transformer<Cvor, String> getVertexLabel1(){
		Transformer<Cvor, String> vertexLabel = new Transformer<Cvor, String>() {
            public String transform(Cvor cvor) {
                return cvor.getNaziv() + 
                	   " (" + (cvor.isPosjecen() ? cvor.getPotencijal() : "inf") + 
                	   (cvor.getCvor()==null ? "" : "/" + cvor.getCvor().getNaziv()) + 
                	   ")";
            }
        }; 
        
        return vertexLabel;
	}
	
	public static Transformer<Cvor, String> getVertexLabel2(){
		Transformer<Cvor, String> vertexLabel = new Transformer<Cvor, String>() {
            public String transform(Cvor cvor) {
                return cvor.getNaziv() + 
                	   (cvor.getOznacen()==1 || cvor.getOznacen()==2 ? " (" + cvor.getRedoslijed() + ")": "");
            }
        }; 
        
        return vertexLabel;
	}
	
	public static Transformer<Grana, String> getEdgeLabel1(){
		Transformer<Grana, String> edgeLabel = new Transformer<Grana, String>() {
            public String transform(Grana grana) {
                return Double.toString(grana.getTezina());
            }
        }; 
        
        return edgeLabel;
	}
	
	public static Transformer<Grana, String> getEdgeLabel2(){
		Transformer<Grana, String> edgeLabel = new Transformer<Grana, String>() {
            public String transform(Grana grana) {
                return grana.getNaziv() + ": " + grana.getTezina() + "/" + grana.getProtok();
            }
        }; 
        
        return edgeLabel;
	}
}

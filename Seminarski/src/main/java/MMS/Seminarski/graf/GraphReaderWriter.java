package MMS.Seminarski.graf;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.io.GraphMLWriter;
import edu.uci.ics.jung.io.graphml.EdgeMetadata;
import edu.uci.ics.jung.io.graphml.GraphMLReader2;
import edu.uci.ics.jung.io.graphml.GraphMetadata;
import edu.uci.ics.jung.io.graphml.GraphMetadata.EdgeDefault;
import edu.uci.ics.jung.io.graphml.HyperEdgeMetadata;
import edu.uci.ics.jung.io.graphml.NodeMetadata;

public class GraphReaderWriter {
	public static GraphMLReader2<Graph<Cvor, Grana>, Cvor, Grana> getGraphReader(String filename) throws FileNotFoundException{
		GraphMLReader2<Graph<Cvor, Grana>, Cvor, Grana> graphReader =
				new GraphMLReader2<Graph<Cvor, Grana>, Cvor, Grana>
						(new FileReader(filename), getGraphTransformer(), getVertexTransformer(), 
						 getEdgeTransformer(), getHyperEdgeTransformer());
		
		return graphReader;
	}
	
	public static GraphMLWriter<Cvor, Grana> getGraphWriter(final StaticLayout<Cvor, Grana> layout){
		GraphMLWriter<Cvor, Grana> graphWriter = new GraphMLWriter<Cvor, Grana>();
		
		graphWriter.addVertexData("x", null, "0.0", new Transformer<Cvor, String>(){
			public String transform(Cvor cvor) {
				return Double.toString(layout.getX(cvor));
			}
		});
		graphWriter.addVertexData("y", null, "0.0", new Transformer<Cvor, String>(){
			public String transform(Cvor cvor) {
				return Double.toString(layout.getY(cvor));
			}
		});
		
		graphWriter.addEdgeData("tezina", null, "10.0", new Transformer<Grana, String>(){
			public String transform(Grana grana) {
				return Double.toString(grana.getTezina());
			}
		});
		
		return graphWriter;
	}
	
	private static Transformer<GraphMetadata, Graph<Cvor, Grana>> getGraphTransformer(){
		Transformer<GraphMetadata, Graph<Cvor, Grana>> graphTransformer = 
				new Transformer<GraphMetadata, Graph<Cvor, Grana>>(){
					public Graph<Cvor, Grana> transform(GraphMetadata metadata) {
						if(metadata.getEdgeDefault().equals(EdgeDefault.DIRECTED)) {
							return new DirectedSparseGraph<Cvor, Grana>();
						}
						else {
							return new UndirectedSparseGraph<Cvor, Grana>();
						}
					}
		};
		
		return graphTransformer;
	}
	
	private static Transformer<NodeMetadata, Cvor> getVertexTransformer(){
		Transformer<NodeMetadata, Cvor> vertexTransformer = 
				new Transformer<NodeMetadata, Cvor>(){
					public Cvor transform(NodeMetadata metadata) {
						Cvor cvor = (new CvorFactory()).create();
						cvor.setX(Double.parseDouble(metadata.getProperty("x")));
						cvor.setY(Double.parseDouble(metadata.getProperty("y")));
						return cvor;
					}
		};
		
		return vertexTransformer;
	}
	
	private static Transformer<EdgeMetadata, Grana> getEdgeTransformer(){
		Transformer<EdgeMetadata, Grana> edgeTransformer =
				 new Transformer<EdgeMetadata, Grana>() {
				     public Grana transform(EdgeMetadata metadata) {
				    	 Grana grana = (new GranaFactory()).create();
				    	 grana.setTezina(Double.parseDouble(metadata.getProperty("tezina")));
				         return grana;
				     }
		};
		
		return edgeTransformer;
	}
	
	private static Transformer<HyperEdgeMetadata, Grana> getHyperEdgeTransformer(){
		Transformer<HyperEdgeMetadata, Grana> hyperEdgeTransformer =
				 new Transformer<HyperEdgeMetadata, Grana>() {
				     public Grana transform(HyperEdgeMetadata metadata) {
				    	 Grana grana = (new GranaFactory()).create();
				    	 grana.setTezina(Double.parseDouble(metadata.getProperty("tezina")));
				         return grana;
				     }
		};
		
		return hyperEdgeTransformer;
	}
}

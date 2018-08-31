package MMS.Seminarski;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections15.Transformer;
import org.xml.sax.SAXException;

import javax.swing.JPanel;
import javax.swing.JFileChooser;

import MMS.Seminarski.algoritmi.Algoritam;
import MMS.Seminarski.algoritmi.FordFulkerson;
import MMS.Seminarski.graf.*;
import MMS.Seminarski.plugini.MisPlugin;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.io.GraphIOException;
import edu.uci.ics.jung.io.graphml.GraphMLReader2;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.FlowLayout;

public class PocetnaForma {

	private JFrame frmGraph;
	private JLabel lblStatus;
	
	private StaticLayout<Cvor, Grana> layout;
	private VisualizationViewer<Cvor, Grana> viewer;
	private String graphPath = "";
	private List<Algoritam> algoritamList = new ArrayList<Algoritam>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PocetnaForma window = new PocetnaForma();
					window.frmGraph.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 */
	public PocetnaForma() throws ParserConfigurationException, SAXException, IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 */
	
	private void initialize() throws ParserConfigurationException, SAXException, IOException {
		frmGraph = new JFrame();
		lblStatus = new JLabel("");
		
		postaviGraf();
		
		frmGraph.setTitle("Graph - Visualisation");
		frmGraph.setIconImage(Toolkit.getDefaultToolkit().getImage(PocetnaForma.class.getResource("/MMS/Seminarski/icons/cayleyicon.jpg")));
		frmGraph.setBounds(100, 100, 505, 300);
		frmGraph.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmGraph.setJMenuBar(menuBar);
		
		JMenu mnGraph = new JMenu("Graph file");
		menuBar.add(mnGraph);
				
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					otvoriGraf();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					System.out.println("Open - ParserConfigurationException: " + e.getMessage());
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					System.out.println("Open - SAXException: " + e.getMessage());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Open - IOException: " + e.getMessage());
				} catch (GraphIOException e) {
					// TODO Auto-generated catch block
					System.out.println("Open - GraphIOException: " + e.getMessage());
				}
			}
		});
		
		JMenu mnNew = new JMenu("New");
		mnGraph.add(mnNew);
		
		JMenuItem mntmDirectedGraph = new JMenuItem("Directed graph");
		mntmDirectedGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				noviGraf(true);
			}
		});
		mnNew.add(mntmDirectedGraph);
		
		JMenuItem mntmUndirectedGraph = new JMenuItem("Undirected graph");
		mntmUndirectedGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				noviGraf(false);
			}
		});
		mnNew.add(mntmUndirectedGraph);
		mnGraph.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					sacuvajGraf();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("Save - IOException: " + e1.getMessage());
				}
			}
		});
		mnGraph.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save as");
		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					sacuvajGrafKao();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("Save as - IOException: " + e1.getMessage());
				}
			}
		});
		mnGraph.add(mntmSaveAs);
		
		EditingModalGraphMouse<Cvor, Grana> graphMouse=(EditingModalGraphMouse<Cvor, Grana>)viewer.getGraphMouse();
		JMenu mnNacinRada = graphMouse.getModeMenu();
		
		mnNacinRada.setText("Graph mode");
		mnNacinRada.setPreferredSize(new Dimension(100,20));
		graphMouse.setMode(ModalGraphMouse.Mode.EDITING);
		menuBar.add(mnNacinRada);
		frmGraph.getContentPane().setLayout(new BorderLayout(0, 0));
		
		frmGraph.getContentPane().add(viewer);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		frmGraph.getContentPane().add(panel, BorderLayout.SOUTH);
		
		JButton btnResetGraph = new JButton("Reset graph");
		btnResetGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Algoritam.resetGraph(viewer.getGraphLayout().getGraph());
				resetGraf();
			}
		});
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel.add(btnResetGraph);
		
		JButton btnFf = new JButton("Start Ford-Fulkerson");
		btnFf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblStatus.setText("");
				
				algoritamList.clear();
				algoritamList.add(new FordFulkerson(viewer.getGraphLayout().getGraph()));
				
				if(algoritamList.get(0).getCvoroviKorak().size() == 0) {
					lblStatus.setText("Graph has not the right format for this algorithm.");
					algoritamList.clear();
				}
				else {
					viewer.getRenderContext().setVertexLabelTransformer(GraphTransformers.getVertexLabel2());
					viewer.getRenderContext().setEdgeLabelTransformer(GraphTransformers.getEdgeLabel2());
					
					viewer.repaint();
				}
			}
		});
		panel.add(btnFf);
		
		JButton btnPreviousStep = new JButton("Previous step");
		btnPreviousStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!algoritamList.isEmpty() && algoritamList.get(0) != null) {
					if(!algoritamList.get(0).previousStep()) {
						lblStatus.setText("Algoritam is at the beginning.");
					}
					else {
						lblStatus.setText("");
					}
				}
				else {
					lblStatus.setText("Algoritam is null.");
				}
				viewer.repaint();
			}
		});
		panel.add(btnPreviousStep);
		
		JButton btnNextStep = new JButton("Next step");
		btnNextStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!algoritamList.isEmpty() && algoritamList.get(0) != null) {
					if(!algoritamList.get(0).nextStep()) {
						lblStatus.setText("Algoritam is at the end.");
					}
					else {
						lblStatus.setText("");
					}
				}
				else {
					lblStatus.setText("Algoritam is null.");
				}
				viewer.repaint();
			}
		});
		panel.add(btnNextStep);
		
		JPanel panel_1 = new JPanel();
		frmGraph.getContentPane().add(panel_1, BorderLayout.NORTH);
		
		panel_1.add(lblStatus);
		frmGraph.pack();
		frmGraph.setVisible(true);
	}
	
	public void postaviGraf() {
		layout = new StaticLayout<Cvor, Grana>(new UndirectedSparseGraph<Cvor, Grana>(), 
				new Transformer<Cvor, Point2D>() {
			    	public Point2D transform(Cvor v) {
			    		return new Point2D.Double(v.getX(), v.getY());
			    	}             
			 	});
		layout.setSize(new Dimension(300, 300));
		
		viewer=new VisualizationViewer<Cvor, Grana>(layout);
		viewer.setPreferredSize(new Dimension(350, 350));
		
		viewer.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<Cvor>());
		viewer.getRenderer().getVertexLabelRenderer().setPosition(Position.AUTO);
        viewer.getRenderContext().setVertexFillPaintTransformer(GraphTransformers.getVertexPaint());
		
		viewer.getRenderContext().setEdgeLabelTransformer(GraphTransformers.getEdgeLabel1());
		viewer.getRenderContext().setEdgeDrawPaintTransformer(GraphTransformers.getEdgePaint());
		
		EditingModalGraphMouse<Cvor, Grana> graphMouse = 
				new EditingModalGraphMouse<Cvor, Grana>(viewer.getRenderContext(), new CvorFactory(), new GranaFactory());
				
		MisPlugin plugin = new MisPlugin(frmGraph, algoritamList, lblStatus);
		graphMouse.remove(graphMouse.getPopupEditingPlugin());
		graphMouse.add(plugin);
		
		viewer.setGraphMouse(graphMouse);
	}
	
	public void resetGraf() {
		lblStatus.setText("");
		algoritamList.clear();
		
		viewer.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<Cvor>());
		viewer.getRenderContext().setEdgeLabelTransformer(GraphTransformers.getEdgeLabel1());
		
		viewer.repaint();
	}
	
	public void noviGraf(boolean directed) {
		CvorFactory.setBrojac(1);
		GranaFactory.setBrojac(1);
		
		if(directed) {
			viewer.getGraphLayout().setGraph(new DirectedSparseGraph<Cvor, Grana>());
		}
		else {
			viewer.getGraphLayout().setGraph(new UndirectedSparseGraph<Cvor, Grana>());
		}
		
		graphPath="";
		
		resetGraf();
	}
	
	public void otvoriGraf() throws ParserConfigurationException, SAXException, IOException, GraphIOException {
		CvorFactory.setBrojac(1);
		GranaFactory.setBrojac(1);
		
		JFileChooser fileChooser=new JFileChooser();
		
		fileChooser.setCurrentDirectory(new File("."));
		fileChooser.setDialogTitle("Open graph");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileFilter(new FileNameExtensionFilter("*.graphml", "graphml"));
		
		if(fileChooser.showOpenDialog(frmGraph)==JFileChooser.APPROVE_OPTION) {
			GraphMLReader2<Graph<Cvor, Grana>, Cvor, Grana> graphReader =
					GraphReaderWriter.getGraphReader(fileChooser.getSelectedFile().toString());
			
			viewer.getGraphLayout().setGraph(graphReader.readGraph());
			
			graphPath=fileChooser.getSelectedFile().toString();
			
			resetGraf();
		}
	}
	
	public void sacuvajGraf() throws IOException {
		if(viewer.getGraphLayout().getGraph().getVertexCount()!=0) {
			if(graphPath.isEmpty()) {
				sacuvajGrafKao();
			}
			else {
				GraphReaderWriter.getGraphWriter(layout)
					.save(viewer.getGraphLayout().getGraph(), new FileWriter(graphPath));
			}
		}
	}

	public void sacuvajGrafKao() throws IOException {
		if(viewer.getGraphLayout().getGraph().getVertexCount()!=0) {
			JFileChooser fileChooser=new JFileChooser();
			
			fileChooser.setCurrentDirectory(new File("."));
			fileChooser.setDialogTitle("Save graph");
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileChooser.setFileFilter(new FileNameExtensionFilter("*.graphml", "graphml"));
			
			if(fileChooser.showSaveDialog(frmGraph)==JFileChooser.APPROVE_OPTION) {
				String put = fileChooser.getSelectedFile().getParent();
				String naziv = fileChooser.getSelectedFile().getName().trim();
				if(naziv.isEmpty()) {
					return;
				}
				
				int indeks = naziv.lastIndexOf(".");
				if(indeks>=0) {
					String ekstenzija = naziv.substring(indeks);
					if(ekstenzija.equals(".graphml")){
						naziv = naziv.substring(0, indeks).trim();
						if(naziv.isEmpty()) {
							return;
						}
					}
				}
				else {
					naziv = naziv + ".graphml";
				}
				
				graphPath = put + "\\" + naziv;
				
				GraphReaderWriter.getGraphWriter(layout)
					.save(viewer.getGraphLayout().getGraph(), new FileWriter(graphPath));
			}
		}
	}
	
}

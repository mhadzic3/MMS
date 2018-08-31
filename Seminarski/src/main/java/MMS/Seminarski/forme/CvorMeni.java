package MMS.Seminarski.forme;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import MMS.Seminarski.algoritmi.Algoritam;
import MMS.Seminarski.algoritmi.DFS;
import MMS.Seminarski.algoritmi.Dijkstra;
import MMS.Seminarski.graf.Cvor;
import MMS.Seminarski.graf.Grana;
import MMS.Seminarski.graf.GraphTransformers;
import edu.uci.ics.jung.visualization.VisualizationViewer;

public class CvorMeni extends JPopupMenu{
	private Cvor cvor;
	private VisualizationViewer<Cvor, Grana> viewer;
	
	private JMenuItem brisanje;
	private JMenuItem dijkstra;
	private JMenuItem dfs;
	private JLabel lblStatus;
	
	private List<Algoritam> algoritamList;
			
	public CvorMeni(List<Algoritam> algoritamList, JLabel lblStatus) {
		setLblStatus(lblStatus);
		setAlgoritamList(algoritamList);
		
		setBrisanje(new JMenuItem("Delete vertex"));
        getBrisanje().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getViewer().getPickedVertexState().pick(getCvor(), false);
				getViewer().getGraphLayout().getGraph().removeVertex(getCvor());
				getViewer().repaint();
			}
		});
        
        setDijkstra(new JMenuItem("Start Dijkstra"));
        getDijkstra().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getAlgoritamList().clear();
				getAlgoritamList().add(new Dijkstra(getViewer().getGraphLayout().getGraph(), getCvor()));
				
				if(getAlgoritamList().get(0).getCvoroviKorak().size() == 0) {
					getLblStatus().setText("Graph has not the right format for this algorithm.");
					getAlgoritamList().clear();
				}
				else {
					getViewer().getRenderContext().setVertexLabelTransformer(GraphTransformers.getVertexLabel1());
					getViewer().repaint();
				}
			}
		});
        
        this.setDfs(new JMenuItem("Start DFS"));
        this.getDfs().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getAlgoritamList().clear();
				getAlgoritamList().add(new DFS(getViewer().getGraphLayout().getGraph(), getCvor()));
				
				if(getAlgoritamList().get(0).getCvoroviKorak().size() == 0) {
					getLblStatus().setText("Graph has not the right format for this algorithm.");
					getAlgoritamList().clear();
				}
				else {
					getViewer().getRenderContext().setVertexLabelTransformer(GraphTransformers.getVertexLabel2());
					getViewer().repaint();
				}
			}
		});
        
        this.add(getBrisanje());
        this.addSeparator();
        
        this.add(getDijkstra());
        this.add(getDfs());
	}
	
	public void postaviAtribute(Cvor cvor, VisualizationViewer<Cvor, Grana> viewer) {
		setCvor(cvor);
		setViewer(viewer);
		
		getBrisanje().setText("Delete vertex " + getCvor().getNaziv());
	}

	public Cvor getCvor() {
		return cvor;
	}

	public void setCvor(Cvor cvor) {
		this.cvor = cvor;
	}

	public VisualizationViewer<Cvor, Grana> getViewer() {
		return viewer;
	}

	public void setViewer(VisualizationViewer<Cvor, Grana> viewer) {
		this.viewer = viewer;
	}

	public JMenuItem getBrisanje() {
		return brisanje;
	}

	public void setBrisanje(JMenuItem brisanje) {
		this.brisanje = brisanje;
	}

	public JMenuItem getDijkstra() {
		return dijkstra;
	}

	public void setDijkstra(JMenuItem dijkstra) {
		this.dijkstra = dijkstra;
	}

	public JMenuItem getDfs() {
		return dfs;
	}

	public void setDfs(JMenuItem dfs) {
		this.dfs = dfs;
	}

	public JLabel getLblStatus() {
		return lblStatus;
	}

	public void setLblStatus(JLabel lblStatus) {
		this.lblStatus = lblStatus;
	}

	public List<Algoritam> getAlgoritamList() {
		return algoritamList;
	}

	public void setAlgoritamList(List<Algoritam> algoritamList) {
		this.algoritamList = algoritamList;
	}
	
	
	
}

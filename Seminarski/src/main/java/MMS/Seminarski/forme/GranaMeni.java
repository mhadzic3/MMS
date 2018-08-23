package MMS.Seminarski.forme;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import MMS.Seminarski.graf.Cvor;
import MMS.Seminarski.graf.Grana;
import edu.uci.ics.jung.visualization.VisualizationViewer;

public class GranaMeni extends JPopupMenu {
	private Grana grana;
	private VisualizationViewer<Cvor, Grana> vv;
	
	private JMenuItem brisanje;
	private JMenuItem tezina;
	private JMenuItem promjena;
		
	private JFrame frame;
	private Point2D point;
	
	public GranaMeni(JFrame frame) {
		setFrame(frame);
		
        setBrisanje(new JMenuItem());
        getBrisanje().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getVv().getPickedEdgeState().pick(getGrana(), false);
				getVv().getGraphLayout().getGraph().removeEdge(getGrana());
				getVv().repaint();
			}
		});
        
        setTezina(new JMenuItem());
        
        setPromjena(new JMenuItem());
        getPromjena().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GranaForma forma=new GranaForma(getFrame(), getGrana());
				forma.setLocation((int)getPoint().getX() + getFrame().getX(), (int)getPoint().getY() + getFrame().getY());
				forma.setVisible(true);
				
				getVv().repaint();
			}
		});
        
        this.add(getBrisanje());
        this.addSeparator();
        
        this.add(getTezina());
        this.addSeparator();
        
        this.add(getPromjena());
    }
	
	public void postaviAtribute(Grana grana, VisualizationViewer<Cvor, Grana> vv, Point2D point) {
		setGrana(grana);
		setVv(vv);
		setPoint(point);
		
		getBrisanje().setText("Delete edge " + getGrana().getNaziv());
		getTezina().setText("Weight: " + getGrana().getTezina());
		getPromjena().setText("Edit edge " + getGrana().getNaziv());
	}

	public Grana getGrana() {
		return grana;
	}

	public void setGrana(Grana grana) {
		this.grana = grana;
	}

	public VisualizationViewer<Cvor, Grana> getVv() {
		return vv;
	}

	public void setVv(VisualizationViewer<Cvor, Grana> vv) {
		this.vv = vv;
	}

	public JMenuItem getBrisanje() {
		return brisanje;
	}

	public void setBrisanje(JMenuItem brisanje) {
		this.brisanje = brisanje;
	}

	public JMenuItem getTezina() {
		return tezina;
	}

	public void setTezina(JMenuItem tezina) {
		this.tezina = tezina;
	}

	public JMenuItem getPromjena() {
		return promjena;
	}

	public void setPromjena(JMenuItem promjena) {
		this.promjena = promjena;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public Point2D getPoint() {
		return point;
	}

	public void setPoint(Point2D point) {
		this.point = point;
	}
	
}

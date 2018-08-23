package MMS.Seminarski.plugini;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.List;

import javax.swing.JFrame;

import MMS.Seminarski.algoritmi.Algoritam;
import MMS.Seminarski.forme.CvorMeni;
import MMS.Seminarski.forme.GranaMeni;
import MMS.Seminarski.graf.Cvor;
import MMS.Seminarski.graf.Grana;
import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;

public class MisPlugin extends AbstractPopupGraphMousePlugin {
	private CvorMeni cvorMeni;
	private GranaMeni granaMeni;
	
	public MisPlugin(JFrame frame, List<Algoritam> algoritamList) {
		this(MouseEvent.BUTTON3_MASK);
		
		this.setCvorMeni(new CvorMeni(algoritamList));
		this.setGranaMeni(new GranaMeni(frame));
	}

	public MisPlugin(int modifiers) {
		super(modifiers);
	}

	@Override
	protected void handlePopup(MouseEvent e) {
		final VisualizationViewer<Cvor, Grana> vv = (VisualizationViewer<Cvor, Grana>)e.getSource();
		Point2D p = e.getPoint();
		
		GraphElementAccessor<Cvor, Grana> pickSupport = vv.getPickSupport();
		if(pickSupport != null) {
			Cvor cvor = pickSupport.getVertex(vv.getGraphLayout(), p.getX(), p.getY());
            if(cvor != null) {
            	cvorMeni.postaviAtribute(cvor, vv);
                cvorMeni.show(vv, e.getX(), e.getY());
            } 
            else {
                Grana grana = pickSupport.getEdge(vv.getGraphLayout(), p.getX(), p.getY());
                if(grana != null) {
                	granaMeni.postaviAtribute(grana, vv, p);
                    granaMeni.show(vv, e.getX(), e.getY());
                }
            }
        }
	}

	public CvorMeni getCvorMeni() {
		return cvorMeni;
	}

	public void setCvorMeni(CvorMeni cvorMeni) {
		this.cvorMeni = cvorMeni;
	}

	public GranaMeni getGranaMeni() {
		return granaMeni;
	}

	public void setGranaMeni(GranaMeni granaMeni) {
		this.granaMeni = granaMeni;
	}

}

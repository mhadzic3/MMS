package MMS.Seminarski.forme;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import MMS.Seminarski.graf.Grana;

import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;

public class GranaForma extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JFormattedTextField txtTezina;
	
	private Grana grana;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			GranaForma dialog = new GranaForma();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	
	public GranaForma() {
		init();
	}
	
	public GranaForma(JFrame frame, Grana grana) {
		super(frame, true);
		setGrana(grana);
		init();
	}

	private void init() {
		setTitle("Edge");
		setIconImage(Toolkit.getDefaultToolkit().getImage(GranaForma.class.getResource("/MMS/Seminarski/icons/cayleyicon.jpg")));
		setBounds(100, 100, 141, 133);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		JLabel lblTezina = new JLabel("Weight:");
		lblTezina.setHorizontalAlignment(SwingConstants.LEFT);
		lblTezina.setBounds(20, 11, 86, 14);
		contentPanel.add(lblTezina);
		
		final GranaForma dialog = this;
		
		JButton btnSacuvaj = new JButton("Save");
		btnSacuvaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(getGrana() != null) {
					getGrana().setTezina((Double)txtTezina.getValue());
					dialog.dispose();
				}
			}
		});
		btnSacuvaj.setBounds(20, 57, 86, 23);
		
		contentPanel.add(btnSacuvaj);
		
		txtTezina = new JFormattedTextField();
		txtTezina.setBounds(20, 26, 86, 20);
		contentPanel.add(txtTezina);
		
		if(getGrana() != null) {
			setTitle(getGrana().getNaziv());
			txtTezina.setValue(getGrana().getTezina());
		}
	}

	public Grana getGrana() {
		return grana;
	}

	public void setGrana(Grana grana) {
		this.grana = grana;
	}
}

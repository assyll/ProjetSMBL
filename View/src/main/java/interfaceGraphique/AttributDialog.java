package interfaceGraphique;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class AttributDialog extends JDialog implements ActionListener {

	private int nbAttribut;
	JButton ok, cancel;
	JPanel panelDialog;
	JScrollPane scrollAtt;
	Box boite;
	String[] attributs;

	public AttributDialog(Frame f, String s, int nbAtt) {
		super(f, s, true);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		nbAttribut = nbAtt;
		boite = Box.createVerticalBox();
		panelDialog = new JPanel();
		panelDialog.setPreferredSize(new Dimension(300, 400));
		scrollAtt = new JScrollPane(panelDialog);
		panelDialog.setLayout(new GridLayout(nbAtt + 1, 2, 20, 5));

		for (int i = 0; i < nbAtt; i++) {
			JLabel label = new JLabel("Attribut " + (i + 1));
			panelDialog.add(label);
			JTextField param = new JTextField(10);
			panelDialog.add(param);
		}

		ok = new JButton("Ok");
		panelDialog.add(ok);

		cancel = new JButton("Cancel");
		panelDialog.add(cancel);

		ok.addActionListener(this);
		cancel.addActionListener(this);

		boite.add(scrollAtt);
		this.add(boite);
		this.setBounds(400, 200, 400, 400);
		this.setVisible(true);
	}

	public int getNbAttributs() {
		return (nbAttribut);
	}

	public String[] getAttributs() {
		String[] tmp = new String[this.getNbAttributs()];
		int cpt = 0;
		for (Component comp : panelDialog.getComponents()) {
			if (comp instanceof JTextField) {
				tmp[cpt++] = ((JTextField) comp).getText();
			}
		}
		return tmp;
	}

	public void actionPerformed(ActionEvent evt) {
		//TODO Manu t'es un clochard
		String[] tmp;
		if (evt.getSource() == ok) {
			tmp = getAttributs();
			this.dispose();
		} else if (evt.getSource() == cancel) {
			this.dispose();
		}
	}

}

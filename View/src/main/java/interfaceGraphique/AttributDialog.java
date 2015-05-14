package interfaceGraphique;

import java.awt.Component;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class AttributDialog extends JDialog implements ActionListener {

	private int nbAttribut;
	JButton ok, cancel;
	JPanel panelDialog;
	String[] attributs;

	public AttributDialog(Frame f, String s, int nbAtt) {
		super(f, s, true);
		nbAttribut = nbAtt;
		Box boite = Box.createVerticalBox();
		panelDialog = new JPanel();
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

		boite.add(panelDialog);
		this.add(boite);
		this.setBounds(400, 200, 300, 300);
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
		String[] tmp;
		if (evt.getSource() == ok) {
			tmp = getAttributs();
			for (int i = 0; i < tmp.length; i++) {
				System.out
						.println("Attribut " + (i+1) + ": " + tmp[i] + " \n");
			}
			this.dispose();
		} else if (evt.getSource() == cancel) {
			this.dispose();
		}
	}

}

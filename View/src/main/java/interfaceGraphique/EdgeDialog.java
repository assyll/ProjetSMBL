package interfaceGraphique;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class EdgeDialog extends JDialog implements ActionListener {

	JTextField nameEdge, sourceEdge, endEdge, actionEdge, nbAttributsEdge;
	JLabel labelName, labelSource, labelEnd, labelAction, labelNbAtt;
	JButton ok, cancel;
	JFrame frame;
	String name, sourceE, endE, actionE;
	int nbAtt;
	boolean ferme;
	AttributDialog attDialog;

	public EdgeDialog(JFrame f, String s) {
		super(f, s, true);
		frame = f;
		Box boite = Box.createVerticalBox();
		JPanel panelDialog = new JPanel();
		panelDialog.setLayout(new GridLayout(6, 2, 20, 5));

		labelName = new JLabel("Label of Edge?");
		panelDialog.add(labelName);
		nameEdge = new JTextField(10);
		panelDialog.add(nameEdge);

		labelSource = new JLabel("What's the start Node?");
		panelDialog.add(labelSource);
		sourceEdge = new JTextField(10);
		panelDialog.add(sourceEdge);

		labelEnd = new JLabel("What's the end Node?");
		panelDialog.add(labelEnd);
		endEdge = new JTextField(10);
		panelDialog.add(endEdge);

		labelAction = new JLabel("What's the action?");
		panelDialog.add(labelAction);
		actionEdge = new JTextField(10);
		panelDialog.add(actionEdge);

		labelNbAtt = new JLabel("How many attributs?");
		panelDialog.add(labelNbAtt);
		nbAttributsEdge = new JTextField(10);
		panelDialog.add(nbAttributsEdge);

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

	public String getLabel() {
		return (nameEdge.getText());
	}

	public String getSource() {
		return (sourceEdge.getText());
	}

	public String getEnd() {
		return (endEdge.getText());
	}

	public String getAction() {
		return (actionEdge.getText());
	}

	public int getNbAtt() {
		int nbAtt = Integer.parseInt(nbAttributsEdge.getText());
		return nbAtt;
	}

	public String[] getAttributs() {
		if (attDialog == null) {
			return null;
		} else {
			return (attDialog.getAttributs());
		}
	}

	public JFrame getFrame() {
		return frame;
	}
	
	public boolean getFerme() {
		return ferme;
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == ok) {
			this.name = getLabel();
			this.sourceE = getSource();
			this.endE = getEnd();
			this.actionE = getAction();
			this.nbAtt = getNbAtt();
			ferme = false;
			this.dispose();
			if (nbAtt != 0) {
				attDialog = new AttributDialog(getFrame(), "Attributs Node",
						getNbAtt());
			}
		} else if (evt.getSource() == cancel) {
			ferme = true;
			this.dispose();
		}
	}
}

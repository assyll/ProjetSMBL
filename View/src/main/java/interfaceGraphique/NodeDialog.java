package interfaceGraphique;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class NodeDialog extends JDialog implements ActionListener {

	JTextField nameNode, nbAttributsNode;
	JLabel labelName, labelRoot, labelFinal, labelNbAtt;
	JButton ok, cancel;
	JFrame frame;
	String name;
	JCheckBox rootNode, finalNode;
	boolean rootN, finalN, ferme, check;
	int nbAtt;
	AttributDialog attDialog;

	@SuppressWarnings("static-access")
	public NodeDialog(JFrame f, String s) {
		super(f, s, true);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		frame = f;
		Box boite = Box.createVerticalBox();
		JPanel panelDialog = new JPanel();
		panelDialog.setLayout(new GridLayout(5, 2, 20, 5));

		labelName = new JLabel("Name of Node?");
		panelDialog.add(labelName);
		nameNode = new JTextField(10);
		panelDialog.add(nameNode);

		labelRoot = new JLabel("Is it a start Node? (y/n)");
		panelDialog.add(labelRoot);
		rootNode = new JCheckBox();
		panelDialog.add(rootNode);

		labelFinal = new JLabel("Is it an end Node? (y/n)");
		panelDialog.add(labelFinal);
		finalNode = new JCheckBox();
		panelDialog.add(finalNode);

		labelNbAtt = new JLabel("How many attributs?");
		panelDialog.add(labelNbAtt);
		nbAttributsNode = new JTextField(10);
		panelDialog.add(nbAttributsNode);

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

	public String getName() {
		return (nameNode.getText());
	}

	public boolean getRoot() {
		return(rootNode.isSelected());
	}

	public boolean getFinal() {
		return(finalNode.isSelected());
	}

	public int getNbAtt() {
		if(nbAttributsNode.getText().isEmpty()){
			return 0;
		} else {
			return Integer.parseInt(nbAttributsNode.getText());
		}
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

	public boolean getCheck() {
		return check;
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == ok) {
			ferme = false;
			check = true;
			if (!ferme) {
				name = getName();
				rootN = getRoot();
				finalN = getFinal();
				nbAtt = getNbAtt();

				if (nbAtt != 0) {
					attDialog = new AttributDialog(getFrame(),
							"Attributs Node", nbAtt);
				}

				dispose();
			}
		} else if (evt.getSource() == cancel) {
			ferme = true;
			check = false;
			dispose();
		}
	}
}

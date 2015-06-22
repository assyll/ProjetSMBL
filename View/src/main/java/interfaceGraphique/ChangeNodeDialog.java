package interfaceGraphique;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jsonAndGS.MyJsonGenerator;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

@SuppressWarnings("serial")
public class ChangeNodeDialog extends JDialog implements ActionListener {

	private static JTextField nameNode;
	private JTextField nbAttributsNode;
	private JLabel labelName, labelRoot, labelFinal, labelNbAtt;
	private JButton ok, cancel;
	private JFrame frame;
	private JCheckBox rootNode, finalNode;
	private static boolean ferme;
	private boolean isSource;
	private boolean isFinal;
	private int nbAtt = 0;
	private AttributDialog attDialog;
	private String nodeName;
	private Node oldNode;

	public ChangeNodeDialog(JFrame f, String s, final String nodeId, Graph graph) {
		super(f, s, true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		nodeName = nodeId;
		oldNode = graph.getNode(nodeName);
		isSource = oldNode.getAttribute(MyJsonGenerator.FORMAT_NODE_SOURCE);
		isFinal = oldNode.getAttribute(MyJsonGenerator.FORMAT_NODE_FINAL);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				ferme = true;
			}
		});

		frame = f;
		Box boite = Box.createVerticalBox();
		JPanel panelDialog = new JPanel();
		panelDialog.setLayout(new GridLayout(5, 2, 20, 5));

		labelName = new JLabel("Name of Node?");
		panelDialog.add(labelName);
		nameNode = new JTextField(10);
		panelDialog.add(nameNode);
		nameNode.setText(nodeName);

		labelRoot = new JLabel("Is it a start Node? (y/n)");
		panelDialog.add(labelRoot);
		rootNode = new JCheckBox();
		panelDialog.add(rootNode);
		rootNode.setSelected(isSource);

		labelFinal = new JLabel("Is it an end Node? (y/n)");
		panelDialog.add(labelFinal);
		finalNode = new JCheckBox();
		panelDialog.add(finalNode);
		finalNode.setSelected(isFinal);

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

	public static String getNameNode() {
		return (nameNode.getText());
	}

	public boolean getRoot() {
		return (rootNode.isSelected());
	}

	public boolean getFinal() {
		return (finalNode.isSelected());
	}

	public int getNbAtt() {
		if (nbAttributsNode.getText().isEmpty()) {
			return 0;
		} else {
			return Integer.parseInt(nbAttributsNode.getText());
		}
	}

	public String[][] getAttributs() {
		if (attDialog == null) {
			return null;
		} else {
			return (attDialog.getAttributs());
		}
	}

	public JFrame getFrame() {
		return frame;
	}

	public static boolean getFerme() {
		return ferme;
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == ok) {
			ferme = false;
			nbAtt = getNbAtt();

			if (nbAtt != 0) {
				attDialog = new AttributDialog(getFrame(), "Attributs Node",
						nbAtt);
				if (attDialog.isExit()) {
					ferme = true;
				}
			}

			dispose();
		} else if (evt.getSource() == cancel) {
			ferme = true;
			dispose();
		}
	}

}

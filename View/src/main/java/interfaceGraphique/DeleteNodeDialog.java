package interfaceGraphique;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

@SuppressWarnings("serial")
public class DeleteNodeDialog extends JDialog implements ActionListener {

	JFrame frame;
	Graph graph;
	String[] nodes;
	Box boite;
	JPanel panelDialog;
	JLabel name;
	JComboBox<String> node;
	JButton ok, cancel;
	boolean ferme, check;
	String nameNode;

	@SuppressWarnings("static-access")
	public DeleteNodeDialog(JFrame f, String s, Graph g) {
		super(f, s, true);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		frame = f;
		graph = g;
		nodes = getNodes();
		boite = Box.createVerticalBox();
		panelDialog = new JPanel(new GridLayout(2, 2, 10, 50));

		name = new JLabel("Node's name: ");
		panelDialog.add(name);
		node = new JComboBox<String>(nodes);
		panelDialog.add(node);

		ok = new JButton("Ok");
		panelDialog.add(ok);

		cancel = new JButton("Cancel");
		panelDialog.add(cancel);

		ok.addActionListener(this);
		cancel.addActionListener(this);

		boite.add(panelDialog);

		this.add(boite);
		this.setBounds(400, 200, 300, 200);
		this.setVisible(true);
	}

	public String getName() {
		return (node.getSelectedItem().toString());
	}

	public String[] getNodes() {
		String[] tmp = new String[graph.getNodeCount()];
		int cpt = 0;
		for (Node node : graph.getEachNode()) {
			tmp[cpt++] = node.toString();
		}
		return tmp;
	}

	public boolean getFerme() {
		return ferme;
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == ok) {
			ferme = false;
			if (!ferme) {
				getName();
				dispose();
			}
		} else if (evt.getSource() == cancel) {
			ferme = true;
			dispose();
		}
	}

}
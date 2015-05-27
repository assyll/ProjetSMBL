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

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;

@SuppressWarnings("serial")
public class SuppEdgeDialog extends JDialog implements ActionListener {

	JFrame frame;
	Graph graph;
	String[] edges;
	Box boite;
	JPanel panelDialog;
	JLabel name;
	JComboBox<String> edge;
	JButton ok, cancel;
	boolean ferme, check;
	String nameNode;

	@SuppressWarnings("static-access")
	public SuppEdgeDialog(JFrame f, String s, Graph g) {
		super(f, s, true);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		frame = f;
		graph = g;
		edges = getEdges();
		boite = Box.createVerticalBox();
		panelDialog = new JPanel(new GridLayout(2, 2, 10, 50));

		name = new JLabel("Node's name: ");
		panelDialog.add(name);
		edge = new JComboBox<String>(edges);
		panelDialog.add(edge);

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
		return (edge.getSelectedItem().toString());
	}

	public String[] getEdges() {
		String[] tmp = new String[graph.getEdgeCount()];
		int cpt = 0;
		for (Edge edge : graph.getEachEdge()) {
			tmp[cpt++] = edge.getId();
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

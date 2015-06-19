package interfaceGraphique;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
public class SelectEdgeDialog extends JDialog implements ActionListener {

	private JLabel labelEdgeName;
	private JButton ok, cancel;
	private JFrame frame;
	private static boolean ferme;
	private Graph graph;
	private JComboBox<String> edgeName;
	private String[] edges;

	public SelectEdgeDialog(JFrame f, String s, Graph g) {
		super(f, s, true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				ferme = true;
			}
		});

		frame = f;
		graph = g;
		edges = getEdges();
		Box boite = Box.createVerticalBox();
		JPanel panelDialog = new JPanel();
		panelDialog.setLayout(new GridLayout(2, 2, 20, 50));

		labelEdgeName = new JLabel("What's the name of the Edge?");
		panelDialog.add(labelEdgeName);
		edgeName = new JComboBox<String>(edges);
		panelDialog.add(edgeName);

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

	public String[] getEdges() {
		String[] tmp = new String[graph.getEdgeCount()];
		int cpt = 0;
		for (Edge edge : graph.getEachEdge()) {
			tmp[cpt++] = edge.getId();
		}
		return tmp;
	}

	public String getEdgeName() {
		return (String) edgeName.getSelectedItem();
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
			dispose();
		} else if (evt.getSource() == cancel) {
			ferme = true;
			dispose();
		}
	}
}

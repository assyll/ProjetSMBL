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

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

@SuppressWarnings("serial")
public class SelectNodeDialog extends JDialog implements ActionListener {
	
	private JLabel labelNodeName;
	private JButton ok, cancel;
	private JFrame frame;
	private static boolean ferme;
	private Graph graph;
	private JComboBox<String> nodeName;
	private String[] nodes;

	public SelectNodeDialog(JFrame f, String s, Graph g) {
		super(f, s, true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				ferme = true;
			}
		});

		frame = f;
		graph = g;
		nodes = getNodes();
		Box boite = Box.createVerticalBox();
		JPanel panelDialog = new JPanel();
		panelDialog.setLayout(new GridLayout(2, 2, 20, 50));

		labelNodeName = new JLabel("Node's name: ");
		panelDialog.add(labelNodeName);
		nodeName = new JComboBox<String>(nodes);
		panelDialog.add(nodeName);
		
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
	
	public String[] getNodes() {
		String[] tmp = new String[graph.getNodeCount()];
		int cpt = 0;
		for (Node node : graph.getEachNode()) {
			tmp[cpt++] = node.getId();
		}
		return tmp;
	}
	
	public String getNodeName(){
		return (String) nodeName.getSelectedItem();
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

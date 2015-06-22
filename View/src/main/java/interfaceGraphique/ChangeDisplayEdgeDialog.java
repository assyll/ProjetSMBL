package interfaceGraphique;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

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
public class ChangeDisplayEdgeDialog extends JDialog implements ActionListener {

	private JLabel labelDisplay;
	private JButton ok, cancel;
	private JFrame frame;
	private JComboBox<String> edgeDisplay;
	private List<String> displays;
	private boolean ferme;
	private Graph graph;
	Object[] tabDisplays;

	@SuppressWarnings({ "static-access", "unchecked", "rawtypes" })
	public ChangeDisplayEdgeDialog(JFrame f, String s, Graph g) {
		super(f, s, true);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				ferme = true;
			}
		});

		frame = f;
		graph = g;
		displays = getDisplays();
		tabDisplays = displays.toArray();
		Box boite = Box.createVerticalBox();
		JPanel panelDialog = new JPanel();
		panelDialog.setLayout(new GridLayout(2, 2, 20, 50));

		labelDisplay = new JLabel("Edge's display :");
		panelDialog.add(labelDisplay);
		edgeDisplay = new JComboBox(displays.toArray());
		edgeDisplay.insertItemAt("Label", 1);
		panelDialog.add(edgeDisplay);

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

	public String getDisplay() {
		return (edgeDisplay.getSelectedItem().toString());
	}

	public List<String> getDisplays() {
		List<String> tmp = new ArrayList<String>();
		for (Edge edge : graph.getEachEdge()) {
			for (String attKey : edge.getAttributeKeySet()) {
				if (!attKey.startsWith("ui.")) {
					if (!tmp.contains(attKey)) {
						tmp.add(attKey);
					}
				}
			}
		}
		return tmp;
	}

	public JFrame getFrame() {
		return frame;
	}

	public boolean getFerme() {
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

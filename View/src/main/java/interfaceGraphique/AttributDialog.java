package interfaceGraphique;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

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
	private JButton ok, cancel;
	private JPanel panelDialog, panelAttributs;
	private JScrollPane scrollAtt;
	private Box boite;
	private boolean exit = false;

	public AttributDialog(Frame f, String s, int nbAtt) {
		super(f, s, true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.addWindowListener(new WindowListener() {

			public void windowOpened(WindowEvent e) {

			}

			public void windowIconified(WindowEvent e) {

			}

			public void windowDeiconified(WindowEvent e) {

			}

			public void windowDeactivated(WindowEvent e) {

			}

			public void windowClosing(WindowEvent e) {
				exit = true;
			}

			public void windowClosed(WindowEvent e) {

			}

			public void windowActivated(WindowEvent e) {

			}
		});

		nbAttribut = nbAtt;
		boite = Box.createVerticalBox();
		panelDialog = new JPanel(new GridLayout(nbAtt, 2, 5, 5));
		panelDialog.setPreferredSize(new Dimension(300, 400));
		panelAttributs = new JPanel();
		scrollAtt = new JScrollPane(panelDialog);

		for (int i = 0; i < nbAtt; i++) {

			JLabel nameL = new JLabel("Name :");
			JTextField nameT = new JTextField(10);
			JLabel valueL = new JLabel(" value :");
			JTextField valueT = new JTextField(10);

			panelAttributs.add(nameL);
			panelAttributs.add(nameT);
			panelAttributs.add(valueL);
			panelAttributs.add(valueT);
			panelDialog.add(panelAttributs);
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

	public String[][] getAttributs() {
		String[][] tmp = new String[this.getNbAttributs()][2];
		int cptX = -1;
		int cptY = 0;
		
		for (Component attLine : panelDialog.getComponents()) {
			if (attLine instanceof JPanel) {
				for (Component attField : ((JPanel) attLine).getComponents()) {
					if (attField instanceof JTextField) {
						tmp[cptX][cptY++] = ((JTextField) attField).getText();
					}
					if (attField instanceof JLabel){
						if(((JLabel) attField).getText().equals("Name :")){
							cptX++;
							cptY = 0;
						}
					}
				}
			}
		}
		return tmp;
	}

	public boolean isExit() {
		return exit;
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == ok) {
			this.dispose();
		} else if (evt.getSource() == cancel) {
			this.dispose();
		}
	}
}

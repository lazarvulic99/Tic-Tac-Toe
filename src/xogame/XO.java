package xogame;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class XO implements ActionListener {
	
	static int actionsCounter = 0;

	Random random = new Random();
	JFrame frame = new JFrame();
	JPanel title_panel = new JPanel();
	JPanel button_panel = new JPanel();
	JLabel textfield = new JLabel();
	JButton[] polja = new JButton[9];
	boolean player1_turn;

	XO() {

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 1000);
		frame.getContentPane().setBackground(new Color(80, 80, 80));
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);

		textfield.setBackground(new Color(153, 51, 154));
		textfield.setForeground(new Color(255, 255, 204));
		textfield.setFont(new Font("Monospace", Font.BOLD, 75));
		textfield.setHorizontalAlignment(JLabel.CENTER);
		textfield.setText("XO Game");
		textfield.setOpaque(true);

		title_panel.setLayout(new BorderLayout());
		title_panel.setBounds(0, 0, 800, 100);

		button_panel.setLayout(new GridLayout(3, 3));
		button_panel.setBackground(new Color(40, 80, 20));

		for (int i = 0; i < 9; i++) {
			polja[i] = new JButton();
			button_panel.add(polja[i]);
			polja[i].setFont(new Font("DialogInput", Font.BOLD, 120));
			polja[i].setFocusable(false);
			polja[i].addActionListener(this);
		}

		title_panel.add(textfield);
		frame.add(title_panel, BorderLayout.NORTH);
		frame.add(button_panel);

		firstTurn();
	}

	public void setField(String igrac, int fieldNum) {
		if (igrac.equalsIgnoreCase("X")) {
			polja[fieldNum].setForeground(new Color(255, 0, 0));
			textfield.setText("O turn");
		} else if (igrac.equalsIgnoreCase("O")) {
			polja[fieldNum].setForeground(new Color(0, 0, 255));
			textfield.setText("X turn");
		}
		polja[fieldNum].setText(igrac);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < 9; i++) {
			if (e.getSource() == polja[i]) {
				if (polja[i].getText() == "") {
					actionsCounter++;
					if((actionsCounter % 2) == 1) {
						setField("X", i);
					}else {
						setField("O", i);
					}
					check();
				} else {
					textfield.setText("Polje popunjeno");
				}
			}
		}
	}

	public void firstTurn() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (random.nextInt(2) == 0) {
			textfield.setText("X turn");
		} else {
			textfield.setText("O turn");
		}
	}

	public void does_X_wonTheGame() {
		pobedaPoVrstama("X");
		pobedaPoKolonama("X");
		pobedaPoDijagonalama("X");
	}

	public void pobedaPoVrstama(String igrac) {
		for (int i = 0; i < 3; i++) {
			if ((polja[i * 3].getText() == igrac) && (polja[i * 3 + 1].getText() == igrac)
					&& (polja[i * 3 + 2].getText() == igrac)) {
				if (igrac.equalsIgnoreCase("X")) {
					gameWon("X", i * 3, i * 3 + 1, i * 3 + 2);
				} else if (igrac.equalsIgnoreCase("O")) {
					gameWon("O", i * 3, i * 3 + 1, i * 3 + 2);
				}
			}
		}
	}

	public void pobedaPoKolonama(String igrac) {
		for (int i = 0; i < 3; i++) {
			if ((polja[i].getText() == igrac) && (polja[i + 3].getText() == igrac)
					&& (polja[i + 6].getText() == igrac)) {
				if (igrac.equalsIgnoreCase("X")) {
					gameWon("X", i, i + 3, i + 6);
				} else if (igrac.equalsIgnoreCase("O")) {
					gameWon("O", i, i + 3, i + 6);
				}
			}
		}
	}

	public void pobedaPoDijagonalama(String igrac) {
		for (int i = 0; i < 2; i++) {
			if ((polja[i * 2].getText() == igrac) && (polja[4].getText() == igrac)
					&& (polja[8 - i * 2].getText() == igrac)) {
				if (igrac.equalsIgnoreCase("X")) {
					gameWon("X", i * 2, 4, 8 - i * 2);
				} else if (igrac.equalsIgnoreCase("O")) {
					gameWon("O", i * 2, 4, 8 - i * 2);
				}
			}
		}
	}

	public void does_O_wonThegame() {
		pobedaPoVrstama("O");
		pobedaPoKolonama("O");
		pobedaPoDijagonalama("O");
	}

	public void check() {
		does_X_wonTheGame();
		does_O_wonThegame();
	}

	public void reset_all_fields() {
		for (int i = 0; i < 9; i++) {
			polja[i].setEnabled(false);
		}
	}

	public void gameWon(String igrac, int field1, int field2, int field3) {
		polja[field1].setBackground(Color.RED);
		polja[field2].setBackground(Color.RED);
		polja[field3].setBackground(Color.RED);
		reset_all_fields();
		textfield.setText(igrac + " won");
	}
}

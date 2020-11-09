package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class GameControlPanel extends JPanel {
	
	String theGuess, guessResult;
	private JButton next, accuse;
	private JTextField guess, result, turn, roll;
	Player player;
	int row;

	public GameControlPanel() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2, 0));
		mainPanel.setPreferredSize(new Dimension(700, 130));
		JPanel panel = createTurnAndRoll();
		mainPanel.add(panel);
		JPanel panel2 = createButtons();
		mainPanel.add(panel2);
		JPanel bottomPanel = createGuess();
		mainPanel.add(bottomPanel);
		JPanel bottomPanel2 = createResult();
		mainPanel.add(bottomPanel2);
		add(mainPanel);
	}
	
	public JPanel createTurnAndRoll() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 0));
		
		JPanel whoseTurnPanel = new JPanel();
		whoseTurnPanel.setLayout(new GridLayout(1, 2));
		JLabel whoseTurn = new JLabel("Whose turn?");
		turn = new JTextField(15);
		whoseTurnPanel.add(whoseTurn);
		whoseTurnPanel.add(turn);
		
		JPanel rollPanel = new JPanel();
		rollPanel.setLayout(new GridLayout(1, 2));
		JLabel theRoll = new JLabel("Roll:");
		roll = new JTextField(5);
		rollPanel.add(theRoll);
		rollPanel.add(roll);
	
		panel.add(whoseTurnPanel, BorderLayout.EAST);
		panel.add(rollPanel, BorderLayout.WEST);
		panel.setBorder(new TitledBorder (new EtchedBorder()));
		panel.setBackground(Color.LIGHT_GRAY);
		return panel;
	}
	
	public JPanel createButtons() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		accuse = new JButton("Make Accusation");
		next = new JButton("NEXT!");
		panel.add(accuse);
		panel.add(next);
		panel.setBorder(new TitledBorder (new EtchedBorder()));
		panel.setBackground(Color.LIGHT_GRAY);
		return panel;		
	}
	
	public JPanel createGuess() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 0));
		guess = new JTextField(theGuess, 20);
		guess.setBorder(BorderFactory.createTitledBorder("Guess"));
		panel.add(guess, BorderLayout.WEST);
		panel.setBorder(new TitledBorder (new EtchedBorder()));
		panel.setBackground(Color.GRAY);
		return panel;

	}
	
	public JPanel createResult() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 0));
		result = new JTextField(guessResult,20);
		result.setBorder(BorderFactory.createTitledBorder("Guess Result!"));
		panel.add(result);
		panel.setBorder(new TitledBorder (new EtchedBorder()));
		panel.setBackground(Color.GRAY);
		return panel;
	}
	
	public void updateDisplay() {
		guess.setText(getGuess());
		result.setText(getResult());
	}

	private void setGuess(String string) {
		this.theGuess = string;

	}
	private void setGuessResult(String string) {
		this.guessResult = string;
	}
	private void setTurn(ComputerPlayer computerPlayer, int i) {
		this.player = computerPlayer;
		this.row = i;
	}
	public String getGuess() {
		return theGuess;
	}
	public String getResult() {
		return guessResult;
	}
	



	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		// test filling in the data
		
		//panel.setTurn(new ComputerPlayer( "Col. Mustard", Color.ORANGE, 6, 5), 5);
		
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
		panel.updateDisplay();
	}

}

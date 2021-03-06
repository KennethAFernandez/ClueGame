package clueGame;

import java.awt.BorderLayout;


import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class GameControlPanel extends JPanel {

	// strings to hold value names
	String theGuess, guessResult, turnName;
	// various variables to hold textfields and buttons
	private JButton next, accuse;
	public JTextField guess, result, turn, roll;

	int rollNum;
	int counter = 0;

	Player player;
	Color color;

	boolean firstIter = true;
	boolean gameStartDice = true;
	ClueGame game;
	private static Board board;

	// sets various panels and adds them to the main panel
	public GameControlPanel(Board gameBoard) {

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2, 0));

		JPanel nameAndDie = createTurnAndRoll();
		mainPanel.add(nameAndDie);

		JPanel buttons = createButtons();
		mainPanel.add(buttons);

		JPanel guess = createGuess();
		mainPanel.add(guess);

		JPanel result = createResult();
		mainPanel.add(result);

		add(mainPanel);
		board = gameBoard;
	}



	// creates the needed panels and labels, and passes an instance variable
	// through for updating purposes. then adds everything to the panel.
	public JPanel createTurnAndRoll() {

		JPanel panel = new JPanel();
		JPanel whoseTurnPanel = new JPanel();
		JPanel rollPanel = new JPanel();

		JLabel whoseTurn = new JLabel("Whose turn?");
		JLabel theRoll = new JLabel("Roll:");

		turn = new JTextField(turnName);
		roll = new JTextField(rollNum);

		panel.setLayout(new GridLayout(2, 0));
		whoseTurnPanel.setLayout(new GridLayout(1, 2));
		rollPanel.setLayout(new GridLayout(1, 2));

		whoseTurnPanel.add(whoseTurn);
		whoseTurnPanel.add(turn);
		rollPanel.add(theRoll);
		rollPanel.add(roll);

		panel.add(whoseTurnPanel, BorderLayout.EAST);
		panel.add(rollPanel, BorderLayout.WEST);
		panel.setBorder(new TitledBorder(new EtchedBorder()));
		panel.setBackground(Color.LIGHT_GRAY);

		return panel;
	}

	// creates the needed panels and labels, and passes an instance variable
	// through for updating purposes. then adds everything to the panel.
	public JPanel createButtons() {

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));

		accuse = new JButton("Make Accusation");
		next = new JButton("NEXT!");
		next.addActionListener(new ButtonListener());
		accuse.addActionListener(new accusationListener());
		panel.add(accuse);
		panel.add(next);

		panel.setBorder(new TitledBorder(new EtchedBorder()));
		panel.setBackground(Color.LIGHT_GRAY);

		return panel;
	}

	// button listener for when next is hit.
	// on certain conditionals various things will happen
	class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			guess.setText("");
			result.setText("");

			if(board.humanPlayerTurn == true && board.hasMoved == false) {
				JButton ok = new JButton();
				JOptionPane.showMessageDialog(ok, "Player has not finished turn");
				return;
			}


			counter = counter % 6;
			player = board.players.get(board.gameCharacters.get(counter).getCardName());
			
			if(counter != 5 && player == board.players.get(Board.getInstance().HumanPlayer.getName())) {
				player = board.players.get(board.gameCharacters.get(5).getCardName());
			}
			if(counter == 5 && player != board.players.get(Board.getInstance().HumanPlayer.getName())) {
				player = board.players.get(Board.getInstance().HumanPlayer.getName());
			}
			
			if(player.getName() == board.HumanPlayer.getName()) {
				board.humanPlayerTurn = true;
				board.hasMoved = false;
			} 
			
			turn.setText(player.getName());
			turn.setBackground(player.getColor());
			counter++;

			rollTheDice(false);
		}
	}

	class accusationListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(board.humanPlayerTurn == true && board.hasMoved == false) {
				AccusationPanel panel = new AccusationPanel();
				panel.setVisible(true);
				board.humanPlayerTurn = false;
			} else if (board.humanPlayerTurn == false) {
				JButton ok = new JButton();
				JOptionPane.showMessageDialog(ok, "Error: Not your turn!");
			} 
		}
	}

	// creates the needed panels and labels, and passes an instance variable
	// through for updating purposes. then adds everything to the panel.
	public JPanel createGuess() {

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 0));

		guess = new JTextField(theGuess, 25);
		guess.setBorder(BorderFactory.createTitledBorder("Guess"));

		panel.add(guess, BorderLayout.WEST);
		panel.setBorder(new TitledBorder(new EtchedBorder()));
		panel.setBackground(Color.GRAY);
		return panel;

	}

	// helper method that will determine the roll and find the valid targets
	public void rollTheDice(boolean firstDraw) {
		if(!firstDraw) {
			rollNum = (int) (Math.random() * 6) + 1;
		}
		roll.setText(String.valueOf(rollNum));
		board.calcTargets(player.getLocation(), rollNum);
		if (player == board.players.get(Board.getInstance().HumanPlayer.getName())) {
			return;
		} else {
			player.setLocation(player.selectTargets(board.targets));
			board.calcTargets(new BoardCell(0, 0), 0);
		}
	}

	// creates the needed panels and labels, and passes an instance variable
	// through for updating purposes. then adds everything to the panel.
	public JPanel createResult() {

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 0));

		result = new JTextField(guessResult, 25);
		result.setBorder(BorderFactory.createTitledBorder("Guess Result!"));

		panel.add(result);
		panel.setBorder(new TitledBorder(new EtchedBorder()));
		panel.setBackground(Color.GRAY);
		return panel;
	}

	// update display function that updates the text boxes
	public void updateDisplay() {
		guess.setText(theGuess);
		result.setText(guessResult);
		if(firstIter == true) {
			turn.setText(turnName);
			turn.setBackground(color);
			roll.setText(Integer.toString(rollNum));
			firstIter = false;
		}
	}

	// setters and getters
	public void setGuess(String string) {
		this.theGuess = string;
	}

	public void setGuessResult(String string) {
		this.guessResult = string;
	}

	void setTurn(Player computerPlayer, int i, boolean drawTargets) {
		this.player = computerPlayer;
		this.turnName = computerPlayer.getName();
		this.rollNum = i;
		this.color = computerPlayer.getColor();
		if(drawTargets) {
			rollTheDice(drawTargets);
		}
	}

	public String getGuess() {
		return theGuess;
	}

	public String getResult() {
		return guessResult;
	}

	public int getRoll() {
		return rollNum;
	}

	public Player getPlayer() {
		return player;
	}
}

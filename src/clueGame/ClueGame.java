package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class ClueGame extends JFrame{
	
	boolean firstTurn = true;
	GameControlPanel controlPanel;
	GameCardPanel cardPanel;
	ClueGame game;
	
	// sets preferred size
	public ClueGame(Board board) {
		setTitle("Clue Game - CSCI306");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(650, 650));
		
		controlPanel = new GameControlPanel(board);
		cardPanel = new GameCardPanel();
		add(controlPanel, BorderLayout.SOUTH);
		add(cardPanel, BorderLayout.EAST);
		showSplash();
		nextTurn();
	}
	
	// determines if it is the first turn to set it to the humanPlayer
	public void nextTurn() {
		if(firstTurn) {
			controlPanel.setTurn((HumanPlayer) Board.getInstance().HumanPlayer, (int) (Math.random() * 6) + 1, true);
			controlPanel.updateDisplay();
			firstTurn = false;
		}
	}
	
	// outputs the splash box with the proper output
	public void showSplash() {
		JButton ok = new JButton();
		JOptionPane.showMessageDialog(ok, "You are " + Board.getInstance().HumanPlayer.getName() + 
				"\n"+ "Can you find the solution\n"+ "before the computer players?");
	}
	
	
	// main game code
	public static void main(String[] args) {
		// gets the instance 
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
		ClueGame frame = new ClueGame(board);	
		frame.add(board, BorderLayout.CENTER);
		frame.repaint();
		frame.pack();
		frame.setVisible(true);
	}
}

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
	public ClueGame() {
		setTitle("Clue Game - CSCI306");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(650, 650));
		
		controlPanel = new GameControlPanel();
		cardPanel = new GameCardPanel();
		add(controlPanel, BorderLayout.SOUTH);
		add(cardPanel, BorderLayout.EAST);
		
		showSplash();
		
	}
	
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
		
		ClueGame frame = new ClueGame();	
		frame.add(board, BorderLayout.CENTER);
		 
		frame.repaint();
		frame.pack();
		frame.setVisible(true);
	}
}

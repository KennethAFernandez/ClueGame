package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class ClueGame extends JFrame{
	
	// sets preferred size
	public ClueGame() {
		this.setPreferredSize(new Dimension(635, 625));		
	}
	
	
	// main game code
	public static void main(String[] args) {
		
		// gets the instance 
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		// creates the frame and adds the panel with proper size
		
		
		JButton ok = new JButton();
		JOptionPane.showMessageDialog(ok, "You are " + Board.getInstance().HumanPlayer.getName() + 
				"\n"+ "Can you find the solution\n"+ "before the computer players?");
		
		
		// creates main gui
		ClueGame frame = new ClueGame();
		GameControlPanel controlPanel = new GameControlPanel();
		GameCardPanel cardPanel = new GameCardPanel();
		frame.setSize(new Dimension(750, 750));
		frame.setTitle("Clue Game-CSCI306");
		// adding everything to the frame where we want it
		frame.add(controlPanel, BorderLayout.SOUTH);
		frame.add(cardPanel, BorderLayout.EAST);	
		frame.add(board, BorderLayout.CENTER);
		// proper JFrame Functionality. 
		frame.repaint();
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
}

package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class AccusationPanel extends JFrame{

	// all necessary variables for creating the accusation panel
	private ArrayList<String> players;
	private ArrayList<String> weapons;
	private ArrayList<String> rooms;
	// strings to find the player's choice
	String personChoice;
	String weaponChoice;
	String roomChoice;
	Card submitPerson;
	Card submitWeapon;
	Card submitRoom;
	JButton submit;
	JButton cancel;
	// combo boxes
	@SuppressWarnings("rawtypes")
	JComboBox personCombo;
	@SuppressWarnings("rawtypes")
	JComboBox weaponCombo;
	@SuppressWarnings("rawtypes")
	JComboBox roomCombo;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	// constructor that creates the lists, orders them
	// as well as creates the labels and combo boxes. 
	public AccusationPanel() {

		players = new ArrayList<String>();
		weapons = new ArrayList<String>();
		rooms = new ArrayList<String>();

		submit = new JButton("Submit");
		cancel = new JButton("Cancel");
		cancel.addActionListener(new cancelListener());
		submit.addActionListener(new submitListener());

		for(Card c: Board.getInstance().gameDeck) {
			if(c.getCardType() == CardType.PERSON) {
				players.add(c.getCardName());
			} else if (c.getCardType() == CardType.WEAPON) {
				weapons.add(c.getCardName());
			} else {
				rooms.add(c.getCardName());
			}
		}

		JLabel room = new JLabel("Current room");
		roomCombo = new JComboBox(rooms.toArray());

		JLabel person = new JLabel("Person");
		personCombo = new JComboBox(players.toArray());

		JLabel weapon = new JLabel("Weapon");
		weaponCombo = new JComboBox(weapons.toArray());

		setTitle("Make an accusation");
		setSize(250, 250);
		setLayout(new GridLayout(4, 2));
		add(room);
		add(roomCombo);
		add(person);
		add(personCombo);
		add(weapon);
		add(weaponCombo);
		add(submit);
		add(cancel);

	}

	// an action listener for the cancel button that makes the panel invisible
	private class cancelListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
		}
	}
	
	// an action listener for the submit button which is going to determine
	// the player's choices and call the check accusation method to determine
	// the result of the game
	private class submitListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			personChoice = personCombo.getSelectedItem().toString();
			weaponChoice = weaponCombo.getSelectedItem().toString();
			roomChoice = roomCombo.getSelectedItem().toString();
			for(Card c: Board.getInstance().gameDeck) {
				if(c.getCardName() == personChoice) {
					submitPerson = c;
				}
				if(c.getCardName() == weaponChoice) {
					submitWeapon = c;
				}
				if(c.getCardName() == roomChoice) {
					submitRoom = c;
				}
			}
			boolean resultOfAccusation =Board.getInstance().checkAccusation(submitPerson, submitRoom, submitWeapon);
			setVisible(false);
			if(resultOfAccusation) {
				JButton ok = new JButton();
				JOptionPane.showMessageDialog(ok, "You win!");
				System.exit(0);
			}
			if(!resultOfAccusation) {
				JButton ok = new JButton();
				JOptionPane.showMessageDialog(ok, "You lose.");
				System.exit(0);
			}
		}

	}

}
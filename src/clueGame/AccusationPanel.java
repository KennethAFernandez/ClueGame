package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class AccusationPanel extends JFrame{

	private ArrayList<String> players;
	private ArrayList<String> weapons;
	private ArrayList<String> rooms;
	String personChoice;
	String weaponChoice;
	String roomChoice;
	Card submitPerson;
	Card submitWeapon;
	Card submitRoom;
	JButton submit;
	JButton cancel;
	@SuppressWarnings("rawtypes")
	JComboBox personCombo;
	@SuppressWarnings("rawtypes")
	JComboBox weaponCombo;
	@SuppressWarnings("rawtypes")
	JComboBox roomCombo;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AccusationPanel() {

		players = new ArrayList<String>();
		weapons = new ArrayList<String>();
		rooms = new ArrayList<String>();

		submit = new JButton("Submit");
		cancel = new JButton("Cancel");
		cancel.addActionListener(new cancelListener());
		submit.addActionListener(new submitListener());

		for(Card c: Board.getInstance().deck) {
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

		setTitle("Make a suggestion");
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

	private class cancelListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
		}

	}
	
	private class submitListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			personChoice = personCombo.getSelectedItem().toString();
			weaponChoice = weaponCombo.getSelectedItem().toString();
			roomChoice = roomCombo.getSelectedItem().toString();
			for(Card c: Board.getInstance().deck) {
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
			Board.getInstance().checkAccusation(submitPerson, submitRoom, submitWeapon);
			setVisible(false);
		}

	}

}
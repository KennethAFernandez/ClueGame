package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class SuggestionPanel extends JFrame{

	private ArrayList<String> players;
	private ArrayList<String> weapons;
	String personChoice;
	String weaponChoice;
	String currRoomName;
	Card submitPerson;
	Card submitWeapon;
	Card submitRoom;
	JButton submit;
	JButton cancel;
	@SuppressWarnings("rawtypes")
	JComboBox personCombo;
	@SuppressWarnings("rawtypes")
	JComboBox weaponCombo;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SuggestionPanel() {
		
		currRoomName = Board.getInstance().roomMap.get(Board.getInstance().HumanPlayer.getLocation().getInitial()).getName();
		players = new ArrayList<String>();
		weapons = new ArrayList<String>();
		
		submit = new JButton("Submit");
		cancel = new JButton("Cancel");
		cancel.addActionListener(new cancelListener());
		submit.addActionListener(new submitListener());
		
		
		JLabel room = new JLabel("Current room");
		JLabel roomText = new JLabel(currRoomName);
		roomText.setBorder(new TitledBorder(new EtchedBorder()));
		
		
		for(Card c: Board.getInstance().deck) {
			if(c.getCardType() == CardType.PERSON) {
				players.add(c.getCardName());
			} else if (c.getCardType() == CardType.WEAPON) {
				weapons.add(c.getCardName());
			}
		}
		JLabel person = new JLabel("Person");
		personCombo = new JComboBox(players.toArray());
		
		JLabel weapon = new JLabel("Weapon");
		weaponCombo = new JComboBox(weapons.toArray());
		
		setTitle("Make a suggestion");
		setSize(250, 250);
		setLayout(new GridLayout(4, 2));
		add(room);
		add(roomText);
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
			for(Card c: Board.getInstance().deck) {
				if(c.getCardName() == personChoice) {
					submitPerson = c;
				}
				if(c.getCardName() == weaponChoice) {
					submitWeapon = c;
				}
				if(c.getCardName() == currRoomName) {
					submitRoom = c;
				}
			}
			Board.getInstance().handleSuggestion(submitPerson, submitRoom, submitWeapon, Board.getInstance().HumanPlayer);
			setVisible(false);
		}
		
	}

}

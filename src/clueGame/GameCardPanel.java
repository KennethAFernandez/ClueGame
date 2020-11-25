package clueGame;

import java.awt.Color;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class GameCardPanel extends JPanel{

	// variabels to hold the main panel and the in hand/ seen cards
	private JPanel mainPanel, inHand, seen;
	private JLabel handLabel, seenLabel;


	// just for testing
	Player humanPlayer;

	// creates jpanels and titles then adds them to the main panel
	public GameCardPanel() {
		humanPlayer = Board.getInstance().HumanPlayer;
		mainPanel = new JPanel();
		JPanel people = people();
		JPanel rooms = rooms();
		JPanel weapons = weapons();

		Border peopleTitle = BorderFactory.createTitledBorder("People");
		people.setBorder(peopleTitle);
		Border roomTitle = BorderFactory.createTitledBorder("Rooms");
		rooms.setBorder(roomTitle);
		Border weaponTitle = BorderFactory.createTitledBorder("Weapons");
		weapons.setBorder(weaponTitle);

		mainPanel.add(people);
		mainPanel.add(rooms);
		mainPanel.add(weapons);
		mainPanel.setLayout(new GridLayout(3, 1));
		mainPanel.setBorder(new TitledBorder (new EtchedBorder()));
		add(mainPanel);
	}

	// after creating various panels and labels, iterates through the hands
	// and assigns them where they need to go
	public JPanel people() {
		JPanel knownCards = new JPanel();
		inHand = new JPanel();
		seen = new JPanel();
		handLabel = new JLabel("In Hand:");
		seenLabel = new JLabel("Seen:");

		ArrayList<Card> hand = new ArrayList<Card>();
		ArrayList<Card> seenList = new ArrayList<Card>();
		hand = Board.getInstance().HumanPlayer.getHand();
		seenList =Board.getInstance().HumanPlayer.getSeen();
		if(hand.size() == 0) {
			JLabel handText = new JLabel("None");  
			inHand.add(handText);
		}
		if(seenList.size() == 0) {
			JTextField alreadySeen = new JTextField("None");
			seen.add(alreadySeen);
		}
		for(Card c: hand) {
			if(c.getCardType() == CardType.PERSON) {
				JLabel handText = new JLabel(); 
				handText.setText(c.getCardName());
				inHand.add(handText);
				handText.revalidate();
			}
		}
		if(seenList.size() > 0) {
			for(Card c: seenList) {
				if(c.getCardType().equals(CardType.PERSON)) {
					JLabel alreadySeen = new JLabel();
					alreadySeen.setText(c.getCardName());
					Color color = Board.getInstance().cardColors.get(c);
					alreadySeen.setBackground(color);
					seen.revalidate();
				}
			}
		}
		knownCards.add(handLabel);
		knownCards.add(inHand);
		knownCards.add(seenLabel);
		knownCards.add(seen);
		seen.revalidate();
		knownCards.setLayout(new GridLayout(0, 1)); // 4
		inHand.setLayout(new GridLayout(0, 1)); // 3
		seen.setLayout(new GridLayout(0, 1)); // 3
		knownCards.setBorder(new TitledBorder (new EtchedBorder()));
		knownCards.setBackground(Color.GRAY);
		return knownCards;
	}

	// after creating various panels and labels, iterates through the hands
	// and assigns them where they need to go
	public JPanel rooms() {
		JPanel roomCards = new JPanel();
		inHand = new JPanel();
		seen = new JPanel();
		handLabel = new JLabel("In Hand:");
		seenLabel = new JLabel("Seen:");

		ArrayList<Card> hand = new ArrayList<Card>();
		ArrayList<Card> seenList = new ArrayList<Card>();
		hand = Board.getInstance().HumanPlayer.getHand();
		seenList = Board.getInstance().HumanPlayer.getSeen();
		if(hand.size() == 0) {
			JLabel handText = new JLabel("None");  
			inHand.add(handText);
		}
		if(seenList.size() == 0) {
			JTextField alreadySeen = new JTextField("None");
			seen.add(alreadySeen);
		}
		for(Card c: hand) {
			if(c.getCardType() == CardType.ROOM) {
				JLabel handText = new JLabel();
				handText.setText(c.getCardName());
				inHand.add(handText);
				handText.revalidate();
			}
		}
		if(seenList.size() > 0) {
			for(Card c: seenList) {
				if(c.getCardType().equals(CardType.ROOM)) {
					JLabel alreadySeen = new JLabel();
					alreadySeen.setText(c.getCardName());
					Color color = Board.getInstance().cardColors.get(c);
					alreadySeen.setBackground(color);
					seen.add(alreadySeen);
					seen.revalidate();
				}
			}
		}
		roomCards.add(handLabel);
		roomCards.add(inHand);
		roomCards.add(seenLabel);
		roomCards.add(seen);
		seen.revalidate();
		roomCards.setLayout(new GridLayout(0, 1, 0, 0));
		inHand.setLayout(new GridLayout(0, 1));
		seen.setLayout(new GridLayout(0, 1));
		roomCards.setBorder(new TitledBorder (new EtchedBorder()));
		roomCards.setBackground(Color.LIGHT_GRAY);
		return roomCards;
	}

	// after creating various panels and labels, iterates through the hands
	// and assigns them where they need to go
	public JPanel weapons() {
		JPanel weaponCards = new JPanel();
		inHand = new JPanel();
		seen = new JPanel();
		handLabel = new JLabel("In Hand:");
		seenLabel = new JLabel("Seen:");

		ArrayList<Card> hand = new ArrayList<Card>();
		ArrayList<Card> seenList = new ArrayList<Card>();
		hand = Board.getInstance().HumanPlayer.getHand();
		seenList = Board.getInstance().HumanPlayer.getSeen();
		
		if(hand.size() == 0) {
			JLabel handText = new JLabel("None"); // 
			inHand.add(handText);
		}
		if(seenList.size() == 0) {
			JTextField alreadySeen = new JTextField("None");
			seen.add(alreadySeen);
		}
		for(Card c: hand) {
			if(c.getCardType().equals(CardType.WEAPON)) {
				JLabel handText = new JLabel();
				handText.setText(c.getCardName());
				inHand.add(handText);
				handText.revalidate();
			} 
				
		}

		if(seenList.size() > 0) {
			for(Card c: seenList) {
				if(c.getCardType().equals(CardType.WEAPON)) {
					JLabel alreadySeen = new JLabel();
					alreadySeen.setText(c.getCardName());
					Color color = Board.getInstance().cardColors.get(c);
					alreadySeen.setBackground(color);
					seen.add(alreadySeen);
					seen.revalidate();
				}
			}
		}

		weaponCards.add(handLabel);
		weaponCards.add(inHand);
		weaponCards.add(seenLabel);
		weaponCards.add(seen);
		seen.revalidate();
		weaponCards.setLayout(new GridLayout(0, 1));
		inHand.setLayout(new GridLayout(0, 1));
		seen.setLayout(new GridLayout(0, 1));
		weaponCards.setBorder(new TitledBorder (new EtchedBorder()));
		weaponCards.setBackground(Color.GRAY);
		return weaponCards;
	}


	// update display function that can be called when needed
	// just removes everything and sets it equal to the new values
	public void updateDisplay() {
		mainPanel.removeAll();
		mainPanel = new GameCardPanel();
	}

}

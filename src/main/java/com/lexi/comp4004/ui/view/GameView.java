package com.lexi.comp4004.ui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

import com.lexi.comp4004.common.game.data.Card;
import com.lexi.comp4004.common.game.data.ClientPoker;
import com.lexi.comp4004.common.game.data.Opponent;
import com.lexi.comp4004.common.game.data.Result;
import com.lexi.comp4004.common.game.util.Config;
import com.lexi.comp4004.game.GameObservable;
import com.lexi.comp4004.game.GameObserver;
import com.lexi.comp4004.ui.component.CardNumberPanel;
import com.lexi.comp4004.ui.component.CardPanel;
import com.lexi.comp4004.ui.component.HeldCardsPanel;
import com.lexi.comp4004.ui.component.OpponentPanel;
import com.lexi.comp4004.ui.dialog.DialogClientInterface;
import com.lexi.comp4004.util.BroadcastKey;

public class GameView extends JFrame implements ActionListener, DialogClientInterface, GameObserver {

	private static final long serialVersionUID = -5324516193270733367L;

	private GameObservable observable;
	private JPanel contentPane;
	private JPanel opponentsPanel;

	private HeldCardsPanel heldCardsPanel;

	private CardNumberPanel deckPanel;

	private JButton btnKeepHand;
	private JButton btnSwapHand;
	private JButton btnLeave;

	private JDialog loadingDialog;

	private List<CardPanel> heldCards;

	private List<CardPanel> swapCards;

	private Map<String, HashMap<OpponentPanel, List<CardPanel>>> opponentsCards;

	Insets defaultPadding = new Insets(15, 15, 15, 15);

	private int numOpponents = 0;

	public GameView(GameObservable observable) {
		this.observable = observable;

		swapCards = new ArrayList<CardPanel>();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 100, 2000, Config.MIN_PLAYERS * Config.MIN_CARD_HEIGHT + 300);
		// setMinimumSize(new Dimension(1500, Config.MAX_PLAYERS *
		// Config.MIN_CARD_HEIGHT + 300));

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 2, 0, 0));

		opponentsPanel = new JPanel();
		opponentsPanel.setBackground(new Color(0, 128, 0));
		contentPane.add(opponentsPanel);
		opponentsPanel.setLayout(new BoxLayout(opponentsPanel, BoxLayout.Y_AXIS));

		JPanel playerViewPanel = new JPanel();
		playerViewPanel.setBackground(new Color(0, 128, 0));
		contentPane.add(playerViewPanel);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { Config.MIN_CARD_HEIGHT + 30, 50, Config.MIN_CARD_HEIGHT + 50,
				Config.MIN_CARD_HEIGHT + 50, 50, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, 1.0, 1.0, 0.0 };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		playerViewPanel.setLayout(gbl_panel_1);

		deckPanel = new CardNumberPanel();
		GridBagConstraints gbc_deckPanel = new GridBagConstraints();
		gbc_deckPanel.anchor = GridBagConstraints.NORTH;
		gbc_deckPanel.insets = defaultPadding;
		gbc_deckPanel.gridx = 0;
		gbc_deckPanel.gridy = 0;
		playerViewPanel.add(deckPanel, gbc_deckPanel);

		heldCardsPanel = new HeldCardsPanel();
		heldCardsPanel.setBackground(new Color(0, 128, 0));
		GridBagConstraints gbc_ha = new GridBagConstraints();
		gbc_ha.insets = defaultPadding;
		gbc_ha.gridwidth = 7;
		gbc_ha.fill = GridBagConstraints.BOTH;
		gbc_ha.gridx = 0;
		gbc_ha.gridy = 3;
		playerViewPanel.add(heldCardsPanel, gbc_ha);

		btnKeepHand = new JButton("Keep Hand");
		btnKeepHand.addActionListener(this);
		
		JButton btnSwapHand_1 = new JButton("Swap Hand");
		btnSwapHand_1.setEnabled(false);
		GridBagConstraints gbc_btnSwapHand_1 = new GridBagConstraints();
		gbc_btnSwapHand_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnSwapHand_1.gridx = 2;
		gbc_btnSwapHand_1.gridy = 5;
		playerViewPanel.add(btnSwapHand_1, gbc_btnSwapHand_1);
		btnKeepHand.setEnabled(false);
		btnKeepHand.setPreferredSize(new Dimension(100, 25));
		GridBagConstraints gbc_btnKeepHand = new GridBagConstraints();
		gbc_btnKeepHand.insets = new Insets(0, 0, 0, 5);
		gbc_btnKeepHand.gridx = 3;
		gbc_btnKeepHand.gridy = 5;
		playerViewPanel.add(btnKeepHand, gbc_btnKeepHand);

		btnLeave = new JButton("Leave");
		btnLeave.addActionListener(this);
		btnLeave.setPreferredSize(new Dimension(100, 25));
		GridBagConstraints gbc_btnLeave = new GridBagConstraints();
		gbc_btnLeave.insets = new Insets(0, 0, 0, 5);
		gbc_btnLeave.gridx = 5;
		gbc_btnLeave.gridy = 5;
		playerViewPanel.add(btnLeave, gbc_btnLeave);

		heldCards = new ArrayList<CardPanel>();

		opponentsCards = new HashMap<String, HashMap<OpponentPanel, List<CardPanel>>>();

		loadingDialog = new JDialog(this, "Please wait", ModalityType.DOCUMENT_MODAL);
		final JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		final JPanel contentPane = new JPanel();
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(new BorderLayout());
		contentPane.add(new JLabel("Waiting for other players..."), BorderLayout.NORTH);
		contentPane.add(progressBar, BorderLayout.CENTER);
		loadingDialog.setContentPane(contentPane);
		loadingDialog.pack();
		loadingDialog.setLocationRelativeTo(null);

		setMinimumSize(
				new Dimension(1500, playerViewPanel.getPreferredSize().height + deckPanel.getPreferredSize().height));
	}

	public void dialogFinished() {
		System.out.println("Finished");
	}

	public void dialogCancelled() {
		System.out.println("Cancelled");
	}

	public void actionPerformed(ActionEvent e) {
		if (btnKeepHand == e.getSource()) {
			observable.keepHand();
		} else if (btnLeave == e.getSource()) {
			int n = JOptionPane.showConfirmDialog(null, "Are you sure you want to leave?", "Exit Game",
					JOptionPane.YES_NO_OPTION);
			if (n == 0) {
				observable.disconnect();
			}
		} else if (btnSwapHand == e.getSource()) {
			if (swapCards.isEmpty()) {
				displayError("You must select cards to swap.");
			} else {
				List<Card> cards = new ArrayList<Card>();
				for (CardPanel cp : swapCards) {
					cards.add(cp.getCard());
				}
				observable.swapHand(cards);
				swapCards.clear();
			}
		}
	}

	public void setOpponents(List<Opponent> opponents) {
		opponentsPanel.removeAll();
		opponentsCards.clear();

		for (int i = 0; i < opponents.size(); i++) {
			OpponentPanel oPanel = new OpponentPanel();
			oPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

			oPanel.setHeldCards(Config.MAX_CARDS);
			oPanel.setOpponentName(opponents.get(i).getName());

			List<CardPanel> display = new ArrayList<CardPanel>();
			for (int j = 0; j < opponents.get(i).getVisibleCards().size(); j++) {
				CardPanel cPanel = new CardPanel(opponents.get(i).getVisibleCards().get(j));
				cPanel.addMouseListener(new MouseListener());
				display.add(cPanel);
			}
			for (int j = 0; j < Config.MAX_CARDS - opponents.get(i).getNumCards(); j++) {
				CardPanel cPanel = new CardPanel(new Card(null, null));
				cPanel.addMouseListener(new MouseListener());
				display.add(cPanel);
			}
			oPanel.setDisplayedCards(display);

			HashMap<OpponentPanel, List<CardPanel>> temp = new HashMap<OpponentPanel, List<CardPanel>>();
			temp.put(oPanel, display);
			opponentsCards.put(opponents.get(i).getName(), temp);

			oPanel.setMaximumSize(new Dimension(oPanel.getMaximumSize().width, oPanel.getPreferredSize().height));
			opponentsPanel.add(oPanel);
		}
		opponentsPanel.revalidate();
		opponentsPanel.repaint();
	}

	public void setNumCardsInDeck(int numCards) {
		deckPanel.changeCardNumber(numCards);
		deckPanel.validate();
	}

	public void displayWinner(List<Result> results) {
		// TODO

		// JOptionPane.showMessageDialog(null, name + " won the game!",
		// "Winner!", JOptionPane.INFORMATION_MESSAGE);
	}

	public void updateTurn() {
		if (observable.isTurn()) {
			JOptionPane.showMessageDialog(null, "It is your turn!", "Message", JOptionPane.INFORMATION_MESSAGE);

			btnKeepHand.setEnabled(true);
			btnSwapHand.setEnabled(true);

			for (String opponent : opponentsCards.keySet()) {
				HashMap<OpponentPanel, List<CardPanel>> temp = opponentsCards.get(opponent);
				for (OpponentPanel oPanel : temp.keySet()) {
					oPanel.setIsTurn(false);
				}
			}
		} else {
			btnKeepHand.setEnabled(false);
			btnSwapHand.setEnabled(false);

			for (String opponent : opponentsCards.keySet()) {
				HashMap<OpponentPanel, List<CardPanel>> temp = opponentsCards.get(opponent);
				for (OpponentPanel oPanel : temp.keySet()) {
					if (opponent.equals(observable.whoseTurn())) {
						oPanel.setIsTurn(true);
					} else {
						oPanel.setIsTurn(false);
					}
				}
			}
		}
	}

	private void displayMessage(String error) {
		JOptionPane.showMessageDialog(null, error, "Message", JOptionPane.INFORMATION_MESSAGE);
	}

	private void displayError(String error) {
		JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
	}

	@SuppressWarnings("unchecked")
	public void update(GameObservable o, BroadcastKey type, Object... arg) {
		switch (type) {
		case GAME_STARTED:
			setVisible(true);
			break;
		case GAME:
			updateView((ClientPoker) arg[0]);
			break;
		case WINNER:
			if (arg[0] instanceof List) {
				displayWinner((List<Result>) arg[0]);
			}
			break;
		case MESSAGE:
			if (!isVisible()) {
				break;
			} else if (arg != null && arg[0] != null) {
				displayMessage(arg[0].toString());
			}
		case ERROR:
			if (!isVisible()) {
				break;
			} else if (arg != null && arg[0] != null && arg[1] != null) {
				displayError(arg[1] + "(" + arg[0].toString() + ")");
			} else {
				displayError("Unknown error occurred!");
			}
			break;
		case FATAL_ERROR:
			if (!isVisible()) {
				break;
			} else if (arg != null && arg[0] != null) {
				displayError(arg[0].toString());
			} else {
				displayError("Unknown error occurred!");
			}
			break;
		default:
			break;
		}
	}

	public void updateView(ClientPoker cGame) {
		if (numOpponents == 0) {
			numOpponents = cGame.getOpponents().size();
			setBounds(0, 100, (int) (Toolkit.getDefaultToolkit().getScreenSize()).getWidth(),
					numOpponents * (new OpponentPanel()).getPreferredSize().height + 200);
		}

		setOpponents(cGame.getOpponents());
		setNumCardsInDeck(cGame.getNumCardInDeck());

		heldCardsPanel.clear();
		this.heldCards.clear();
		// TODO visible vs hidden
		List<Card> cards = cGame.getCards();

		for (int i = 0; i < cards.size(); i++) {
			CardPanel cPanel = new CardPanel(cards.get(i));
			cPanel.addMouseListener(new MouseListener());
			this.heldCards.add(cPanel);
			heldCardsPanel.addCard(cPanel);
		}

		updateTurn();

		validate();
	}

	private class MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() instanceof CardPanel) {
				for (CardPanel cPanel : heldCards) {
					if (cPanel.equals(e.getSource())) {
						System.out.println("Clicked! Card: " + cPanel.getCard());
						if (swapCards.contains(cPanel)) {
							heldCardsPanel.hoverOff(cPanel);
							swapCards.remove(cPanel);
						} else {
							heldCardsPanel.hoverOn(cPanel);
							swapCards.add(cPanel);
							return;
						}
						return;
					}
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if (e.getSource() instanceof CardPanel) {
				for (CardPanel cPanel : heldCards) {
					if (cPanel.equals(e.getSource())) {
						if (swapCards.contains(cPanel)) {
							return;
						}
						heldCardsPanel.hoverOn(cPanel);
						return;
					}
				}
				for (String name : opponentsCards.keySet()) {
					HashMap<OpponentPanel, List<CardPanel>> temp = opponentsCards.get(name);
					for (OpponentPanel oPanel : temp.keySet()) {
						for (CardPanel cPanel : temp.get(oPanel)) {
							if (cPanel.equals(e.getSource())) {
								oPanel.getDisplayedCards().hoverOn(cPanel);
								return;
							}
						}
					}
				}
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (e.getSource() instanceof CardPanel) {
				for (CardPanel cPanel : heldCards) {
					if (cPanel.equals(e.getSource())) {
						if (swapCards.contains(cPanel)) {
							return;
						}
						heldCardsPanel.hoverOff(cPanel);
						return;
					}
				}
				for (String name : opponentsCards.keySet()) {
					HashMap<OpponentPanel, List<CardPanel>> temp = opponentsCards.get(name);
					for (OpponentPanel oPanel : temp.keySet()) {
						for (CardPanel cPanel : temp.get(oPanel)) {
							if (cPanel.equals(e.getSource())) {
								oPanel.getDisplayedCards().hoverOff(cPanel);
								return;
							}
						}
					}
				}
			}
		}
	}

	public void update(Observable o, Object arg) {
	}
}

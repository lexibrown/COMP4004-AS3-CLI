package com.lexi.comp4004.ui.component;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.lexi.comp4004.common.game.util.Config;

public class OpponentPanel extends JPanel {

	private static final long serialVersionUID = -718661445289768305L;

	private CardNumberPanel cardNumberPanel;
	private CardDisplayPanel displayPanel;
	
	private String name;
	private JLabel lblName;

	public OpponentPanel() {
		name = Config.DEFAULT_NAME;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, Config.MIN_CARD_WIDTH, Config.MIN_CARD_WIDTH, 0};
		gridBagLayout.rowHeights = new int[] {Config.MIN_CARD_HEIGHT + 20, 50, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0};
		setLayout(gridBagLayout);
		
		lblName = new JLabel(name);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		add(lblName, gbc_lblNewLabel);
		
		JPanel pointsPanel = new JPanel();
		pointsPanel.setLayout(new GridLayout());
		pointsPanel.setBackground(Color.BLACK);
		GridBagConstraints gbc_lblPoints = new GridBagConstraints();
		gbc_lblPoints.insets = new Insets(0, 0, 5, 0);
		gbc_lblPoints.fill = GridBagConstraints.BOTH;
		gbc_lblPoints.gridx = 0;
		gbc_lblPoints.gridy = 1;
		add(pointsPanel, gbc_lblPoints);
		
		cardNumberPanel = new CardNumberPanel();
		GridBagConstraints gbc_cardNumberPanel = new GridBagConstraints();
		gbc_cardNumberPanel.insets = new Insets(0, 0, 5, 5);
		gbc_cardNumberPanel.gridx = 1;
		gbc_cardNumberPanel.gridy = 0;
		add(cardNumberPanel, gbc_cardNumberPanel);
		
		displayPanel = new CardDisplayPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 3;
		gbc_panel.gridy = 0;
		add(displayPanel, gbc_panel);
		
		
		setBackground(new Color(0, 128, 0));
		lblName.setBackground(new Color(0, 128, 0));		
		cardNumberPanel.setBackground(new Color(0, 128, 0));
		displayPanel.setBackground(new Color(0, 128, 0));
	}
	
	public void setHeldCards(int numCards) {
		cardNumberPanel.changeCardNumber(numCards);
	}

	public void setDisplayedCards(List<CardPanel> cards) {
		displayPanel.clear();
		
		for (int i = 0; i < cards.size(); i++) {
			displayPanel.addCard(cards.get(i));
		}
	}

	public CardDisplayPanel getDisplayedCards() {
		return displayPanel;
	}
	
	public void setOpponentName(String name) {
		this.name = name;
		lblName.setText(this.name);
	}
	
	public String getOpponentName() {
		return name;
	}
	
	public void setIsTurn(boolean turn) {
		if (turn) {
			setBackground(Color.YELLOW);
			lblName.setBackground(Color.YELLOW);		
			cardNumberPanel.setBackground(Color.YELLOW);
			displayPanel.setBackground(Color.YELLOW);
		} else {
			setBackground(new Color(0, 128, 0));
			lblName.setBackground(new Color(0, 128, 0));		
			cardNumberPanel.setBackground(new Color(0, 128, 0));
			displayPanel.setBackground(new Color(0, 128, 0));
		}		
	}
	
}

package com.lexi.comp4004.ui.component;

import java.awt.Dimension;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.lexi.comp4004.common.game.util.Config;

public class HeldCardsPanel extends JPanel {

	private static final long serialVersionUID = 5012553766234443628L;

	private static int OFFSET_X = (int) (Config.MIN_CARD_WIDTH / (3.0 / 2.0));
	private static int OFFSET_Y = 20;
	
	private List<CardPanel> cards;
	private int offset;
	
	public HeldCardsPanel() {
		setLayout(null);
		cards = new ArrayList<CardPanel>();
		offset = 0;		
	}

	public void addCard(CardPanel cPanel) {
		add(cPanel);
		cards.add(cPanel);
		Insets insets = getInsets();
		Dimension size = cPanel.getPreferredSize();
		cPanel.setBounds(insets.left + offset, insets.top + OFFSET_Y,
		             size.width, size.height);
		
		offset += OFFSET_X;
		validate();
	}
	
	public void removeCard(CardPanel cPanel) {
		if (!cards.contains(cPanel)) {
			return;
		}
		cards.remove(cPanel);
		removeAll();
		offset = 0;
		
		for (int i = 0; i < cards.size(); i++) {
			add(cards.get(i));
			Insets insets = getInsets();
			Dimension size = cards.get(i).getPreferredSize();
			cards.get(i).setBounds(insets.left + offset, insets.top + OFFSET_Y,
			             size.width, size.height);
			offset += OFFSET_X;
		}
		revalidate();
		repaint();
	}
	
	public void removeCard(int index) {
		if (cards.isEmpty()) {
			return;
		} else if (index < 0 || index >= cards.size()) {
			return;
		}
		removeCard(cards.get(index));
	}
	
	public void clear() {
		cards.clear();
		removeAll();
		offset = 0;
		revalidate();
		repaint();
	}
	
	public CardPanel getCardPanel(int index) {
		return cards.get(index);
	}
	
	public void hoverOn(CardPanel cPanel) {
		if (!cards.contains(cPanel)) {
			return;
		}
		
		Insets insets = getInsets();
		Dimension size = cPanel.getPreferredSize();

		if (cPanel.getY() == insets.top) {
			return;
		}
		
		cPanel.setBounds(cPanel.getX(), insets.top,
		             size.width, size.height);
	}
	
	public void hoverOff(CardPanel cPanel) {
		if (!cards.contains(cPanel)) {
			return;
		}
		
		Insets insets = getInsets();
		Dimension size = cPanel.getPreferredSize();
		cPanel.setBounds(cPanel.getX(), insets.top + OFFSET_Y,
		             size.width, size.height);
	}
	
}

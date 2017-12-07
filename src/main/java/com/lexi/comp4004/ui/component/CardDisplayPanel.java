package com.lexi.comp4004.ui.component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.lexi.comp4004.common.game.util.Config;

public class CardDisplayPanel extends JPanel {

	private static final long serialVersionUID = 199662406665026532L;
	private static int OFFSET_X = Config.MIN_CARD_WIDTH / 2;
	private static int OFFSET_Y = 20;

	private JLayeredPane layeredPane;

	private List<CardPanel> cards;
	private int offset;

	public CardDisplayPanel() {
		this(OFFSET_X, OFFSET_Y);
	}

	public CardDisplayPanel(int off_x) {
		this(off_x, OFFSET_Y);
	}

	public CardDisplayPanel(int off_x, int off_y) {
		layeredPane = new JLayeredPane();
		layeredPane.setLayout(null);
		layeredPane.setPreferredSize(new Dimension(getWidth(), getHeight()));

		setLayout(new BorderLayout());
		add(layeredPane, BorderLayout.CENTER);

		cards = new ArrayList<CardPanel>();
		offset = 0;
		OFFSET_X = off_x;
		OFFSET_Y = off_y;
	}

	public void addCard(CardPanel cPanel) {
		layeredPane.add(cPanel, cards.size(), 0);
		cards.add(cPanel);
		Insets insets = getInsets();
		Dimension size = cPanel.getPreferredSize();
		cPanel.setBounds(insets.left + offset, insets.top + OFFSET_Y, size.width, size.height);
		offset += OFFSET_X;
		validate();
	}

	public void removeCard(CardPanel cPanel) {
		if (!cards.contains(cPanel)) {
			return;
		}
		cards.remove(cPanel);
		layeredPane.removeAll();
		offset = 0;

		for (int i = 0; i < cards.size(); i++) {
			layeredPane.add(cards.get(i), i, 0);
			Insets insets = getInsets();
			Dimension size = cards.get(i).getPreferredSize();
			cards.get(i).setBounds(insets.left + offset, insets.top + OFFSET_Y, size.width, size.height);
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
		layeredPane.removeAll();
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

		cPanel.setBounds(cPanel.getX(), insets.top, size.width, size.height);
	}

	public void hoverOff(CardPanel cPanel) {
		if (!cards.contains(cPanel)) {
			return;
		}

		Insets insets = getInsets();
		Dimension size = cPanel.getPreferredSize();
		cPanel.setBounds(cPanel.getX(), insets.top + OFFSET_Y, size.width, size.height);
	}

}

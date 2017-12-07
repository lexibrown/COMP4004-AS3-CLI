package com.lexi.comp4004.ui.component;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.lexi.comp4004.common.game.data.Card;
import com.lexi.comp4004.common.game.util.Config;

public class CardPanel extends JPanel {

	private static final long serialVersionUID = -1806772390887103006L;

	private BufferedImage image;
	private Card card;

	public CardPanel() {
		try {
			Image img = new ImageIcon(this.getClass().getResource("/cardImages/cardBack.jpeg")).getImage();
			BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null),
					BufferedImage.TYPE_INT_ARGB);

			Graphics2D bGr = bimage.createGraphics();
			bGr.drawImage(img, 0, 0, null);
			bGr.dispose();

			scaleImage(bimage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		card = null;
	}

	public CardPanel(Card c) {
		try {
			Image img = null;
			if (c.getRank() == null || c.getSuit() == null) {
				img = new ImageIcon(this.getClass().getResource("/cardImages/cardBack.jpeg")).getImage();
			} else {
				img = new ImageIcon(this.getClass().getResource("/cardImages/" + c.getSuit() + c.getRank() + ".jpeg"))
						.getImage();
			}
			BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null),
					BufferedImage.TYPE_INT_ARGB);

			Graphics2D bGr = bimage.createGraphics();
			bGr.drawImage(img, 0, 0, null);
			bGr.dispose();

			scaleImage(bimage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.card = c;
	}

	private void scaleImage(BufferedImage current) {
		Image tmp = current.getScaledInstance(Config.MIN_CARD_WIDTH, Config.MIN_CARD_HEIGHT, Image.SCALE_SMOOTH);
		BufferedImage img = new BufferedImage(Config.MIN_CARD_WIDTH, Config.MIN_CARD_HEIGHT,
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = img.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		this.image = img;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(image.getWidth(), image.getHeight());
	}

	public Card getCard() {
		return card;
	}

}

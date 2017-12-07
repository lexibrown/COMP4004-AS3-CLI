package com.lexi.comp4004.ui.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.lexi.comp4004.common.game.util.Config;

public class CardNumberPanel extends JPanel {

	private static final long serialVersionUID = 563637846769307989L;

	private BufferedImage image;

	private JLabel lblCards;
	
	public CardNumberPanel() {
		setSize(Config.MIN_CARD_WIDTH, Config.MIN_CARD_HEIGHT);
		setLayout(new GridLayout(0, 1, 0, 0));
		
		try {
			Image img = new ImageIcon(
					this.getClass().getResource("/cardImages/cardBack.jpeg")).getImage();
			BufferedImage bimage = new BufferedImage(img.getWidth(null),
					img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		    Graphics2D bGr = bimage.createGraphics();
		    bGr.drawImage(img, 0, 0, null);
		    bGr.dispose();
			
		    image = scale(bimage);
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		lblCards = new JLabel("0");
		lblCards.setForeground(Color.WHITE);
		lblCards.setFont(new Font("Serif", Font.BOLD, 50));
		lblCards.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblCards);
	}

	private BufferedImage scale(BufferedImage old) {		
		Image tmp = old.getScaledInstance(
				Config.MIN_CARD_WIDTH, Config.MIN_CARD_HEIGHT, Image.SCALE_SMOOTH);
		BufferedImage img = new BufferedImage(
				Config.MIN_CARD_WIDTH, Config.MIN_CARD_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2d = img.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		
		return img;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}

	@Override
	public Dimension getPreferredSize() {
		if (image == null) {
			return new Dimension(Config.MIN_CARD_WIDTH, Config.MIN_CARD_HEIGHT);
		}
		return new Dimension(image.getWidth(), image.getHeight());
	}
	
	public void changeCardNumber(int numCards) {
		lblCards.setText(String.valueOf(numCards));
	}
	
}

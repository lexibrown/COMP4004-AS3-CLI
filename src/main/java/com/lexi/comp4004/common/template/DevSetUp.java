package com.lexi.comp4004.common.template;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.lexi.comp4004.common.game.data.Card;

public class DevSetUp extends SetUp implements Serializable {

	private static final long serialVersionUID = 965947400573115615L;

	private String playerName;
	private List<Card> playerCards = new ArrayList<Card>();
	private List<List<Card>> aiCards = new ArrayList<List<Card>>();
	private List<Card> deck;

	@Override
	public SetUpPoker setUpGame(SetUpPoker poker) {
		poker.setMaxNumPlayers(getNumPlayers());
		poker.setPlayerName(getPlayerName());
		poker.setPlayerCards(getPlayerCards());
		poker.setAiPlayers(getAiPlayers());
		poker.setAiCards(getAiCards());
		poker.setDeck(deck);
		return poker;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public List<Card> getPlayerCards() {
		return playerCards;
	}

	public void setPlayerCards(List<Card> playerCards) {
		this.playerCards = playerCards;
	}

	public List<Card> getDeck() {
		return deck;
	}

	public void setDeck(List<Card> deck) {
		this.deck = deck;
	}

	public List<List<Card>> getAiCards() {
		return aiCards;
	}

	public void setAiCards(List<List<Card>> aiCards) {
		this.aiCards = aiCards;
	}
	
	public void addAiCards(List<Card> aiCards) {
		this.aiCards.add(aiCards);
	}

}

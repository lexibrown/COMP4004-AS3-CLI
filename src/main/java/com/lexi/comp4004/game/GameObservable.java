package com.lexi.comp4004.game;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.lexi.comp4004.common.game.data.Card;
import com.lexi.comp4004.common.template.DevSetUp;
import com.lexi.comp4004.common.template.SetUp;
import com.lexi.comp4004.util.BroadcastKey;

public abstract class GameObservable extends Observable {

	private Queue<GameObserver> observers;

	public GameObservable() {
		observers = new ConcurrentLinkedQueue<GameObserver>();
	}

	public void addObserver(GameObserver o) {
		observers.add(o);
		super.addObserver((Observer) o);
	}

	public void deleteObserver(GameObserver o) {
		observers.remove(o);
		super.deleteObserver((Observer) o);
	}

	public void removeAllObservers() {
		observers.clear();
	}

	public void notifyObservers(BroadcastKey type, Object... arg) { // BroadcastKey
		for (GameObserver o : observers) {
			o.update(this, type, arg);
		}
	}

	public abstract void connect(String username);

	public abstract void disconnect();

	public abstract void setUpGame(SetUp setUp);

	public abstract void joinGame();

	public abstract void startGame();
	
	public abstract void lobbyStatus();
	
	public abstract void lobbyUsers();

	public abstract void keepHand();

	public abstract void swapHand(List<Card> cards);

	public abstract List<Card> getHiddenCards();

	public abstract List<Card> getVisibleCards();

	public abstract String getName();

	public abstract boolean isTurn();
	
	public abstract String whoseTurn();

	public abstract void devConnect(String admin, String password);

	public abstract void resetGame();

	public abstract void setUpGame(DevSetUp setUp);

	public abstract boolean hasDevToken();

}
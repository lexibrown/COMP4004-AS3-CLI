package com.lexi.comp4004.game;

import java.util.List;

import com.lexi.comp4004.ClientApplication;
import com.lexi.comp4004.ClientSocket;
import com.lexi.comp4004.common.game.data.Card;
import com.lexi.comp4004.common.game.data.ClientPoker;
import com.lexi.comp4004.common.game.data.Result;
import com.lexi.comp4004.common.template.DevSetUp;
import com.lexi.comp4004.common.template.SetUp;
import com.lexi.comp4004.util.BroadcastKey;

public class GameController extends GameObservable {

	private ClientApplication client;
	private ClientSocket socket;
	
	private String playerName;

	private ClientPoker game;

	public GameController() {
		this.game = null;
		this.playerName = null;
	}
	
	public void setClient(ClientApplication client) {
		this.client = client;
	}

	public void setSocket(ClientSocket socket) {
		this.socket = socket;
	}
	
	public void start() {
		notifyObservers(BroadcastKey.STARTED, "");
	}

	// client to ui
	public void errorOccured(String error, String message) {
		notifyObservers(BroadcastKey.ERROR, error, message);
	}

	public void errorOccured(String message) {
		notifyObservers(BroadcastKey.FATAL_ERROR, message);
	}

	public void connected() {
		// connect to websocket
		socket.connectToWebSocket();
		notifyObservers(BroadcastKey.CONNECTED, getName());
	}

	public void receivedMessage(String message) {
		notifyObservers(BroadcastKey.MESSAGE, message);
	}

	public void receivedLobbyStatus(boolean status) {
		notifyObservers(BroadcastKey.GAME_IN_PROGRESS, status);
	}

	public void receivedLobby(List<String> users) {
		notifyObservers(BroadcastKey.USERS_UPDATED, users);
	}
	
	public void receivedGameStart() {
		notifyObservers(BroadcastKey.GAME_STARTED, "");
	}

	public void receivedGame(ClientPoker poker) {
		this.game = poker;
		notifyObservers(BroadcastKey.GAME, poker);
	}

	public void receivedResults(List<Result> results) {
		notifyObservers(BroadcastKey.WINNER, results);
	}
	
	public void receivedDevToken() {
		notifyObservers(BroadcastKey.DEV_TOKEN, "Received dev token.");
	}

	// ui to client
	@Override
	public void connect(String username) {
		playerName = username;
		client.connectToServer(username);
	}

	@Override
	public void disconnect() {
		socket.disconnectFromWebSocket();
		client.disconnnectFromServer();
	}

	@Override
	public void setUpGame(SetUp setUp) {
		client.setUpGame(setUp);
	}

	@Override
	public void joinGame() {
		client.joinGame();
	}

	@Override
	public void startGame() {
		client.startGame();
	}
	
	@Override
	public void lobbyStatus() {
		client.getLobbyStatus();
	}
	
	@Override
	public void lobbyUsers() {
		client.getLobby();
	}

	@Override
	public void keepHand() {
		client.keepHand();
	}

	@Override
	public void swapHand(List<Card> cards) {
		client.swapHand(cards);
	}

	@Override
	public List<Card> getHiddenCards() {
		if (game != null) {
			return game.getHiddenCards();
		}
		return null;
	}

	@Override
	public List<Card> getVisibleCards() {
		if (game != null) {
			return game.getVisibleCards();
		}
		return null;
	}

	@Override
	public String getName() {
		return playerName;
	}

	@Override
	public boolean isTurn() {
		return game.isTurn();
	}
	
	@Override
	public String whoseTurn() {
		return game.getCurrentTurn();
	}
	
	@Override
	public void devConnect(String admin, String password) {
		client.getDevToken(admin, password);
	}

	@Override
	public void resetGame() {
		if (!hasDevToken()) {
			return;
		}
		client.reset();
	}

	@Override
	public void setUpGame(DevSetUp setUp) {
		if (!hasDevToken()) {
			return;
		}
		client.setUpGame(setUp);
	}

	@Override
	public boolean hasDevToken() {
		return client.hasDevToken();
	}

}

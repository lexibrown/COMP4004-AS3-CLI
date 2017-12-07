package com.lexi.comp4004.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Observable;

import com.lexi.comp4004.common.game.data.Card;
import com.lexi.comp4004.common.game.data.ClientPoker;
import com.lexi.comp4004.common.game.data.Opponent;
import com.lexi.comp4004.common.game.data.Result;
import com.lexi.comp4004.common.template.ClientSetUp;
import com.lexi.comp4004.common.template.DevSetUp;
import com.lexi.comp4004.common.template.SetUp;
import com.lexi.comp4004.game.GameObservable;
import com.lexi.comp4004.game.GameObserver;
import com.lexi.comp4004.util.BroadcastKey;

public class ConsoleInterface implements GameObserver {

	private BufferedReader c = new BufferedReader(new InputStreamReader(System.in));

	private boolean paused = false;

	private int state = 0;

	private GameObservable observable;

	public ConsoleInterface(GameObservable observable) {
		this.observable = observable;
		this.state = 0;
	}

	public void updateUserList(List<String> users) {
		System.out.println("\nLobby");
		System.out.println("-----------------------");
		for (String user : users) {
			System.out.println(user);
		}
		System.out.println();
	}
	
	private Card makeCard(String input) {
		try {
		String suit = input.substring(0, 0);
		String rank = input.substring(1);
		
		// TODO
		if ("H".equals(suit)) {
			
		} else if ("S".equals(suit)) {
			
		} else if ("C".equals(suit)) {
			
		} else if ("D".equals(suit)) {
			
		}
		
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	public DevSetUp setUpDevGame() {
		DevSetUp setUp = new DevSetUp();
		while (true) {
			try {
				System.out.println("Enter number of human players:");
				System.out.print("> ");
				int numHumans = Integer.valueOf(c.readLine());


				System.out.println("Enter number of ai players:");
				System.out.print("> ");
				int numAI = Integer.valueOf(c.readLine());

				List<Integer> aiPlayers = new ArrayList<Integer>();
				for (int i = 0; i < numAI; i++) {
					System.out.println("Enter strategy number for ai player " + (i + 1));
					System.out.print("> ");
					int strat = Integer.valueOf(c.readLine());
					aiPlayers.add(strat == 1 || strat == 2 ? strat : 1);
					List<Card> cards = new ArrayList<Card>();
					while (cards.size() < 5) {
						System.out.println("Enter cards for ai player " + (i + 1) + " (i.e., CA for ace of clubs)");
						System.out.print("> ");
						String input = c.readLine();
						Card c = makeCard(input);
						if (c == null) {
							System.out.println("Invalid card input");
						} else {
							cards.add(c);
						}
					}
					setUp.addAiCards(cards);
				}
				setUp.setAiPlayers(aiPlayers);
				setUp.setNumPlayers(numHumans + numAI);
				break;
			} catch (Exception e) {
				System.out.println("Invalid input");
			}
		}
		return setUp;
	}

	public SetUp setUpGame() {
		SetUp setUp = new ClientSetUp();
		while (true) {
			try {
				System.out.println("Enter maximum number of players:");
				System.out.print("> ");
				int numPlayers = Integer.valueOf(c.readLine());

				setUp.setNumPlayers(numPlayers);

				System.out.println("Enter number of ai players:");
				System.out.print("> ");
				int numAI = Integer.valueOf(c.readLine());

				List<Integer> aiPlayers = new ArrayList<Integer>();
				for (int i = 0; i < numAI; i++) {
					System.out.println("Enter strategy number for ai player " + (i + 1));
					System.out.print("> ");
					int strat = Integer.valueOf(c.readLine());
					aiPlayers.add(strat);
				}
				setUp.setAiPlayers(aiPlayers);
				break;
			} catch (Exception e) {
				System.out.println("Invalid input");
			}
		}
		return setUp;
	}

	public List<Card> chooseSwap() {
		List<Card> swapCards = new ArrayList<Card>();
		List<Card> cards = observable.getHiddenCards();

		while (true) {
			System.out.println("Enter the cards you'd like to swap. Enter nothing when done.");
			System.out.println(cards.size());
			for (int i = 0; i < cards.size(); i++) {
				System.out.println((i + 1) + ": " + cards.get(i).toString());
			}
			System.out.println();
			while (true) {
				try {
					System.out.print("> ");
					String input = c.readLine();
					if (input.isEmpty()) {
						break;
					}
					int choice = Integer.valueOf(input) - 1;
					if (choice < 0 || choice >= cards.size()) {
						System.out.println("Invalid input");
					} else if (swapCards.contains(cards.get(choice))) {
						System.out.println("Already being swapped");
					}

					swapCards.add(cards.get(choice));
				} catch (Exception e) {
					System.out.println("Invalid input");
				}
			}
			if (!swapCards.isEmpty()) {
				break;
			}
			System.out.println("You must select card(s) to swap.");
		}
		return swapCards;
	}

	public void updateView(ClientPoker game) {
		System.out.println("Number of Cards in Deck: " + game.getNumCardInDeck());
		System.out.println("Current Turn: " + game.getCurrentTurn());
		System.out.println();
		System.out.println("=====Opponents=====");
		for (Opponent o : game.getOpponents()) {
			System.out.println("Name: " + o.getName());
			System.out.println("Number of Cards: " + o.getNumCards());
			System.out.println("Visible Cards:");
			if (o.getVisibleCards().isEmpty()) {
				System.out.println("No visible cards");
			} else {
				for (Card c : o.getVisibleCards()) {
					System.out.println("\t" + c.toString());
				}
			}
			System.out.println();
		}

		System.out.println("=====Player=====");
		System.out.println("Name: " + game.getName());
		System.out.println("Number of Cards: " + game.getCards().size());
		System.out.println("Held Cards:");
		if (game.getHiddenCards().isEmpty()) {
			System.out.println("No held cards");
		} else {
			for (Card c : game.getHiddenCards()) {
				System.out.println("\t" + c.toString());
			}
		}
		System.out.println("Visible Cards:");
		if (game.getVisibleCards().isEmpty()) {
			System.out.println("No visible cards");
		} else {
			for (Card c : game.getVisibleCards()) {
				System.out.println("\t" + c.toString());
			}
		}
		System.out.println();

		if (observable.isTurn()) {
			//listen();
		}
	}

	public void startGame() {
		System.out.println("Game started.");
		state++;
	}

	public void displayWinner(List<Result> results) {
		System.out.println(results.get(0).getUser() + " is the winner with a " + results.get(0).getOutcome());
		for (int i = 0; i < results.size(); i++) {
			System.out.println(results.get(i).toString());
		}
		state++;
		paused = false;
		observable.disconnect();
	}

	public void displayMessage(String message) {
		System.out.println(message);
		if (message.equals("Successfully disconnected.")) {
			disconnect();
		}
		listen();
	}

	public void displayError(String message) {
		System.out.println(message);
		listen();
	}

	public void disconnect() {
		System.out.println("Disconnecting. Goodbye.");
		System.exit(0);
	}

	@SuppressWarnings("unchecked")
	public void update(GameObservable o, BroadcastKey type, Object... arg) {
		switch (type) {
		case STARTED:
			listen();
			break;
		case CONNECTED:
			state++;
			System.out.println("Username: " + arg[0].toString());
			observable.lobbyUsers();
			listen();
			break;
		case GAME_IN_PROGRESS:
			if ((Boolean) arg[0]) {
				System.out.println("Game currently in session");
			} else {
				System.out.println("No game currently in session");
			}
			listen();
			break;
		case USERS_UPDATED:
			if (arg[0] instanceof List) {
				updateUserList((List<String>) arg[0]);
			}
			break;
		case GAME_STARTED:
			startGame();
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
			displayMessage(arg[0].toString());
			break;
		case ERROR:
			if (arg != null) {
				displayError(arg[1] + "(" + arg[0].toString() + ")");
			} else {
				displayError("Unknown error occurred!");
			}
			break;
		case FATAL_ERROR:
			if (arg != null) {
				displayError(arg[0].toString());
			} else {
				displayError("Unknown error occurred!");
			}
			break;
		case DISCONNECT:
			disconnect();
			break;
		case DEV_TOKEN:
			displayMessage("Dev token received.");
			break;
		default:
			break;
		}
	}

	public void update(Observable o, Object arg) {
	}

	public void listen() {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						if (paused) {
							continue;
						}

						System.out.println();
						System.out.println();
						if (state == 0) { // login
							System.out.println("=====Login==== (Type in the command you'd like to perform)");
							System.out.println();

							System.out.println("Connect");
						} else if (state == 1) { // lobby
							System.out.println("=====Lobby==== (Type in the command you'd like to perform)");
							System.out.println();
							System.out.println("Game Status");
							System.out.println("Set Up Game");
							System.out.println("Join Game");
							System.out.println("Start Game");
							System.out.println("Dev Access");

							if (observable.hasDevToken()) {
								System.out.println();
								System.out.println("=====Dev Tools=====");
								System.out.println("Reset Game");
								System.out.println("Set Up Test Game");
							}

						} else if (state == 2) { // game
							System.out.println("=====Game==== (Type in the command you'd like to perform)");
							System.out.println();
							System.out.println("Keep Hand");
							System.out.println("Swap Hand");
						} else { // end game
							observable.disconnect();
							return;
						}
						System.out.println("Disconnect");
						System.out.println();

						System.out.print("> ");
						String answer = c.readLine();

						if (state == 0) { // login
							if ("CONNECT".equalsIgnoreCase(answer)) {
								System.out.print("Enter your username: ");
								System.out.print("> ");
								String username = c.readLine();
								observable.connect(username);
								return;
							}
						} else if (state == 1) { // lobby
							if ("GAME STATUS".equalsIgnoreCase(answer)) {
								observable.lobbyStatus();
								return;
							} else if ("SET UP GAME".equalsIgnoreCase(answer)) {
								observable.setUpGame(setUpGame());
								return;
							} else if ("JOIN GAME".equalsIgnoreCase(answer)) {
								observable.joinGame();
								return;
							} else if ("START GAME".equalsIgnoreCase(answer)) {
								observable.startGame();
								return;
							} else if ("DEV ACCESS".equalsIgnoreCase(answer)) {
								System.out.print("Enter username: ");
								System.out.print("> ");
								String username = c.readLine();
								System.out.print("Enter password: ");
								System.out.print("> ");
								String password = c.readLine();
								observable.devConnect(username, password);
								return;
							}

							if (observable.hasDevToken()) {
								if ("RESET GAME".equalsIgnoreCase(answer)) {
									observable.resetGame();
									return;
								} else if ("TEST GAME".equalsIgnoreCase(answer)) {
									observable.setUpGame(setUpDevGame());
									return;
								}
							}

						} else if (state == 2) { // game
							if ("KEEP HAND".equalsIgnoreCase(answer)) {
								observable.keepHand();
								paused = true;
								listen();
								return;
							} else if ("SWAP HAND".equalsIgnoreCase(answer)) {
								observable.swapHand(chooseSwap());
								paused = true;
								listen();
								return;
							}
						}
						if ("DISCONNECT".equalsIgnoreCase(answer)) {
							observable.disconnect();
							return;
						} else {
							System.out.println("Unknown command.");
						}
					} catch (NoSuchElementException e) {
						System.out.println("Connection ended unexpectedly.");
						break;
					} catch (IOException e) {
						System.out.println("Unable to read input.");
						e.printStackTrace();
					}
				}
				try {
					c.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}
}

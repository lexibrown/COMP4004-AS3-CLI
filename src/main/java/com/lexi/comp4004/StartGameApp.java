package com.lexi.comp4004;

import javax.swing.SwingUtilities;

import com.lexi.comp4004.common.game.util.Config;
import com.lexi.comp4004.game.GameController;
import com.lexi.comp4004.ui.ConsoleInterface;
import com.lexi.comp4004.ui.view.GameLobbyView;
import com.lexi.comp4004.ui.view.GameView;
import com.lexi.comp4004.ui.view.LoginView;


public class StartGameApp {

	public static void main(String[] args) {
		if (args.length > 0) {
			if ("dev".equalsIgnoreCase(args[0].toString())) {
				System.out.println("Dev flag raised.");
				Config.DEV = true;
			}
		}

		// Add views
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					GameController controller = new GameController();
					ClientSocket socket = new ClientSocket(controller);
					ClientApplication client = new ClientApplication(controller);
					controller.setClient(client);
					controller.setSocket(socket);
					
					if (Config.DEV) {
						controller.addObserver(new ConsoleInterface(controller));
					} else {
						controller.addObserver(new LoginView(controller));
						controller.addObserver(new GameLobbyView(controller));
						controller.addObserver(new GameView(controller));
					}
					controller.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

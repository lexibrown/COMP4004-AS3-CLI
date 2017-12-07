package com.lexi.comp4004;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import com.fasterxml.jackson.core.JsonParseException;
import com.lexi.comp4004.common.game.data.ClientPoker;
import com.lexi.comp4004.common.game.data.Result;
import com.lexi.comp4004.common.game.util.AuthorizationConfigurator;
import com.lexi.comp4004.common.game.util.Config;
import com.lexi.comp4004.common.game.util.Config.Key;
import com.lexi.comp4004.game.GameController;
import com.lexi.comp4004.util.JsonUtil;

@ClientEndpoint(configurator = AuthorizationConfigurator.class)
public class ClientSocket {

	private WebSocketContainer container = ContainerProvider.getWebSocketContainer();
	private Session session = null;

	private GameController controller;

	public ClientSocket(GameController controller) {
		this.controller = controller;
	}
	
    public Session getSession() {
        return session;
    }
	
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }
	
	@OnClose
	public void onClose(Session session) {
		//System.out.println("onClose");
	}

	@SuppressWarnings("unchecked")
	@OnMessage
	public void onMessage(String message) {
		//System.out.println("Received msg: " + message);

		try {
			Map<String, Object> msg = JsonUtil.parse(message, Map.class);

			if (msg.containsKey(Key.LOBBY)) {
				controller.receivedLobby(JsonUtil.parse(msg.get(Key.LOBBY).toString(), List.class));

				//controller.receivedLobby((List<String>) JsonUtil.parseList(msg.get(Key.LOBBY).toString(), String.class));
			} else if (msg.containsKey(Key.GAMESTARTED)) {
				controller.receivedGameStart();
			} else if (msg.containsKey(Key.GAME)) {
				controller.receivedGame(JsonUtil.parse(msg.get(Key.GAME).toString(), ClientPoker.class));
			} else if (msg.containsKey(Key.RESULTS)) {
				controller.receivedResults((List<Result>) JsonUtil.parseList(msg.get(Key.RESULTS).toString(), Result.class));
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void connectToWebSocket() {
		try {
			URI uri = URI.create("ws://" + Config.DEFAULT_HOST + Config.BASE_PATH + Config.WEB_SOCKET_PATH);
			session = container.connectToServer(this, uri);
		} catch (DeploymentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void disconnectFromWebSocket() {
		try {
			if (session != null) {
				session.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
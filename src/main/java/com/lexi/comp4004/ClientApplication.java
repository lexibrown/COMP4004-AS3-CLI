package com.lexi.comp4004;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.lexi.comp4004.common.game.data.Card;
import com.lexi.comp4004.common.game.data.ClientPoker;
import com.lexi.comp4004.common.game.util.Config;
import com.lexi.comp4004.common.game.util.Config.Endpoint;
import com.lexi.comp4004.common.game.util.Config.Key;
import com.lexi.comp4004.common.template.DevSetUp;
import com.lexi.comp4004.common.template.SetUp;
import com.lexi.comp4004.game.GameController;
import com.lexi.comp4004.exception.ErrorException;
import com.lexi.comp4004.exception.UnexpectedExecption;
import com.lexi.comp4004.util.JsonUtil;

public class ClientApplication {

	private String adminToken;

	private GameController controller;

	private static Client client = ClientBuilder.newClient();
	private static WebTarget target = client.target("http://" + Config.DEFAULT_HOST + Config.BASE_PATH);

	public ClientApplication(GameController controller) {
		this.controller = controller;
		this.adminToken = null;
	}

	private void reloadUri() {
		target = null;
		target = client.target("http://" + Config.DEFAULT_HOST + Config.BASE_PATH);
	}

	public boolean hasDevToken() {
		return adminToken != null && !adminToken.isEmpty();
	}

	// connection service
	public void connectToServer(String name) {
		try {
			reloadUri();
			target = target.path(Endpoint.Connect.CONNECT);

			Map<String, Object> response = connectRequest(name);
			if (response.containsKey(Key.ERROR)) {
				throw new ErrorException(response.get(Key.ERROR).toString(), response.get(Key.MESSAGE).toString());
			} else if (response.containsKey(Key.TOKEN)) {
				Config.token = response.get(Key.TOKEN).toString();
				controller.connected();
			} else {
				throw new UnexpectedExecption();
			}
		} catch (ErrorException ee) {
			controller.errorOccured(ee.getError(), ee.getMessage());
		} catch (UnexpectedExecption ue) {
			controller.errorOccured(ue.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// disconnection service
	public void disconnnectFromServer() {
		try {
			reloadUri();
			target = target.path(Endpoint.Disconnect.DISCONNECT);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put(Key.TOKEN, Config.token);

			Map<String, Object> response = postRequest(JsonUtil.stringify(params));

			if (response.containsKey(Key.ERROR)) {
				throw new ErrorException(response.get(Key.ERROR).toString(), response.get(Key.MESSAGE).toString());
			} else if (response.containsKey(Key.MESSAGE)) {
				controller.receivedMessage(response.get(Key.MESSAGE).toString());
			} else {
				throw new UnexpectedExecption();
			}
		} catch (ErrorException ee) {
			controller.errorOccured(ee.getError(), ee.getMessage());
		} catch (UnexpectedExecption ue) {
			controller.errorOccured(ue.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// lobby service
	public void getLobbyStatus() {
		try {
			reloadUri();
			target = target.path(Endpoint.Lobby.STATUS);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put(Key.TOKEN, Config.token);

			Map<String, Object> response = postRequest(JsonUtil.stringify(params));

			if (response.containsKey(Key.ERROR)) {
				throw new ErrorException(response.get(Key.ERROR).toString(), response.get(Key.MESSAGE).toString());
			} else if (response.containsKey(Key.STATUS)) {
				controller.receivedLobbyStatus((Boolean) response.get(Key.STATUS));
			} else {
				throw new UnexpectedExecption();
			}
		} catch (ErrorException ee) {
			controller.errorOccured(ee.getError(), ee.getMessage());
		} catch (UnexpectedExecption ue) {
			controller.errorOccured(ue.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void getLobby() {
		try {
			reloadUri();
			target = target.path(Endpoint.Lobby.USERS);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put(Key.TOKEN, Config.token);

			Map<String, Object> response = postRequest(JsonUtil.stringify(params));

			if (response.containsKey(Key.ERROR)) {
				throw new ErrorException(response.get(Key.ERROR).toString(), response.get(Key.MESSAGE).toString());
			} else if (response.containsKey(Key.LOBBY)) {
				controller.receivedLobby(JsonUtil.parse(response.get(Key.LOBBY).toString(), List.class));
			} else {
				throw new UnexpectedExecption();
			}
		} catch (ErrorException ee) {
			controller.errorOccured(ee.getError(), ee.getMessage());
		} catch (UnexpectedExecption ue) {
			controller.errorOccured(ue.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setUpGame(SetUp setup) {
		try {
			reloadUri();
			target = target.path(Endpoint.Lobby.SETUPGAME);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put(Key.TOKEN, Config.token);
			params.put(Key.SETUP, JsonUtil.stringify(setup));

			Map<String, Object> response = postRequest(JsonUtil.stringify(params));

			if (response.containsKey(Key.ERROR)) {
				throw new ErrorException(response.get(Key.ERROR).toString(), response.get(Key.MESSAGE).toString());
			} else if (response.containsKey(Key.MESSAGE)) {
				controller.receivedMessage(response.get(Key.MESSAGE).toString());
			} else {
				throw new UnexpectedExecption();
			}
		} catch (ErrorException ee) {
			controller.errorOccured(ee.getError(), ee.getMessage());
		} catch (UnexpectedExecption ue) {
			controller.errorOccured(ue.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startGame() {
		try {
			reloadUri();
			target = target.path(Endpoint.Lobby.STARTGAME);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put(Key.TOKEN, Config.token);

			Map<String, Object> response = postRequest(JsonUtil.stringify(params));

			if (response.containsKey(Key.ERROR)) {
				throw new ErrorException(response.get(Key.ERROR).toString(), response.get(Key.MESSAGE).toString());
			} else if (response.containsKey(Key.MESSAGE)) {
				controller.receivedMessage(response.get(Key.MESSAGE).toString());
			} else {
				throw new UnexpectedExecption();
			}
		} catch (ErrorException ee) {
			controller.errorOccured(ee.getError(), ee.getMessage());
		} catch (UnexpectedExecption ue) {
			controller.errorOccured(ue.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void joinGame() {
		try {
			reloadUri();
			target = target.path(Endpoint.Lobby.JOINGAME);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put(Key.TOKEN, Config.token);

			Map<String, Object> response = postRequest(JsonUtil.stringify(params));

			if (response.containsKey(Key.ERROR)) {
				throw new ErrorException(response.get(Key.ERROR).toString(), response.get(Key.MESSAGE).toString());
			} else if (response.containsKey(Key.MESSAGE)) {
				controller.receivedMessage(response.get(Key.MESSAGE).toString());
			} else {
				throw new UnexpectedExecption();
			}
		} catch (ErrorException ee) {
			controller.errorOccured(ee.getError(), ee.getMessage());
		} catch (UnexpectedExecption ue) {
			controller.errorOccured(ue.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// poker service
	public void getGameStatus() {
		try {
			reloadUri();
			target = target.path(Endpoint.Game.STATUS);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put(Key.TOKEN, Config.token);

			Map<String, Object> response = postRequest(JsonUtil.stringify(params));

			if (response.containsKey(Key.ERROR)) {
				throw new ErrorException(response.get(Key.ERROR).toString(), response.get(Key.MESSAGE).toString());
			} else if (response.containsKey(Key.GAME)) {
				controller.receivedGame((ClientPoker) response.get(Key.GAME));
			} else {
				throw new UnexpectedExecption();
			}
		} catch (ErrorException ee) {
			controller.errorOccured(ee.getError(), ee.getMessage());
		} catch (UnexpectedExecption ue) {
			controller.errorOccured(ue.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void keepHand() {
		try {
			reloadUri();
			target = target.path(Endpoint.Game.KEEPHAND);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put(Key.TOKEN, Config.token);

			Map<String, Object> response = postRequest(JsonUtil.stringify(params));

			if (response.containsKey(Key.ERROR)) {
				throw new ErrorException(response.get(Key.ERROR).toString(), response.get(Key.MESSAGE).toString());
			} else if (response.containsKey(Key.GAME)) {
				controller.receivedGame(JsonUtil.parse(response.get(Key.GAME).toString(), ClientPoker.class));
			} else {
				throw new UnexpectedExecption();
			}
		} catch (ErrorException ee) {
			controller.errorOccured(ee.getError(), ee.getMessage());
		} catch (UnexpectedExecption ue) {
			controller.errorOccured(ue.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void swapHand(List<Card> cards) {
		try {
			reloadUri();
			target = target.path(Endpoint.Game.SWAPHAND);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put(Key.TOKEN, Config.token);
			params.put(Key.CARDS, JsonUtil.stringify(cards));

			Map<String, Object> response = postRequest(JsonUtil.stringify(params));

			if (response.containsKey(Key.ERROR)) {
				throw new ErrorException(response.get(Key.ERROR).toString(), response.get(Key.MESSAGE).toString());
			} else if (response.containsKey(Key.GAME)) {
				controller.receivedGame(JsonUtil.parse(response.get(Key.GAME).toString(), ClientPoker.class));
			} else {
				throw new UnexpectedExecption();
			}
		} catch (ErrorException ee) {
			controller.errorOccured(ee.getError(), ee.getMessage());
		} catch (UnexpectedExecption ue) {
			controller.errorOccured(ue.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getDevToken(String admin, String password) {
		try {
			reloadUri();
			target = target.path(Endpoint.Dev.DEV);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put(Key.TOKEN, Config.token);
			params.put(Key.ADMIN, admin);
			params.put(Key.PASSWORD, password);

			Map<String, Object> response = postRequest(JsonUtil.stringify(params));

			if (response.containsKey(Key.ERROR)) {
				throw new ErrorException(response.get(Key.ERROR).toString(), response.get(Key.MESSAGE).toString());
			} else if (response.containsKey(Key.ADMIN_TOKEN)) {
				adminToken = response.get(Key.ADMIN_TOKEN).toString();
				controller.receivedDevToken();
			} else {
				throw new UnexpectedExecption();
			}
		} catch (ErrorException ee) {
			controller.errorOccured(ee.getError(), ee.getMessage());
		} catch (UnexpectedExecption ue) {
			controller.errorOccured(ue.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setUpGame(DevSetUp setup) {
		try {
			reloadUri();
			target = target.path(Endpoint.Dev.TEST);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put(Key.TOKEN, Config.token);
			params.put(Key.ADMIN_TOKEN, adminToken);
			params.put(Key.SETUP, JsonUtil.stringify(setup));

			Map<String, Object> response = postRequest(JsonUtil.stringify(params));

			if (response.containsKey(Key.ERROR)) {
				throw new ErrorException(response.get(Key.ERROR).toString(), response.get(Key.MESSAGE).toString());
			} else if (response.containsKey(Key.MESSAGE)) {
				controller.receivedMessage(response.get(Key.MESSAGE).toString());
			} else {
				throw new UnexpectedExecption();
			}
		} catch (ErrorException ee) {
			controller.errorOccured(ee.getError(), ee.getMessage());
		} catch (UnexpectedExecption ue) {
			controller.errorOccured(ue.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reset() {
		try {
			reloadUri();
			target = target.path(Endpoint.Dev.RESET);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put(Key.TOKEN, Config.token);
			params.put(Key.ADMIN_TOKEN, adminToken);

			Map<String, Object> response = postRequest(JsonUtil.stringify(params));

			if (response.containsKey(Key.ERROR)) {
				throw new ErrorException(response.get(Key.ERROR).toString(), response.get(Key.MESSAGE).toString());
			} else if (response.containsKey(Key.MESSAGE)) {
				controller.receivedMessage(response.get(Key.MESSAGE).toString());
			} else {
				throw new UnexpectedExecption();
			}
		} catch (ErrorException ee) {
			controller.errorOccured(ee.getError(), ee.getMessage());
		} catch (UnexpectedExecption ue) {
			controller.errorOccured(ue.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// request sending
	private Map<String, Object> connectRequest(String user) {
		Response response = target.queryParam(Key.USER, user).request(MediaType.APPLICATION_JSON).get();
		@SuppressWarnings("unchecked")
		Map<String, Object> payload = response.readEntity(Map.class);
		return payload;
	}

	private Map<String, Object> postRequest(String input) {
		Response response = target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(input, MediaType.APPLICATION_JSON), Response.class);
		@SuppressWarnings("unchecked")
		Map<String, Object> payload = response.readEntity(Map.class);
		return payload;
	}

}

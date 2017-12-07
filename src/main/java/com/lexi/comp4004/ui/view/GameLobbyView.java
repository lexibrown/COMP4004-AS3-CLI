package com.lexi.comp4004.ui.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.lexi.comp4004.common.template.SetUp;
import com.lexi.comp4004.game.GameObservable;
import com.lexi.comp4004.game.GameObserver;
import com.lexi.comp4004.ui.dialog.DialogClientInterface;
import com.lexi.comp4004.util.BroadcastKey;

public class GameLobbyView extends JFrame implements ActionListener, DialogClientInterface, GameObserver {

	private static final long serialVersionUID = 9060115529639774315L;

	private GameObservable observable;

	private JPanel contentPane;

	private DefaultListModel<String> listModel;
	private JList<String> userList;
	
	private JButton btnSetUp;
	private JButton btnStartGame;
	private JLabel lblUsername;
	private JButton btnExit;
	private JButton btnJoinGame;
	
	private JPanel panel;
	private JLabel lblGameStatus;

	public GameLobbyView(GameObservable observable) {
		this.observable = observable;

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] {287, 0};
		gbl_contentPane.rowHeights = new int[] {0, 0, 40, 0, 0};
		gbl_contentPane.columnWeights = new double[] { 1.0, 1.0, 0.0 };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0 };
		contentPane.setLayout(gbl_contentPane);

		lblUsername = new JLabel("Username:");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.gridwidth = 3;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 0);
		gbc_lblUsername.gridx = 0;
		gbc_lblUsername.gridy = 0;
		contentPane.add(lblUsername, gbc_lblUsername);

		JLabel lblPlayersInLobby = new JLabel("Players in Lobby");
		GridBagConstraints gbc_lblPlayersInLobby = new GridBagConstraints();
		gbc_lblPlayersInLobby.gridwidth = 3;
		gbc_lblPlayersInLobby.insets = new Insets(0, 0, 5, 0);
		gbc_lblPlayersInLobby.gridx = 0;
		gbc_lblPlayersInLobby.gridy = 1;
		contentPane.add(lblPlayersInLobby, gbc_lblPlayersInLobby);

		listModel = new DefaultListModel<String>();

		userList = new JList<String>(listModel);
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridwidth = 3;
		gbc_list.insets = new Insets(0, 0, 5, 0);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 2;
		contentPane.add(userList, gbc_list);
		
		lblGameStatus = new JLabel("Game Status:");
		GridBagConstraints gbc_lblGameStatus = new GridBagConstraints();
		gbc_lblGameStatus.gridwidth = 3;
		gbc_lblGameStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblGameStatus.gridx = 0;
		gbc_lblGameStatus.gridy = 3;
		contentPane.add(lblGameStatus, gbc_lblGameStatus);

		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 3;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 4;
		contentPane.add(panel, gbc_panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		btnSetUp = new JButton("Set Up Game");
		panel.add(btnSetUp);

		btnStartGame = new JButton("Start Game");
		panel.add(btnStartGame);
		btnStartGame.addActionListener(this);
		btnStartGame.setPreferredSize(new Dimension(100, 25));
		
		btnJoinGame = new JButton("Join Game");
		panel.add(btnJoinGame);

		btnExit = new JButton("Exit");
		panel.add(btnExit);
		btnExit.addActionListener(this);
		btnExit.setPreferredSize(new Dimension(75, 25));
		btnSetUp.addActionListener(this);
	}

	public void dialogFinished() {
		System.out.println("Finished");
	}

	public void dialogCancelled() {
		System.out.println("Cancelled");
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnStartGame) {
			observable.startGame();
		} else if (e.getSource() == btnExit) {
			int n = JOptionPane.showConfirmDialog(null, "Are you sure you want to leave?", "Exit Game",
					JOptionPane.YES_NO_OPTION);
			if (n == 0) {
				observable.disconnect();
			}
		} else if (e.getSource() == btnJoinGame) {
			observable.joinGame();
		} else if (e.getSource() == btnSetUp) {
			// TODO
			SetUp setUp = null;
			observable.setUpGame(setUp);
		}
	}

	private void updateUserList(List<String> users) {
		listModel.clear();
		for (int i = 0; i < users.size(); i++) {
			listModel.addElement(users.get(i));
		}
	}
	
	private void updateGameStatus(boolean started) {
		if (started) {
			lblGameStatus.setText("Game Status: Game In Session");
		} else {
			lblGameStatus.setText("Game Status: Game Not In Session");
		}
	}

	private void startGame() {
		observable.deleteObserver(this);
		dispose();
	}

	private void displayMessage(String error) {
		JOptionPane.showMessageDialog(null, error, "Message", JOptionPane.INFORMATION_MESSAGE);
	}

	private void displayError(String error) {
		JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public void update(Observable o, Object arg) {
	}

	@SuppressWarnings("unchecked")
	public void update(GameObservable o, BroadcastKey type, Object... arg) {
		switch (type) {
		case CONNECTED:
			lblUsername.setText("Username: " + arg[0].toString());
			setVisible(true);
			break;
		case USERS_UPDATED:
			if (arg[0] instanceof List) {
				updateUserList((List<String>) arg[0]);
			}
			break;
		case GAME_IN_PROGRESS:
			updateGameStatus((Boolean) arg[0]);
		case GAME_STARTED:
			startGame();
			break;
		case MESSAGE:
			if (!isVisible()) {
				break;
			} else if (arg != null && arg[0] != null) {
				displayMessage(arg[0].toString());
			}
		case ERROR:
			if (!isVisible()) {
				break;
			} else if (arg != null && arg[0] != null && arg[1] != null) {
				displayError(arg[1] + "(" + arg[0].toString() + ")");
			} else {
				displayError("Unknown error occurred!");
			}
			break;
		case FATAL_ERROR:
			if (!isVisible()) {
				break;
			} else if (arg != null && arg[0] != null) {
				displayError(arg[0].toString());
			} else {
				displayError("Unknown error occurred!");
			}
			break;
		default:
			break;
		}
	}

}

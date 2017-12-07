package com.lexi.comp4004.ui.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.lexi.comp4004.game.GameObservable;
import com.lexi.comp4004.game.GameObserver;
import com.lexi.comp4004.ui.dialog.DialogClientInterface;
import com.lexi.comp4004.util.BroadcastKey;

public class LoginView extends JFrame implements ActionListener, DialogClientInterface, GameObserver {

	private static final long serialVersionUID = 8864584544580377716L;

	private GameObservable observable;

	private JButton btnEnter;
	private JTextField userTextField;

	public LoginView(GameObservable observable) {
		this.observable = observable;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 169);
		setResizable(false);
		getContentPane().setLayout(new GridLayout(2, 1, 10, 0));

		JLabel lblEnterYourUsername = new JLabel("Enter your username to sign in:");
		lblEnterYourUsername.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblEnterYourUsername);

		JPanel panel = new JPanel();
		getContentPane().add(panel);

		JLabel lblUsername = new JLabel("Username:  ");
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblUsername);

		userTextField = new JTextField();
		panel.add(userTextField);
		userTextField.setColumns(10);

		btnEnter = new JButton("Enter");
		btnEnter.addActionListener(this);
		panel.add(btnEnter);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnEnter) {
			String username = new String(userTextField.getText());
			if (username.length() <= 0 || username.length() > 9) {
				JOptionPane.showMessageDialog(null, "Username must be between 1-9 characters!", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				observable.connect(username);
			}
		}
	}

	private void connectError(String error) {
		JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
	}

	private void loginPass() {
		observable.deleteObserver(this);
		dispose();
	}

	public void dialogFinished() {
		System.out.println("Finished");
	}

	public void dialogCancelled() {
		System.out.println("Cancelled");
	}

	public void update(Observable o, Object arg) {
	}

	public void update(GameObservable o, BroadcastKey type, Object... arg) {
		switch (type) {
		case STARTED:
			setVisible(true);
			break;
		case CONNECTED:
			loginPass();
			break;
		case ERROR:
			if (arg != null && arg[0] != null && arg[1] != null) {
				connectError(arg[1] + "(" + arg[0].toString() + ")");
			} else {
				connectError("Unknown error occurred!");
			}
			break;
		case FATAL_ERROR:
			if (arg != null && arg[0] != null) {
				connectError(arg[0].toString());
			} else {
				connectError("Unknown error occurred!");
			}
			break;
		default:
			break;
		}
	}

}

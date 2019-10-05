package ca.mcgill.ecse223.block.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;

public class SignupPanel extends JPanel {

	private static final long serialVersionUID = 1123412365166L;

	private Block223Page block223Page;

	private JLabel titleLabel;
	private JLabel subtitleLabel;

	private JLabel usernameLabel;
	private JTextField usernameField;

	private JLabel passwordLabel;
	private JPasswordField passwordField;

	private JLabel adminPasswordLabel;
	private JPasswordField adminPasswordField;

	private JButton loginButton;
	private JButton signupButton;

	private JLabel error;

	public SignupPanel() {
		super();
		this.block223Page = Block223Page.getPage();
		init();
	}

	private void init() {
		// Initialize components
		titleLabel = new JLabel("Block223", SwingConstants.CENTER);
		titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 36));
		subtitleLabel = new JLabel("Sign Up", SwingConstants.CENTER);
		subtitleLabel.setFont(new Font(subtitleLabel.getFont().getName(), Font.PLAIN, 24));

		usernameLabel = new JLabel("Username");
		usernameField = new JTextField();

		passwordLabel = new JLabel("Player Password");
		passwordField = new JPasswordField();
		adminPasswordLabel = new JLabel("Admin Password");
		adminPasswordField = new JPasswordField();

		signupButton = new JButton("Sign Up");
		loginButton = new JButton("Login Here!");

		error = new JLabel();
		error.setForeground(new Color(0xFF0000));
		error.setVisible(false);

		// Action listener
		signupButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String password = new String(passwordField.getPassword());
				String adminPassword = new String(adminPasswordField.getPassword());

				try {
					Block223Controller.register(username, password, adminPassword);
					block223Page.showLogin();
				} catch (InvalidInputException ex) {
					error.setText(ex.getMessage());
					error.setVisible(true);
				}

			}
		});

		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				block223Page.showLogin();
			}
		});

		// Layout
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(error, Alignment.CENTER)
						.addComponent(titleLabel, Alignment.CENTER)
						.addComponent(subtitleLabel, Alignment.CENTER)
						.addComponent(loginButton, Alignment.CENTER)
						.addComponent(signupButton, Alignment.CENTER)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup()
										.addComponent(usernameLabel, 125, 125, 125)
										.addComponent(passwordLabel, 125, 125, 125)
										.addComponent(adminPasswordLabel, 125, 125, 125))
								.addGroup(layout.createParallelGroup()
										.addComponent(usernameField, 200, 200, 200)
										.addComponent(passwordField, 200, 200, 200)
										.addComponent(adminPasswordField, 200, 200, 200)))));

		layout.linkSize(SwingConstants.VERTICAL, new Component[] { usernameField, passwordField, adminPasswordField });
		layout.linkSize(SwingConstants.HORIZONTAL,
				new Component[] { usernameField, passwordField, adminPasswordField });

		layout.setVerticalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addComponent(titleLabel)
						.addComponent(subtitleLabel)
						.addGroup(layout.createParallelGroup()
								.addComponent(usernameLabel)
								.addComponent(usernameField))
				.addGroup(layout.createParallelGroup()
						.addComponent(passwordLabel)
						.addComponent(passwordField))
				.addGroup(layout.createParallelGroup()
						.addComponent(adminPasswordLabel)
						.addComponent(adminPasswordField))
				.addComponent(signupButton)
				.addComponent(loginButton)
				.addComponent(error)));
	}
}

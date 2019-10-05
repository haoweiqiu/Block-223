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
import ca.mcgill.ecse223.block.controller.TOUserMode;
import ca.mcgill.ecse223.block.controller.TOUserMode.Mode;

public class LoginPanel extends JPanel {

    private static final long serialVersionUID = 1123412355115L;

    private Block223Page block223Page;

    private JLabel titleLabel;
    private JLabel subtitleLabel;

    private JLabel usernameLabel;
    private JTextField usernameField;

    private JLabel passwordLabel;
    private JPasswordField passwordField;

    private JButton loginButton;
    private JButton signupButton;

    private JLabel error;

    public LoginPanel() {
        super();
        this.block223Page = Block223Page.getPage();
        init();
    }

    private void init() {
        // Set text
        titleLabel = new JLabel("Block223", SwingConstants.CENTER);
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 36));
        subtitleLabel = new JLabel("Login", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font(subtitleLabel.getFont().getName(), Font.PLAIN, 24));

        usernameLabel = new JLabel("Username");
        usernameField = new JTextField();

        passwordLabel = new JLabel("Password");
        passwordField = new JPasswordField();

        loginButton = new JButton("Login");
        signupButton = new JButton("First time playing? Sign up!");

        error = new JLabel();
        error.setForeground(new Color(0xFF0000));
        error.setVisible(false);

        // Action listeners
        loginButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                try {
                    Block223Controller.login(username, password);
                    block223Page.clearBackStack();
                    TOUserMode mode = Block223Controller.getUserMode();
                    System.out.println(mode.getMode().toString());
                    if (mode.getMode().equals(Mode.Design)) {
                        block223Page.showAdminGames();
                    } else if (mode.getMode().equals(Mode.Play)) {
                        block223Page.showPlayableGamePicker();
                    }
                } catch (InvalidInputException ex) {
                    error.setText(ex.getMessage());
                    error.setVisible(true);
                }
            }
        });

        signupButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                block223Page.showSignUp();
            }
        });

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup().addComponent(error, Alignment.CENTER)
                        .addComponent(titleLabel, Alignment.CENTER).addComponent(subtitleLabel, Alignment.CENTER)
                        .addComponent(loginButton, Alignment.CENTER).addComponent(signupButton, Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup().addComponent(usernameLabel, 125, 125, 125)
                                        .addComponent(passwordLabel, 125, 125, 125))
                                .addGroup(layout.createParallelGroup().addComponent(usernameField, 200, 200, 400)
                                        .addComponent(passwordField, 200, 200, 400)))));

        layout.linkSize(SwingConstants.VERTICAL, new Component[] { usernameField, passwordField });
        layout.linkSize(SwingConstants.HORIZONTAL, new Component[] { usernameField, passwordField });

        layout.setVerticalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup().addComponent(titleLabel).addComponent(subtitleLabel)
                        .addGroup(layout.createParallelGroup().addComponent(usernameLabel).addComponent(usernameField))
                        .addGroup(layout.createParallelGroup().addComponent(passwordLabel).addComponent(passwordField))
                        .addComponent(loginButton).addComponent(signupButton).addComponent(error)));
    }
}
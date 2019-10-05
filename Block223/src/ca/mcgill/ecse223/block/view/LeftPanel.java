package ca.mcgill.ecse223.block.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;

public class LeftPanel extends JPanel {

    private static final long serialVersionUID = 166626223465114L;

    private Block223Page block223Page;
    
    private JLabel title;
    
    private JLabel user;
    
    private JButton save;
    private JButton logout;
    private JButton back;
    private JLabel message;
    
    private JLabel introText;
    private JLabel signUpText;

    public LeftPanel() {
        super();
        this.block223Page = Block223Page.getPage();
        init();
    }
    
    private void init() {
        // Set text
        title = new JLabel("<html><body><center>Welcome to Block223!</center></body><html>");
        title.setFont(new Font(title.getFont().getName(), Font.PLAIN, 24));
        
        String username = "temp";
        user = new JLabel("Signed in as: " + username);
        user.setForeground(Color.GRAY);
        
        save = new JButton("Save");
        logout = new JButton("Logout");
        back = new JButton("Back");
        message = new JLabel();
        message.setVisible(false);
        
        save.setVisible(false);
        logout.setVisible(false);
        back.setVisible(false);
        user.setVisible(false);
        
        // Login and Signup pages text
        introText = new JLabel("<html><body><center>The creators of this game do not know why you even come back to this basic game devoid of common sense, but we are glad you like it and thank you for your support &lt 3 </center></body></html>");
        signUpText = new JLabel("<html><body><center>where you will be hooked by the unending block ad levels and eventually reach desperation like the people behind this (not so) sophisticated game.</center></body></html>");
        
        signUpText.setVisible(false);
        
        save.addActionListener(new ActionListener(){
               	
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		try {
        			Block223Controller.saveGame();
        			message.setText("Saved!");
        			message.setForeground(Color.green);
        			message.setVisible(true);
        		} catch (InvalidInputException ex) {
        			message.setText("Failed to save");
        			message.setForeground(new Color(0xFF0000));
        			message.setVisible(true);
        		}
        	}
        });
        
        back.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                block223Page.goBack();
            }
        });

        logout.addActionListener(new ActionListener() {
        	
        	@Override
        	public void actionPerformed(ActionEvent e) {
                Block223Controller.logout();
                block223Page.clearBackStack();
                block223Page.showLogin();
        	}
        });
        
        // Define layout
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addComponent(title, Alignment.CENTER)
                .addComponent(save, Alignment.CENTER)
                .addComponent(logout, Alignment.CENTER)
                .addComponent(message, Alignment.CENTER)
                .addComponent(user, Alignment.CENTER)
                .addComponent(back, Alignment.CENTER)
                .addComponent(introText, Alignment.CENTER)
                .addComponent(signUpText, Alignment.CENTER)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
            	.addComponent(title)
                .addComponent(user)
                .addComponent(save)
                .addComponent(logout)
                .addComponent(back)
                .addComponent(introText)
                .addComponent(signUpText)
                .addComponent(message)
        );
    }
    
    public void initComponents() {
    	save.setVisible(true);
        logout.setVisible(true);
        back.setVisible(true);
        title.setText("Block223");
        String username = Block223Application.getCurrentUserName();
        user.setText("Signed in as: " + username);
        user.setVisible(true);
    }
    
    public void setSignUpTextVisible() {
    	signUpText.setVisible(true);
    	introText.setVisible(false);
    }

    public void setIntroTextVisible() {
    	introText.setVisible(true);
    	signUpText.setVisible(false);
    	save.setVisible(false);
        logout.setVisible(false);
        back.setVisible(false);
        user.setVisible(false);
        title.setText("<html><body><center>Welcome to Block223!</center></body><html>");
    }
    
    public void setTextsInvisible() {
    	introText.setVisible(false);
    	signUpText.setVisible(false);
    }
    
    public void updateUsername() {
        user.setText(Block223Application.getCurrentUserName());
    }
}
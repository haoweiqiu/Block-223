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
import javax.swing.SwingConstants;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOBlock;
import ca.mcgill.ecse223.block.controller.TOGame;

public class BlockDeletePanel extends JPanel {

	private static final long serialVersionUID = 3543340297409455509L;
	
	private JLabel titleLabel;
	private JLabel idLabel;
	private JLabel redLabel;
	private JLabel greenLabel;
	private JLabel blueLabel;
    private JLabel pointLabel;
    private JLabel blastLabel;

	private JButton deleteButton;
    private TOBlock block;
	private JLabel error;
	
	private BlockDrawPanel bdPanel;

	public BlockDeletePanel(TOBlock block) {
        super();
        this.block = block;
		init();
	}
	
	public void init() {
		TOGame game = null;
		try {
            game = Block223Controller.getCurrentDesignableGame();
        } catch (InvalidInputException ex) {
        	error.setText(ex.getMessage());
            error.setVisible(true);
        }
        if (game == null) {
            System.out.println("Fail to get the current game");
            return;
        }
        
        // Initialize the components        
        bdPanel = new BlockDrawPanel(block);
        
		titleLabel = new JLabel("Block Information", SwingConstants.CENTER);
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 24));
        
        idLabel = new JLabel("ID: " + block.getId());
        idLabel.setFont(new Font(idLabel.getFont().getName(), Font.BOLD, 18));
        
        redLabel = new JLabel("Red: " + block.getRed());
        redLabel.setFont(new Font(redLabel.getFont().getName(), Font.BOLD, 18));
        
        greenLabel = new JLabel("Green:" + block.getGreen());
        greenLabel.setFont(new Font(greenLabel.getFont().getName(), Font.BOLD, 18));
        
        blueLabel = new JLabel("Blue:" + block.getBlue());
        blueLabel.setFont(new Font(blueLabel.getFont().getName(), Font.BOLD, 18));

        pointLabel = new JLabel("Points: " + block.getPoints());
        pointLabel.setFont(new Font(pointLabel.getFont().getName(), Font.BOLD, 18));
        
        blastLabel = new JLabel("Blast Size: " + block.getBlastRadius());
        blastLabel.setFont(new Font(blastLabel.getFont().getName(), Font.BOLD, 18));

        deleteButton = new JButton("Delete");
        
        error = new JLabel();
        error.setForeground(new Color(0xFF0000));
        error.setVisible(false);
        
        deleteButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	TOGame game = null;
            	try {
                    game = Block223Controller.getCurrentDesignableGame();
                } catch (InvalidInputException ex) {
                	error.setText(ex.getMessage());
                    error.setVisible(true);
                }
                if (game == null) {
                    System.out.println("Fail to get the current game");
                    return;
                }
                
                try {
                	// Show that the block has successfully been deleted
                    Block223Controller.deleteBlock(block.getId());
					Block223Page.getPage().goBack();
                }  catch (InvalidInputException ex) {
                	error.setText(ex.getMessage());
                    error.setVisible(true);
                }
            }
        }); 
        
        // Setup the layout
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addComponent(titleLabel, Alignment.CENTER)
                .addComponent(bdPanel, 200, 200, 200)
                .addComponent(idLabel, Alignment.CENTER)                
                .addComponent(redLabel, Alignment.CENTER)
                .addComponent(greenLabel, Alignment.CENTER)
                .addComponent(blueLabel, Alignment.CENTER)
                .addComponent(pointLabel, Alignment.CENTER)
                .addComponent(blastLabel, Alignment.CENTER)
                .addComponent(deleteButton, Alignment.CENTER)
                .addComponent(error, Alignment.CENTER)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
            .addComponent(titleLabel)
            .addComponent(bdPanel, 100, 100, 100)
            .addComponent(idLabel)
            .addComponent(redLabel)  
            .addComponent(greenLabel)
            .addComponent(blueLabel)
            .addComponent(pointLabel)    
            .addComponent(blastLabel)
            .addComponent(deleteButton)
            .addComponent(error)
        );
	}

}

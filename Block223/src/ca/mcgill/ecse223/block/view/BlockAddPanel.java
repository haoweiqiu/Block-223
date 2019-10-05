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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOGame;

public class BlockAddPanel extends JPanel {

    private static final long serialVersionUID = -8361280539958837781L;

    private JLabel titleLabel;
    private JLabel redLabel;
    private JLabel greenLabel;
    private JLabel blueLabel;
    private JLabel pointLabel;

    private JTextField redField;
    private JTextField greenField;
    private JTextField blueField;
    private JTextField pointField;

    private JButton cancelButton;
    private JButton addButton;

    private JLabel error;

    public BlockAddPanel() {
        super();
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
            System.out.println("Fail to get current game");
            return;
        }

        // Initialize components
        titleLabel = new JLabel("Add Blocks");
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 24));

        redLabel = new JLabel("Red:");
        redLabel.setFont(new Font(redLabel.getFont().getName(), Font.BOLD, 18));
        redField = new JTextField();

        greenLabel = new JLabel("Green:");
        greenLabel.setFont(new Font(greenLabel.getFont().getName(), Font.BOLD, 18));
        greenField = new JTextField();

        blueLabel = new JLabel("Blue:");
        blueLabel.setFont(new Font(blueLabel.getFont().getName(), Font.BOLD, 18));
        blueField = new JTextField();

        pointLabel = new JLabel("Point:");
        pointLabel.setFont(new Font(pointLabel.getFont().getName(), Font.BOLD, 18));
        pointField = new JTextField();

        addButton = new JButton("Add");
        cancelButton = new JButton("Done");

        error = new JLabel();
        error.setForeground(new Color(0xFF0000));
        error.setVisible(false);

        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int red = Integer.parseInt(redField.getText());
                    int green = Integer.parseInt(greenField.getText());
                    int blue = Integer.parseInt(blueField.getText());
                    int points = Integer.parseInt(pointField.getText());
                    Block223Controller.addBlock(red, green, blue, points);
                    
                    // Show that the block has successfully been added
                    redField.setText("");
                    greenField.setText("");
                    blueField.setText("");
                    pointField.setText("");
                    error.setVisible(false);
                } catch (NumberFormatException ex) {
                    error.setText("Missing one or more fields");
                    error.setVisible(true);
                } catch (InvalidInputException ex) {
                    error.setText(ex.getMessage());
                    error.setVisible(true);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Block223Page.getPage().goBack();
            }
        });
        
        // Setup the layout
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup().addComponent(error, Alignment.CENTER)
                        .addComponent(titleLabel)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup().addComponent(redLabel, 100, 100, 100)
                                        .addComponent(greenLabel, 100, 100, 100).addComponent(blueLabel, 100, 100, 100)
                                        .addComponent(pointLabel, 100, 100, 100).addComponent(addButton))
                                .addGroup(layout.createParallelGroup().addComponent(redField, 50, 50, 50)
                                        .addComponent(greenField, 50, 50, 50).addComponent(blueField, 50, 50, 50)
                                        .addComponent(pointField, 50, 50, 50).addComponent(cancelButton)))));
        
        // Make the fields the same size
        layout.linkSize(SwingConstants.VERTICAL, new Component[] { redField, greenField, blueField, pointField });
        layout.linkSize(SwingConstants.HORIZONTAL, new Component[] { redField, greenField, blueField, pointField });
        
        // Make the buttons the same size
        layout.linkSize(SwingConstants.VERTICAL, new Component[] { addButton, cancelButton });
        layout.linkSize(SwingConstants.HORIZONTAL, new Component[] { addButton, cancelButton });

        layout.setVerticalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup().addComponent(titleLabel)
                        .addGroup(layout.createParallelGroup().addComponent(redLabel).addComponent(redField))
                        .addGroup(layout.createParallelGroup().addComponent(greenLabel).addComponent(greenField))
                        .addGroup(layout.createParallelGroup().addComponent(blueLabel).addComponent(blueField))
                        .addGroup(layout.createParallelGroup().addComponent(pointLabel).addComponent(pointField))
                        .addGroup(layout.createParallelGroup().addComponent(addButton).addComponent(cancelButton))
                        .addComponent(error)));

    }
}

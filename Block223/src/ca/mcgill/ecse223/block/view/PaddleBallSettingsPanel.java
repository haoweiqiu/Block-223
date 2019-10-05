package ca.mcgill.ecse223.block.view;

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

public class PaddleBallSettingsPanel extends JPanel {

    private static final long serialVersionUID = 19862359687542369L;

    private JLabel gameHeaderLabel;
    private JLabel nrBlocksPerLevelLabel;
    private JTextField nrBlocksPerLevelField;

    private JLabel ballHeaderLabel;
    private JLabel minXSpeedLabel;
    private JTextField minXSpeedField;
    private JLabel minYSpeedLabel;
    private JTextField minYSpeedField;
    private JLabel speedIncreaseLabel;
    private JTextField speedIncreaseField;

    private JLabel paddleHeaderLabel;
    private JLabel minPaddleLengthLabel;
    private JTextField minPaddleLengthField;
    private JLabel maxPaddleLengthLabel;
    private JTextField maxPaddleLengthField;

    private JButton submitButton;

    public PaddleBallSettingsPanel() {
        super();
        init();
    }

    private void init() {
        TOGame game = null;
        try {
            game = Block223Controller.getCurrentDesignableGame();
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }
        if (game == null) {
            System.out.println("Fail");
            return;
        }
        
        // Initialize components
        gameHeaderLabel = new JLabel("Game", SwingConstants.CENTER);
        gameHeaderLabel.setFont(new Font(gameHeaderLabel.getFont().getName(), Font.BOLD, 25));
        nrBlocksPerLevelLabel = new JLabel("Blocks per Level");
        nrBlocksPerLevelField = new JTextField("" + game.getNrBlocksPerLevel());

        ballHeaderLabel = new JLabel("Ball", SwingConstants.CENTER);
        ballHeaderLabel.setFont(new Font(ballHeaderLabel.getFont().getName(), Font.BOLD, 25));
        minXSpeedLabel = new JLabel("Min X Speed");
        minXSpeedField = new JTextField("" + game.getMinBallSpeedX());
        minYSpeedLabel = new JLabel("Min Y Speed");
        minYSpeedField = new JTextField("" + game.getMinBallSpeedY());
        speedIncreaseLabel = new JLabel("Speed Increase");
        speedIncreaseField = new JTextField("" + game.getBallSpeedIncreaseFactor());

        paddleHeaderLabel = new JLabel("Paddle", SwingConstants.CENTER);
        paddleHeaderLabel.setFont(new Font(paddleHeaderLabel.getFont().getName(), Font.BOLD, 25));
        minPaddleLengthLabel = new JLabel("Min Paddle Length");
        minPaddleLengthField = new JTextField("" + game.getMinPaddleLength());
        maxPaddleLengthLabel = new JLabel("Max Paddle Length");
        maxPaddleLengthField = new JTextField("" + game.getMaxPaddleLength());

        submitButton = new JButton("Submit");

        // Action listener
        submitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                TOGame game = null;
                try {
                    game = Block223Controller.getCurrentDesignableGame();
                } catch (InvalidInputException ex) {
                    System.out.println(ex.getMessage());
                }
                if (game == null) {
                    System.out.println("Fail");
                    return;
                }

                try {
                    Block223Controller.updateGame(game.getName(), game.getNrLevels(),
                            Integer.parseInt(nrBlocksPerLevelField.getText()),
                            Integer.parseInt(minXSpeedField.getText()), Integer.parseInt(minYSpeedField.getText()),
                            Double.parseDouble(speedIncreaseField.getText()),
                            Integer.parseInt(maxPaddleLengthField.getText()),
                            Integer.parseInt(minPaddleLengthField.getText()));
                } catch (NumberFormatException ex) {
                    System.out.println(ex.getMessage());
                } catch (InvalidInputException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });

        // Layout
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createSequentialGroup()
        		.addGroup(layout.createParallelGroup()
        				.addComponent(ballHeaderLabel, Alignment.CENTER)
        				.addComponent(paddleHeaderLabel, Alignment.CENTER)
        				.addComponent(submitButton, Alignment.CENTER)
        				.addComponent(gameHeaderLabel, Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                        		.addComponent(minXSpeedLabel)
                        		.addComponent(minYSpeedLabel)
                                .addComponent(speedIncreaseLabel)
                                .addComponent(minPaddleLengthLabel)
                                .addComponent(maxPaddleLengthLabel)
                                .addComponent(nrBlocksPerLevelLabel))
                        .addGroup(layout.createParallelGroup()
                        		.addComponent(minXSpeedField, 50, 50, 50)
                                .addComponent(minYSpeedField, 50, 50, 50)
                                .addComponent(speedIncreaseField, 50, 50, 50)
                                .addComponent(minPaddleLengthField, 50, 50, 50)
                                .addComponent(maxPaddleLengthField, 50, 50, 50)
                                .addComponent(nrBlocksPerLevelField, 50, 50, 50)))));

        layout.linkSize(SwingConstants.HORIZONTAL, nrBlocksPerLevelField, minXSpeedField, minYSpeedField,
                speedIncreaseField, minPaddleLengthField, maxPaddleLengthField);
        layout.linkSize(SwingConstants.VERTICAL, nrBlocksPerLevelField, minXSpeedField, minYSpeedField,
                speedIncreaseField, minPaddleLengthField, maxPaddleLengthField);

        layout.setVerticalGroup(layout.createSequentialGroup()
        		.addComponent(gameHeaderLabel)
        		.addGroup(layout.createParallelGroup()
        				.addComponent(nrBlocksPerLevelLabel)
                        .addComponent(nrBlocksPerLevelField))
        		.addComponent(ballHeaderLabel)
                .addGroup(layout.createParallelGroup()
                		.addComponent(minXSpeedLabel)
                		.addComponent(minXSpeedField))
                .addGroup(layout.createParallelGroup()
                		.addComponent(minYSpeedLabel)
                		.addComponent(minYSpeedField))
                .addGroup(layout.createParallelGroup()
                		.addComponent(speedIncreaseLabel)
                		.addComponent(speedIncreaseField))
                .addComponent(paddleHeaderLabel)
                .addGroup(layout.createParallelGroup()
                		.addComponent(minPaddleLengthLabel)
                        .addComponent(minPaddleLengthField))
                .addGroup(layout.createParallelGroup()
                		.addComponent(maxPaddleLengthLabel)
                        .addComponent(maxPaddleLengthField))
                .addComponent(submitButton));

    }
}
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
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOBlock;
import ca.mcgill.ecse223.block.controller.TOGridCell;

public class LevelBuilderPanel extends JPanel {

    private static final long serialVersionUID = 116619861986L;

    private final int level;

    private JLabel header;
    private JLabel error;

    private LevelViewerPanel viewer;

    private JScrollPane blockScrollPane;
    private BlockPickerPanel blockPicker;

    private JButton removeBlockButton;
    private JButton placeBlockButton;
    private JButton moveBlockButton;

    public LevelBuilderPanel(int level) {
        super();
        this.level = level;
        init();
    }

    private void init() {
        String gameName;
        try {
            gameName = Block223Controller.getCurrentDesignableGame().getName();
        } catch (InvalidInputException e) {
            e.printStackTrace();
            gameName = "Unknown Game";
        }
        
        header = new JLabel(gameName + " - Level " + level);
        error = new JLabel("");
        error.setForeground(Color.RED);
        header.setFont(new Font(header.getFont().getFontName(), Font.PLAIN, 18));
        viewer = new LevelViewerPanel(level);

        blockPicker = new BlockPickerPanel();
        blockScrollPane = new JScrollPane(blockPicker);

        removeBlockButton = new JButton("Remove Block");
        placeBlockButton = new JButton("Place Block");
        moveBlockButton = new JButton("Move Block");

        // Action listener
        removeBlockButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                TOGridCell tgc = viewer.getSelectedCell();
                if (tgc == null) {
                    error.setText("Need to select a block");
                    error.setVisible(true);
                    return;
                }
                try {
                    Block223Controller.removeBlock(level, tgc.getGridHorizontalPosition(),
                            tgc.getGridVerticalPosition());
                    repaint();
                    error.setVisible(false);
                } catch (InvalidInputException ex) {
                    System.out.println(ex.getMessage());
                    error.setText(ex.getMessage());
                    error.setVisible(true);
                }
            }
        });

        placeBlockButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                TOBlock tb = blockPicker.getSelectedBlock();
                TOGridCell tgc = viewer.getSelectedEmptyCell();

                if (tb == null || tgc == null) {
                    System.out.println("Placement failed");
                    error.setText("Need to select a block and an empty cell");
                    error.setVisible(true);
                    return;
                }

                try {
                    Block223Controller.positionBlock(tb.getId(), level, tgc.getGridHorizontalPosition(),
                            tgc.getGridVerticalPosition());
                    repaint();
                    error.setVisible(false);
                } catch (InvalidInputException ex) {
                    System.out.println(ex.getMessage());
                    error.setText(ex.getMessage());
                    error.setVisible(true);
                }
            }
        });

        moveBlockButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                TOGridCell tgc1 = viewer.getSelectedCell();
                TOGridCell tgc2 = viewer.getSelectedEmptyCell();

                if (tgc1 == null || tgc2 == null) {
                    error.setText("Need to select an existing block and empty cell");
                    error.setVisible(true);
                    return;
                }

                try {
                    Block223Controller.moveBlock(level, tgc1.getGridHorizontalPosition(),
                            tgc1.getGridVerticalPosition(), tgc2.getGridHorizontalPosition(),
                            tgc2.getGridVerticalPosition());
                    repaint();
                    error.setVisible(false);
                } catch (InvalidInputException ex) {
                    System.out.println(ex.getMessage());
                    error.setText(ex.getMessage());
                    error.setVisible(true);
                }
            }
        });

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(Alignment.CENTER)
                .addComponent(header)
                .addComponent(viewer, 400,400, 400)
                .addComponent(error))
            .addGroup(layout.createParallelGroup(Alignment.CENTER)
                .addComponent(blockScrollPane, 165, 165, 165)
                .addComponent(placeBlockButton)
                .addComponent(removeBlockButton)
                .addComponent(moveBlockButton)));

        layout.linkSize(SwingConstants.HORIZONTAL, blockScrollPane, placeBlockButton, removeBlockButton, moveBlockButton);
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(header)
                .addGroup(layout.createParallelGroup()
                    .addComponent(viewer, 400, 400, 400)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(blockScrollPane, 310, 310, 310)
                    .addComponent(removeBlockButton)
                    .addComponent(placeBlockButton)
                    .addComponent(moveBlockButton)))
            .addComponent(error));
    }
}
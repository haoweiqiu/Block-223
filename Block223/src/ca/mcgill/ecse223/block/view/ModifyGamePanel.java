package ca.mcgill.ecse223.block.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;

public class ModifyGamePanel extends JPanel {

    private static final long serialVersionUID = -196432969698776L;

    private Block223Page block223Page;

    private JScrollPane lpScrollPane;
    private LevelPickerPanel lpPanel;
    private PaddleBallSettingsPanel pbsPanel;

    private JButton editBlocksButton;
    private JButton deleteGameButton;
    private JButton addBlocksButton;
    private JButton publishGameButton;
    private JButton testGameButton;

    public ModifyGamePanel() {
        super();
        this.block223Page = Block223Page.getPage();
        init();
    }

    private void init() {
    	// Objects
        lpPanel = new LevelPickerPanel();
        lpScrollPane = new JScrollPane(lpPanel);
        pbsPanel = new PaddleBallSettingsPanel();
        editBlocksButton = new JButton("Edit Blocks");
        deleteGameButton = new JButton("Delete Game");
        addBlocksButton = new JButton("Add Blocks");
        publishGameButton = new JButton("Publish Game");
        testGameButton = new JButton("Test Game");

        // Action listeners
        editBlocksButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                block223Page.showBlockViewer();

            }
        });

        deleteGameButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = Block223Controller.getCurrentDesignableGame().getName();
                    Block223Controller.deleteGame(name);
                    block223Page.showAdminGames();
                    block223Page.clearBackStack();
                } catch (InvalidInputException ex) {
                    System.out.println(ex.getMessage());
                }

            }
        });

        addBlocksButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                block223Page.showAddBlock();
            }
        });

        testGameButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    Block223Controller.testGame(Block223Page.getPage());
                } catch (InvalidInputException ex) {
                    ex.printStackTrace();
                }
                
                Block223Page.getPage().showTestGamePanel();
                
            }
        });

        publishGameButton.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Block223Controller.publishGame();
                    block223Page.goBack();
                } catch (InvalidInputException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addComponent(lpScrollPane, 410, 410, 410)
                	.addGroup(layout.createParallelGroup()
                            .addComponent(pbsPanel)
                            .addComponent(testGameButton, Alignment.CENTER)
                            .addComponent(publishGameButton, Alignment.CENTER)
                	.addGroup(layout.createParallelGroup()
                        .addComponent(editBlocksButton, Alignment.CENTER)
                        .addComponent(deleteGameButton, Alignment.CENTER)
                        .addComponent(addBlocksButton, Alignment.CENTER))));

        layout.linkSize(SwingConstants.VERTICAL,
                new Component[] { editBlocksButton, deleteGameButton, addBlocksButton });
        layout.linkSize(SwingConstants.HORIZONTAL,
                new Component[] { editBlocksButton, deleteGameButton, addBlocksButton });

        layout.setVerticalGroup(layout.createParallelGroup()
                .addComponent(lpScrollPane, Block223Consts.PLAY_AREA_SIDE, Block223Consts.PLAY_AREA_SIDE,
                        Block223Consts.PLAY_AREA_SIDE)
                .addGroup(layout.createSequentialGroup()
                		.addGroup(layout.createSequentialGroup()
                				.addComponent(deleteGameButton)
                                .addComponent(editBlocksButton))
                                .addComponent(addBlocksButton)
                                .addComponent(pbsPanel)
                                .addComponent(publishGameButton)
                                .addComponent(testGameButton)));
    }
}
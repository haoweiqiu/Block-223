package ca.mcgill.ecse223.block.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.GroupLayout;
import javax.swing.JPanel;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOCurrentlyPlayedGame;
import ca.mcgill.ecse223.block.controller.TOHallOfFameEntry;

public class InGamePanel extends JPanel {

    private static final long serialVersionUID = 6931488878568872547L;

    private GamePlayerPanel gamePlayerPanel;
    private HallOfFamePanel hallOfFamePanel;

    public InGamePanel() {
        super();
        init();
    }

    private void init() {
        // gameRunning = false;
        gamePlayerPanel = new GamePlayerPanel();
        hallOfFamePanel = new HallOfFamePanel();

        // addKeyListener(new KeyListener() {

        //     @Override
        //     public void keyTyped(KeyEvent e) {

        //     }

        //     @Override
        //     public void keyReleased(KeyEvent e) {

        //     }

        //     @Override
        //     public void keyPressed(KeyEvent e) {
        //         if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        //             System.out.println("Space pressed");
        //             if (gamePlayerPanel.getGameOver()) {
        //                 Block223Page.getPage().goBack();
        //                 return;
        //             }

        //             bp = new Block223PlayModeListener();
        //             Thread t1 = new Thread(new Runnable() {

        //                 @Override
        //                 public void run() {
        //                     InGamePanel.this.addKeyListener(bp);
        //                     // gamePlayerPanel.requestFocus();
        //                 }
        //             });
        //             t1.start();
        //             try {
        //                 t1.join();
        //             } catch (InterruptedException ex) {
        //                 ex.printStackTrace();
        //             }

        //             Thread t2 = new Thread(new Runnable() {

        //                 @Override
        //                 public void run() {
        //                     try {
        //                         Block223Controller.startGame(InGamePanel.this);
        //                     } catch (InvalidInputException ex) {
        //                         ex.printStackTrace();
        //                     }
        //                 }
        //             });
        //             t2.start();
        //             // removeKeyListener(this);
        //         }
        //     }
        // });
        // bp = new Block223PlayModeListener();
        // Thread t1 = new Thread(new Runnable() {

        // @Override
        // public void run() {
        // InGamePanel.this.addKeyListener(bp);
        // // gamePlayerPanel.requestFocus();
        // }
        // });
        // t1.start();
        // try {
        // t1.join();
        // } catch (InterruptedException ex) {
        // ex.printStackTrace();
        // }

        // Thread t2 = new Thread(new Runnable() {

        // @Override
        // public void run() {
        // try {
        // Block223Controller.startGame(InGamePanel.this);
        // } catch (InvalidInputException ex) {
        // ex.printStackTrace();
        // }
        // }
        // });
        // t2.start();

        // addKeyListener(new KeyListener() {

        // @Override
        // public void keyTyped(KeyEvent e) {

        // }

        // @Override
        // public void keyReleased(KeyEvent e) {

        // }

        // @Override
        // public void keyPressed(KeyEvent e) {
        // if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        // System.out.println("Enter pressed");
        // if (gamePlayerPanel.getGameOver()) {
        // Block223Page.getPage().goBack();
        // return;
        // }

        // // removeKeyListener(this);

        // }
        // }
        // });

        buildLayout();
    }

    private void buildLayout() {
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup().addComponent(gamePlayerPanel).addComponent(hallOfFamePanel));

        layout.setVerticalGroup(
                layout.createParallelGroup().addComponent(gamePlayerPanel).addComponent(hallOfFamePanel));
    }

    public void endGame(int nrOfLives, TOHallOfFameEntry hof) {
        gamePlayerPanel.setGameOver(true);
        gamePlayerPanel.repaint();
        hallOfFamePanel.addFinalEntry(hof);
        // hallOfFamePanel.gameOverLabels();
        /* hallOfFamePanel = new HallOfFamePanel(); */
        // buildLayout();
    }

    public boolean getGameOver() {
        return gamePlayerPanel.getGameOver();
    }

}
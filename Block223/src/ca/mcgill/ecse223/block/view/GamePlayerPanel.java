package ca.mcgill.ecse223.block.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOCurrentBlock;
import ca.mcgill.ecse223.block.controller.TOCurrentlyPlayedGame;

public class GamePlayerPanel extends JPanel {

    private static final long serialVersionUID = -15023578547295L;

    private TOCurrentlyPlayedGame currentGame;

    private List<TOCurrentBlock> blockArrangement;

    private boolean gameOver;
	/*
	 * private boolean gameRunning; private boolean inGame;
	 */

    public GamePlayerPanel() {
        super();
        init();
    }

    private void init() {
        // need to get current played game
        currentGame = null;
        gameOver = false;
		/*
		 * gameRunning = false; inGame = false;
		 */

         addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                Block223Page.getPage().requestFocus();
            }
         });
    }

    private void doDrawing(Graphics g) {
        try {
            currentGame = Block223Controller.getCurrentPlayableGame();
        } catch (InvalidInputException e) {
        	//System.out.println("oh my god bugs");
        	System.out.println(e.getMessage());
            // return;
        }
        
        String path = "C:\\Users\\Murray Kornelsen\\Documents\\ecse223-group-project-p-6\\Block223\\face.png";
        
        // Graphics2D g2d = (Graphics2D) g.create(0, 0, Block223Consts.PLAY_AREA_SIDE, Block223Consts.PLAY_AREA_SIDE);
        Graphics2D g2d = (Graphics2D) g;
        BasicStroke thinStroke = new BasicStroke(2);
        BasicStroke thickStroke = new BasicStroke(4);
        g2d.setStroke(thinStroke);

        // Play area outline
        

        try {
            Image bg = ImageIO.read(new File(path));
            
            g2d.drawImage(bg.getScaledInstance(390, 390, 0), 0, 0, null);
        } catch (IOException e) {
            
        }
        
        g2d.drawLine(0, 0, Block223Consts.PLAY_AREA_SIDE, 0);
        g2d.drawLine(0, 0, 0, Block223Consts.PLAY_AREA_SIDE);
        g2d.drawLine(Block223Consts.PLAY_AREA_SIDE, 0, Block223Consts.PLAY_AREA_SIDE, Block223Consts.PLAY_AREA_SIDE);
        g2d.drawLine(0, Block223Consts.PLAY_AREA_SIDE, Block223Consts.PLAY_AREA_SIDE, Block223Consts.PLAY_AREA_SIDE);

        if (gameOver) {
            g2d.setFont(new Font(g2d.getFont().getName(), Font.BOLD, 30));
            g2d.drawString("Game Over", 100, Block223Consts.PLAY_AREA_SIDE / 2);   
            return;
        }

        blockArrangement = currentGame.getBlocks();   

        for(int i=0; i<blockArrangement.size(); i++){
            Point tl = new Point(blockArrangement.get(i).getX(), blockArrangement.get(i).getY());
            Rectangle2D block = new Rectangle(tl, new Dimension(Block223Consts.SIZE, Block223Consts.SIZE));
            // drawnBlocks.add(block);
            g2d.setColor(new Color(blockArrangement.get(i).getRed(),blockArrangement.get(i).getGreen(),blockArrangement.get(i).getBlue()));
            g2d.fill(block);
        }        
        g2d.setColor(new Color(255, 0, 255));
;
        // paddle
        Rectangle2D.Double paddle = new Rectangle2D.Double(currentGame.getCurrentPaddleX(), Block223Consts.PLAY_AREA_SIDE - Block223Consts.VERTICAL_DISTANCE - Block223Consts.PADDLE_WIDTH, currentGame.getCurrentPaddleLength(), Block223Consts.PADDLE_WIDTH);
        // g2d.setColor(new Color(0,0,0));
        g2d.fill(paddle);
        g2d.draw(paddle);


        double rad = Block223Consts.BALL_DIAMETER / 2;
        double diam = Block223Consts.BALL_DIAMETER;
        Ellipse2D ball = new Ellipse2D.Double(currentGame.getCurrentBallX() - rad, currentGame.getCurrentBallY() - rad, diam, diam);
        
        g2d.fill(ball);
        g2d.draw(ball);

        // g2d.setColor(new Color(0, 0, 0));
        g2d.drawString("Score = " + currentGame.getScore(), 10, Block223Consts.PLAY_AREA_SIDE - 10);
        g2d.drawString("Lives left: " + currentGame.getLives(), 300, Block223Consts.PLAY_AREA_SIDE - 10);
    }

    public void setGameOver(boolean go) {
        this.gameOver = go;
    }

    public boolean getGameOver() {
        return gameOver;
    }

	/*
	 * public void setGameRunning(boolean flag) { this.gameRunning = flag; }
	 * 
	 * public boolean getGameRunning() { return gameRunning; }
	 * 
	 * public void setInGame(boolean flag) { this.inGame = flag; }
	 * 
	 * public boolean getInGame() { return inGame; }
	 */
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
}
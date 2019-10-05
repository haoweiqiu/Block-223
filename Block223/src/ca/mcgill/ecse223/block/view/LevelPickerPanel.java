package ca.mcgill.ecse223.block.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Scrollable;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOGame;

public class LevelPickerPanel extends JPanel implements Scrollable {

    private static final long serialVersionUID = 0x6452375082407L;

    private TOGame currentTOGame;

    private Block223Page block223Page;

    private List<Rectangle2D> drawnLevels;

    private HashMap<Rectangle2D, Integer> rectLevels;

    private Rectangle2D newLevelRect;

    public LevelPickerPanel() {
        super();
        this.block223Page = Block223Page.getPage();
        init();
    }

    private void init() {
        // Objects
        drawnLevels = new ArrayList<>();
        rectLevels = new HashMap<>();

        // Action listeners
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                if (newLevelRect.contains(x, y)){
                    try {
                        Block223Controller.addLevelToGame();
                        doDrawing(getGraphics());
                        block223Page.redrawFrame();
                    } catch (InvalidInputException e1) {
                        System.out.println(e1.getMessage());
                    }
                } else {
                    for (int i = 0; i < drawnLevels.size(); i++) {
                        if (drawnLevels.get(i).contains(x, y)) {
                            System.out.println("level " + rectLevels.get(drawnLevels.get(i)) + " clicked");
                            block223Page.showLevelBuilder(rectLevels.get(drawnLevels.get(i)));
                            break;
                        }
                    }
                }
            }
        });
    }

    // Define drawing parameters
    private void doDrawing(Graphics g) {
        drawnLevels.clear();
        rectLevels.clear();

        try {
            currentTOGame = Block223Controller.getCurrentDesignableGame();
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }

        Graphics2D g2d = (Graphics2D) g;

        BasicStroke thinStroke = new BasicStroke(2);
        g2d.setStroke(thinStroke);
        g2d.setColor(Color.BLACK);

        int x = 0, y = 0;

        for (int i = 1; i <= currentTOGame.getNrLevels(); i++) {

            Point topLeft = new Point(x * (100 + 25) + 20, y * (40 + 10) + 15); 
            Rectangle2D level = new Rectangle(topLeft, new Dimension(100, 40));
            drawnLevels.add(level);
            rectLevels.put(level, i);
            
            g2d.draw(level);
            g2d.drawString("Level " + i, (int) topLeft.getX() + 5, (int) topLeft.getY() + 15);

            // Add `level` box on next line when the row is full (3 boxes)
            x++;
            if (x == 3) {
                x = 0;
                y++;
            }
        }
        this.setPreferredSize(new Dimension(390, y * 50 + 15 + 50));
        Point topLeft = new Point(x * (100 + 25) + 20, y * (40 + 10) + 15); 
        newLevelRect = new Rectangle(topLeft, new Dimension(100, 40));
        g2d.draw(newLevelRect);
        g2d.setFont(new Font(g2d.getFont().getName(), Font.PLAIN, 18));
        g2d.drawString("+", (int) topLeft.getX() + 42, (int) topLeft.getY() + 21);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return new Dimension(Block223Consts.PLAY_AREA_SIDE, Block223Consts.PLAY_AREA_SIDE);
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 10;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 10;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return true; 
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
}
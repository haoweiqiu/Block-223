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

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Scrollable;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOGame;

public class ViewGamesPanel extends JPanel implements Scrollable {

    private static final long serialVersionUID = 11234132415L;

    // Get block223Page as the buildlayout method
    private Block223Page block223page;

    // Main part
    private List<Rectangle2D> drawnGames;
    private HashMap<Rectangle2D, TOGame> rectGames;

    private List<TOGame> currentGames;

    private Rectangle2D newGameRect;

    public ViewGamesPanel() {
        super();
        this.block223page = Block223Page.getPage();
        init();
    }

    private void init() {
    	// Objects
        drawnGames = new ArrayList<>();
        rectGames = new HashMap<>();
        currentGames = new ArrayList<>();

        // Action listeners
        addMouseListener(new MouseAdapter() {
        	
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                if (newGameRect.contains(x, y)) {
                    String newGameName = JOptionPane.showInputDialog("Enter name of new game");
                    try {
                        Block223Controller.createGame(newGameName);
                        doDrawing(getGraphics());
                        block223page.redrawFrame();
                    } catch (InvalidInputException ex) {
                        System.out.println(ex.getMessage());
                    }
                } else {

                    for (Rectangle2D rect : drawnGames) {

                        if (rect.contains(x, y)) {
                            try {
                                Block223Controller.selectGame(rectGames.get(rect).getName());
                                block223page.showModifyGame();
                            } catch (InvalidInputException ex) {
                                System.out.println(ex.getMessage());
                            }
                        }
                    }
                }
            }
        });
    }

    // Define drawing parameters
    private void doDrawing(Graphics g) {
        drawnGames.clear();
        rectGames.clear();

        try {
            currentGames = Block223Controller.getDesignableGames();
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }

        Graphics2D g2d = (Graphics2D) g;

        BasicStroke thinStroke = new BasicStroke(2);
        g2d.setStroke(thinStroke);
        g2d.setColor(Color.BLACK);

        int x = 0, y = 0;

        for (int i = 0; i < currentGames.size(); i++) {
            Point tl = new Point(x * (100 + 25) + 20, y * (100 + 10) + 15);
            Rectangle2D game = new Rectangle(tl, new Dimension(100, 100));
            drawnGames.add(game);
            rectGames.put(game, currentGames.get(i));

            g2d.draw(game);
            g2d.setFont(new Font(g2d.getFont().getName(), Font.PLAIN, 18));
            String name = currentGames.get(i).getName();
            if (name.length() > 8) {
                name = name.substring(0, 8);
            }
            g2d.drawString(name, (int) tl.getX() + 10, (int) tl.getY() + 50);

            // Add `game` box on next line when the row is full (3 boxes)
            x++;
            if (x == 3) {
                x = 0;
                y++;
            }
        }
        this.setPreferredSize(new Dimension(390, y * 110 + 15 + 110));

        Point tl = new Point(x * (100 + 25) + 20, y * (100 + 10) + 15);
        newGameRect = new Rectangle(tl, new Dimension(100, 100));
        g2d.draw(newGameRect);
        g2d.drawString("+", (int) tl.getX() + 42, (int) tl.getY() + 54);
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
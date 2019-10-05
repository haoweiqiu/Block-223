package ca.mcgill.ecse223.block.view;

import java.awt.BasicStroke;
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
import ca.mcgill.ecse223.block.controller.TOPlayableGame;

public class PlayableGamePickerPanel extends JPanel implements Scrollable {

    private static final long serialVersionUID = 1192837421984619238L;

    private List<TOPlayableGame> availableGames;
    private List<Rectangle2D> drawnGames;

    private HashMap<Rectangle2D, TOPlayableGame> rectGames;

    public PlayableGamePickerPanel() {
        super();
        init();
    }

    private void init() {
        availableGames = new ArrayList<>();
        drawnGames = new ArrayList<>();
        rectGames = new HashMap<>();

        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                for (Rectangle2D rect : drawnGames) {
                    if (rect.contains(x, y)) {
                        TOPlayableGame tg = rectGames.get(rect);
                        try {
                            if (tg.getNumber() == -1) {
                                Block223Controller.selectPlayableGame(tg.getName(), tg.getNumber());
                            } else {
                                Block223Controller.selectPlayableGame(null, tg.getNumber());
                            }
                            Block223Page.getPage().showInGamePanel();
                        } catch (InvalidInputException ex) {
                            ex.printStackTrace();
                        }
                        
                    }
                }
            }
        });
    }

    private void doDrawing(Graphics g) {

        availableGames.clear();
        drawnGames.clear();
        rectGames.clear();

        try {
            availableGames = Block223Controller.getPlayableGames();
            // System.out.println("Found " + availableGames.size() + " Games");
        } catch (InvalidInputException e) {
            e.printStackTrace();
        }

        Graphics2D g2d = (Graphics2D) g;
        BasicStroke thinStroke = new BasicStroke(2);
        BasicStroke thickStroke = new BasicStroke(4);
        g2d.setStroke(thinStroke);

        int x = 0, y = 0;
        for (int i = 0; i < availableGames.size(); i++) {
            Point tl = new Point(x * (100 + 25) + 20, y * (100 + 10) + 15);
            Rectangle2D game = new Rectangle(tl, new Dimension(100, 100));
            drawnGames.add(game);
            rectGames.put(game, availableGames.get(i));

            g2d.draw(game);
            g2d.setFont(new Font(g2d.getFont().getName(), Font.PLAIN, 18));
            int num = availableGames.get(i).getNumber();
            String name = "" + num;
            if (num == -1) {
                name = availableGames.get(i).getName();
            }
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
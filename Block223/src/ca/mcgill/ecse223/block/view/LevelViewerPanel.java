package ca.mcgill.ecse223.block.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
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

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOGridCell;

public class LevelViewerPanel extends JPanel {

    private static final long serialVersionUID = 19191741649843L;

    private final int level;

    private List<TOGridCell> blockArrangement;
    private List<TOGridCell> placeholders;
    private List<Rectangle2D> drawnBlocks;
    private TOGridCell selectedBlock;
    private TOGridCell selectedPlaceholder;

    private boolean[][] occupied;

    private HashMap<Rectangle2D, TOGridCell> rectGridCells;

    public LevelViewerPanel(int level) {
        super();
        this.level = level;
        init();
    }

    public TOGridCell getSelectedCell() {
        return selectedBlock;
    }

    public TOGridCell getSelectedEmptyCell() {
        return selectedPlaceholder;
    }

    private void init() {

        // Objects
        occupied = new boolean[15][15];
        selectedBlock = null;
        selectedPlaceholder = null;
        drawnBlocks = new ArrayList<>();
        rectGridCells = new HashMap<>();

        blockArrangement = new ArrayList<>();
        placeholders = new ArrayList<>();

        // Action listeners
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                for (Rectangle2D rect : drawnBlocks) {
                    if (rect.contains(x, y)) {
                        TOGridCell tgc = rectGridCells.get(rect);
                        if (blockArrangement.contains(tgc)) {
                            selectedBlock = tgc;
                            System.out.println("Selected a block");
                        } else if (placeholders.contains(tgc)) {
                            selectedPlaceholder = tgc;
                            System.out.println("Selected a placeholder");
                        }
                        break;
                    }
                }
                repaint();
            }
        });
    }

    // Define drawing parameters
    private void doDrawing(Graphics g) {
        drawnBlocks.clear();
        rectGridCells.clear();
        placeholders.clear();

        try {
            blockArrangement = Block223Controller.getBlocksAtLevelOfCurrentDesignableGame(level);
        } catch (InvalidInputException ex) {
            System.out.println("Something went wrong");
            ex.printStackTrace();
        }
        for (int i = 0; i < occupied.length; i++) {
            for (int j = 0; j < occupied[i].length; j++) {
                occupied[i][j] = false;
            }
        }
        for (int i = 0; i < blockArrangement.size(); i++) {
            occupied[blockArrangement.get(i).getGridHorizontalPosition() - 1][blockArrangement.get(i)
                    .getGridVerticalPosition() - 1] = true;
        }

        for (int i = 0; i < occupied.length; i++) {
            for (int j = 0; j < occupied[i].length; j++) {
                if (!occupied[i][j]) {
                    placeholders.add(new TOGridCell(i + 1, j + 1, -1, 200, 200, 200, -1, 0));
                }
            }
        }

        Graphics2D g2d = (Graphics2D) g.create(0, 0, Block223Consts.PLAY_AREA_SIDE, Block223Consts.PLAY_AREA_SIDE);

        BasicStroke thinStroke = new BasicStroke(2);
        BasicStroke thickStroke = new BasicStroke(4);
        g2d.setStroke(thinStroke);

        // Play area outline
        g2d.drawLine(0, 0, Block223Consts.PLAY_AREA_SIDE, 0);
        g2d.drawLine(0, 0, 0, Block223Consts.PLAY_AREA_SIDE);
        g2d.drawLine(Block223Consts.PLAY_AREA_SIDE, 0, Block223Consts.PLAY_AREA_SIDE, Block223Consts.PLAY_AREA_SIDE);
        g2d.drawLine(0, Block223Consts.PLAY_AREA_SIDE, Block223Consts.PLAY_AREA_SIDE, Block223Consts.PLAY_AREA_SIDE);

        // Blocks
        for (int i = 0; i < blockArrangement.size(); i++) {
            TOGridCell tgc = blockArrangement.get(i);
            Point tl = new Point(
                    (tgc.getGridHorizontalPosition() - 1) * (Block223Consts.SIZE + Block223Consts.COLUMNS_PADDING)
                            + Block223Consts.WALL_PADDING,
                    (tgc.getGridVerticalPosition() - 1) * (Block223Consts.SIZE + Block223Consts.ROW_PADDING)
                            + Block223Consts.WALL_PADDING);
            Rectangle2D block = new Rectangle(tl, new Dimension(Block223Consts.SIZE, Block223Consts.SIZE));
            drawnBlocks.add(block);
            rectGridCells.put(block, tgc);

            g2d.setColor(new Color(tgc.getRed(), tgc.getGreen(), tgc.getBlue()));
            g2d.fill(block);
            g2d.setColor(Color.BLACK);
            if (selectedBlock != null && selectedBlock.getGridHorizontalPosition() == tgc.getGridHorizontalPosition()
                    && selectedBlock.getGridVerticalPosition() == tgc.getGridVerticalPosition()) {
                g2d.setStroke(thickStroke);
            } else {
                g2d.setStroke(thinStroke);
            }
            g2d.draw(block);
        }

        // Placeholders
        for (int i = 0; i < placeholders.size(); i++) {
            TOGridCell tgc = placeholders.get(i);
            Point tl = new Point(
                    (tgc.getGridHorizontalPosition() - 1) * (Block223Consts.SIZE + Block223Consts.COLUMNS_PADDING)
                            + Block223Consts.WALL_PADDING,
                    (tgc.getGridVerticalPosition() - 1) * (Block223Consts.SIZE + Block223Consts.ROW_PADDING)
                            + Block223Consts.WALL_PADDING);
            Rectangle2D block = new Rectangle(tl, new Dimension(Block223Consts.SIZE, Block223Consts.SIZE));
            drawnBlocks.add(block);
            rectGridCells.put(block, tgc);

            g2d.setColor(new Color(tgc.getRed(), tgc.getGreen(), tgc.getBlue()));
            g2d.fill(block);
            g2d.setColor(Color.GRAY);
            if (selectedPlaceholder != null
                    && selectedPlaceholder.getGridHorizontalPosition() == tgc.getGridHorizontalPosition()
                    && selectedPlaceholder.getGridVerticalPosition() == tgc.getGridVerticalPosition()) {
                g2d.setStroke(new BasicStroke(1.5f));
            } else {
                g2d.setStroke(new BasicStroke(0.5f));
            }

            g2d.draw(block);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
}
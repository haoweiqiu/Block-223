package ca.mcgill.ecse223.block.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import ca.mcgill.ecse223.block.controller.TOBlock;

public class BlockDrawPanel extends JPanel {

	private static final long serialVersionUID = 1762405827807803688L;
	
	private TOBlock block;

	public BlockDrawPanel(TOBlock block) {
		super();
        this.block = block;
	}
    
    // Define drawing parameters
	private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create(0, 0, 450, 400);

        BasicStroke thinStroke = new BasicStroke(2);
        // BasicStroke thickStroke = new BasicStroke(4);
        g2d.setStroke(thinStroke);
        
        Point point = new Point(70, 30);
        Rectangle2D selectedBlock = new Rectangle(point, new Dimension(Block223Consts.SIZE *3, Block223Consts.SIZE *3));
        g2d.setColor(new Color(block.getRed(), block.getGreen(), block.getBlue()));
        g2d.fill(selectedBlock);
        g2d.setColor(Color.BLACK);
        g2d.draw(selectedBlock);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
}

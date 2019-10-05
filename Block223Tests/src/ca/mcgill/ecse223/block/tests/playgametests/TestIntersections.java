package ca.mcgill.ecse223.block.tests.playgametests;

import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Point2D;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse223.block.model.Admin;
import ca.mcgill.ecse223.block.model.Block;
import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.BlockAssignment;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.PlayedGame;

import static ca.mcgill.ecse223.block.tests.playgametests.Block223TestConstants.RED;
import static org.junit.Assert.fail;
import static ca.mcgill.ecse223.block.tests.playgametests.Block223TestConstants.GREEN;
import static ca.mcgill.ecse223.block.tests.playgametests.Block223TestConstants.BLUE;
import static ca.mcgill.ecse223.block.tests.playgametests.Block223TestConstants.POINTS;
import static ca.mcgill.ecse223.block.tests.playgametests.Block223TestConstants.BLOCK_LEVEL;

public class TestIntersections {
	
	private Block testBlock;
	private int aGridHorizontalPosition = 1;
	private int aGridVerticalPosition = 1;
	private Game game;
	
	private PlayedGame pgame;

	@Before
	public void setUp() {
		Block223 block223 = Block223TestUtil.initializeTestBlock223();
		Admin admin = Block223TestUtil.createAndAssignAdminRoleToBlock223(block223);
		game = Block223TestUtil.initializeTestGame(block223, admin);
		testBlock = new Block(RED, GREEN, BLUE, POINTS, game);
		new BlockAssignment(aGridHorizontalPosition, aGridVerticalPosition,
				game.getLevel(BLOCK_LEVEL - 1), testBlock, game);
		
		pgame = new PlayedGame("testplayer", game, block223);
	}
	
	@Test
	public void testTwoIntersectingLines() {
		Line2D l1 = new Line2D.Double(15, 15, 20, 20);
		Line2D l2 = new Line2D.Double(10, 20, 1000, 11);
		Point2D p = pgame.intersectionTester(l1, l2);
		
//		printPoint(p);
	}
	
	@Test
	public void testTwoNonIntersectingLines() {
		Line2D l1 = new Line2D.Double(10, 10, 100, 100);
		Line2D l2 = new Line2D.Double(10, 20, 10, 200);
		
//		System.out.println(l1.intersectsLine(l2));
		Point2D p = pgame.intersectionTester(l1, l2);
		if (p != null) {
//			printPoint(p);
			fail("Expected no intersection.");
		}
	}
	
	@Test
	public void testLineIntersectingArc() {
		Line2D l = new Line2D.Double(10, 10, 9, 0);
		Arc2D a = new Arc2D.Double(0, 0, 20, 20, 90, 90, Arc2D.OPEN);
		
		Point2D p = pgame.intersectionTester(l, a);
		printPoint(p);
	}
	
	@Test
	public void testLineNotIntersectingArc() {
		Line2D l = new Line2D.Double(10, 10, 20, 20);
		Arc2D a = new Arc2D.Double(0, 0, 20, 20, 90, 90, Arc2D.OPEN);
		
		Point2D p = pgame.intersectionTester(l, a);
		if (p != null) {
			fail("Expected no intersection.");
		}
	}
	
	private void printPoint(Point2D p) {
		System.out.println(String.format("x = %f, y = %f", p.getX(), p.getY()));
	}

}

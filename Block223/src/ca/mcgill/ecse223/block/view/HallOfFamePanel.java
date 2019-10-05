package ca.mcgill.ecse223.block.view;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOCurrentlyPlayedGame;
import ca.mcgill.ecse223.block.controller.TOHallOfFame;
import ca.mcgill.ecse223.block.controller.TOHallOfFameEntry;

public class HallOfFamePanel extends JPanel{
    
    private static final long serialVersionUID = 132432455509L;

    private TOCurrentlyPlayedGame currentGame;
    // private TOHallOfFame hof;
    private List<TOHallOfFameEntry> entries;

    private JLabel hofHeader;
    private JButton prev;
    private JButton next;
    private JLabel message;

    private JLabel titleRank;
    private JLabel titleName;
    private JLabel titlePoints;
    private List<JLabel> rank;
    private List<JLabel> name;
    private List<JLabel> points;

    private int start;
    private int end;

    public HallOfFamePanel(){
        super();
        init();
    }

    private void init() {

        rank = new ArrayList<>();
        name = new ArrayList<>();
        points = new ArrayList<>();
        entries = new ArrayList<>();
        
        start = 1;
        end = start + 9;
        try {
            currentGame = Block223Controller.getCurrentPlayableGame();
            // entries.addAll(Block223Controller.getHallOfFame(start, end).getEntries());
            
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }
        int i = 1;
        while (true) {
            List<TOHallOfFameEntry> temp = new ArrayList<>();
            try {
                temp = Block223Controller.getHallOfFame(i, i + 9).getEntries();
            } catch (InvalidInputException ex) {
                System.out.println(ex.getMessage());
            }
            if (temp.size() > 0) {
                entries.addAll(temp);
            } else {
                break;
            }
            i = i + 10;
        }

        entries.sort(new Comparator<TOHallOfFameEntry>() {

            @Override
            public int compare(TOHallOfFameEntry o1, TOHallOfFameEntry o2) {
                return o1.getPosition() - o2.getPosition();
            }

        });
    
        for (i = 0; i < 10; i++) {
            rank.add(new JLabel(""));
            name.add(new JLabel(""));
            points.add(new JLabel(""));
        }

        updateLabels();
        
        hofHeader = new JLabel("Hall of Fame for " + currentGame.getGamename());
        prev = new JButton("Prev");
        next = new JButton("Next");
        message = new JLabel("Message");

        titleRank = new JLabel("Rank");
        titleName = new JLabel("Name");
        titlePoints = new JLabel("Points");

        prev.setVisible(true);
        next.setVisible(true);
        message.setVisible(false);
        
        prev.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                message.setVisible(false);
                start = start - 10;
                if (start < 1) {
                    start = 1;
                }
                end = start + 9;
                updateLabels();
            }
        });

        next.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {
                message.setVisible(false);
                start = end + 1;
                if (start > entries.size()) {
                    start = entries.size() - 9;
                }
                if (start < 1) {
                    start = 1;
                }
                end = start + 9;
                updateLabels();
            }
        });

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        SequentialGroup g1 = layout.createSequentialGroup();
        ParallelGroup pg1 = layout.createParallelGroup();
        ParallelGroup pg2 = layout.createParallelGroup();
        ParallelGroup pg3 = layout.createParallelGroup();

        pg1.addComponent(titleRank);
        pg2.addComponent(titleName);
        pg3.addComponent(titlePoints);

        for (i = 0; i < 10; i++) {
            pg1.addComponent(rank.get(i));
            pg2.addComponent(name.get(i));
            pg3.addComponent(points.get(i));
        }

        g1.addGroup(pg1);
        g1.addGroup(pg2);
        g1.addGroup(pg3);

        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addComponent(hofHeader, Alignment.CENTER)
                .addComponent(message)
                .addGroup(g1)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(prev)
                    .addComponent(next)));
        
        SequentialGroup group = layout.createSequentialGroup();
        group.addGroup(layout.createParallelGroup()
            .addComponent(titleRank)
            .addComponent(titleName)
            .addComponent(titlePoints));
        for (i = 0; i < 10; i++) {
            group.addGroup(layout.createParallelGroup()
            .addComponent(rank.get(i))
            .addComponent(name.get(i))
            .addComponent(points.get(i)));
        }
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(hofHeader)
            .addGroup(layout.createParallelGroup()
                .addComponent(titleRank)
                .addComponent(titleName)
                .addComponent(titlePoints))
            .addGroup(group)
            .addGroup(layout.createParallelGroup()
                .addComponent(next)
                .addComponent(prev))
            .addComponent(message));
            

    }

    private void updateLabels() {
        for (int i = 0; i < 10; i++) {
            int entidx = i + start - 1;
        	if (entidx < entries.size()) {
        		rank.get(i).setText(entries.get(entidx).getPosition() + "");
            	name.get(i).setText(entries.get(entidx).getPlayername());
            	points.get(i).setText(entries.get(entidx).getScore() + "");
        	} else {
        		rank.get(i).setText("");
        		name.get(i).setText("");
        		points.get(i).setText("");	
        	}
        }
    }

    public void addFinalEntry(TOHallOfFameEntry entry) {
        entries.add(entry);
        entries.sort(new Comparator<TOHallOfFameEntry>() {

            @Override
            public int compare(TOHallOfFameEntry o1, TOHallOfFameEntry o2) {
                return o2.getScore() - o1.getScore();
            }

        });

        int loc = entries.indexOf(entry);
        entry.setPosition(loc + 1);

        start = loc - 4;
        if (start < 1) {
            start = 1;
        }
        end = start + 9;

        for (int i = 0; i < entries.size(); i++) {
            entries.get(i).setPosition(i + 1);
        }
        updateLabels();
    }
    
    // public void gameOverLabels() {
    //     try {
    //     	//System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAH");
	// 		hof = Block223Controller.getHallOfFameWithMostRecentEntry(10);
			
	// 	} catch (InvalidInputException e) {
	// 		e.printStackTrace();
	// 	}
    //     updateLabels();
    // }

}
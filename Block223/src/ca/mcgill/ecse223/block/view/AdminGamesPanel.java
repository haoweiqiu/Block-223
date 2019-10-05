package ca.mcgill.ecse223.block.view;

import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class AdminGamesPanel extends JPanel {

    private static final long serialVersionUID = 112984776139874L;

    private JLabel yourGamesLabel;

    private JScrollPane vgScrollPane;
    private ViewGamesPanel vgPanel;

    public AdminGamesPanel() {
        super();
        init();
    }

    private void init() {
    	// Initialize components
        yourGamesLabel = new JLabel("Your Games");
        yourGamesLabel.setFont(new Font(yourGamesLabel.getFont().getName(), Font.PLAIN, 18));
        vgPanel = new ViewGamesPanel();

        vgScrollPane = new JScrollPane(vgPanel);

        // Layout
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(yourGamesLabel, Alignment.CENTER)
                .addComponent(vgScrollPane, 410, 410, 410));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(yourGamesLabel)
                .addComponent(vgScrollPane, 390, 390, 390));
    }
}
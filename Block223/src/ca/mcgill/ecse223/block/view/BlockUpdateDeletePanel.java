package ca.mcgill.ecse223.block.view;

import javax.swing.GroupLayout;
import javax.swing.JPanel;

import ca.mcgill.ecse223.block.controller.TOBlock;

public class BlockUpdateDeletePanel extends JPanel {

    private static final long serialVersionUID = -3636590061072357508L;

    private BlockDeletePanel bdPanel;
    private BlockUpdatePanel buPanel;
    private TOBlock block;

    public BlockUpdateDeletePanel(TOBlock block) {
        super();
        this.block = block;
        init();
    }

    // 
    private void init() {

        bdPanel = new BlockDeletePanel(block);
        buPanel = new BlockUpdatePanel(block);

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addComponent(bdPanel, 300, 300, 300)
                .addComponent(buPanel, 300, 300, 300));

        layout.setVerticalGroup(layout.createParallelGroup()
                .addComponent(bdPanel, 500, 500, 500)
                .addComponent(buPanel, 500, 500, 500));// .addComponent(buPanel));

    }
}

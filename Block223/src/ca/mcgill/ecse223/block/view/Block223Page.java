package ca.mcgill.ecse223.block.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOBlock;
import ca.mcgill.ecse223.block.controller.TOCurrentlyPlayedGame;
import ca.mcgill.ecse223.block.controller.TOHallOfFameEntry;

public class Block223Page extends JFrame implements Block223PlayModeInterface {

    private static Block223Page page;

    private static final long serialVersionUID = -1123412341134L;
    private GroupLayout layout;

    private LeftPanel leftPanel;

    private JPanel mainPanel;
    private LoginPanel loginPanel;
    private SignupPanel signupPanel;
    private LevelBuilderPanel levelBuilderPanel;
    private AdminGamesPanel adminGamesPanel;
    private BlockAddPanel blockAddPanel;
    private BlockUpdateDeletePanel blockUpdateDeletePanel;
    private ModifyGamePanel modifyGamePanel;
    private BlockViewerPanel blockViewerPanel;
    private PlayableGamePickerPanel playableGamePickerPanel;
    private InGamePanel inGamePanel;
    private GamePlayerPanel testerPanel;

    private Block223PlayModeListener bp;

    private Stack<JPanel> backStack;

    public Block223Page() {
        page = this;
        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static Block223Page getPage() {
        return page;
    }

    private void initComponents() {
        backStack = new Stack<>(); // stack for `back` button
        leftPanel = new LeftPanel();
        LoginPanel temp = new LoginPanel();
        mainPanel = temp;
        backStack.push(temp);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Block223 - Team 6");

        buildLayout();

        pack();
    }

    // Layout
    private void buildLayout() {
        layout = new GroupLayout(getContentPane());
        getContentPane().removeAll();
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createSequentialGroup().addComponent(leftPanel, 150, 150, 150)
                .addComponent(mainPanel, 700, 700, 700));

        layout.setVerticalGroup(
                layout.createParallelGroup().addComponent(leftPanel).addComponent(mainPanel, 500, 500, 500));
        pack();
    }

    private void switchPanel(JPanel panel) {
        if (mainPanel == inGamePanel || mainPanel == testerPanel) {
            bp = null;
        }

        backStack.push(panel);
        mainPanel = panel;
        mainPanel.repaint();
        buildLayout();
    }

    // Login page display
    public void showLogin() {
        loginPanel = new LoginPanel();
        switchPanel(loginPanel);
        leftPanel.setIntroTextVisible();
    }

    // Sign up page display
    public void showSignUp() {
        signupPanel = new SignupPanel();
        switchPanel(signupPanel);
        leftPanel.setSignUpTextVisible();
    }

    public void showLevelBuilder(int level) {
        levelBuilderPanel = new LevelBuilderPanel(level);
        switchPanel(levelBuilderPanel);
    }

    public void showAdminGames() {
        adminGamesPanel = new AdminGamesPanel();
        switchPanel(adminGamesPanel);
        leftPanel.setTextsInvisible();
        leftPanel.initComponents();
    }

    public void showAddBlock() {
        blockAddPanel = new BlockAddPanel();
        switchPanel(blockAddPanel);
    }

    public void showBlockUpdateDelete(TOBlock block) {
        blockUpdateDeletePanel = new BlockUpdateDeletePanel(block);
        switchPanel(blockUpdateDeletePanel);
    }

    public void showModifyGame() {
        modifyGamePanel = new ModifyGamePanel();
        switchPanel(modifyGamePanel);
    }

    public void showPlayableGamePicker() {
        playableGamePickerPanel = new PlayableGamePickerPanel();
        JScrollPane pgpScrollPane = new JScrollPane(playableGamePickerPanel);
        JPanel p = new JPanel();
        GroupLayout layout = new GroupLayout(p);
        p.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup().addComponent(pgpScrollPane, 420, 420, 420));

        layout.setVerticalGroup(layout.createSequentialGroup().addComponent(pgpScrollPane, 400, 400, 400));
        switchPanel(p);
        leftPanel.setTextsInvisible();
        leftPanel.initComponents();
    }

    public void showInGamePanel() {
        System.out.println("igp");
        inGamePanel = new InGamePanel();
        switchPanel(inGamePanel);

        bp = new Block223PlayModeListener();
        addKeyListener(bp);

        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                while (!inGamePanel.getGameOver() && mainPanel == inGamePanel) {
                    Block223Page.this.requestFocus();
                    while (!takeInputs().contains(" ")) {
                        // System.out.println("waiting");
                        if (!Block223Page.this.isFocused()) {
                            Block223Page.this.requestFocus();
                        }
                    }
                    try {
                        Block223Controller.startGame(Block223Page.this);
                    } catch (InvalidInputException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                // focusHolder.interrupt();
            }
        });

        t1.start();
    }

    public void showTestGamePanel() {
        testerPanel = new GamePlayerPanel();
        switchPanel(testerPanel);

        bp = new Block223PlayModeListener();
        addKeyListener(bp);

        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                while (!testerPanel.getGameOver() && mainPanel == testerPanel) {
                    Block223Page.this.requestFocus();
                    while (!takeInputs().contains(" ")) {
                        // System.out.println("waiting");
                        if (!Block223Page.this.isFocused()) {
                            Block223Page.this.requestFocus();
                        }
                    }
                    try {
                        Block223Controller.startGame(Block223Page.this);
                    } catch (InvalidInputException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                // goBack();
                // focusHolder.interrupt();
            }
        });

        t1.start();
    }

    public void showBlockViewer() {
        blockViewerPanel = new BlockViewerPanel();
        JScrollPane bvScrollPane = new JScrollPane(blockViewerPanel);
        JPanel p = new JPanel();
        GroupLayout layout = new GroupLayout(p);
        p.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup().addComponent(bvScrollPane, 420, 420, 420));

        layout.setVerticalGroup(layout.createSequentialGroup().addComponent(bvScrollPane, 400, 400, 400));

        switchPanel(p);
    }

    // Back button
    public void goBack() {
        if (backStack.size() > 1) {
            backStack.pop();
            JPanel newPanel = backStack.pop();
            switchPanel(newPanel);
        }
    }

    public void clearBackStack() {
        backStack.clear();
    }

    public void redrawFrame() {
        mainPanel.revalidate();
        mainPanel.repaint();
        buildLayout();
    }

    @Override
    public void refresh() {
        // mainPanel.revalidate();
        mainPanel.repaint();
    }

    @Override
    public String takeInputs() {
        if (bp == null) {
            return " ";
        }
        return bp.takeInputs();
    }

    @Override
    public void endGame(int nrOfLives, TOHallOfFameEntry hof) {
        if (mainPanel == inGamePanel) {
            inGamePanel.endGame(nrOfLives, hof);
        } else if (mainPanel == testerPanel) {
            testerPanel.setGameOver(true);
            mainPanel.repaint();
        }

        addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Block223Page.this.goBack();
                    Block223Page.this.removeKeyListener(this);
                }
            }
        });
    }
}

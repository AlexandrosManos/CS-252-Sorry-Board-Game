package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The BoardFrame class represents the main frame of the Sorry! gui game
 * It includes a menu bar with options for starting a new game, saving the game,
 * continuing a saved game, and exiting the application
 */
public class BoardFrame extends JFrame implements ActionListener {

    //Menu bar for the game
    JMenuBar menuBar;
    // Menu for starting a new game
    JMenu NewGame;
    //Menu item for starting a new game
    JMenuItem newGameItem;
    //Icon for the New Game menu item
    ImageIcon NewGameIcon;
    //Menu for saving the game
    JMenu SaveGame;
    //Menu item for saving the game
    JMenuItem saveItem;
    //Icon for the Save Game menu item
    ImageIcon SaveIcon;
    //Menu for continuing a saved game
    JMenu ContinueGame;
    //Menu item for continuing a saved game
    JMenuItem continueItem;
    //Icon for the Continue Saved Game menu item
    ImageIcon ContinueIcon;
    //Menu for exiting the game
    JMenu ExitGame;
    //Menu item for exiting the game
    JMenuItem exitItem;
    //Icon for the Exit Game menu item
    ImageIcon exitIcon;
    //Panel for displaying the card deck
    public CardsPanel cardPanel;
    //Panel for displaying the game board
    public MapPanel map;

    /**
     * Constructor for the BoardFrame class
     * @param n The number of players in the game
     * @param cardPanel The Card panel
     * @param map The map panel of the game
     */
    public BoardFrame(int n, CardsPanel cardPanel, MapPanel map){
        //Icons set
        NewGameIcon = new ImageIcon("images/Menu/NewGameIcon.png");
        SaveIcon = new ImageIcon("images/Menu/saveIcon.png");
        ContinueIcon = new ImageIcon("images/Menu/ContinueIcon.png");
        exitIcon = new ImageIcon("images/Menu/ExitIcon.png");
        ImageIcon feltIcon = new ImageIcon("images/background.png");

        // Background Panel
        JPanel felt = new JPanel();
        JLabel LabelFelt = new JLabel();
        LabelFelt.setIcon(feltIcon);
        felt.add(LabelFelt);
        felt.setBounds(0,0,1200,1000);

        // LayeredPane for organizing panels
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0,0,1200,900);
        layeredPane.add(felt, Integer.valueOf(0));
        this.cardPanel = cardPanel ;
        layeredPane.add(cardPanel, Integer.valueOf(1));
        this.map = map;
        layeredPane.add(map, Integer.valueOf(2));

        //Menu Bar
         menuBar = new JMenuBar();
            //New Game Menu
         NewGame = new JMenu("New Game");
        newGameItem = new JMenuItem("New Game");
        newGameItem.addActionListener(this);
        NewGame.add(newGameItem);
        newGameItem.setIcon(NewGameIcon);
            //Save Game Menu
         SaveGame = new JMenu("Save Game");
        saveItem = new JMenuItem("Save Game");
        saveItem.addActionListener(this);
        SaveGame.add(saveItem);
        saveItem.setIcon(SaveIcon);
            //Continue Saved Game Menu
        ContinueGame = new JMenu("Continue Saved Game");
        continueItem = new JMenuItem("Continue Saved Game");
        continueItem.addActionListener(this);
        ContinueGame.add(continueItem);
        continueItem.setIcon(ContinueIcon);
            //Exit menu
        ExitGame = new JMenu("Exit Game");
        exitItem = new JMenuItem("Exit");
        ExitGame.add(exitItem);
        exitItem.addActionListener(this);
        exitItem.setIcon(exitIcon);

        menuBar.add(NewGame);
        menuBar.add(SaveGame);
        menuBar.add(ContinueGame);
        menuBar.add(ExitGame);

        this.setJMenuBar(menuBar);
        this.add(layeredPane);
        this.setLayout(null);
        this.setTitle("Sorry Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(1150,800);
        ImageIcon windowIcon = new ImageIcon("images/cards/FrameIcon.png");
        this.setIconImage(windowIcon.getImage());//change icon of frame
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitItem){
            System.exit(0);
        }
    }
}

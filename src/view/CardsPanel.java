package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * The CardsPanel class represents the graphical user interface panel for managing card-related actions.
 * It includes buttons for receiving, current, folding, and reshuffling cards, as well as labels and text areas
 * to display information and card images.
 */
public class CardsPanel extends JPanel { //implements ActionListener

    // Button to receive a card.
    public JButton Receive;
    //Button to display the current card
    public JButton Current;
    //Button to fold the current hand
    public JButton  fold;
    //Button to reshuffle the deck
    public JButton reshuffle;

    //ImageIcon for the current card
    ImageIcon curr;
    //JLabel for displaying the current card text below the Current JButton
    JLabel CurrentLabel;
    //JLabel for displaying the received card text below the Current JButton
    JLabel ReceiveLabel;
    //JLabel for displaying the reshuffle label text below the Current JButton
    JLabel ReshuffleLabel;
    //Label representing the last card
    JLabel prevCard;
    //JTextArea for displaying information about the round
    JTextArea infoBox;
    //The path of the default card image
    private String ImagePath = "images/cards/backCard.png";

    /**
     * Constructor for the CardsPanel class
     * Sets up the panel with cards, fold, buttons and text areas
     */
    public CardsPanel(){
        //Initialization of components

        //path of images
        ImageIcon rec = new ImageIcon("images/cards/backCard.png");
        curr = new ImageIcon(ImagePath );

        //JLabels
        CurrentLabel = new JLabel("Current Card");
        CurrentLabel.setBounds(280,310,100,20);
        CurrentLabel.setForeground(Color.MAGENTA);
        CurrentLabel.setFont(new Font("serif",Font.BOLD,14));

        ReceiveLabel = new JLabel("Receive Card");
        ReceiveLabel.setForeground(Color.BLACK);
        ReceiveLabel.setFont(new Font("serif",Font.BOLD,14));
        ReceiveLabel.setBounds(120,310,100,20);

        prevCard = new JLabel();
        prevCard.setBounds(250, 100, 135, 200);
        prevCard.setVisible(false);

        ReshuffleLabel = new JLabel("Reshuffle");
        ReshuffleLabel.setForeground(new Color(0x00FF00));
        ReshuffleLabel.setFont(new Font("Comic Sans",Font.BOLD,15));
        ReshuffleLabel.setBounds(215,70,100,30);
        ReshuffleLabel.setVisible(false);

        //JButtons
        Receive = new JButton();
        Receive.setBounds(100, 100, 132, 200);
        Receive.setIcon(rec);
        Receive.setBorder(BorderFactory.createEtchedBorder());

        Current = new JButton();
        Current.setBounds(250, 100, 135, 200);
        Current.setIcon(curr);
        Current.setVisible(false);

        fold = new JButton();
        fold.setText("Fold Button");
        fold.setFocusable(false);
        fold.setBounds(100,350,285,50);
        fold.setBackground(Color.RED);
        fold.setFont(new Font("Comic Sans",Font.BOLD,15));
        fold.setBorder(BorderFactory.createCompoundBorder());

        reshuffle = new JButton();
        reshuffle.setBounds(200,10,100,65);
        reshuffle.setIcon(new ImageIcon("images/cards/shuffle.png"));
        reshuffle.setVisible(false);

        //JTextArea
        infoBox = new JTextArea();
        infoBox.setBounds(100,430,285,150);
        infoBox.setBackground(Color.white);
        infoBox.setBorder(new LineBorder(Color.BLACK, 2));
        infoBox.setFont(new Font("Cascadia Code", Font.BOLD, 18 ));

        infoBox.setEditable(false);
        // Set layout and position for components
        this.setLayout(null);
        this.setBounds(650,50,450,700);
        this.add(CurrentLabel);
        this.add(ReceiveLabel);
        this.add(ReshuffleLabel);
        this.add(fold);
        this.add(Receive);
        this.add(Current);
        this.add(reshuffle);
        this.add(infoBox);
        this.add(prevCard);
        this.setOpaque(false);
    }

    /**
     * Adds an ActionListener to a JButton
     * @param button The JButton to which the listener will be added
     * @param listener The ActionListener to be added
     */
    public void addCardsListener(JButton button, ActionListener listener)
    {
        button.addActionListener(listener);
    }

    /**
     * Changes the image of the current card
     * @param card The name of the card to be displayed
     */
    public void changeImage(String card)
    {
        ImagePath = "images/cards/"+card+".png";
        curr = new ImageIcon(ImagePath);
        Current.setIcon(curr);
    }

    class CardInfoWindow {
        /**
         * Constructor for CardInfoWindow
         * Displays an informational pop-up window about the drawn card
         * @param Info The information to be displayed in the window
         * @param Title The title of the window
         */
        CardInfoWindow(String Info, String Title)
        {
            JOptionPane.showMessageDialog(null,Info,Title,JOptionPane.INFORMATION_MESSAGE);
        }

    }
    /**
     * Sets the text in the information box
     * @param name The name of the player
     * @param color The color of the player
     * @param numOfCards Represents the number of remaining cards
     */
   public void setInfoText(String name, String color,int numOfCards)
    {
        infoBox.setText("Info Box\n\nTurn:"+name+"("+color+")\nCards Left: "+numOfCards);
    }
    /**
     * Displays an informational pop-up window
     * @param info  The information to be displayed
     * @param title The title of the window
     */
    public void InfoWindow(String info, String title)
    {
        new CardInfoWindow(info,title);
    }
    /**
     * Sets the visibility of the previous card and "moves" the current card
     */
    public void setPrevCard()
    {
        if (!Current.isVisible())
        {
            Current.setBounds(250, 100, 135, 200);
            prevCard.setVisible(false);
        }
        else{
            if (!prevCard.isVisible())
            {
                Current.setBounds(300, 100, 135, 200);
                prevCard.setVisible(true);
            }
            prevCard.setIcon(curr);
        }
    }
    /**
     * Hides the reshuffle button from the panel
     */
    public void removeReshuffle()
    {
        if (reshuffle.isVisible())
        {
            reshuffle.setVisible(false);
            ReshuffleLabel.setVisible(false);
            Current.setVisible(false);
            Receive.setVisible(true);
            Receive.setEnabled(true);
            setPrevCard();

        }
    }
    /**
     * Sets the reshuffle button to be visible.
     */
    public void setReshuffle()
    {
        if (!reshuffle.isVisible())
        {
            Receive.setVisible(false);
            reshuffle.setVisible(true);
            ReshuffleLabel.setVisible(true);

        }
    }

    /**
     * Pop-up a CANCEL_OPTION window, if the game was canceled
     */
    public static void CancelGame()
    {
        JOptionPane.showMessageDialog(null,"Game Was Canceled",
                "Game Status",JOptionPane.CANCEL_OPTION);
    }


}

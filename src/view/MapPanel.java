package view;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;


/**
 * The MapPanel class represents the gui panel for the "map" of the game
 * It includes all the squares, pawns of the game
 */
public class MapPanel extends JLayeredPane{

    //An array representing the game board Simple squares
    public JButton[][] squares;
    private final int Width =41; //squares width
    private final int Height = 41; //squares width
    //An array representing player's squares (SafetySquares)
    public JButton[][] playerSquares;
    //Labels representing home areas for each player
    private JLabel HomeArea[], StartArea[];
    //Buttons representing home positions for each player
    public JButton HomePosition[][], StartPosition[][];
    private final int HomeWidth =88; //squares width
    private final int HomeHeight = 84; //squares width
    //An array with the images for slides
    private ImageIcon[][] Slides;
    // Selected pawn button
    public JButton selectedPawn;
    //Main board panel
    JPanel board;
    //"Glass" pane for additional
    JPanel glassPane;

    //An array representing pawn buttons
    public JButton[][] Pawns;

    //Number of players in the game
    final int NumOfPlayers;

    /**
     * Constructor for the MapPanel class
     * @param n The number of players in the game
     */
    public MapPanel(int n){
        //Initialization of components
        NumOfPlayers = n;
        ImageIcon sorryIcon;
        sorryIcon = new ImageIcon("images/sorryImage.png");
        JLabel SorryLabel = new JLabel();
        SorryLabel.setBounds(230,300,200,58);
        SorryLabel.setIcon(sorryIcon);

        //squares
        squares = new JButton[4][15];
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j< 15; j++)
            {
                squares[i][j] = new JButton();
                if (i == 0){
                    squares[i][j].setBounds(0+j*Width,0,Width,Height);
                }
                else if(i == 1)
                {
                    squares[i][j].setBounds(0+15*Width,0+j*Height,Width,Height);
                }
                else if(i == 2)
                {
                    squares[i][j].setBounds(0+Width*(15-j),0+15*Height,Width,Height);
                }
                else
                {
                    squares[i][j].setBounds(0,0+(15-j)*Width,Width,Height);
                }
                squares[i][j].setBackground(Color.white);
                squares[i][j].setBorder(new LineBorder(Color.BLACK, 2));
                this.add(squares[i][j]);
            }
        }


        this.setLayout(null);
        this.setBounds(0,5,657,657);

        //Creating the glass Pane
        glassPane = new JPanel();
        glassPane.setLayout(null);
        glassPane.setBounds(0,0,657,657);
        glassPane.setOpaque(false);
        board = new JPanel();
        board.setLayout(null);
        board.setBounds(0,5,657,657);
        board.setOpaque(true);
        board.add(SorryLabel);
        board.setBackground(Color.CYAN);
        board.setBorder(BorderFactory.createLineBorder(Color.black));


        addSlides();
        HomeStart();
        PawnsInit();

        this.add(board,Integer.valueOf(0));
        this.add(glassPane, Integer.valueOf(1));

    }
    /**
     * Corresponds images to slides
     */
    private void addSlides()
    {
        String color="",part="";
        int i,j;
        Slides = new ImageIcon[4][3];//4 colors, 3 parts
        for (i = 0; i < 4; i++)
        {
            switch (i) {
                case 0:
                    color = "red";
                    break;
                case 1:
                    color = "blue";
                    break;
                case 2:
                    color = "yellow";
                    break;
                case 3:
                    color = "green";
            }
            for (j = 0; j < 3; j++)
            {
                switch (j){
                    case 0:
                        part = "Start";
                        break;
                    case 1:
                        part = "Medium";
                        break;
                    case 2:
                        part = "End";
                        break;
                }
                Slides[i][j] = new ImageIcon("images/slides/"+color+"Slide"+part+".png");
            }
        }
        for (i = 0; i < 4; i++)
        {
            for (j = 0; j < 3; j++)
            {
                switch (j){
                    case 0:
                        squares[i][1].setIcon(Slides[i][j]);
                        squares[i][9].setIcon(Slides[i][j]);
                        break;
                    case 1:
                        squares[i][2].setIcon(Slides[i][j]);
                        squares[i][3].setIcon(Slides[i][j]);
                        squares[i][10].setIcon(Slides[i][j]);
                        squares[i][11].setIcon(Slides[i][j]);
                        squares[i][12].setIcon(Slides[i][j]);
                        break;
                    case 2:
                        squares[i][4].setIcon(Slides[i][j]);
                        squares[i][13].setIcon(Slides[i][j]);
                        break;
                }
            }
        }

    }

    /**
     * Initializes players' home and start areas on the board
     */
    private void HomeStart()
    {
        HomeArea = new JLabel[NumOfPlayers];
        StartArea = new JLabel[NumOfPlayers];
        HomePosition = new JButton[NumOfPlayers][2];
        StartPosition = new JButton[NumOfPlayers][2];
        playerSquares = new JButton[5][NumOfPlayers];
        for(int j = 0; j< NumOfPlayers; j++)
        {
            StartArea[j] = new JLabel();
            StartArea[j].setBackground(Color.white);
            StartArea[j].setOpaque(true);
            StartArea[j].setBorder(new LineBorder(ColorConverter(j), 3));
            StartArea[j].setFont(new Font("Comic Sans MS", Font.BOLD, 20 ));
            StartArea[j].setText("Start");
            StartArea[j].setHorizontalAlignment(JLabel.CENTER);

            HomeArea[j] = new JLabel();
            HomeArea[j].setBackground(Color.white);
            HomeArea[j].setOpaque(true);
            HomeArea[j].setBorder(new LineBorder(ColorConverter(j), 3));
            HomeArea[j].setFont(new Font("Comic Sans MS", Font.BOLD, 20 ));
            HomeArea[j].setText("Home");
            HomeArea[j].setHorizontalAlignment(JLabel.CENTER);


            switch (j){
                case 0:
                    StartArea[j].setVerticalAlignment(JLabel.BOTTOM);
                    StartArea[j].setBounds(squares[0][3].getX()+20,squares[1][1].getY()-5,HomeWidth,HomeHeight);

                    HomeArea[j].setVerticalAlignment(JLabel.BOTTOM);
                    HomeArea[j].setBounds(squares[0][2].getX()-24,squares[3][9].getY()-5,HomeWidth,HomeHeight);
                    break;
                case 1: //yellow
                    StartArea[j].setVerticalAlignment(JLabel.TOP);
                    StartArea[j].setBounds(squares[2][5].getX()+20,squares[3][2].getY()-7,HomeWidth,HomeHeight);

                    HomeArea[j].setVerticalAlignment(JLabel.TOP);
                    HomeArea[j].setBounds(squares[2][2].getX()-24,squares[1][8].getY()-6,HomeWidth,HomeHeight);
                    break;
                case 2: //blue
                    StartArea[j].setVerticalAlignment(JLabel.BOTTOM);
                    StartArea[j].setBounds(squares[0][13].getX()-6,squares[1][3].getY()+20,HomeWidth,HomeHeight);


                    HomeArea[j].setVerticalAlignment(JLabel.BOTTOM);
                    HomeArea[j].setBounds(squares[0][8].getX()-6,squares[1][2].getY()-20,HomeWidth,HomeHeight);
                    break;
                case 3:
                    StartArea[j].setVerticalAlignment(JLabel.TOP);
                    StartArea[j].setBounds(squares[2][14].getX(),squares[3][5].getY()+20,HomeWidth,HomeHeight);

                    HomeArea[j].setVerticalAlignment(JLabel.TOP);
                    HomeArea[j].setBounds(squares[2][9].getX(),squares[3][2].getY()-22,HomeWidth,HomeHeight);
                    break;

            }

            for (int i = 0; i < 5; i++)
            {
                playerSquares[i][j] = new JButton();
                if (j == 0){ //red
                    playerSquares[i][j].setBounds(2*Width,0+(i+1)*Height-5,Width,Height);
                }
                else if(j == 1) //yellow
                {
                    playerSquares[i][j].setBounds(Width*13,-4+(14-i)*Height,Width,Height);

                }
                else if(j == 2) // blue
                {
                    playerSquares[i][j].setBounds(0+(14-i)*Width,0+2*Height,Width,Height);

                }
                else //green
                {
                    playerSquares[i][j].setBounds(0+(i+1)*Width,0+Height*13 ,Width,Height);
                }
                board.add(playerSquares[i][j]);
                playerSquares[i][j].setBackground(ColorConverter(j));
                playerSquares[i][j].setBorder(new LineBorder(Color.BLACK, 2));
            }
            board.add(StartArea[j]);
            board.add(HomeArea[j]);
        }



        }

    /**
     * Initializes pawn
     */
    private void PawnsInit(){
        Pawns = new JButton[NumOfPlayers][2];
        for (int j = 0; j < NumOfPlayers; j++)
        {
            for (int i = 0; i < 2; i++)
            {
                HomePosition[j][i] = new JButton();
                HomePosition[j][i].setIcon(new ImageIcon("images/pawns/num"+Integer.toString(1+i)+".png"));
                HomePosition[j][i].setFocusable(false);
                HomePosition[j][i].setBackground(Color.white);

                StartPosition[j][i] = new JButton();
                StartPosition[j][i].setIcon(new ImageIcon("images/pawns/num"+Integer.toString(1+i)+".png"));
                StartPosition[j][i].setBackground(Color.white);
                StartPosition[j][i].setFocusable(false);

                Pawns[j][i] = new JButton();
                Pawns[j][i].setIcon(new ImageIcon("images/pawns/"+StringColor(j)+"Pawn" +(i+1)+".png"));
                Pawns[j][i].setBorder(null);

                if (j % 2 == 0) //red and blue
                {
                    Pawns[j][i].setBounds(StartArea[j].getX()+6+Width*i ,StartArea[j].getY()+14,
                            Width-4,Height-4);
                    StartPosition[j][i].setBounds(StartArea[j].getX()+6+Width*i ,StartArea[j].getY()+9,
                            Width-4,Height-4);
                    HomePosition[j][i].setBounds(HomeArea[j].getX()+6+Width*i ,HomeArea[j].getY()+14,
                            Width-4,Height-4);
                }
                else
                {
                    Pawns[j][i].setBounds(StartArea[j].getX()+6+Width*i ,StartArea[j].getY()+35,
                            Width-4,Height-4);
                    StartPosition[j][i].setBounds(StartArea[j].getX()+6+Width*i ,StartArea[j].getY()+30,
                        Width-4,Height-4);
                    HomePosition[j][i].setBounds(HomeArea[j].getX()+6+Width*i ,HomeArea[j].getY()+30,
                            Width-4,Height-4);
                }

                board.setComponentZOrder(StartPosition[j][i], 0);
                board.setComponentZOrder(HomePosition[j][i], 0);


                glassPane.add(Pawns[j][i]);
                glassPane.setComponentZOrder(Pawns[j][i], 0);
            }

        }

    }

    /**
     * Converts player index to corresponding color
     * @param j The player index
     * @return The corresponding color
     */
    private Color ColorConverter(int j)
    {
        Color color = null;

        switch (j){
            case 0:
                color = Color.red;
                break;
            case 1:
                color= Color.yellow;
                break;
            case 2:
                color = Color.blue;
            break;
            case 3:
                color = Color.green;
                break;
        }
        return color;
    }

    /**
     * Adds an ActionListener to a JButton
     * @param button The JButton to which the listener will be added
     * @param listener The ActionListener to be added
     */
    public void addListener(JButton button, ActionListener listener)
    {
        button.addActionListener(listener);
    }
    /**
     * Converts player index to corresponding color
     * @param j The player index
     * @return A String with the corresponding color name
     */
    private String StringColor(int j) {
        String color = "";
        switch (j) {
            case 0:
                color = "red";
                break;
            case 1:
                color = "yellow";
                break;
            case 2:
                color = "blue";
                break;
            case 3:
                color = "green";
        }
        return color;
    }

    /**
     * Moves a pawn to a specified square
     * @param Pawn The pawn button to be moved
     * @param Square The target square button
     */
    public void MovePawn(JButton Pawn, JButton Square)
    {
        if (Square == null || Pawn == null)
            return;
        if (MatchButton(Square,StartPosition) || MatchButton(Square,HomePosition))
        {
            Pawn.setBounds(Square.getX() ,Square.getY()+5,
                    Width-4,Height-4);
        } else if (MatchButton(Square,playerSquares)) {
            Pawn.setBounds(Square.getX()+2 ,Square.getY()+7,
                    Width-4,Height-4);
        } else
        {
            Pawn.setBounds(Square.getX()+2 ,Square.getY()+2,
                    Width-4,Height-4);
        }

    }
    /**
     * Checks if a button matches any button from the given array
     * @param selectedButton The selected button to check for a match
     * @param array An array of buttons
     * @return True if selected buttons belongs to the given array, otherwise false
     */
    public boolean MatchButton(JButton selectedButton, JButton[][] array){

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
              if (selectedButton == array[i][j])
                    return true;
            }
        }
        return false;
    }


}

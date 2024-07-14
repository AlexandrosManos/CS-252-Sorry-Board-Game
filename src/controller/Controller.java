package controller;

import model.*;
import view.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Controller {

    private Board board;

    private Deck deck;

    private String currentCard;

    private CardsPanel cardsPanel;
    private MapPanel mapPanel;

    /**
     * Number of registered players
     * @inv when the initialization ends, registered must be equal to NumberOfPlayers
     */
    private int registered = 0;

    BoardFrame boardFrame;
    private Cards drawn = null;


    /**
     * Constructor for the controller
     */
    public Controller(){
        int i,j;
        try {
            deck = new Deck(new DialogPlayers().n);
        }catch (Exception e)
        {
            CardsPanel.CancelGame();
            System.exit(-1);
        }

        board = deck.InitBoard();
        for(i =0; i < board.getNumberOfPlayers(); i++)
        {
            board.addName(PlayersName.fun(i));
        }
        board.CreatePlayerList(board.getNumberOfPlayers());
        board.InitSquares();
        board.InitPawn();
        cardsPanel = new CardsPanel();
        Player curr = board.players.get((board.getPlaying()));

        cardsPanel.setInfoText
                (curr.getName(),curr.getColor().toString(),deck.getLeftCards());
        mapPanel = new MapPanel(board.getNumberOfPlayers());
        boardFrame = new BoardFrame(board.getNumberOfPlayers(), cardsPanel,mapPanel);

        //adding Action Listeners
        cardsPanel.addCardsListener(cardsPanel.Current,new CardsListener());
        cardsPanel.addCardsListener(cardsPanel.Receive,new CardsListener());
        cardsPanel.addCardsListener(cardsPanel.fold,new CardsListener());
        cardsPanel.addCardsListener(cardsPanel.reshuffle,new CardsListener());

        for (i = 0; i < mapPanel.Pawns.length; i++)
        {
            for (j =0; j < mapPanel.Pawns[i].length; j++)
            {
                mapPanel.addListener(mapPanel.Pawns[i][j], new PawnListener());
            }
        }
        for (j = 0; j <board.getNumberOfPlayers(); j++)
        {
            for (i =0; i < 2; i++)
            {
                mapPanel.addListener(mapPanel.HomePosition[j][i], new PawnListener());
                mapPanel.addListener(mapPanel.StartPosition[j][i], new PawnListener());
            }
        }
        for (i = 0; i < mapPanel.squares.length; i++)
        {
            for (j =0; j < mapPanel.squares[i].length; j++)
            {
                mapPanel.addListener(mapPanel.squares[i][j], new PawnListener());

            }
        }
        for (i = 0; i < mapPanel.playerSquares.length; i++)
        {
            for (j =0; j < mapPanel.playerSquares[i].length; j++)
            {
                mapPanel.addListener(mapPanel.playerSquares[i][j], new PawnListener());

            }
        }

    }

    /**
     * Resets the board and the deck and starts a new game
     */

    public void NewGame(){}

    /**
     * Check if there is a winner
     * @return the name of the winner
     */
    public void getWinner(){
        if (board.winner())
        {
            String info = "Player " + board.getPlayer().getName() + "("+board.getPlayer().getColor().toString()
                +")";
            cardsPanel.InfoWindow(info,"Winner");
            System.exit(1);
        }
    }

    //Action Listener

    class CardsListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            cardsPanel.fold.setEnabled(true);
            if (mapPanel.selectedPawn != null)
            {
                mapPanel.selectedPawn.setBorder(null);
                mapPanel.selectedPawn = null;
            }

            reload();
            if(e.getSource()==cardsPanel.Receive) {
                cardsPanel.setPrevCard();
                drawn = deck.giveCard();
                currentCard = drawn.IntToName();
                //convert Card nto proper name
                cardsPanel.changeImage(currentCard);
                if (!board.CanRedraw(drawn))
                    cardsPanel.Receive.setEnabled(false);
                if (!cardsPanel.Current.isVisible())
                {
                    cardsPanel.Current.setVisible(true);
                }
                if (deck.OutOfCards())
                {
                    cardsPanel.Receive.setVisible(false);
                }
            }else if(e.getSource()==cardsPanel.Current) {
                if (drawn != null)
                    cardsPanel.InfoWindow(drawn.getDescription(),"Card Info" );
            }else if (e.getSource() == cardsPanel.fold){
                 if (board.Fold(drawn)){
                     if (deck.OutOfCards())
                     {
                         cardsPanel.setReshuffle();
                     }else
                     {
                         cardsPanel.Receive.setEnabled(true);
                     }
                    board.nextPlayer(drawn);
                }
                else{
                    cardsPanel.InfoWindow("You can not fold","Fold Button");
                    cardsPanel.fold.setEnabled(false);
                }
            }else if (e.getSource() == cardsPanel.reshuffle)
            {
                cardsPanel.removeReshuffle();
                deck.InitCards();

            }
            cardsPanel.setInfoText(board.getPlayer().getName(),
                    board.getPlayer().getColor().toString(),deck.getLeftCards());

        }
    }
    class PawnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton selectedButton = (JButton) e.getSource();
            Boolean success = false;
            if (mapPanel.MatchButton(selectedButton,mapPanel.Pawns))//selectedButton is Pawn
            {
                if (ButtonToPawn(selectedButton).getState() != 2)//Pawn is active
                {
                    //player has chosen their own pawn
                     if (selectedButton == mapPanel.Pawns[board.getPlaying()][0]
                        ||selectedButton == mapPanel.Pawns[board.getPlaying()][1])
                    {
                        if(mapPanel.selectedPawn != null || selectedButton == mapPanel.selectedPawn)
                        {
                            mapPanel.selectedPawn.setBorder(null);
                            mapPanel.selectedPawn = null;
                            if (selectedButton != mapPanel.selectedPawn)
                            {
                                mapPanel.selectedPawn = selectedButton;
                                mapPanel.selectedPawn.setBorder(new LineBorder(new Color(0x00FF00),2));
                                //highlight
                            }
                        }else
                        {
                            mapPanel.selectedPawn = selectedButton;
                            mapPanel.selectedPawn.setBorder(new LineBorder(new Color(0x00FF00),2));
                            //highlight
                        }
                    } else
                    {
                        if (mapPanel.selectedPawn != null)
                        {
                            Pawn opponent = ButtonToPawn(selectedButton);
                            success = board.MovePawn(ButtonToPawn(mapPanel.selectedPawn),
                                    opponent.getSquare(),drawn,opponent);
                            if (opponent!= null && success) {
                                if (board.DoSwap(drawn))
                                    opponent.returnToStart(board);
                                reload();
                                if (board.Finished(drawn))
                                    board.nextPlayer(drawn);
                                mapPanel.selectedPawn.setBorder(null);
                                mapPanel.selectedPawn = null;


                           }
                        }

                    }
                }

            }
            //selectedButton is a square button
            else
            {
                if (mapPanel.selectedPawn != null)
                {
                    success = board.MovePawn(ButtonToPawn(mapPanel.selectedPawn),
                            ButtonToSquare(selectedButton), drawn, null);
                    if (success) //move was successful
                    {
                        reload();
                        if (board.Finished(drawn))
                        {
                            board.nextPlayer(drawn);
                            drawn = null;

                        }
                        mapPanel.selectedPawn.setBorder(null);
                        mapPanel.selectedPawn = null;
                    }
                }

            }
            if (success)
            {
                if(board.Finished(drawn))
                {
                    cardsPanel.Receive.setEnabled(true);
                    drawn = null;
                }
                cardsPanel.fold.setEnabled(true);
                if (deck.OutOfCards())
                {
                    cardsPanel.setReshuffle();
                }
            }
            if (deck.OutOfCards() && board.CanRedraw(drawn))
            {
                cardsPanel.setReshuffle();
            }
            cardsPanel.setInfoText(board.getPlayer().getName(),
                    board.getPlayer().getColor().toString(),deck.getLeftCards());
            getWinner();
        }
    }
    private void reload()
    {
        for (int i =0; i < board.getNumberOfPlayers(); i++)
        {
            for (int j = 0; j <2; j++)
            {
                Pawn toMove = board.players.get(i).getPawn(j);
                Square square = toMove.getSquare();
                int x = square.getX(),y = square.getY();
                JButton pawn = mapPanel.Pawns[i][j], toGo = null;
                switch (toMove.getState()){
                    case 0:
                        toGo = mapPanel.StartPosition[x][y];
                        break;
                    case 1:
                        toGo = mapPanel.squares[x][y];
                        break;
                    case 2:
                        toGo = mapPanel.HomePosition[x][y];
                        break;
                    case 3:
                        toGo = mapPanel.playerSquares[x][y];
                        break;
                    default:
                        break;
                }
                mapPanel.MovePawn(pawn,toGo);
            }

        }
    }
    private Square ButtonToSquare(JButton square)
    {
        if (mapPanel.MatchButton(square,mapPanel.StartPosition))
        {
            for (int i = 0; i < mapPanel.StartPosition.length; i++)
            {
                for (int j=  0; j < mapPanel.StartPosition[i].length; j++)
                {
                    if (square == mapPanel.StartPosition[i][j])
                        return board.startSquares[i][j];
                }
            }
        } else if (mapPanel.MatchButton(square, mapPanel.HomePosition)) {
            for (int i = 0; i < mapPanel.HomePosition.length; i++)
            {
                for (int j=  0; j < mapPanel.HomePosition[i].length; j++)
                {
                    if (square == mapPanel.HomePosition[i][j])
                        return board.homeSquares[i][j];
                }
            }
        }else if (mapPanel.MatchButton(square, mapPanel.playerSquares))
        {
            for (int i = 0; i < mapPanel.playerSquares.length; i++)
            {
                for (int j=  0; j < mapPanel.playerSquares[i].length; j++)
                {
                    if (square == mapPanel.playerSquares[i][j])
                        return board.playerSquares[i][j];
                }
            }
        }else {
            for (int i = 0; i < mapPanel.squares.length; i++)
            {
                for (int j=  0; j < mapPanel.squares[i].length; j++)
                {
                    if (square == mapPanel.squares[i][j])
                        return board.squares[i][j];
                }
            }
        }
        return null;
    }
    private Pawn ButtonToPawn(JButton pawn)
    {
        for (int i =0; i < board.getNumberOfPlayers(); i++)
        {
            for (int j =0; j < 2; j++)
            {
                if (pawn.equals(mapPanel.Pawns[i][j]))
                    return board.players.get(i).getPawn(j);
            }
        }
        return null;
    }


}

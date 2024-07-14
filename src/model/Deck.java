package model;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.Delayed;

import static model.NumElevenCard.*;

public class Deck  {
    /**
     * An array list with the available cards
     */
    private Stack<Cards> cards;

    /**
     * The number of players
     */
    private final int NumberOfPlayers;

    int[] leftCards = new int[11];



    /**
     * Constructor for the card deck
     */
    public Deck(int n) throws IllegalArgumentException{
            if (n < 2 || n >4)
                throw new NumberOfPlayersException();
        NumberOfPlayers = n;
        cards = new Stack<Cards>();
        InitCards();
    }

    /**
     * Initializes the cards and push them in a specific order
     */
    public void InitCards() {
        Random random = new Random();
        int randomNumber, i;
        for (i = 0; i < 11; i++)
        {
            leftCards[i] = 4;
        }
        for (i = 0; i < 44; i++)
        {
            do {
                randomNumber = random.nextInt(11);
            }while (leftCards[randomNumber] == 0);
            leftCards[randomNumber]--;
            cards.push(NumToCard(randomNumber));
        }
    }

    /**
     * Creates and initialize a board, based on the number of players
     * @return the board
     */
    public Board InitBoard(){
        Board board = new Board(NumberOfPlayers);
        return board;
    }


    /**
     * Getter for the top card of the deck
     * @return the top card of the stack
     */
    public Cards giveCard(){
        if (OutOfCards())
        {
            return null;
        }
        return cards.pop();
    }

    /**
     * Observer, checks if the card deck is empty
     * @return true if the card deck is empty
     */
    public boolean OutOfCards(){return cards.isEmpty();}

    /**
     * Gets the number of remaining cards
     * @return The number of cards left in the deck
     */
    public int getLeftCards() {return cards.size();}

}

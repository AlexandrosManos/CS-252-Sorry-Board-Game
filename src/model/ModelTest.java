package model;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Arrays;


public class ModelTest {
    ArrayList<Player> players;
    Deck deck;
    Board board;
    public void TestIntro(){
        deck = new Deck(2);
        board = deck.InitBoard();
        board.addName("Player 1");
        board.addName("Player 2");
        board.CreatePlayerList(board.getNumberOfPlayers());
        board.InitSquares();
        board.InitPawn();


    }

    @org.junit.Test
    @Test
    public void testPlayers(){
        TestIntro();
        for (int i =0; i < 2; i++)
        {
            Assert.assertEquals(board.players.get(i).getName(), ("Player "+ (i+1)));
            Assert.assertEquals(board.players.get(i).getPawn(0).getNumber(), 0);
            Assert.assertEquals(board.players.get(i).getPawn(1).getNumber(), 1);

        }
        Assert.assertEquals(board.players.get(0).getColor(), GameColors.RED);
        Assert.assertEquals(board.players.get(1).getColor(), GameColors.YELLOW);


    }

    @org.junit.Test
    @Test
    public void testSquares(){
        TestIntro();
        Assert.assertEquals(board.squares.length, 4);
        for (int i =0; i < board.squares.length; i++)
        {
            GameColors color = null;
            switch (i) {
                case 0:
                    color = GameColors.RED;
                    break;
                case 1:
                    color = GameColors.BLUE;
                    break;
                case 2:
                    color = GameColors.YELLOW;
                    break;
                case 3:
                    color = GameColors.GREEN;
                    break;
            }
            Assert.assertEquals(board.squares[i].length, 15);
            for (int j = 0; j < board.squares[i].length; j++)
            {
                if ((j >= 1 && j <=4) || (j >=9 && j<= 13))
                {
                    Assert.assertEquals(board.squares[i][j].getColor(), color);
                }else
                {
                    Assert.assertNull(board.squares[i][j].getColor());
                }
            }
        }

    }

    @org.junit.Test
    @Test
    public void testCards()
    {
        TestIntro();
        int counter = 44;
        Assert.assertFalse(deck.OutOfCards());
        System.out.println();
        while (!deck.OutOfCards())
        {
            Assert.assertNotNull(deck.giveCard());
            counter--;
            Assert.assertEquals(counter, deck.getLeftCards());
        }
        Assert.assertTrue(deck.OutOfCards());
        Assert.assertEquals(deck.getLeftCards(), 0);
    }

}

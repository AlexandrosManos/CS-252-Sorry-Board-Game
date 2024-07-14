package model;

import java.util.ArrayList;


public class Board {

    /**
     * The number of players
     */
    private int NumberOfPlayers;

    /**
     *  A list for the registered players
     * @inv the size of this list must be equals to NumberOfPlayers
     */
    public ArrayList<Player> players;

    /**
     * List of player names registered for the game.
     */
    private ArrayList<String>names;

    /**
     * Represents the index of the player who is currently playing
     */
    private int playing = 0;

    /**
     * Array for Board's squares
     */

    public Square[][] squares;

    /**
     * An array representing the home squares of each player
     */
    public HomeSquare[][] homeSquares;
    /**
     * An array representing the start squares of each player
     */
    public StartSquare[][] startSquares;
    /**
     * An array representing the Safety squares of each player
     */
    public PlayerSquare[][] playerSquares;
    //note it's an array 5 x n, which n represents the number of players


    /**
     * Default Constructor for the board
     */
    public Board(int NumberOfPlayers){
         setNumberOfPlayers(NumberOfPlayers);
        players = new ArrayList<Player>();
        names = new ArrayList<String>();

    }

    public void addName(String name) {
        names.add(name);
    }

    /**
     * Creates a list of players
     * @param n the number of registered players
     * @pre n is less or equal to 4 and greater or equal to 2
     */

    public void CreatePlayerList(int n)
    {
        if (n != names.size())
            throw new IllegalArgumentException();
        players = new ArrayList<Player>(n);
        for (int i = 0; i < n; i++)
        {
            RegisterPlayer(names.get(i),i);
        }
    }
    /**
     * Initialize the color and pawns of the player with the given name
     * add player to the list
     * @param name the name of the player
     */
    public void RegisterPlayer(String name, int registered){
        GameColors color = chooseColor(registered);
        Player toAdd= new Player(name,color);
        players.add(toAdd);
    }

    /**
     * @return a color based on registration number of the player
     */
    private GameColors chooseColor(int registered){
        switch (registered){
            case 0:
                return GameColors.RED;
            case 1:
                return GameColors.YELLOW;
            case 2:
                return GameColors.BLUE;
            case 3:
                return GameColors.GREEN;
            default:
                return null;
        }
    }

    /**
     * Initializes player's Pawn
     * @return the Pawn
     */
    public void InitPawn(){
        for (int i =0; i < NumberOfPlayers; i++)
        {
            for (int j = 0; j < 2; j++)
            {
                Square start = startSquares[i][j];
                Pawn toAdd = new Pawn(players.get(i).getColor(),start,j);
                players.get(i).setPawn(j,toAdd);
            }
        }
    }

    /**
     * Setter for the number of players
     * @param n int number of players
     */

    public void setNumberOfPlayers(int n){
        try {
            if (n < 2 || n >4)
                throw new NumberOfPlayersException();
        }catch (Exception e)
        {
            System.err.println(e.getMessage());
            System.exit(-1);
        }

       NumberOfPlayers = n;
    }

    /**
     * Getter for the number of players
     * @return the number of the registered players
     */
    public int getNumberOfPlayers(){return NumberOfPlayers;}

    /**
     * Getter for the playing
     * @return whose turn is to play next
     */
    public int getPlaying(){return playing;}

    /**
     * Setter for the playing, next one in list
     */
    public void nextPlayer(Cards card){
        if (card != null && card instanceof NumTwoCard )
            return;
        if (card != null && card instanceof NumThreeCard
                ||card instanceof NumFiveCard ||card instanceof NumSevenCard  )
        {
            if (card.CanUse(this, this.getPlaying()))
                return;
        }
        if (playing == NumberOfPlayers-1)
            playing = 0;
        else
            playing++;
    }

    /**
     * Checks for a winner
     * @return true if the current player has both of their pawns in the Home position
     */
    public boolean winner(){
       Player player = players.get(playing);
       if (player.getPawn(0).getState() == 2 && player.getPawn(1).getState() == 2)
       {
           return true;
       }
       return false;
    }

    /**
     * Initialize the squares of the board
     */
    public void InitSquares()
    {
        //initialize squares
        squares = new Square[4][15];
        for (int i =0; i< squares.length; i++)
        {
            GameColors color = null;
            switch (i){
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
            squares[i] = new Square[15];
            for (int j =0; j < squares[i].length; j++)
            {
                if (j == 1 || j == 9)//slide start
                {
                    squares[i][j]= new StartSlideSquare(i,j, color);
                    ((StartSlideSquare) squares[i][j]).setEnd(13);
                    if (j == 1)
                      ((StartSlideSquare) squares[i][j]).setEnd(4);
                } else if (j == 2 ||j == 3 || j > 9 && j < 13 ) { //Medium slide
                    squares[i][j] = new InternalSlideSquare(i,j, color);
                } else if (j == 4 || j ==13) { //end slide
                    squares[i][j] = new EndSlideSquare(i,j, color);
                }else { //regular square
                    squares[i][j] = new SimpleSquare(i,j, null);
                }
            }

        }

        homeSquares = new HomeSquare[NumberOfPlayers][2];
        startSquares = new StartSquare[NumberOfPlayers][2];
        playerSquares = new PlayerSquare[5][NumberOfPlayers];
        for (int i =0; i < NumberOfPlayers; i++)
        {
            GameColors color = null;
            Player player = players.get(i);
            switch (i){
                case 0:
                    color = GameColors.RED;
                    break;
                case 2:
                    color = GameColors.BLUE;
                    break;
                case 1:
                    color = GameColors.YELLOW;
                    break;
                case 3:
                    color = GameColors.GREEN;
                    break;
            }
            for (int j = 0; j < 2; j++)
            {
                homeSquares[i][j] = new HomeSquare(i,j,color);
                startSquares[i][j] = new StartSquare(i,j,color);
                startSquares[i][j].setPawn(player.getPawn(0));
            }
            for (int j = 0; j <5 ; j++) {
                playerSquares[j][i] = new SafetyZoneSquare(j, i, color);
            }
        }


    }

    /**
     * Getter for the current player
     * @return The player who is currently playing
     */
    public Player getPlayer(){return players.get(playing);}

    /**
     * Moves the specified pawn to the given square based on the provided card
     * Handles different types of card movements
     * @param pawn The pawn to be moved
     * @param square The target square for the pawn, null if the player choose other pawn
     * @param card The card used for the movement
     * @param enemy The enemy pawn involved in the move, null if the player choose square to go to
     * @return true if the movement was successful, false otherwise
     */
    public boolean MovePawn(Pawn pawn, Square square, Cards card, Pawn enemy)
    {
        boolean state = false;
        if (pawn == null || square == null)
            return false;
        if(square instanceof HomeSquare
                || square instanceof StartSquare
                ||  square instanceof SafetyZoneSquare )
        {
            if (!pawn.getColor().equals(square.getColor()))
                return false;
        }


        if (card instanceof SorryCard)
        {
            state = ((SorryCard)card).SorryMove(pawn,enemy,this);
        }
        else if (card instanceof NumOneCard)
        {
            state = ((NumOneCard)card).OneMove(pawn,square,this);
        } else if (card instanceof NumTwoCard) {
            state = ((NumTwoCard)card).TwoMove(pawn,square,this);

        } else if (card instanceof NumThreeCard) {
            state = ((NumThreeCard)card).ThreeMove(pawn,square,this);

        } else if (card instanceof NumFourCard) {
            state = ((NumFourCard)card).FourMove(pawn,square,this);

        }else if (card instanceof NumFiveCard) {
            state = ((NumFiveCard)card).FiveMove(pawn,square,this);

        }else if (card instanceof NumSevenCard) {
            state = ((NumSevenCard)card).SevenMove(pawn,square,this);

        }else if (card instanceof NumEightCard) {
            state = ((NumEightCard)card).EightMove(pawn,square,this);

        }else if (card instanceof NumTenCard) {
            state = ((NumTenCard)card).TenMove(pawn,square,this);

        }else if (card instanceof NumElevenCard) {
            state = ((NumElevenCard)card).ElevenMove(pawn,enemy,square,this);

        }else if (card instanceof NumTwelveCard) {
            state = ((NumTwelveCard)card).TwelveMove(pawn,square,this);

        }
        if (pawn.getSquare() instanceof StartSlideSquare && !pawn.getColor().equals(pawn.getSquare().getColor()))
        {
            int x = pawn.getSquare().getX(), end =((StartSlideSquare) pawn.getSquare()).getSEnd();
            for (int i =pawn.getSquare().getY()+1; i <= end ; i++)
            {
                Pawn PawnOnSlide = squares[x][i].getPawn();
                if (PawnOnSlide != null)
                    System.out.println(PawnOnSlide.getColor());
                if (PawnOnSlide != null)
                {
                    PawnOnSlide.returnToStart(this);
                }

            }
            pawn.setSquare(squares[x][end]);

        }
        return state;
    }

    /**
     * Checks if the player can fold based on the given card
     * @param card The card to be checked
     * @return true if the player can fold, false otherwise
     */
    public boolean Fold(Cards card){
        if (card == null)
            return false;
        return !card.CanUse(this, playing);
    }
    /**
     * Determines the next square for a pawn based on the current square and the number of steps to move
     * @param square The current square of the pawn
     * @param Next The number of steps to move
     * @param pawn The pawn moving
     * @return The next square for the pawn based on the steps number
     */
    public Square NextSquare(Square square, int Next,Pawn pawn)
    {
      int  x = square.getX();
      int  y = square.getY();
      if (square instanceof SimpleSquare || square instanceof SlideSquare)
      {
          y += Next;
          if (y > 14)
          {
              y -= 15;
              x++;
          }
          if (y < 0)
          {
              y = 15 + y;
              x--;
          }
          if (x > 3)
              x = 0;
          if (x < 0)
              x = 3;
          return this.squares[x][y];
      }else if (square instanceof SafetyZoneSquare)
      {
          x += Next;
          if (x == 5)
          {
              y = pawn.getNumber();
              x = pawn.colorToInt();
              return homeSquares[x][y];
          }else if (x > 5)
          {
              return null;
          }else if (x < 0)
          {
              if (x == -4)
                  y = 14;
              else
                y = 3 + x;
              x = pawn.colorToInt();
              switch (x){
                  case 1:
                      x = 2;
                      break;
                  case 2:
                      x = 1;
              }
              if (y == 14)
              {
                  x--;
                  if (x < 0) x =3;
               }
              return this.squares[x][y];
          }
          else
          {
              y = pawn.colorToInt();
              System.out.println("INer X = " + x + "<Y = "+ y);
             return playerSquares[x][y];
          }
      }else if(square instanceof StartSquare)
      {
          x = pawn.colorToInt();
          switch (x)
          {
              case 1:
                  x =2;
                  break;
              case 2:
                  x = 1;
          }
          return squares[x][4];
      }
      return null;

    }
    /**
     * Checks if, based on move, that was made should swap positions
     * or the enemy's pawn should go on the start position
     * @param card The card used for the swap
     * @return true if a swap move is allowed, false otherwise
     */
    public boolean DoSwap(Cards card)
    {
        //only card Eleven can swap position between two pawns
        if (card instanceof NumElevenCard && ((NumElevenCard)card).distance != 11)
            return false;
        return true;
    }

    /**
     * Checks if the current player has finished their turn based on the given card
     * @param card The card used for the turn
     * @return true if the turn is finished,false otherwise
     */
    public boolean Finished(Cards card)
    {
        if (card != null)
        {
            if (card instanceof NumSevenCard && (((NumSevenCard)card).firstMove + ((NumSevenCard)card).secondMove) !=7 )
                return false;
            if (card instanceof NumFiveCard && card.CanUse(this,getPlaying()))
                return false;
            if (card instanceof NumThreeCard && card.CanUse(this,getPlaying()))
                return false;
        }
        return true;
    }
    /**
     * Checks if a card allows for redrawing based on the card type, without folding
     * @param card The card to be checked
     * @return true if redrawing is allowed, false otherwise
     */
    public boolean CanRedraw(Cards card)
    {
        if (card instanceof NumEightCard|| card instanceof  NumTwelveCard )
            return true;
        return false;
    }


}

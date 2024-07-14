package model;


public abstract class Cards {

    /**
     * String variable that stores information about this card
     */
    private String description;

    /**
     * Constructor for Card
     */
    public Cards() {}

    /**
     * Getter for description
     * @return the description of the card
     */
    public String getDescription(){return description;}

    /**
     * Converts the card's type to a string
     * @return The name of the card based on card's type
     * @throws CardNumberException If the card type is not recognized
     */
    public String IntToName()
    {
        if (this instanceof NumberCard)
        {
            int NumberOfCard = ((NumberCard) this).getNumberOfCard();
            return ("card"+NumberOfCard);

        }else if (this instanceof SorryCard)
        {
            return "cardSorry";

        }
        throw new CardNumberException();

    }
    /**
     * Creates the description for the card based on card's type.
     * @return The description of the card.
     */
    public String DescriptionCreate()
    {
        String des = null;
        if (this instanceof NumberCard)
        {
            int NumberOfCard = ((NumberCard) this).getNumberOfCard();
            switch (NumberOfCard){
                case 1:
                    des = "Either start a pawn out\n or move one pawn forward 1 space ";
                    break;
                case 2:
                    des = "Either start a pawn out\n or move one pawn forward 2 spaces\nDraw again new card";
                    break;
                case 3:
                    des = "Move all your pawns forward 3 spaces\n if not possible move one pawn forward 3 spaces";
                    break;
                case 4:
                    des = "Move one pawn backwards 4 spaces";
                    break;
                case 5:
                    des = "Move all your pawns forward 5 spaces\n if not possible move one pawn forward 5 spaces";
                    break;
                case 7:
                    des = "Either move one pawn forward 7 spaces\n Or split the movie between any two pawns";
                    break;
                case 8:
                    des = "Move one pawn forward 8 spaces\n or draw new card";
                    break;
                case 10:
                    des = "Either move one pawn forward 10 spaces\n or move one pawn backward 1 space";
                    break;
                case 11:
                    des = "Move one pawn forward 11 space\n or may switch any one of your pawns with\n" +
                            "one of-any opponent's";
                    break;
                case 12:
                    des = "Either move one pawn forward 12 spaces\n or draw a card";
                    break;
            }
        }else
        {
            des = "Τake one of your pawns that is in the Start position\n " +
                    "and swap positions with one of the opponent's pawn's ";
        }
        return des;
    }

    /**
     * Sets the description of the card.
     */
    public void setDescription(){this.description = DescriptionCreate();}
    /**
     * Converts a number representation to a card instance
     * @param j The numerical representation of the card type
     * @return A new instance of the corresponding card type
     * @throws IllegalArgumentException If the numerical representation is invalid
     */
    public static Cards NumToCard(int j) throws IllegalArgumentException
    {
        switch (j){
            case 0:
                return new NumOneCard();
            case 1:
                return new NumTwoCard();
            case 2:
                return new NumThreeCard();
            case 3:
                return new NumFourCard();
            case 4:
                return new NumFiveCard();
            case 5:
                return new NumSevenCard();
            case 6:
                return new NumEightCard();
            case 7:
                return new NumTenCard();
            case 8:
                return new NumElevenCard();
            case 9:
                return new NumTwelveCard();
            case 10:
                return new SorryCard();
        }
        throw new CardNumberException();
    }

    /**
     * Checks if the card can be used based on card's type and the currents game state
     * @param board The game board on which the card is used
     * @param index The index of the player using the card
     * @return true if the card can be used, false otherwise
     */
    public abstract boolean CanUse(Board board, int index);
}
/**
 * Abstract class representing card with number
 * Extends the Cards class
 */
abstract class  NumberCard extends Cards
{
    // Card's number
    private int NumberOfCard;

    /**
     * @param NumberOfCard the number of the card
     */
    NumberCard(int NumberOfCard)
    {
        super();
        this.NumberOfCard = NumberOfCard;
        super.setDescription();
    }
    /**
     *  Getter for the number of the card
     * @return The number of the card
    */
    public int getNumberOfCard(){return this.NumberOfCard;}

    public boolean CanUse(Board board, int index)
    {
        return true;
    }


}

//--------Cards-----------------------------------------
/**
 * Class representing the Sorry card
 * Extends the Cards class
 */
class SorryCard extends Cards{
    /**
     * Constructor for SorryCard
     * Sets the description of the card
     */
    SorryCard()
    {
        super.setDescription();
    }

    /**
     *Swap the opponent's position with the player's position
     * @param friendly pawn belongs to the player who drew the card
     * @param enemy pawn belongs to the opponent
     * @pre friendly pawn must be on start position
     * @pre enemy must be on a non-safe square
     * @post enemy pawn must be back to its start position
     * @post friendly pawn is in the opponent's position before the swap
     * @return true if the swap was successful
     */
    boolean SorryMove(Pawn friendly, Pawn enemy, Board board)
    {
        //checks
        if (enemy == null || !this.CanUse(board, friendly.colorToInt()))
        {
            return false;
        }
        //this.movePawn(friendly,enemy.getSquare())
        if ((friendly.getSquare() instanceof StartSquare) && !(enemy.getSquare() instanceof PlayerSquare) )
        {

            if (enemy.getState() != 2 && !(enemy.getSquare() instanceof PlayerSquare) )
            {
                //can do sorry
                friendly.setSquare(enemy.getSquare());
//                Square enemyStartSquare = board.startSquares[enemy.colorToInt()][enemy.getNumber()];
//                enemy.setSquare(enemyStartSquare);
                enemy.returnToStart(board);
                return true;

            }
        }
        return false;
    }

    public boolean CanUse(Board board, int index)
    {
        Player player = board.players.get(index);
     if (player.getPawn(0).getState() == 0 || player.getPawn(1).getState() == 0)
     {
         for (int i =0; i < board.getNumberOfPlayers(); i++)
         {
             for (int j=0; j < 2; j++)
             {
                 if(!player.equals(board.players.get(i))
                         && board.players.get(i).getPawn(j).getState() == 1)
                 {
                     return true;
                 }
             }
         }
     }
        return false;
    }
}
//Card Number One----------------------------------
/**
 * Class representing the Number One card
 * Extends the NumberCard class
 */
class NumOneCard extends NumberCard {
    /**
     * Constructor for NumOneCard
     * Calls the constructor of the superclass NumberCard with the value 1
     */
    NumOneCard() {
        super(1);
    }


    public boolean CanUse(Board board, int index)
    {
        //you can always use card one
        return true;
    }
    /**
     * Checks if the Number One card can be used
     * Performs the movement if the movement is valid
     * @param friendly The pawn belongs to the player who drew the card
     * @param wantToGo The square where the player wants to move the pawn
     * @param board The game board on which the card is used
     * @return true if the movement was successful, false otherwise
     * @pre The friendly pawn must be on a non-home type square
     * @post If the movement is successful, the friendly pawn is on the given square
     */
    boolean OneMove(Pawn friendly,Square wantToGo, Board board)
    {
        int x = friendly.colorToInt();
        switch (x){
            case 1:
                x = 2;
                break;
            case 2:
                x = 1;
        }
         if(friendly.getSquare() == board.squares[x][2] && wantToGo instanceof SafetyZoneSquare
                 && friendly.getSquare().getX() == x)
        {
            if (board.playerSquares[0][friendly.colorToInt()].getPawn() == null)
            {
                // is true if it can enter the safety zone.
                friendly.setSquare(board.playerSquares[0][friendly.colorToInt()]);
                return true;
            }
        }else
        {
            Square nextsquare = board.NextSquare(friendly.getSquare(),getNumberOfCard(),friendly);
            //checks
            if (nextsquare == wantToGo )
            {
                if (nextsquare.getPawn() == null
                        || !nextsquare.getPawn().getColor().equals(friendly.getColor()))
                {
                    friendly.setSquare(wantToGo);
                    return true;

                }
            }
        }
        return false;
    }

}
//-----------------------------------------------

//Card Two ---------------------------------------
/**
 * Class representing the Number Two card
 * Extends the NumberCard class
 */
class NumTwoCard extends NumberCard  {

    /**
     * Constructor for NumTwoCard
     * Calls the constructor of the superclass NumberCard with the value 2
     */
    NumTwoCard(){
        super(2);
    }

    public boolean CanUse(Board board, int index)
    {
        Pawn p1 = board.players.get(index).getPawn(0);
        Pawn p2 = board.players.get(index).getPawn(1);

        if (p1.getState() == 2 && p2.getSquare().equals(board.playerSquares[4][p2.colorToInt()]))
            return false;
        if (p2.getState() == 2 && p1.getSquare().equals(board.playerSquares[4][p1.colorToInt()]))
            return false;

        return true;
    }
    /**
     * Checks if the Number Two card movement is valid
     * Performs the movement if the movement is valid
     * @param friendly The pawn belongs to the player who drew the card
     * @param wantToGo The square where the player wants to move the pawn
     * @param board The game board on which the card is used
     * @return true if the movement was successful,false otherwise
     * @pre The friendly pawn must be on a square that allows the Number Two card movement
     * @post If the movement is successful, the friendly pawn is on the given square
     */
    boolean TwoMove(Pawn friendly,Square wantToGo, Board board)
    {
        int x = friendly.colorToInt();
        switch (x){
            case 1:
                x = 2;
                break;
            case 2:
                x = 1;
        }
        int y = friendly.getSquare().getY() -  wantToGo.getX() ;
        if( wantToGo instanceof SafetyZoneSquare && y == 1 && friendly.getSquare().getX() == x)
        {
            Square playersSquare = board.playerSquares[friendly.getSquare().getY()-1][friendly.colorToInt()];
            if (playersSquare.getPawn() == null
                    && friendly.getSquare().getY() <= 2)
            {
                // is true if it can enter the safety zone.
                friendly.setSquare(playersSquare);
                return true;
            }
        }else
        {
            Square nextsquare = board.NextSquare(friendly.getSquare(),getNumberOfCard(),friendly);
            //checks
            if (nextsquare == wantToGo )
            {
                if (nextsquare.getPawn() == null
                        || !nextsquare.getPawn().getColor().equals(friendly.getColor()))
                {
                    friendly.setSquare(wantToGo);
                    return true;

                }
            }
        }
        return false;
    }

}
//-----------------------------------------------

//Card Three--------------------------------------
/**
 * Class representing the Number Three card
 * Extends the NumberCard class
 */
class NumThreeCard extends NumberCard  {
    /**
     * Constructor for NumThreeCard
     * Calls the constructor of the superclass NumberCard with the value 3
     */
    NumThreeCard(){
        super(3);
    }
    Pawn moved1= null, moved2 = null;

    public boolean CanUse(Board board, int index){
        if (moved1 != null && moved2 != null)
            return false;
        Player player = board.players.get(index);
       for (int i =0; i < 2; i++)
       {
           Pawn pawn = player.getPawn(i);
           if (!pawn.equals(moved1))
           {
               if (pawn.getState() == 1)
               {
                   return true;
               }
               if (pawn.getState() == 3 && pawn.getSquare().getX() < 3)
               {
                   return true;
               }
           }
       }
        return false;
    }
    /**
     * Checks if the Number Three card movement is valid
     * Performs the movement if the movement is valid
     * @param friendly The pawn belongs to the player who drew the card.
     * @param wantToGo The square where the player wants to move the pawn.
     * @param board The game board on which the card is used
     * @return true if the movement was successful, false otherwise
     * @pre The friendly pawn must be in a valid state for movement
     * @post If the movement is successful, the friendly pawn is on the given square
     */
    boolean ThreeMove(Pawn friendly,Square wantToGo, Board board)
    {
        if (friendly.getState() != 1 && friendly.getState()!= 3)
        {
            return false;
        }
        boolean flag = false;
        if (moved1 == friendly)
        {
            return false;
        }
        Square current = friendly.getSquare();
        int x = friendly.colorToInt();
        switch (x){
            case 1:
                x = 2;
                break;
            case 2:
                x = 1;
        }
        if (wantToGo instanceof SafetyZoneSquare)
        {
            if (current instanceof SimpleSquare || current instanceof SlideSquare )
            {
                if (current.getX() == x && current.getY() == wantToGo.getX())
                {
                    friendly.setSquare(wantToGo);
                    flag =  true;
                }
            }else
            {
                if (current.getX() < 3)
                {
                    friendly.setSquare(wantToGo);
                    flag =  true;
                }
            }


        } else if (wantToGo instanceof StartSquare) {
            flag = false;
        }
        else
        {
            Square nextsquare = board.NextSquare(friendly.getSquare(),getNumberOfCard(),friendly);
            if (nextsquare == wantToGo )
            {
                if (nextsquare.getPawn() == null
                        || !nextsquare.getPawn().getColor().equals(friendly.getColor()))
                {
                    friendly.setSquare(wantToGo);
                    flag = true;

                }
            }
        }
        if (flag)
        {
            if (moved1 == null)
                moved1 = friendly;
            else{
                moved2 = friendly;
            }
        }
        return flag;




    }

}
//-----------------------------------------------

//Card Four---------------------------------------
/**
 * Class representing the Number FOur card
 * Extends the NumberCard class
 */
class NumFourCard extends NumberCard  {
    /**
     * Constructor for NumFourCard
     * Calls the constructor of the superclass NumberCard with the value 4
     */
    NumFourCard(){
        super(4);
    }

    /**
     * Checks if the Number Three card movement is valid
     * Performs the movement if the movement is valid
     * @param friendly The pawn belongs to the player who drew the card.
     * @param wantToGo The square where the player wants to move the pawn.
     * @param board The game board on which the card is used
     * @return true if the movement was successful, false otherwise
     * @pre The friendly pawn must be in a valid state for movement
     * @post If the movement is successful, the friendly pawn is on the given square
     */
    boolean FourMove(Pawn friendly,Square wantToGo, Board board)
    {
        if (friendly.getState() != 1 && friendly.getState()!= 3)
        {
            return false;
        }
        int x = friendly.colorToInt();
        switch (x){
            case 1:
                x = 2;
                break;
            case 2:
                x = 1;
        }
        int y = wantToGo.getX();
        if( wantToGo instanceof SafetyZoneSquare )
        {
            if (wantToGo.getX() == 0 && wantToGo.getColor().equals(friendly.getColor()))
            {
               if (wantToGo.Isfree())
               {
                   friendly.setSquare(wantToGo);
                   return true;
               }
            }

        }else
        {
            Square current = friendly.getSquare();
            Square nextsquare = board.NextSquare(current,(-1)*getNumberOfCard(),friendly);
            if (current instanceof SafetyZoneSquare)
            {
                if (current.getY()==0)
                {
                    current = board.squares[x][2];
                    nextsquare = board.NextSquare(current,(-1)*(getNumberOfCard()-1),friendly);
                }
            }
            if (nextsquare == wantToGo)
            {
                if (nextsquare.getPawn() == null
                        || !nextsquare.getPawn().getColor().equals(friendly.getColor()))
                {
                    friendly.setSquare(wantToGo);
                    return true;

                }
            }
        }
        return false;
    }


    @Override
    public boolean CanUse(Board board, int index)
    {
        Pawn p1 = board.players.get(index).getPawn(0);
        Pawn p2 = board.players.get(index).getPawn(1);
        if (p1.getState() == 0 || p1.getState() == 2)
        {
            if (p2.getState() == 0 || p2.getState() == 2)
                return false;
        }
        return true;
    }
}
//-----------------------------------------------

//Five Card---------------------------------------
/**
 * Class representing the Number Five card
 * Extends the NumberCard class
 */
    class NumFiveCard extends NumberCard {
        /**
         * Constructor for NumFiveCard
         * Calls the constructor of the superclass (NumberCard) with the value 5
         */
        NumFiveCard() {
            super(5);
        }
        /**
         * Stores the pawns that have moved using this card
        */
        Pawn moved1 = null, moved2 = null;

        public boolean CanUse(Board board, int index) {
            if (moved1 != null && moved2 != null)
                return false;
            Player player = board.players.get(index);
            for (int i = 0; i < 2; i++) {
                Pawn pawn = player.getPawn(i);
                if (!pawn.equals(moved1)) {
                    if (pawn.getState() == 1) {
                        return true;
                    }
                }
            }
            return false;
        }
    /**
     * Checks if the Number Three card movement is valid
     * Performs the movement if the movement is valid
     * @param friendly The pawn belongs to the player who drew the card.
     * @param wantToGo The square where the player wants to move the pawn.
     * @param board The game board on which the card is used
     * @return true if the movement was successful, false otherwise
     * @pre The friendly pawn must be in a valid state for movement
     * @post If the movement is successful, the friendly pawn is on the given square
     */
        boolean FiveMove(Pawn friendly, Square wantToGo, Board board) {
            if (friendly.getState() != 1 && friendly.getState() != 3) {
                return false;
            }
            boolean flag = false;
            if (moved1 == friendly) {
                return false;
            }
            Square current = friendly.getSquare();
            int x = friendly.colorToInt();
            switch (x) {
                case 1:
                    x = 2;
                    break;
                case 2:
                    x = 1;
            }
            if (wantToGo instanceof SafetyZoneSquare) {
                if (current instanceof SimpleSquare || current instanceof SlideSquare) {
                    //---Pawn on Simple Square
                    //case, pawn is one line before his line
                    int prevLine = x -1;
                    if (prevLine < 0) prevLine = 3;
                    if (friendly.getSquare().getX() == prevLine)
                    {
                        if (friendly.getSquare().getY() > 12)
                        {
                            if (wantToGo.getX() == friendly.getSquare().getY() - 13
                                    && wantToGo.getY() == friendly.colorToInt())
                            {
                                friendly.setSquare(wantToGo);
                                flag = true;
                            }
                        }
                    }else if(friendly.getSquare().getX() == x) //case, pawn on his line
                    {

                        if (friendly.getSquare().getY() < 3)
                        {
                            if (wantToGo.getX() == 5 - 3 + friendly.getSquare().getY()
                                    && wantToGo.getY() == friendly.colorToInt())
                            {
                                friendly.setSquare(wantToGo);
                                flag = true;
                            }
                        }
                    }
                }
            } else if (wantToGo instanceof StartSquare) {
                flag = false;
            } else {
                Square nextsquare = board.NextSquare(friendly.getSquare(), getNumberOfCard(), friendly);
                if (nextsquare == wantToGo) {
                    if (nextsquare.getPawn() == null
                            || !nextsquare.getPawn().getColor().equals(friendly.getColor())) {
                        friendly.setSquare(wantToGo);
                        flag = true;

                    }
                }
            }
            if (flag) {
                if (moved1 == null)
                    moved1 = friendly;
                else {
                    moved2 = friendly;
                }
            }
            return flag;


        }
    }
//-----------------------------------------------
/**
 * Class representing the Number Seven card
 * Extends the NumberCard class
 */
class NumSevenCard extends NumberCard  {
    /**
     * Constructor for NumSevenCard
     * Calls the constructor of the superclass (NumberCard) with the value 7
     */
    NumSevenCard(){
        super(7);
    }

    /**
     * Represents the first and second moves made using the Number Seven card
     */
    public int firstMove = 0, secondMove = 0;

    /**
     * Checks if the Number Three card movement is valid
     * Performs the movement if the movement is valid
     * @param friendly The pawn belongs to the player who drew the card.
     * @param wantToGo The square where the player wants to move the pawn.
     * @param board The game board on which the card is used
     * @return true if the movement was successful, false otherwise
     * @pre The friendly pawn must be in a valid state for movement
     * @post If the movement is successful, the friendly pawn is on the given square
     */
    boolean SevenMove(Pawn friendly,Square wantToGo, Board board)
    {
        if (friendly.getState() != 1 && friendly.getState()!= 3)
        {
            return false;
        }
        boolean flag = false;

        Square current = friendly.getSquare();
        int x = friendly.colorToInt();
        switch (x){
            case 1:
                x = 2;
                break;
            case 2:
                x = 1;
        }
        if (wantToGo instanceof SafetyZoneSquare)
        {
            if (current instanceof SimpleSquare || current instanceof SlideSquare )
            {
                int prevLine = x -1;
                if (prevLine < 0) prevLine = 3;
                //&& current.getY() == wantToGo.getX())
                if (current.getX() == x && wantToGo.getColor().equals(friendly.getColor())) //Pawn on his line
                {
                    if (firstMove == 0)
                    {
                        firstMove = 7 - current.getY();
                        friendly.setSquare(wantToGo);
                        flag =  true;
                    }else
                    {
                        //within the range
                        if(firstMove < 7 && current.getY() + 7 - firstMove -3 == wantToGo.getX())
                        {
                            secondMove = 7 -firstMove;
                            friendly.setSquare(wantToGo);
                            flag =  true;
                        }
                    }

                }else if (current.getX() == prevLine && wantToGo.getColor().equals(friendly.getColor()))
                {
                    int y = current.getY() + 7 - firstMove;
                    if (y >= 18)//if safe zone is reachable
                    {
                        if (wantToGo.getX()<= y - 18)
                        {
                            if (firstMove == 0)
                                firstMove = 7 + 18 - y ;
                            else
                                secondMove = 7 -firstMove;
                            friendly.setSquare(wantToGo);
                            flag =  true;
                        }
                    }
                }
            } else if (current instanceof SafetyZoneSquare && wantToGo.getColor().equals(current.getColor())) {
               if (firstMove == 0)
                {
                    firstMove = wantToGo.getX() - current.getX();
                    friendly.setSquare(wantToGo);
                    flag =  true;
                }
                else if (wantToGo.getX() - current.getX() == (7 -firstMove))
                {
                    secondMove = 7 -firstMove;
                    friendly.setSquare(wantToGo);
                    flag =  true;
                }
            } else
            {
               flag = false;
            }
        } else if (wantToGo instanceof StartSquare) {
            flag = false;
        }else if (wantToGo instanceof HomeSquare && wantToGo.getColor().equals(friendly.getColor()))
        {
            if (current instanceof SlideSquare)
            {
                if (current.getY() == 2 && 7 - firstMove >= 6)
                {
                    int oldFirst = firstMove;
                    if (firstMove == 0)
                    {
                        firstMove = wantToGo.getX() - current.getX();
                    }
                    if (firstMove == 1 || firstMove == 0 )
                    {
                        if (oldFirst == firstMove)
                            secondMove = 7 -firstMove;
                        if (friendly.getNumber() == wantToGo.getY())
                        {
                            friendly.setSquare(wantToGo);
                            flag =  true;
                        }
                    }
                } else if (current.getY() == 1 &&  firstMove == 0) {

                    if (friendly.getNumber() == wantToGo.getY())
                    {
                        if (firstMove == 0)
                            firstMove = wantToGo.getX() - current.getX();
                        else
                            secondMove = 7 -firstMove;
                        friendly.setSquare(wantToGo);
                        flag =  true;
                    }
                }
            } else if (current instanceof SafetyZoneSquare) {
                if(current.getX() + 7 - firstMove >= 5)
                {
                    if (friendly.getNumber() == wantToGo.getY())
                    {
                        if (firstMove == 0)
                            firstMove = 5 - current.getX();
                        else
                            secondMove = 7 -firstMove;
                        friendly.setSquare(wantToGo);
                        flag =  true;
                    }
                }
            }

        }
        else
        {
            int move = SquareDistance(current, wantToGo);
            if (firstMove == 0 && move <= 7)
            {
                firstMove = move;
            }
            else if(firstMove + move == 7)
            {
                secondMove = move;

            }
            else
            {
                return false;
            }
            Square nextsquare = board.NextSquare(friendly.getSquare(),move,friendly);
            if (nextsquare == wantToGo )
            {
                if (nextsquare.getPawn() == null
                        || !nextsquare.getPawn().getColor().equals(friendly.getColor()))
                {
                    friendly.setSquare(wantToGo);
                    flag = true;

                }
            }
        }
        return flag;
    }

    /**
     * Calculates the distance between two squares on the game board
     * @param s1 The first square
     * @param s2 The second square
     * @return The distance between the two squares
     */
    static int SquareDistance(Square s1, Square s2)
    {
        int count = 0, Xmax , Xmin , Ymax , Ymin ;
        Xmax = s2.getX();
        Ymax = s2.getY();
        Xmin = s1.getX();
        Ymin = s1.getY();
        while (!(Xmax == Xmin && Ymax == Ymin))
        {
            count++;
            Ymin++;
            if (Ymin > 14)
            {
                Ymin = 0;
                Xmin++;
                if (Xmin > 3)
                    Xmin = 0;
            }
        }
        return count;
    }

    public boolean CanUse(Board board, int index)
    {
        if (firstMove + secondMove >= 7)
            return false;
        Player player = board.players.get(index);
        for (int i =0; i < 2; i++)
        {
            Pawn pawn = player.getPawn(i);
                if (pawn.getState() == 1)
                {
                    return true;
                }
                if (pawn.getState() == 3 && pawn.getSquare().getX() < 7 - firstMove)
                {
                    return true;
                }

        }
        return false;
    }
}
//-----------------------------------------------

//Card Eight-------------------------------------
/**
 * Class representing the Number Eight card
 * Extends the NumberCard class
 */
class NumEightCard extends NumberCard  {
    /**
     * Constructor for NumEightCard
     * Calls the constructor of the superclass (NumberCard) with the value 8
     */
    NumEightCard(){
        super(8);
    }

    /**
     * Checks if the Number Three card movement is valid
     * Performs the movement if the movement is valid
     * @param friendly The pawn belongs to the player who drew the card.
     * @param wantToGo The square where the player wants to move the pawn.
     * @param board The game board on which the card is used
     * @return true if the movement was successful, false otherwise
     * @pre The friendly pawn must be in a valid state for movement
     * @post If the movement is successful, the friendly pawn is on the given square
     */
    boolean EightMove(Pawn friendly,Square wantToGo, Board board)
    {
        int x = friendly.colorToInt();
        switch (x){
            case 1:
                x = 2;
                break;
            case 2:
                x = 1;
        }
        Square current = friendly.getSquare();
        if (current instanceof StartSquare || current instanceof SafetyZoneSquare)
            return false;
        if (wantToGo instanceof SafetyZoneSquare && wantToGo.getColor().equals(friendly.getColor()))
        {
            int prevLine = x -1;
            if (prevLine < 0) prevLine = 3;
            if (current.getX() == prevLine && current.getY()>= 10) {
                // is true if it can enter the safety zone.
                if (wantToGo.getX() == current.getY()%10)
                {
                    friendly.setSquare(wantToGo);
                    return true;
                }
            }

        }else if(wantToGo instanceof HomeSquare && wantToGo.getColor().equals(friendly.getColor()))
        {
            if (current.getX() == x && current.getY() == 0)
            {
                if (wantToGo.getY() == friendly.getNumber())
                {
                    friendly.setSquare(wantToGo);
                    return true;
                }
            }
        }
        else
        {
            Square nextsquare = board.NextSquare(friendly.getSquare(),getNumberOfCard(),friendly);
            //checks
            if (nextsquare == wantToGo )
            {
                if (nextsquare.getPawn() == null
                        || !nextsquare.getPawn().getColor().equals(friendly.getColor()))
                {
                    friendly.setSquare(wantToGo);
                    return true;

                }
            }
        }
        return false;
    }

    @Override
    public boolean CanUse(Board board, int index)
    {
        //If the player can not move any of their pawns, they can draw a card.
        return true;
    }
}
//-----------------------------------------------
/**
 * Class representing the Number Ten card
 * Extends the NumberCard class
 */
class NumTenCard extends NumberCard  {
    /**
     * Constructor for NumTenCard
     * Calls the constructor of the superclass (NumberCard) with the value 10
     */
    NumTenCard(){
        super(10);
    }

    /**
     * Checks if the Number Three card movement is valid
     * Performs the movement if the movement is valid
     * @param friendly The pawn belongs to the player who drew the card.
     * @param wantToGo The square where the player wants to move the pawn.
     * @param board The game board on which the card is used
     * @return true if the movement was successful, false otherwise
     * @pre The friendly pawn must be in a valid state for movement
     * @post If the movement is successful, the friendly pawn is on the given square
     */
    boolean TenMove(Pawn friendly,Square wantToGo, Board board)
    {
        int x = friendly.colorToInt();
        switch (x){
            case 1:
                x = 2;
                break;
            case 2:
                x = 1;
        }
        Square current = friendly.getSquare();
        if (current instanceof StartSquare)
            return false;
        if (wantToGo instanceof SafetyZoneSquare && wantToGo.getColor().equals(friendly.getColor()))
        {
            if (current instanceof SafetyZoneSquare)
            {
                if (current.getY() == wantToGo.getY() && current.getX() == wantToGo.getX()+1){
                    //go one square back
                    friendly.setSquare(wantToGo);
                    return true;
                }
            }else
            {
                int prev = x -1;
                if (prev < 0) prev =3;
                if (current.getX() == prev && current.getY()>= 8 && current.getY()< 13){
                    // is true if it can enter the safety zone.
                    if (wantToGo.getX() == current.getY() - 8)
                    {
                        friendly.setSquare(wantToGo);
                        return true;
                    }

                }
            }


        }else if(wantToGo instanceof HomeSquare && wantToGo.getColor().equals(friendly.getColor()))
        {
            int prev = x -1;
            if (prev < 0) prev =3;
            if (current.getX() == prev && current.getY() == 13)
            {
                if (wantToGo.getY() == friendly.getNumber())
                {
                    friendly.setSquare(wantToGo);
                    return true;
                }
            }
        }
        else
        {
            Square nextsquare = board.NextSquare(friendly.getSquare(),getNumberOfCard(),friendly);
            Square previous;
            if (current instanceof SimpleSquare || current instanceof SlideSquare)
            {
                previous = board.NextSquare(friendly.getSquare(),-1,friendly);
            }
            else
            {
                previous = board.squares[x][2];
            }
            //checks
            if (nextsquare == wantToGo || wantToGo == previous )
            {
                if (nextsquare != null && (nextsquare.getPawn() == null
                        || !nextsquare.getPawn().getColor().equals(friendly.getColor())))
                {
                    friendly.setSquare(wantToGo);
                    return true;
                }
                if (previous != null && (previous.getPawn() == null
                    || !previous.getPawn().getColor().equals(friendly.getColor())))
                {
                    friendly.setSquare(wantToGo);
                    return true;

                }
            }
        }
        return false;
    }

    public boolean CanUse(Board board, int index)
    {
        Player player = board.players.get(index);
        for (int i =0; i < 2 ; i++)
        {
            if (player.getPawn(i).getState() == 1 || player.getPawn(i).getState() == 3 )
                return true;
        }
        return false;
    }
}
//-----------------------------------------------
/**
 * Class representing the Number Eleven card
 * Extends the NumberCard class
 */

class NumElevenCard extends NumberCard  {
    /**
     * Constructor for NumElevenCard
     * Calls the constructor of the superclass (NumberCard) with the value 11
     */
    int distance = 0;
    public boolean swapPerformed = false;
    NumElevenCard(){
        super(11);
    }
    /**
     * Moves the friendly pawn and possibly the enemy pawn based on the conditions specified for the Number Eleven card
     * @param friendly The friendly pawn belongs to the player who drew the card
     * @param enemy The opponent's pawn to be moved or swapped, null if the player choose a square to go to
     * @param wantToGo The square where the player wants to move the friendly pawn, null if the player choose to swamp
     * @param board  The game board on which the card is used
     * @return true if the movement or swap was successful, false otherwise
     * @pre The friendly pawn must be on a simple square
     * @pre If swapping, the enemy pawn must be in a valid state for swapping
     * @pre If moving, the target square must be a valid destination
     * @post If the movement is successful, the friendly pawn is on the given square
     * @post If swapping, the enemy pawn is in the friendly pawn's original square
     */
    boolean ElevenMove(Pawn friendly, Pawn enemy,Square wantToGo, Board board)
    {
        int x = friendly.colorToInt(), prev = x-1;
        switch (x){
            case 1:
                prev = 1;
                x = 2;
                break;
            case 2:
                x = 1;
                prev = 0;
                break;
            case 0:
                prev = 3;
        }
        Square current = friendly.getSquare();
        //if the pawn is not on a simple square
        if (friendly.getState() != 1)
            return false;
        if (enemy != null && enemy.getState() == 1)
        {
            Square enemySquare = enemy.getSquare();
            distance = NumSevenCard.SquareDistance(current,enemySquare);
            if (distance == 11)
            {
                //If the movement is exactly 11 squares,
                // the opponent's pawn returns to the start position.
                friendly.setSquare(enemySquare);
                enemy.returnToStart(board);
            }else //swap
            {
                swapPerformed = true;
                friendly.setSquare(enemySquare);
                enemy.setSquare(current);
                return true;

            }
            return true;
        }else if(wantToGo != null)
        {
            if (wantToGo instanceof SafetyZoneSquare && wantToGo.getColor().equals(friendly.getColor()))
            {
                int y = current.getY();
                if (current.getX() == prev && (y >= 7 && y < 12))
                {
                    friendly.setSquare(wantToGo);
                    return true;
                }
            }else if (wantToGo instanceof HomeSquare && wantToGo.getColor().equals(friendly.getColor()))
            {
                int y = current.getY();
                if (y == 12)
                {
                    if (friendly.getNumber() == wantToGo.getY())
                    {
                        friendly.setSquare(wantToGo);
                        return true;
                    }
                }
            }else
            {
                Square nextsquare = board.NextSquare(friendly.getSquare(),this.getNumberOfCard(),friendly);
                if (nextsquare == wantToGo )
                {
                    if (nextsquare.getPawn() == null
                            || !nextsquare.getPawn().getColor().equals(friendly.getColor()))
                    {
                        friendly.setSquare(wantToGo);
                        return true;

                    }
                }
            }
        }

        return false;
    }
    public boolean CanUse(Board board, int index)
    {
        Player player = board.players.get(index);
        for (int i =0; i < 2 ; i++)
        {
            if (player.getPawn(i).getState() == 1 )
                return true;
        }
        return false;
    }
}
//-----------------------------------------------
/**
 * Class representing the Number Twelve card
 * Extends the NumberCard class
 */
class NumTwelveCard extends NumberCard  {
    /**
     * Constructor for NumTwelveCard
     * Calls the constructor of the superclass (NumberCard) with the value 12
     */
    NumTwelveCard(){
        super(12);
    }
    /**
     * Moves the friendly pawn based on the conditions specified for the Number Twelve card
     * Performs the move if the movement is valid
     * @param friendly The friendly pawn belongs to the player who drew the card
     * @param wantToGo The square where the player wants to move the friendly pawn
     * @param board   The game board on which the card is used
     * @return ture if the movement was successful,false otherwise
     * @pre The friendly pawn must be on a simple square
     * @pre The target square must be a valid destination
     * @post If the movement is successful, the friendly pawn is on the target square
     */
    boolean TwelveMove(Pawn friendly,Square wantToGo, Board board)
    {
       if (friendly.getState() != 1)
           return false;
        int x = friendly.colorToInt(), prev = x-1;
        switch (x){
            case 1:
                prev = 1;
                x = 2;
                break;
            case 2:
                x = 1;
                prev = 0;
                break;
            case 0:
                prev = 3;
        }
        Square current = friendly.getSquare();
        int y = current.getY();
       if (wantToGo instanceof HomeSquare && wantToGo.getColor().equals(friendly.getColor()))
        {
            if (current.getX() == prev && y == 11)
            {
                if (wantToGo.getY() == friendly.getNumber())
                {
                    friendly.setSquare(wantToGo);
                    return true;
                }
            }
        } else if (wantToGo instanceof SafetyZoneSquare && wantToGo.getColor().equals(friendly.getColor())) {
           if (current.getX() == prev && (y >= 6 && y < 11))
           {
               if (current.getY() -6 == wantToGo.getX())
               {
                   friendly.setSquare(wantToGo);
                   return true;
               }

           }
       } else if (wantToGo instanceof StartSquare) {
           return false;
       }else
       {
           Square nextsquare = board.NextSquare(friendly.getSquare(),getNumberOfCard(),friendly);
           //checks
           if (nextsquare == wantToGo )
           {
               if (nextsquare.getPawn() == null
                       || !nextsquare.getPawn().getColor().equals(friendly.getColor()))
               {
                   friendly.setSquare(wantToGo);
                   return true;

               }
           }
       }
       return false;
    }

    public boolean CanUse(Board board, int index)
    {
        //If the player can not move any of their pawns, they can draw a card.
        return true;
    }

}




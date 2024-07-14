package model;

public class Pawn {

    /**
     * Pawn's color
     */
    private final GameColors color;
    /**
     * Pawn's square
     */
    private Square square;


    /**
     * Pawn's state
     * @pre state is greater than or equal to zero and less than or equal to three.
     * State zero, the pawn is at the starting position
     * State 1, the pawn is on the board
     * State 2, the pawn is at home
     * State 3, the pawn is on safe zone
     */
    private int state;

    private final int number;

    /**
     * Constructor for a Pawn
     * @param color Pawn's color
     * @param square Pawn's starting position
     */

    Pawn(GameColors color, Square square, int number)
    {
        this.color = color;
        this.square = square;
        state = 0;
        this.number = number;
    }

    /**
     * Getter for pawn's color
     * @return pawn's color
     */
    public GameColors getColor(){return color;}

    /**
     * Getter for pawn's square
     * @return pawn's square
     */

    public Square getSquare(){return square;}

    /**
     * Getter for pawn's activity
     * @return true if the pawn is active
     */
    public int getState(){this.setState(); return state;}

    /**
     * Updates pawn's activity
     */
    public void setState(){
         if (square instanceof StartSquare)
        {
            state = 0;
        } else if (square instanceof HomeSquare )   {
            state = 2;
        }else if (square instanceof SafetyZoneSquare)
        {
            state = 3;
        }else
        {
            state = 1;
        }
    }

    /**
     * Getter for pawn's number
     * @return the number of this pawn
     */
    public int getNumber(){return this.number;}

    /**
     * Updates pawn's squares
     * @param square the new position of this pawn
     */
    public void setSquare(Square square){
        if (this.square != null)
            this.square.removePawn();
        this.square = square;
        this.square.setPawn(this);

    }
    /**
     * Converts the pawn color to an integer index
     * @return The integer index corresponding to the pawn color
     *         Returns -1 if the color is not recognized
     */
    public int colorToInt()
    {
        int index =-1;
        switch (color){
            case RED:
                index = 0;
                break;
            case YELLOW:
                index = 1;
                break;
            case BLUE:
                index = 2;
                break;
            case GREEN:
                index = 3;
                break;
        }
        return index;
    }

    /**
     * Returns the pawn to its start position on the board
     * @param board The board of the game
     */
    public void returnToStart(Board board)
    {
        Square toGo = board.startSquares[this.colorToInt()][this.number];
        setSquare(toGo);
    }



}

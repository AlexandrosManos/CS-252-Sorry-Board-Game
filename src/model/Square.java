package model;

public abstract class Square {
    /**
     * square color
     */
    private GameColors color;
    /**
     * Square Coordinates
     */
    private int x;
    private int y;
    /**
     * Boolean variable, is true when there is no pawn on it
     * By default is free
     */
    private boolean free = true;
    /**
     * The pawn currently on the square
     */
    private Pawn pawn;

    /**
     * Square Constructor
     * @param x is coordinate
     * @param y is coordinate
     * @param color the color
     */

    Square(int x, int y, GameColors color)
    {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    //-----------Accessors----------------------------------

    /**
     * Getter for x coordinate
     * @return x coordinate of the square
     */
    public int getX(){return x;}
    /**
     * Getter for y coordinate
     * @return y coordinate of the square
     */
    public int getY(){return y;}

    /**
     * Getter for the color of the square
     * @return the color of the square
     */
    public GameColors getColor(){return color;}

    /**
     * Getter if the square is free
     * @return true, if the square is free
     */
    public boolean Isfree(){return free;}

    /**
     * Setter for x coordinate
     * @param x the new coordinate
     * @post x coordinate has been set
     */
    public void setX(int x){this.x = x;}
    /**
     * Setter for y coordinate
     * @param y the new coordinate
     * @post y coordinate has been set
     */
    public void setY(int y){this.y = y;}

    /**
     * Setter for Colors
     * @param color the new color
     * @post color has been set
     */
    public void setColor(GameColors color){this.color = color;}

    /**
     * Binds or unbinds the square
     * @param state the new state
     * @throws IsNotFreeException is the square is not free and the new state is also false
     */
    public void setFree(boolean state) throws IsNotFreeException{
        if(!Isfree() && !state )
            throw new IsNotFreeException();
        free = state;
    }
    /**
     * Gets the pawn currently on the square
     * @return The pawn on the square
     */
    public Pawn getPawn(){return this.pawn;}
    /**
     * Sets the pawn on the square
     * @param pawn The pawn to set on the square
     */
    public void setPawn(Pawn pawn){this.pawn=pawn;}
    /**
     * Removes the pawn from the square.
     */
    public void removePawn(){this.pawn = null;}
}



abstract class PlayerSquare extends Square
{

    /**
     * Constructor for these squares that belong to someone
     *  @param x is coordinate
     * @param y is coordinate
     * @param color the color
     */
    PlayerSquare(int x, int y, GameColors color){
        super(x,y,color);
    }

}

class StartSquare extends PlayerSquare {
    /**
     * Constructor for Start squares
     *  @param x is coordinate
     * @param y is coordinate
     * @param color the color
     */
    StartSquare(int x, int y, GameColors color)
    {
        super(x, y, color);
    }



}

class HomeSquare extends PlayerSquare{

    /**
     * Constructor for Home squares
     *  @param x is coordinate
     * @param y is coordinate
     * @param color the color
     */
    HomeSquare(int x, int y, GameColors color)
    {
        super(x, y, color);
    }
}

class SafetyZoneSquare extends PlayerSquare{

    /**
     * Constructor for Safety Zone squares
     *  @param x is coordinate
     * @param y is coordinate
     * @param color the color
     */
    SafetyZoneSquare(int x, int y, GameColors color)
    {
        super(x, y, color);
    }

}

class SimpleSquare extends Square{
    /**
     * Simple Square Constructor
     * @param x is coordinate
     * @param y is coordinate
     * @param color the color
     */

    SimpleSquare(int x, int y, GameColors color)
    {
        super(x, y, color);
    }

}

abstract class  SlideSquare extends Square{
    /**
     * Square Constructor
     * @param x is coordinate
     * @param y is coordinate
     * @param color the color
     */
    SlideSquare(int x, int y, GameColors color)
    {
        super(x, y, color);
    }

}

class StartSlideSquare extends SlideSquare{
    /**
     * The end of the slide square
     */
    int end;
    /**
     * Constructor for StartSlideSquare
     * @param x is coordinate
     * @param y is coordinate
     * @param color The color of the square
     */
    StartSlideSquare(int x, int y, GameColors color){super(x,y,color);}
    public int getSEnd(){return this.end;}
    public void setEnd(int end){this.end = end;}
}


class InternalSlideSquare  extends SlideSquare{

    InternalSlideSquare(int x, int y, GameColors color)
    {
        super(x,y,color);
    }

}


class EndSlideSquare  extends SlideSquare{


    EndSlideSquare(int x, int y, GameColors color)
    {
        super(x,y,color);
    }

}

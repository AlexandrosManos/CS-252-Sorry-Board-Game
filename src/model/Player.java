package model;

public class Player {

    /**
     * The name of the player
     */
    private final String name;
    /**
     * The color of the player
     */
    private final GameColors color;

    /**
     * Player's pawns
     */
    private Pawn pawn[];


    /**
     * Constructor for players
     * @param name the name of the player
     * @param color the color of the player
     */
    public Player(String name, GameColors color)
    {
        this.color =color;
        this.name = name;
        pawn = new Pawn[2];
        pawn[0]= null;
        pawn[1] = null;
    }

    /**
     * Getter for player's name
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for player's color
     * @return the color of the player
     */
    public GameColors getColor() {return color;}

    /**
     * Getter for Pawns
     * @param i the number of the pawn
     * @return the pawn
     */
    public Pawn getPawn(int i){return pawn[i];}

    /**
     * Setter for Pawns
     * @param i the number of the pawn
     */
    public void setPawn(int i, Pawn toAdd){pawn[i] = toAdd;}

}

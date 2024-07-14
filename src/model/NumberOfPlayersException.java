package model;

public class NumberOfPlayersException extends IllegalArgumentException{
    NumberOfPlayersException(){throw new IllegalArgumentException("Unacceptable number of players");}
}

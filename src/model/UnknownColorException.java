package model;

public class UnknownColorException extends IllegalArgumentException{
    UnknownColorException(){throw new IllegalArgumentException("Unknown given color ");}
}

package model;

public class IsNotFreeException extends IllegalArgumentException{
    IsNotFreeException(){throw new IllegalArgumentException("The square is not free");}
}

package model;

public class CardNumberException extends IllegalArgumentException{
    CardNumberException(){throw new IllegalArgumentException("Unknow card number");}

}

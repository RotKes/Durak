package ru.kpfu.itis.kadyrov.models;

/**
 * Created by Амир on 11.12.2016.
 */
public class Card {
    private int number;
    private Suit suit;

    public Card(int number, Suit suit) {
        this.number = number;
        this.suit = suit;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    @Override
    public String toString() {
        return number + " " + suit;
    }
}

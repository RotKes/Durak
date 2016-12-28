package ru.kpfu.itis.kadyrov.models;

/**
 * Created by Амир on 11.12.2016.
 */
public class Card {
    private int id;
    private int number;
    private Suit suit;

    public Card(int id, int number, Suit suit) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "[" + id + " | " + number + " " + suit + "]";
    }

}

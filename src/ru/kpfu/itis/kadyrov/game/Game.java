package ru.kpfu.itis.kadyrov.game;

import ru.kpfu.itis.kadyrov.models.Card;
import ru.kpfu.itis.kadyrov.models.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Амир on 14.12.2016.
 */
public class Game {
    public Suit trump;

    public Suit getTrumpCard() {
        return trump;
    }

    public void setTrumpCard(Suit trump) {
        this.trump = trump;
    }

    public Game(Suit trump) {
        this.trump = trump;
    }

    public Game() {
    }

    public LinkedList<Card> gamingDeckCreating() {
        LinkedList<Card> deck = new LinkedList<>();
        for (int i = 6; i <= 14; i++) {
            for (Suit suit : Suit.values()) {
                deck.add(new Card(i, suit));
            }
        }
        Collections.shuffle(deck);
        return deck;
    }

    public ArrayList<Card> giveCardsToPlayer(ArrayList<Card> playerCards, LinkedList<Card> deck) {
        int size = playerCards.size();
        for (int i = 0; i < 6 - size; i++) {
            playerCards.add(deck.poll());
        }
        return playerCards;
    }

    public boolean compareCards (Card beatPlayerCard, Card cardOnTable) {
        if (beatPlayerCard.getSuit() == cardOnTable.getSuit()) {
            return beatPlayerCard.getNumber() > cardOnTable.getNumber();
        }
        else {
            return beatPlayerCard.getSuit() == trump;
        }
    }

    public boolean addCardOnGamingDesk(Card card, LinkedList<Card> deskCards){
        for (Card deskCard : deskCards) {
            if (deskCard.getNumber() == card.getNumber()) {
                return true;
            }
        }
        return false;
    }
}

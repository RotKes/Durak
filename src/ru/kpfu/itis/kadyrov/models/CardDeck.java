package ru.kpfu.itis.kadyrov.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Амир on 28.12.2016.
 */
public class CardDeck {
    private List<Card> cards;
    private Suit trump;

    public Suit getTrump() {
        return trump;
    }

    public CardDeck(boolean empty) {
        cards = new ArrayList<>();
        if (!empty) {
            int id = 1;
            for (int i = 6; i < 15; i++) {
                cards.add(new Card(id, i, Suit.CLUBS));
                id++;
            }
            for (int i = 6; i < 15; i++) {
                cards.add(new Card(id, i, Suit.DIAMONDS));
                id++;
            }
            for (int i = 6; i < 15; i++) {
                cards.add(new Card(id, i, Suit.HEARTS));
                id++;
            }
            for (int i = 6; i < 15; i++) {
                cards.add(new Card(id, i, Suit.SPADES));
                id++;
            }
            this.trump = cards.get(cards.size() - 1).getSuit();
        }
    }

    public boolean isContainsId(int id) {
        for (Card card : cards) {
            if (card.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public Card draw() {//Вытащить карту из этой колоды
        Card draw = cards.get(0);
        cards.remove(0);
        return draw;
    }

    public Card getCardByIdAndRemoveFromDeck(int cardId) {
        Card neededCard = getCardById(cardId);
        boolean flag = false;
        for (int i = 0; i < cards.size() && !flag; i++) {
            if (cards.get(i).getId() == cardId) {
                cards.remove(cards.get(i));
                flag = true;
            }
        }
        return neededCard;
    }

    public Card getLastCard(){
        return cards.get(cards.size() - 1);
    }

    public void takeCard(CardDeck cardDeck, CardDeck discardPile) {//Взять карту из другой колоды и положить в эту
        if (cardDeck.getCards().size() == 0) {
            int i = discardPile.getCards().size();
            for (int j = 0; j < i; j++) {
                Card card = discardPile.draw();
                cardDeck.putCard(card);
            }
            cardDeck.reshuffle();
        }
        Card card = cardDeck.draw();
        this.putCard(card);
    }

    public void putCard(Card card) {//Положить карту в колоду
        cards.add(card);
    }

    public Card getCardById(int card_id) {
        for (Card card : cards) {
            if (card.getId() == card_id)
                return card;
        }
        return null;
    }

    public void playCardById(int card_id, CardDeck discardPile) {
        Card card = this.getCardById(card_id);
        discardPile.getCards().add(card);
        this.getCards().remove(card);
    }

    public List<Card> getCards() {
        return cards;
    }

    public void takeCardsFromTable(CardDeck tableCards){
        while (tableCards.getCards().size() != 0) {
            this.putCard(tableCards.getCards().remove(tableCards.getCards().size() - 1));
        }
    }

    public void reshuffle() {
        Collections.shuffle(cards);
    }

    public String showCards() {
        String message = "";
        for (Card card : getCards()) {
            message = message + card + " ";
        }
        if (message.equals("")) {
            message = message + " The desk is empty.";
        }
        return message;
    }
}

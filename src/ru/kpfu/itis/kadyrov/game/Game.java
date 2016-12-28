package ru.kpfu.itis.kadyrov.game;

import ru.kpfu.itis.kadyrov.models.Card;
import ru.kpfu.itis.kadyrov.models.CardDeck;
import ru.kpfu.itis.kadyrov.models.Suit;
import ru.kpfu.itis.kadyrov.models.User;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Амир on 14.12.2016.
 */
public class Game {
    public Suit trump;
    private List<User> users;
    private CardDeck deck = new CardDeck(false);
    private CardDeck deskCards = new CardDeck(true);


    public Suit getTrumpCard() {
        return trump;
    }

    public void setTrumpCard(Suit trump) {
        this.trump = trump;
    }

    public Game(Suit trump) {
        this.trump = trump;
    }


    public Game(List<User> users) {
        this.users = users;
    }


    public void play() throws IOException {
        //start game
        deck.reshuffle();

        //start hands
        for (User user : users) {
            for (int i = 0; i < 6; i++) {
                user.getHand().putCard(deck.draw());
            }
        }

        //first hands show
        for (User user : users) {
            user.handOut();
        }

        User player1 = users.get(0);
        User player2 = users.get(1);

        //Game loop
        while (player1.getHand().getCards().size() != 0 && player2.getHand().getCards().size() != 0) {
            if (!player1.isPickedUpCards()) {
                attack(player1, player2);
            }
            if (!player2.isPickedUpCards()) {
                attack(player2, player1);
            }
        }

    }


    // -----------------------------------------------------------------------------------------------


    public ArrayList<Card> giveCardsToPlayer(ArrayList<Card> playerCards, LinkedList<Card> deck) {
        int size = playerCards.size();
        for (int i = 0; i < 6 - size; i++) {
            playerCards.add(deck.poll());
        }
        return playerCards;
    }

    public boolean canBeatCard(Card beatPlayerCard, Card cardOnTable) {
        if (beatPlayerCard.getSuit() == cardOnTable.getSuit()) {
            return beatPlayerCard.getNumber() > cardOnTable.getNumber();
        } else {
            return beatPlayerCard.getSuit() == trump;
        }
    }

    public boolean addCardOnGamingDesk(Card card, CardDeck deskCards) {
        if (deskCards.getCards().size() == 0) {
            return true;
        }
        for (Card deskCard : deskCards.getCards()) {
            if (deskCard.getNumber() == card.getNumber()) {
                return true;
            }
        }
        return false;
    }

    public void attack(User player, User opponent) throws IOException {
        player.getOut().println("Your turn");
        player.getOut().println("Trump is " + trump);
        player.handOut();
        player.getOut().println("Cards on the desk: ");
        deskCards.showCard();
        BufferedReader in = player.getIn();
        boolean completeAttack = false;
        while (!completeAttack) {
            String message = in.readLine();
            if (message.contains("beat")) {
                deskCards.getCards().clear();
                completeAttack = true;
            } else {
                int id = Integer.parseInt(message);
                if (player.getHand().isContainsId(id)) {
                    Card card = player.getHand().getCardById(id);
                    if (addCardOnGamingDesk(card, deskCards)) {
                        deskCards.putCard(player.getHand().getCardByIdAndRemoveFromDeck(id));
                        completeAttack = true;
                    } else {
                        player.getOut().println("There is no such card rank in the table deck. Try again another card.");
                    }
                } else {
                    player.getOut().println("You have no such card. Try again.");
                }
            }
        }
        player.getOut().println("Wait for your opponent");
        defend(opponent, player);
    }

    public void defend(User player, User opponent) throws IOException {
        player.getOut().println("Your turn");
        player.getOut().println("Trump is " + trump);
        player.handOut();
        player.getOut().println("Cards on the desk: ");
        deskCards.showCard();
        BufferedReader in = player.getIn();
        boolean completeDefend = false;
        while (!completeDefend) {
            String message = in.readLine();
            if (message.contains("pick")) {
                player.getHand().takeCardsFromTable(deskCards);
                player.pickedUpCards(true);
                completeDefend = true;
            } else {
                int id = Integer.parseInt(message);
                if (player.getHand().isContainsId(id)) {
                    Card card = player.getHand().getCardById(id);
                    if (canBeatCard(card, deskCards.getLastCard())) {
                        deskCards.putCard(player.getHand().getCardByIdAndRemoveFromDeck(id));
                        completeDefend = true;
                        player.pickedUpCards(false);
                    } else {
                        player.getOut().println("Try again another card.");
                    }
                } else {
                    player.getOut().println("You have no such card. Try again.");
                }
            }
        }
        player.getOut().println("Wait for your opponent");
        attack(opponent, player);
    }
}

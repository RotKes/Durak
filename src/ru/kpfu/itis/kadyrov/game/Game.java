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
        deck.reshuffle();
        setTrumpCard(deck.getTrump());

        for (User user : users) {
            for (int i = 0; i < 6; i++) {
                user.getHand().putCard(deck.draw());
            }
        }

        for (User user : users) {
            user.handOut();
        }

        User player1 = users.get(0);
        User player2 = users.get(1);
        boolean finish = false;

        while (!finish) {
            if (!player1.isPickedUpCards()) {
                attack(player1, player2);
            }
            if (checkWinning(player1)) {
                finish = true;
                continue;
            }

            if (!player2.isPickedUpCards() && !checkWinning(player1)) {
                attack(player2, player1);
            }

            if (checkWinning(player2))
                finish = true;
        }

        if (checkWinning(player1)){
            player1.getOut().println("You won!");
            player2.getOut().println("You lost!");
        }
        else {
            player2.getOut().println("You won!");
            player1.getOut().println("You lost!");
        }

        player1.close();
        player2.close();
    }


    // -----------------------------------------------------------------------------------------------


    public void giveCardsToPlayer(User player, CardDeck cardDeck) {
        int size = player.getHand().getCards().size();
        for (int i = 0; i < 6 - size && cardDeck.getCards().size() != 0; i++) {
            player.getHand().getCards().add(cardDeck.draw());
        }
    }

    public boolean checkWinning(User player) {
        return player.getHand().getCards().size() == 0;
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
        player.getOut().println(deskCards.showCards());
        BufferedReader in = player.getIn();
        boolean completeAttack = false;
        boolean isBeat = false;
        if (player.getHand().getCards().size() == 0) {
            completeAttack = true;
        }
        while (!completeAttack && !isBeat) {
            String message = in.readLine();
            boolean canContinue = !(deskCards.getCards().size() > 12);
            if (canContinue) {
                if (message.toLowerCase().contains("beat")) {
                    deskCards.getCards().clear();
                    completeAttack = true;
                    isBeat = true;
                    giveCardsToPlayer(opponent, deck);
                    giveCardsToPlayer(player, deck);
                    player.getOut().println(deck.getCards().size() + " cards left in the deck");
                    opponent.getOut().println(deck.getCards().size() + " cards left in the deck");
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
            else {
                player.getOut().println("You can't place cards anymore.");
            }
        }
        player.getOut().println("Wait for your opponent");
        if (!isBeat) {
            defend(opponent, player);
        }
    }

    public void defend(User player, User opponent) throws IOException {
        player.getOut().println("Your turn");
        player.getOut().println("Trump is " + trump);
        player.handOut();
        player.getOut().println("Cards on the desk: ");
        player.getOut().println(deskCards.showCards());
        BufferedReader in = player.getIn();
        boolean completeDefend = false;
        while (!completeDefend) {
            String message = in.readLine();
            if (message.toLowerCase().contains("pick")) {
                player.getHand().takeCardsFromTable(deskCards);
                player.pickedUpCards(true);
                giveCardsToPlayer(opponent, deck);
                player.getOut().println(deck.getCards().size() + " cards left in the deck");
                opponent.getOut().println(deck.getCards().size() + " cards left in the deck");
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

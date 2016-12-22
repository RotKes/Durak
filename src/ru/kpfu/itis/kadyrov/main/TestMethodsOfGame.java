package ru.kpfu.itis.kadyrov.main;

import ru.kpfu.itis.kadyrov.game.Game;
import ru.kpfu.itis.kadyrov.models.Card;
import ru.kpfu.itis.kadyrov.models.Suit;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by Амир on 14.12.2016.
 */
public class TestMethodsOfGame {
    public static void main(String[] args) {
        // preparation to the game
        Game cardGame = new Game();
        LinkedList<Card> gamingDeck = cardGame.gamingDeckCreating();
        //creating trump card
        Suit trumpCard = gamingDeck.peekLast().getSuit();
        cardGame.setTrumpCard(trumpCard);
        //giving cards to player
        ArrayList<Card> myGamingCards = cardGame.giveCardsToPlayer(new ArrayList<Card>(), gamingDeck);
        ArrayList<Card> computerGamingCards = cardGame.giveCardsToPlayer(new ArrayList<Card>(), gamingDeck);
        LinkedList<Card> deskCards = new LinkedList<>();

        for (Card card : myGamingCards) {
            System.out.print(card + " ");
        }

        Scanner sc = new Scanner(System.in);
        Card card = new Card(sc.nextInt(), Suit.valueOf(sc.next()));
        deskCards.add(card);
    }
}
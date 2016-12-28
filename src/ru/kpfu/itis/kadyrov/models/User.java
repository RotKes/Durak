package ru.kpfu.itis.kadyrov.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Амир on 28.12.2016.
 */
public class User {
    private long id;
    private String name;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private CardDeck hand = new CardDeck(true);
    private boolean pickedUpCards = false;

    public CardDeck getHand() {
        return hand;
    }

    public void setHand(CardDeck hand) {
        this.hand = hand;
    }

    public User(long id, String name, Socket socket) {
        this.id = id;
        this.name = name;
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }

    public void close() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            System.err.println("Ошибка в методе close() user.");
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }

    public BufferedReader getIn() {
        return in;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }

    public void handOut(){
        out.println("Your current hand:");
        for (Card card : hand.getCards()) {
            out.print(card + " ");
        }
        out.println();
    }

    public boolean isPickedUpCards() {
        return pickedUpCards;
    }

    public void pickedUpCards(boolean pickedUpCards) {
        this.pickedUpCards = pickedUpCards;
    }
}

package ru.kpfu.itis.kadyrov.game.online;

import ru.kpfu.itis.kadyrov.game.Game;
import ru.kpfu.itis.kadyrov.models.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class Server extends Thread {

    public static void main(String[] args) {
        new Server();
    }


    private ServerSocket server;
    private boolean running = true;


    public Server() {
        try {
            server = new ServerSocket(3456);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (running) {
            List<User> users = new ArrayList<User>();
            while (users.size() < 2) {
                try {
                    Socket socket = server.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            socket.getInputStream()));
                    User user = new User(users.size(), socket);
                    users.add(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Game game = new Game(users);
            try {
                game.play();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopServer(){
        running = false;
    }
}

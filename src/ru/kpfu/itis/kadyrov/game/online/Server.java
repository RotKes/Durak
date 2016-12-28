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


public class Server {

    public static void main(String[] args) {
        new Server();
    }


    private ServerSocket server;


    public Server() {
        try {
            server = new ServerSocket(3456);

            while (true) {
                List<User> users = new ArrayList<User>();
                while (users.size() < 2) {
                    Socket socket = server.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            socket.getInputStream()));
                    User user = new User(users.size(), in.readLine(), socket);
                    users.add(user);
                }
                Game game = new Game(users);
                game.play();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

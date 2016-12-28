package ru.kpfu.itis.kadyrov.game.online;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Амир on 28.12.2016.
 */
public class Broadcast extends Thread {
    private BufferedReader in;
    private boolean stoped;

    public boolean isStoped() {
        return stoped;
    }

    public Broadcast(BufferedReader in) {
        this.in = in;
    }

    public void setStop() {
        stoped = true;
    }

    @Override
    public void run() {
        try {
            while (!stoped) {
                String str = in.readLine();
                if (str.equals("exit")){
                    break;
                }
                System.out.println(str);
            }

        } catch (IOException e) {
            System.err.println("Ошибка при получении сообщения.");
            e.printStackTrace();
        }
    }
}

package ru.kpfu.itis.kadyrov.gui.server;

import ru.kpfu.itis.kadyrov.game.online.Server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Амир on 28.12.2016.
 */
public class ServerGUI extends JFrame implements ActionListener {
    private JButton startServerButton;
    private JButton stopServerButton;
    private JLabel statusLabel;

    private Server server;

    public ServerGUI() {
        setTitle("Game Server GUI");
        setBounds(350, 200, 500, 500);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(null);
        startServerButton = new JButton("Start Server");
        startServerButton.setBounds(20, 30, 180, 40);
        startServerButton.addActionListener(this);

        stopServerButton = new JButton("Stop Server");
        stopServerButton.setBounds(280, 30, 180, 40);
        stopServerButton.addActionListener(this);

        statusLabel = new JLabel();
        statusLabel.setBounds(190, 140, 200, 25);

        getContentPane().add(statusLabel);
        getContentPane().add(startServerButton);
        getContentPane().add(stopServerButton);
        server = new Server();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startServerButton) {
            server.start();
            startServerButton.setEnabled(false);
            statusLabel.setText("Server is running.....");
        }

        if (e.getSource() == stopServerButton) {
            server.stopServer();
            statusLabel.setText("Server is stopping.....");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.exit(0);
        }
    }
}

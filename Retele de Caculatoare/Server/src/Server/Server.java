package Server;

import Common.Logger;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    /**
     * The port of the Server
     */
    private static final int portNumber = 1234;

    static List<ClientThread> onlineClients = new ArrayList<ClientThread>();

    public static void main(String... args){
        Logger.Log("Server started");
        ServerGUI serverGUI = new ServerGUI();
        JFrame serverFrame = new JFrame("Client Chat Login");
        serverFrame.setContentPane(serverGUI.panel);
        serverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        serverFrame.pack();
        serverFrame.setVisible(true);
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);

            while(true) {
                Socket newClient = serverSocket.accept();

                ClientThread newClientThread = new ClientThread(newClient);
                newClientThread.start();
                onlineClients.add(newClientThread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

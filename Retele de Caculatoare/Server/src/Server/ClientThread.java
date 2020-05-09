package Server;

import Common.Logger;
import Common.Request;
import Common.RequestType;

import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientThread implements Runnable{
    private Socket ClientSocket;
    private Thread t;
    ClientThread(Socket ClientSocket) {
        this.ClientSocket = ClientSocket;
    }
    ObjectOutputStream oos;
    @Override
    public void run() {
        System.out.println("Client Accepted");
        try {
            ObjectInputStream ois = new ObjectInputStream(ClientSocket.getInputStream());
            oos = new ObjectOutputStream(ClientSocket.getOutputStream());
            while(true) {
                Request receivedRequest = (Request) ois.readObject();
                switch(receivedRequest.getRequestType()) {
                    case LOGIN:
                        Logger.Log(receivedRequest);
                        LoginSuccessful(oos);
                        break;
                    case DISCONNECT:
                        Logger.Log(receivedRequest);
                        oos.close();
                        ClientSocket.close();
                        Server.onlineClients.remove(this);
                        return;
                    case MESSAGE:
                        Logger.Log(receivedRequest);
                        Broadcast(receivedRequest);
                        break;
                }
            }
        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Server.onlineClients.remove(this);
            try {
                oos.close();
                ClientSocket.close();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    private void Broadcast(Request receivedRequest) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String formattedDate = dateFormat.format(date);

        Server.onlineClients.parallelStream().forEach(clientThread -> {
            try {
                Request request = new Request(RequestType.BROADCAST,
                        formattedDate + " " + receivedRequest.getUsername() + ":" + receivedRequest.getRequestMessage());
                clientThread.oos.writeObject(request);
            }catch(Exception ex){
                Logger.Log(ex.toString());
            }
        });
    }

    private void LoginSuccessful(ObjectOutputStream oos) {
        Request request = new Request(RequestType.LOGINSUCCESSFUL);
        try {
            oos.writeObject(request);
        }catch(Exception ex){
            Logger.Log(ex.toString());
        }
    }

    void start(){
        if (t == null) {
            t = new Thread (this);
            t.start();
        }
    }


}

package Client;

import Common.Logger;
import Common.Request;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ReceiveMessageThread implements Runnable {

	private static Socket serverSocket;
	private static ObjectInputStream ois;
	private Thread t;

	public ReceiveMessageThread(Socket serverSocket) throws IOException {
		ois = new ObjectInputStream(serverSocket.getInputStream());
	}

	void start(){
		if (t == null) {
			t = new Thread (this);
			t.start();
		}
	}

	@Override
	public void run() {
		while(true) {
			try {
				Request receivedRequest = (Request) ois.readObject();
				switch(receivedRequest.getRequestType()) {
					case LOGINSUCCESSFUL:
						Logger.Log(receivedRequest);
						Client.loginFrame.setVisible(false);
						Client.loginFrame.dispose();
						Client.username = Client.loginGUI.tbUsername.getText();

						Client.clientFrame = new JFrame("Client Chat Login");
						Client.clientFrame.setContentPane(Client.clientGUI.panel);
						Client.clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						Client.clientFrame.pack();
						Client.clientFrame.setVisible(true);
						break;
					case BROADCAST:
						Client.clientGUI.taMessages.append(receivedRequest.getRequestMessage() + "\n");
						break;
				}
			} catch(IOException | ClassNotFoundException e) {
				Logger.Log(e.toString());
				JOptionPane.showMessageDialog(null,"Cannot connect to server");
				System.exit(1);
			}
		}
	}
}

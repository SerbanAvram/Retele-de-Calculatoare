package Client;

import Common.Request;
import Common.RequestType;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
	static LoginGUI loginGUI;
	static ClientGUI clientGUI;
	static JFrame loginFrame;
	static JFrame clientFrame;
	static String username;
	private static Socket serverSocket;
	private static ObjectOutputStream oos;

	public static void main(String args[]) throws IOException {
		try {
			serverSocket = new Socket(InetAddress.getLocalHost(), 1234);
			oos = new ObjectOutputStream(serverSocket.getOutputStream());
			ReceiveMessageThread receiveMessageThread = new ReceiveMessageThread(serverSocket);
			receiveMessageThread.start();
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "Could not connect to server");
			System.exit(1);
		}

		loginGUI = new LoginGUI();
		clientGUI = new ClientGUI();

		loginFrame = new JFrame("Client Chat Login");
		loginFrame.setContentPane(loginGUI.panel);
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.pack();
		loginFrame.setVisible(true);

	}

	static void SendRequest(Request request) throws IOException {
		try {
			switch(request.getRequestType()) {
				case LOGIN:
					if(request.getRequestMessage().isBlank())
						return;
					oos.writeObject(request);
					break;
				case DISCONNECT:
					oos.writeObject(request);
					break;
				case MESSAGE:
					if(request.getRequestMessage().isBlank())
						return;
					request.setUsername(username);
					oos.writeObject(request);
					break;
			}
		} catch(Exception ex) {
			if(request.getRequestType()!= RequestType.DISCONNECT) {
				JOptionPane.showMessageDialog(null, "Error on sending request");
				System.exit(1);
			}
		}
	}
}

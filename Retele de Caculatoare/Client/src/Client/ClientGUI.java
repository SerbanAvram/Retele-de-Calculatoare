package Client;

import Common.Logger;
import Common.Request;
import Common.RequestType;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class ClientGUI {
	JPanel panel;
	JTextArea taMessages;
	JButton bntSend;
	JTextArea taMessage;

	public ClientGUI() {

		bntSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				Request request = new Request(RequestType.MESSAGE,taMessage.getText());
				try {
					Client.SendRequest(request);
				} catch(IOException e) {
					Logger.Log(e.toString());
				}
				taMessage.setText("");
			}
		});

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			try {
				Request request = new Request(RequestType.DISCONNECT);
				Client.SendRequest(request);
			}catch(Exception ex){
				Logger.Log(ex.toString());
				System.exit(1);
			}
		}));
	}
}

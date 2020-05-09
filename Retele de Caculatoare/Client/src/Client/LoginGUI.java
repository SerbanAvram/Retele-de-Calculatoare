package Client;

import Common.Logger;
import Common.Request;
import Common.RequestType;
import javafx.application.Application;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginGUI {
	JTextField tbUsername;
	JButton btnLogin;
	JPanel panel;

	public LoginGUI() {
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				Request request = new Request(RequestType.LOGIN, tbUsername.getText());
				try {
					Client.SendRequest(request);
				} catch(IOException e) {
					Logger.Log(e.toString());
				}
			}
		});

		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			@Override
			public void run()
			{
				try {
					Request request = new Request(RequestType.DISCONNECT);
					Client.SendRequest(request);
				}catch(Exception ex) {
					Logger.Log(ex.toString());
				}
			}
		});
	}
}

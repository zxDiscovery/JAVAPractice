package com.zxxa.nettystudy.s02;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientFrame extends Frame{
	
	public static ClientFrame INSTANCE = new ClientFrame();
	
	TextArea ta = new TextArea();
	TextField tf = new TextField();
	Client client = null;
	
	private ClientFrame() {
		this.setSize(400, 400);
		this.setLocation(100, 20);
		this.add(ta, BorderLayout.CENTER);
		this.add(tf, BorderLayout.SOUTH);
		
		tf.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				client.send(tf.getText());
//				ta.setText(ta.getText()+tf.getText());
				tf.setText("");
			}
			
		});
		
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				client.closeconnect();
				System.exit(0);
			}
		});
		
	}
	
	private void connectToServer() {
		client = new Client();
		try {
			client.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ClientFrame clientFrame = ClientFrame.INSTANCE;
		clientFrame.setVisible(true);
		clientFrame.connectToServer();
	}
	
	public void updateText(String msgAccepted) {
		this.ta.setText(ta.getText() + System.getProperty("line.separator") + msgAccepted);
	}
}

package com.zxxa.tank;

import com.zxxa.tank.net.Client;

public class Main {
	public static void main(String[] args) {

		TankFrame tf = TankFrame.INSTANCE;
		tf.setVisible(true);
		
//		new Thread(()->new Audio("audio/war1.wav").loop()).start();
		
		
		new Thread(()->{
			while(true) {
				try {
				Thread.sleep(50);
				}
				catch (Exception e){
					
				}
				tf.repaint();
			}
		}).start();
		
		Client c = Client.INSTANCE;
		c.connect();
	}
	
}

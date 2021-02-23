package com.zxxa.tank;



public class Main {
	public static void main(String[] args) {

		TankFrame tf = new TankFrame();
		
		while(true) {
			try {
			Thread.sleep(50);
			}
			catch (Exception e){
				
			}
			tf.repaint();
		}
		
	}
	
}

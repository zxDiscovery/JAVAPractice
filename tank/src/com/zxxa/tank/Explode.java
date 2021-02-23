package com.zxxa.tank;

import java.awt.Color;
import java.awt.Graphics;

import com.zxxa.tank.abstractfactory.BaseExplode;

public class Explode extends BaseExplode{
	private int x, y;
	private int step = 0;
	public static int HEIGHT = ResourceMgr.explodes[0].getHeight();
	private TankFrame tf = null;
//    private boolean isliving = true;

	public Explode(int x, int y, TankFrame tf) {
		this.x = x;
		this.y = y;
		this.tf = tf;
		
		new Thread(()->new Audio("audio/explode.wav").play()).start();
		
	}
	
	public static int getHEIGHT() {
		return HEIGHT;
	}

	public static int WIDTH = ResourceMgr.explodes[0].getWidth();

	public static int getWIDTH() {
		return WIDTH;
	}
	
	public void paint(Graphics g) {
		
		Color explodeColor = g.getColor();
		g.drawImage(ResourceMgr.explodes[step++], x, y, null);
		
		if (step >= ResourceMgr.explodes.length) {
			tf.explodes.remove(this);
		}
	}
}

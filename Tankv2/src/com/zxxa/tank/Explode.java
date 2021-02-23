package com.zxxa.tank;

import java.awt.Color;
import java.awt.Graphics;

import com.zxxa.tank.abstractfactory.BaseExplode;

public class Explode extends GameObject{
	private int step = 0;
	public static int HEIGHT = ResourceMgr.explodes[0].getHeight();
	public static int WIDTH = ResourceMgr.explodes[0].getWidth();

//    private boolean isliving = true;

	public Explode(int x, int y) {
		this.x = x;
		this.y = y;
		
		GameModel.getInstence().add(this);
		
		new Thread(()->new Audio("audio/explode.wav").play()).start();
		
	}
	
	public int getHeight() {
		return HEIGHT;
	}

	public int getWidth() {
		return WIDTH;
	}
	
	public void paint(Graphics g) {
		
		Color explodeColor = g.getColor();
		g.drawImage(ResourceMgr.explodes[step++], x, y, null);
		
		if (step >= ResourceMgr.explodes.length) {
			GameModel.getInstence().remove(this);
		}
	}
}

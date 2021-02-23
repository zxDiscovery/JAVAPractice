package com.zxxa.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Wall extends GameObject {
	
	int x, y, h, w;
	
	public Rectangle rect;
	
	public Wall(int x, int y, int w, int h){
		this.x = x;
		this.y = y;
		this.h = h;
		this.w = w;
		
		this.rect = new Rectangle();
		
		rect.x = x;
		rect.y = y;
		rect.height = h;
		rect.width = w;
		
		GameModel.getInstence().add(this);
	}

	@Override
	public void paint(Graphics g) {

		Color c = g.getColor();
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, w, h);
		g.setColor(c);
	}

	public Rectangle getRect() {
		return rect;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return w;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return h;
	}

}

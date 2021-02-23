package com.zxxa.tank.decorator;

import java.awt.Color;
import java.awt.Graphics;

import com.zxxa.tank.GameObject;

public class TailDecorator extends GoDecorator {

	public TailDecorator(GameObject go) {
		super(go);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		this.x = go.x;
		this.y = go.y;
		
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.drawLine(go.x, go.y, go.x+10, go.y+10);
		g.setColor(c);
	}

	@Override
	public int getWidth() {
		return super.go.getWidth();
	}

	@Override
	public int getHeight() {
		return super.go.getHeight();
	}

}

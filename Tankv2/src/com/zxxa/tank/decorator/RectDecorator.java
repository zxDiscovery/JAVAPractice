package com.zxxa.tank.decorator;

import java.awt.Color;
import java.awt.Graphics;

import com.zxxa.tank.GameObject;

public class RectDecorator extends GoDecorator {

	public RectDecorator(GameObject go) {
		super(go);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		this.x = go.x;
		this.y = go.y;
		
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.drawRect(go.x, go.y, go.getWidth(), go.getHeight());
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

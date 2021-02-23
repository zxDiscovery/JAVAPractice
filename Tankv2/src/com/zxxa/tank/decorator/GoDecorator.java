package com.zxxa.tank.decorator;

import java.awt.Graphics;

import com.zxxa.tank.GameObject;

public abstract class GoDecorator extends GameObject{
	GameObject go;
	
	public GoDecorator(GameObject go) {
		this.go = go;
	}

	@Override
	public void paint(Graphics g) {
		go.paint(g);
	}
}

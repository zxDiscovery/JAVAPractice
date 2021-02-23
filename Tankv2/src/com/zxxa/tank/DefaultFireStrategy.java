package com.zxxa.tank;

import com.zxxa.tank.decorator.RectDecorator;
import com.zxxa.tank.decorator.TailDecorator;

public class DefaultFireStrategy implements FireStrategy{
	@Override
	public void fire (Tank t) {
		int bX = t.getX() + Tank.WIDTH/2 - Bullet.WIDTH/2;
		int bY = t.getY() + Tank.HEIGHT/2 - Bullet.HEIGHT/2;
		
		//GameModel.getInstence().add((new RectDecorator(new TailDecorator(new Bullet(bX, bY, t.getDir(), t.getGroup())))));

		new Bullet(bX, bY, t.getDir(), t.getGroup());
		
		if (t.getGroup() == Group.GOOD) {
			new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
		}
	}
}

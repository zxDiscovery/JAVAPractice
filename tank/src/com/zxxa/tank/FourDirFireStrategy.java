package com.zxxa.tank;

public class FourDirFireStrategy implements FireStrategy {
	@Override
	public void fire (Tank t) {
		int bX = t.getX() + Tank.WIDTH/2 - Bullet.WIDTH/2;
		int bY = t.getY() + Tank.HEIGHT/2 - Bullet.HEIGHT/2;
		
		Dir[] dirs = Dir.values();
		
		for (Dir dir: dirs) {
			new Bullet(bX, bY, dir, t.getGroup(), t.getTF());
		}

		if (t.getGroup() == Group.GOOD) {
			new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
		}
	}
}

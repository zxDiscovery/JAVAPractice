package com.zxxa.tank.abstractfactory;

import com.zxxa.tank.Bullet;
import com.zxxa.tank.Dir;
import com.zxxa.tank.Explode;
import com.zxxa.tank.Group;
import com.zxxa.tank.Tank;
import com.zxxa.tank.TankFrame;

public class DefaultFactory extends GameFactory {


	@Override
	public BaseTank createTank(int x, int y, Dir dir, Group group, TankFrame TF) {
		return new Tank(x, y, dir, group, TF);
	}

	@Override
	public BaseExplode createExplode(int x, int y, TankFrame tf) {
		return new Explode(x, y, tf);
	}

	@Override
	public BaseBullet createBullet(int x, int y, Dir dir, Group group, TankFrame tf) {
		return new Bullet(x, y, dir, group, tf);
	}
}

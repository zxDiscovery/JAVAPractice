package com.zxxa.tank.abstractfactory;

import com.zxxa.tank.Bullet;
import com.zxxa.tank.Dir;
import com.zxxa.tank.Explode;
import com.zxxa.tank.GameModel;
import com.zxxa.tank.Group;
import com.zxxa.tank.Tank;
import com.zxxa.tank.TankFrame;

public class DefaultFactory extends GameFactory {


	@Override
	public BaseTank createTank(int x, int y, Dir dir, Group group, GameModel gm) {
		return null; //new Tank(x, y, dir, group, gm);
	}

	@Override
	public BaseExplode createExplode(int x, int y, GameModel gm) {
		return null; //new Explode(x, y, gm);
	}

	@Override
	public BaseBullet createBullet(int x, int y, Dir dir, Group group, GameModel gm) {
		return null; //new Bullet(x, y, dir, group, gm);
	}
}

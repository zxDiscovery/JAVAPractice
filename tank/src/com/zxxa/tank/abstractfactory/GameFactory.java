package com.zxxa.tank.abstractfactory;

import com.zxxa.tank.Dir;
import com.zxxa.tank.Group;
import com.zxxa.tank.TankFrame;

public abstract class GameFactory {
	public abstract BaseTank createTank(int x, int y, Dir dir, Group group, TankFrame TF);
	public abstract BaseExplode createExplode(int x, int y, TankFrame tf);
	public abstract BaseBullet createBullet(int x, int y, Dir dir, Group group, TankFrame tf);
}

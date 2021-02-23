package com.zxxa.tank.abstractfactory;

import java.io.Serializable;

import com.zxxa.tank.Dir;
import com.zxxa.tank.GameModel;
import com.zxxa.tank.Group;

public abstract class GameFactory {
	public abstract BaseTank createTank(int x, int y, Dir dir, Group group, GameModel gm);
	public abstract BaseExplode createExplode(int x, int y, GameModel gm);
	public abstract BaseBullet createBullet(int x, int y, Dir dir, Group group, GameModel gm);
}

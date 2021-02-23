package com.zxxa.tank.colliderofresponsibility;

import com.zxxa.tank.Bullet;
import com.zxxa.tank.Explode;
import com.zxxa.tank.GameModel;
import com.zxxa.tank.GameObject;
import com.zxxa.tank.Tank;

public class TankTankCollider implements Collide {

	@Override
	public boolean collide(GameObject go1, GameObject go2) {

		if (go1 instanceof Tank && go2 instanceof Tank ) {
			Tank t1 = (Tank)go1;
			Tank t2 = (Tank)go2;

					
			if (t1.getRect().intersects(t2.getRect())) {
				t1.resetDir();
				t2.resetDir();
			}
		}
		return true;
	}

}

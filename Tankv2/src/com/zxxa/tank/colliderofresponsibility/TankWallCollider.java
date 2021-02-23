package com.zxxa.tank.colliderofresponsibility;

import com.zxxa.tank.GameModel;
import com.zxxa.tank.GameObject;
import com.zxxa.tank.Tank;
import com.zxxa.tank.Wall;

public class TankWallCollider implements Collide {

	@Override
	public boolean collide(GameObject go1, GameObject go2) {

		if (go1 instanceof Tank && go2 instanceof Wall ) {
			Tank t = (Tank)go1;
			Wall w = (Wall)go2;

					
			if (t.getRect().intersects(w.getRect())) {
				t.resetDir();
			}
		} else if (go1 instanceof Wall && go2 instanceof Tank) {
			Tank t = (Tank)go2;
			Wall w = (Wall)go1;

			if (t.getRect().intersects(w.getRect())) {
				t.resetDir();
			}
		}
		return true;
	}

}

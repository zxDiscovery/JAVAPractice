package com.zxxa.tank.colliderofresponsibility;

import com.zxxa.tank.Bullet;
import com.zxxa.tank.Explode;
import com.zxxa.tank.GameModel;
import com.zxxa.tank.GameObject;
import com.zxxa.tank.Tank;
import com.zxxa.tank.Wall;

public class BulletWallCollider implements Collide {

	@Override
	public boolean collide(GameObject go1, GameObject go2) {

		if (go1 instanceof Bullet && go2 instanceof Wall ) {
			Bullet b = (Bullet)go1;
			Wall w = (Wall)go2;
					
			if (b.getRect().intersects(w.getRect())) {
				b.die();
			}
		} else if (go1 instanceof Wall && go2 instanceof Bullet) {
			return collide(go2, go1);
		}  
		return true;
	}

}

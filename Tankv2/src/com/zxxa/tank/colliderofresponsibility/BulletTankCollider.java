package com.zxxa.tank.colliderofresponsibility;

import com.zxxa.tank.Bullet;
import com.zxxa.tank.Explode;
import com.zxxa.tank.GameModel;
import com.zxxa.tank.GameObject;
import com.zxxa.tank.Tank;

public class BulletTankCollider implements Collide {

	@Override
	public boolean collide(GameObject go1, GameObject go2) {

		if (go1 instanceof Bullet && go2 instanceof Tank ) {
			Bullet b = (Bullet)go1;
			Tank t = (Tank)go2;
			if (b.getGroup() == t.getGroup()) {
				return true;
			}
					
			if (b.getRect().intersects(t.getRect())) {
				t.die();
				b.die();
				
			    int Ex = t.getX() + t.WIDTH/2 - Explode.WIDTH/2;
			    int Ey = t.getY() + t.HEIGHT/2 - Explode.HEIGHT/2;		
				
				new Explode(Ex, Ey);
			}
			return false;
		} else if (go1 instanceof Tank && go2 instanceof Bullet) {
			return collide(go2, go1);
		}  
		return true;
	}

}

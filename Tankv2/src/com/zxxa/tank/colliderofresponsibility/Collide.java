package com.zxxa.tank.colliderofresponsibility;

import java.io.Serializable;

import com.zxxa.tank.GameModel;
import com.zxxa.tank.GameObject;

public interface Collide {
	boolean collide(GameObject go1, GameObject go2);
}

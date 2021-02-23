package com.zxxa.tank.colliderofresponsibility;

import java.util.LinkedList;
import java.util.List;

import com.zxxa.tank.GameModel;
import com.zxxa.tank.GameObject;

public class CollideChain implements Collide{
	private List<Collide> colliders = new LinkedList<>();
	

	public void addChain(Collide co) {
		colliders.add(co);
	}

	@Override
	public boolean collide(GameObject go1, GameObject go2) {
		for(Collide co: colliders) {
			if (!co.collide(go1, go2)) {
				return false; 
			}
		}
		
		return true;
	}
}

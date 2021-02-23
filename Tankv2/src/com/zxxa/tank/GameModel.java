package com.zxxa.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.zxxa.tank.abstractfactory.BaseExplode;
import com.zxxa.tank.abstractfactory.DefaultFactory;
import com.zxxa.tank.abstractfactory.GameFactory;
import com.zxxa.tank.colliderofresponsibility.BulletTankCollider;
import com.zxxa.tank.colliderofresponsibility.Collide;
import com.zxxa.tank.colliderofresponsibility.CollideChain;
import com.zxxa.tank.colliderofresponsibility.TankTankCollider;

public class GameModel{
	private static final GameModel INSTANCE = new GameModel();
	
	static {
		INSTANCE.init();
	}
    
	Tank myTank;
	
    List<GameObject> gameObjects = new ArrayList<>();
    
    CollideChain Collides = new CollideChain();
    
    GameFactory gf = new DefaultFactory();
    
    public static GameModel getInstence() {
    	return INSTANCE;
    }
	
	private void init() {
		myTank = new Tank(300, 300, Dir.DOWN, Group.GOOD);
		int initTankCount = Integer.parseInt((String)PropertyMgr.get("initTankCount"));
		
		for (int i=0; i<initTankCount; i++) {
			new Tank(100+i*70, 50, Dir.DOWN, Group.BAD);
		}
		
		new Wall(300, 300, 200, 50);
		
	}

	private GameModel() {
				
		String collidesName = (String)PropertyMgr.get("collides");
		
		String collides[] = collidesName.split(",");
		
				
		for (String collide: collides) {
			try {
				Collides.addChain((Collide)Class.forName(collide).getDeclaredConstructor().newInstance());
				System.out.println(collide);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
    
	public void add(GameObject go) {
		gameObjects.add(go);
	}
	
	public void remove(GameObject go) {
		gameObjects.remove(go);
	}
	
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.WHITE);
//		g.drawString("The number of bulltes:" + bullets.size(), 10, 40);
//		g.drawString("The number of enemytanks:"+ enemyTanks.size(), 10, 50);
//		g.drawString("The number of explodes:"+ explodes.size(), 10, 60);
		g.setColor(c);
		
		myTank.paint(g);
		
		for (int i = 0; i < gameObjects.size(); i++) {
			gameObjects.get(i).paint(g);
		}
		
		for (int i = 0; i < gameObjects.size(); i++) {
			for (int j = i + 1; j < gameObjects.size(); j++) {
				GameObject go1 = gameObjects.get(i);
				GameObject go2 = gameObjects.get(j);
				
				Collides.collide(go1, go2);
			}
		}
		
	}
	
	public void save() {
		File f = new File("/Users/zxxa/Downloads/test/tank.data");
		
		ObjectOutputStream oos = null;
		
		try {
			oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(myTank);
			oos.writeObject(gameObjects);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void load() {
		File f = new File("/Users/zxxa/Downloads/test/tank.data");
		ObjectInputStream ois  = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(f));
		
			myTank = (Tank)ois.readObject();
			gameObjects = (List)ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
}

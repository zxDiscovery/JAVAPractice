package com.zxxa.tank;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import com.zxxa.tank.abstractfactory.BaseExplode;
import com.zxxa.tank.abstractfactory.DefaultFactory;
import com.zxxa.tank.abstractfactory.GameFactory;
import com.zxxa.tank.net.Client;
import com.zxxa.tank.net.TankDirMsg;
import com.zxxa.tank.net.TankFireMsg;

public class TankFrame extends Frame {
	
	public static final TankFrame INSTANCE = new TankFrame();
	
	static final int GAME_WIDTH = 800, GAME_HIGHT = 800;
	
	Random r = new Random();
	
    Tank myTank = new Tank(r.nextInt(GAME_WIDTH - Tank.WIDTH), r.nextInt(GAME_HIGHT - Tank.HEIGHT), Dir.DOWN, Group.GOOD, this);
    
    List<Bullet> bullets = new ArrayList<>();
    
    Map<UUID, Tank> enemyTanks = new HashMap<>();
    
	List<BaseExplode> explodes = new ArrayList<>();
    
    GameFactory gf = new DefaultFactory();
	
    public Map<UUID, Tank> getEnemyTanks() {
		return enemyTanks;
	}
    
	private TankFrame() {
		setSize(GAME_WIDTH,GAME_HIGHT);
		setResizable(false);
		setTitle("Tank word");
		setVisible(true);
		
		this.addKeyListener(new MyKeyListener());
		
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
/*		
		int initTankCount = Integer.parseInt((String)PropertyMgr.get("initTankCount"));
		
    	for (int i=0; i<initTankCount; i++) {
    		enemyTanks.add(new Tank(100+i*50, 50, Dir.DOWN, Group.BAD, this));
    	}*/
	}
	
	Image offScreenImage = null;
	@Override
	public void update(Graphics g) {
		if(offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH,GAME_HIGHT);
		}
		
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	@Override
	public void paint(Graphics g) {
		
		Color c = g.getColor();
		g.setColor(Color.WHITE);
		g.drawString("The number of bulltes:" + bullets.size(), 10, 40);
		g.drawString("The number of enemytanks:"+ enemyTanks.size(), 10, 50);
		g.drawString("The number of explodes:"+ explodes.size(), 10, 60);
		g.setColor(c);
		
		myTank.paint(g);
		
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).paint(g);
		}
		
		enemyTanks.values().stream().forEach((e)->e.paint(g));
/*		
		for (int i = 0; i < enemyTanks.size(); i++) {
			enemyTanks.get(i).paint(g);
		}*/
		Iterator<Map.Entry<UUID, Tank>> it = enemyTanks.entrySet().iterator();
		
		for (int i = 0; i < bullets.size(); i++) {
			
			for (Tank value : enemyTanks.values()) {
				bullets.get(i).collideWith(value);
			}
		}
		
		for (int i = 0; i < explodes.size(); i++) {
			explodes.get(i).paint(g);
		}
	}
	
	class MyKeyListener extends KeyAdapter{

		boolean bU = false;
		boolean bD = false;
		boolean bR = false;
		boolean bL = false;
		
		private void setMainTankDir() {
			
			if (!bL && !bU && !bR && !bD) {
				myTank.setMoving(false);
			} else {
				myTank.setMoving(true);
				if (bL) myTank.setDir(Dir.LEFT); 
				if (bU) myTank.setDir(Dir.UP); 
				if (bR) myTank.setDir(Dir.RIGHT); 
				if (bD) myTank.setDir(Dir.DOWN); 
			}	
			Client.INSTANCE.send(new TankDirMsg(myTank));
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			switch (key) {
			case KeyEvent.VK_LEFT:
				bL = true;
				break;
			case KeyEvent.VK_RIGHT:
				bR = true;
				break;
			case KeyEvent.VK_UP:
				bU = true;
				break;
			case KeyEvent.VK_DOWN:
				bD = true;
				break;
			default:
				break;
			}
			
			setMainTankDir();

		}


		@Override
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			switch (key) {
			case KeyEvent.VK_LEFT:
				bL = false;
				break;
			case KeyEvent.VK_RIGHT:
				bR = false;
				break;
			case KeyEvent.VK_UP:
				bU = false;
				break;
			case KeyEvent.VK_DOWN:
				bD = false;
				break;
			case KeyEvent.VK_CONTROL:
				myTank.fire();
				break;
			default:
				break;
			}
			

			
			setMainTankDir();
		}
		
	}
	
	public Tank getMainTank() {
		return this.myTank;
	}

	public Tank findByUuid(UUID id) {
			
		return enemyTanks.get(id);
	}

	public void addTank(Tank t) {
		enemyTanks.put(t.getUuid(), t);
	}
}

package com.zxxa.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.UUID;

import com.zxxa.tank.abstractfactory.BaseTank;
import com.zxxa.tank.net.TankJoinMsg;

public class Tank extends BaseTank{
	private int x, y;
	private Dir dir = Dir.DOWN;
	private static final int SPEED = 5;
	private TankFrame TF = null;

	Rectangle rect = new Rectangle();
	private boolean moving = false;
	private boolean islive = true;
	private Random random = new Random();
	private Group group = Group.BAD;
	
	public UUID uuid = UUID.randomUUID();

	public static int WIDTH = ResourceMgr.GoodtankU.getWidth();
	public static int HEIGHT = ResourceMgr.GoodtankU.getHeight();
	
	FireStrategy fs;
	
	public UUID getUuid() {
		return uuid;
	}
	
	public TankFrame getTF() {
		return TF;
	}
	
	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}
	
	public int setY(int y) {
		return y;
	}

	public int setX(int x) {
		return x;
	}

	public Group getGroup() {
		return group;
	}
	
	public Tank(TankJoinMsg t) {
		this.x = t.x;
		this.y = t.y;
		this.dir = t.dir;
		this.group = t.group;
		this.moving = t.moving;
		this.TF = TankFrame.INSTANCE;
		this.uuid = t.id;
		
		rect.x = x;
		rect.y = y;
		rect.height = HEIGHT;
		rect.width = WIDTH;
		
		if (group == Group.GOOD) {
			
			fs = new DefaultFireStrategy();
			/*
			String goodFSName = (String)PropertyMgr.get("goodFs");
		
			try {
				fs = (FireStrategy)Class.forName(goodFSName).getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
*/
		} else {
			String BadFSName = (String)PropertyMgr.get("badFs");
	
			try {
				fs = (FireStrategy)Class.forName(BadFSName).getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public Tank(int x, int y, Dir dir, Group group, TankFrame TF) {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.TF = TF;
		this.group = group;
		
		rect.x = x;
		rect.y = y;
		rect.height = HEIGHT;
		rect.width = WIDTH;
		
		if (group == Group.GOOD) {
			
			fs = new DefaultFireStrategy();
			/*
			String goodFSName = (String)PropertyMgr.get("goodFs");
		
			try {
				fs = (FireStrategy)Class.forName(goodFSName).getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
*/
		} else {
			String BadFSName = (String)PropertyMgr.get("badFs");
	
			try {
				fs = (FireStrategy)Class.forName(BadFSName).getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void paint(Graphics g) {
		
		if (!this.islive) {
		    TF.enemyTanks.remove(this);
		}

		Color c = g.getColor();
		g.setColor(Color.WHITE);
		g.drawString(uuid.toString(), this.x, this.y-10);
		g.setColor(c);
		
		switch(dir) {
		case LEFT:
			g.drawImage(this.group == Group.GOOD?ResourceMgr.GoodtankL:ResourceMgr.BadtankL , x, y, null);
			break;
		case DOWN:
			g.drawImage(this.group == Group.GOOD?ResourceMgr.GoodtankD:ResourceMgr.BadtankD, x, y, null);
			break;
		case UP:
			g.drawImage(this.group == Group.GOOD?ResourceMgr.GoodtankU:ResourceMgr.BadtankU, x, y, null);
			break;
		case RIGHT:
			g.drawImage(this.group == Group.GOOD?ResourceMgr.GoodtankR:ResourceMgr.BadtankR, x, y, null);
			break;
		default:
			break;
		}
		
/*		
		g.setColor(Color.YELLOW);
		g.fillRect(this.x, this.y, 50,50);
		g.setColor(c);
*/		
		move();
	}
	
	private void move() {
		if (!moving) {
			return;
		}
		
		switch(dir) {
		case LEFT:
			x -= SPEED;
			break;
		case DOWN:
			y += SPEED;
			break;
		case UP:
			y -= SPEED;
			break;
		case RIGHT:
			x += SPEED;
			break;
		default:
			break;
		}
		
		rect.x = this.x;
		rect.y = this.y;
/*	
		if (this.group == Group.BAD && random.nextInt(100) > 95) {
			this.fire();
		}
		if (this.group == Group.BAD && random.nextInt(10) > 8) {
			randomDir();
		}*/
		
		boundsCheck();
		
	}

	private void boundsCheck() {
		if (this.x < 5) {
			this.x = 5;
		}
		if (this.y < 5) {
			this.y = 5;
		}
		if (this.x > TankFrame.GAME_WIDTH - Tank.WIDTH - 5) {
			this.x = TankFrame.GAME_WIDTH - Tank.WIDTH - 5;
		}
		if (this.y > TankFrame.GAME_HIGHT - Tank.HEIGHT - 5) {
			this.y = TankFrame.GAME_HIGHT - Tank.HEIGHT - 5;
		}
		
	}

	private void randomDir() {
		this.dir = Dir.values()[random.nextInt(4)];
	}

	public Dir getDir() {
		return dir;
	}

	public void setDir(Dir dir) {
		this.dir = dir;
	}
	
	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public void fire() {
		fs.fire(this);
	}

	public void die() {
		this.islive = false;
	}
}

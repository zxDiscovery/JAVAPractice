package com.zxxa.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.zxxa.tank.abstractfactory.BaseTank;
import com.zxxa.tank.observer.DefaultFire;
import com.zxxa.tank.observer.Observer;
import com.zxxa.tank.observer.fireEvent;

public class Tank extends GameObject{
	private int oldX, oldY;
	private Dir dir = Dir.DOWN;
	private static final int SPEED = 5;

	Rectangle rect = new Rectangle();

	private boolean moving = true;
	private boolean islive = true;
	private Random random = new Random();
	private Group group = Group.BAD;

	public static int WIDTH = ResourceMgr.GoodtankU.getWidth();
	public static int HEIGHT = ResourceMgr.GoodtankU.getHeight();
	
//	FireStrategy fs;
	
	public List<Observer> observers = new ArrayList<>();
	
	public Rectangle getRect() {
		return rect;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

	public Group getGroup() {
		return group;
	}
	
	public Tank(int x, int y, Dir dir, Group group) {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.group = group;
		
		rect.x = x;
		rect.y = y;
		rect.height = HEIGHT;
		rect.width = WIDTH;
		
		GameModel.getInstence().add(this);
		
		observers.add(new DefaultFire());
/*		
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

		} else {
			String BadFSName = (String)PropertyMgr.get("badFs");
	
			try {
				fs = (FireStrategy)Class.forName(BadFSName).getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
	}
	
	@Override
	public void paint(Graphics g) {
		
		if (!this.islive) {
		    GameModel.getInstence().remove(this);
		}

		Color c = g.getColor();
		
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
		
		this.oldX = x;
		this.oldY = y;
		
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
	
		if (this.group == Group.BAD && random.nextInt(100) > 95) {
			this.fire();
		}
		if (this.group == Group.BAD && random.nextInt(10) > 8) {
			randomDir();
		}
		
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
		
		fireEvent event = new fireEvent(this);
		
		for (Observer observer: observers) {
			observer.actionOnFire(event);
		}
//		fs.fire(this);
	}

	public void die() {
		this.islive = false;
	}
	
	public void resetDir() {
		this.setX(oldX);
		this.setY(oldY);
	}

	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return HEIGHT;
	}
}

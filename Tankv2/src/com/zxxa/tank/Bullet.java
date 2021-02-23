package com.zxxa.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.zxxa.tank.abstractfactory.BaseBullet;

public class Bullet extends GameObject{

	private Dir dir = Dir.DOWN;
	private static final int SPEED = 10;
	private boolean live = true;
	public static int HEIGHT = ResourceMgr.bulletU.getHeight();
	public static int WIDTH = ResourceMgr.bulletU.getWidth();
	private Group group = Group.BAD;
	
	Rectangle rect = new Rectangle();

	public Rectangle getRect() {
		return rect;
	}

	public Group getGroup() {
		return group;
	}
	
	public Bullet(int x, int y, Dir dir, Group group) {
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
	}
	
	
	public boolean isLive() {
		return live;
	}


	public void setLive(boolean live) {
		this.live = live;
	}

	public void paint(Graphics g) {
		
		if (!this.live) {
			GameModel.getInstence().remove(this);
		}

		Color bulletColor = g.getColor();
		
		switch(dir) {
		case LEFT:
			g.drawImage(ResourceMgr.bulletL, x, y, null);
			break;
		case DOWN:
			g.drawImage(ResourceMgr.bulletD, x, y, null);
			break;
		case UP:
			g.drawImage(ResourceMgr.bulletU, x, y, null);
			break;
		case RIGHT:
			g.drawImage(ResourceMgr.bulletR, x, y, null);
			break;
		default:
			break;
		}
		
		move();
	}
	
	private void move() {
		
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
		
		if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HIGHT) {
			this.live = false;
		}
	}
	
	public Dir getDir() {
		return dir;
	}


	public void setDir(Dir dir) {
		this.dir = dir;
	}

/*
	public void collideWith(Tank tank) {
		
		if (this.group == tank.getGroup()) {
			return;
		}
				
		if (this.rect.intersects(tank.rect)) {
			tank.die();
			this.die();
			
		    int Ex = tank.getX() + tank.WIDTH/2 - Explode.getWIDTH()/2;
		    int Ey = tank.getY() + tank.HEIGHT/2 - Explode.getHEIGHT()/2;		
			
			gm.add(new Explode(Ex, Ey, gm));
		}
		
	}*/


	public void die() {
		this.live = false;
	}

	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public int getHeight() {
		return HEIGHT;
	}

}

package com.zxxa.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.zxxa.tank.abstractfactory.BaseBullet;

public class Bullet extends BaseBullet{

	private int x, y;
	private Dir dir = Dir.DOWN;
	private static final int SPEED = 10;
	private boolean live = true;
	private TankFrame tf = null;
	public static int HEIGHT = ResourceMgr.bulletU.getHeight();
	public static int WIDTH = ResourceMgr.bulletU.getWidth();
	private Group group = Group.BAD;
	
	Rectangle rect = new Rectangle();

	public Bullet(int x, int y, Dir dir, Group group, TankFrame tf) {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.tf = tf;
		this.group = group;
		
		rect.x = x;
		rect.y = y;
		rect.height = HEIGHT;
		rect.width = WIDTH;
		
		tf.bullets.add(this);
	}
	
	
	public boolean isLive() {
		return live;
	}


	public void setLive(boolean live) {
		this.live = live;
	}

	public void paint(Graphics g) {
		
		if (!this.live) {
			tf.bullets.remove(this);
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
		
		if (x < 0 || y < 0 || x > this.tf.GAME_WIDTH || y > this.tf.GAME_HIGHT) {
			this.live = false;
		}
	}
	
	public Dir getDir() {
		return dir;
	}


	public void setDir(Dir dir) {
		this.dir = dir;
	}


	public void collideWith(Tank tank) {
		
		if (this.group == tank.getGroup()) {
			return;
		}
				
		if (this.rect.intersects(tank.rect)) {
			tank.die();
			this.die();
			
		    int Ex = tank.getX() + tank.WIDTH/2 - Explode.getWIDTH()/2;
		    int Ey = tank.getY() + tank.HEIGHT/2 - Explode.getHEIGHT()/2;		
			
			tf.explodes.add(tf.gf.createExplode(Ex, Ey, tf));
		}
		
	}


	private void die() {
		this.live = false;
	}

}

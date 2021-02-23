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
import java.util.List;

import com.zxxa.tank.abstractfactory.BaseExplode;
import com.zxxa.tank.abstractfactory.DefaultFactory;
import com.zxxa.tank.abstractfactory.GameFactory;

public class TankFrame extends Frame {
	
	static final int GAME_WIDTH = 800, GAME_HIGHT = 800;

	GameModel gm = GameModel.getInstence();
	
	public TankFrame() {
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
		
		gm.paint(g);
		
	}
	
	class MyKeyListener extends KeyAdapter{

		boolean bU = false;
		boolean bD = false;
		boolean bR = false;
		boolean bL = false;
		
		private void setMainTankDir() {
			
			if (!bL && !bU && !bR && !bD) {
				gm.myTank.setMoving(false);
			} else {
				gm.myTank.setMoving(true);
				if (bL) gm.myTank.setDir(Dir.LEFT); 
				if (bU) gm.myTank.setDir(Dir.UP); 
				if (bR) gm.myTank.setDir(Dir.RIGHT); 
				if (bD) gm.myTank.setDir(Dir.DOWN); 
			}	
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
				gm.myTank.fire();
				break;
			case KeyEvent.VK_S:
				gm.save();
				break;
			case KeyEvent.VK_L:
				gm.load();
				break;
			default:
				break;
			}
			
			setMainTankDir();
		}
		
	}
}

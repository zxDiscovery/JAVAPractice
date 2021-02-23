package com.zxxa.tank;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ResourceMgr {
	public static BufferedImage GoodtankL, GoodtankU, GoodtankR, GoodtankD;
	public static BufferedImage BadtankL, BadtankU, BadtankR, BadtankD;
	public static BufferedImage bulletL, bulletR, bulletU, bulletD;
	public static BufferedImage[] explodes = new BufferedImage[16];
	
	static {
		try {
			GoodtankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/GoodTank1.png"));
			GoodtankL = ImageUtil.rotateImage(GoodtankU, -90);
			GoodtankR = ImageUtil.rotateImage(GoodtankU, 90);
			GoodtankD = ImageUtil.rotateImage(GoodtankU, 180);
			
			BadtankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/BadTank1.png"));
			BadtankL = ImageUtil.rotateImage(BadtankU, -90);
			BadtankR = ImageUtil.rotateImage(BadtankU, 90);
			BadtankD = ImageUtil.rotateImage(BadtankU, 180);
		
			bulletL = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bulletL.gif"));
			bulletR = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bulletR.gif"));
			bulletD = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bulletD.gif"));
			bulletU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bulletU.gif"));
			
			for (int i = 0; i < 16; i++) {
				explodes[i] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/e" + (i+1) + ".gif"));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

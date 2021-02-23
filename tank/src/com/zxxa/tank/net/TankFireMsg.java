package com.zxxa.tank.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

import com.zxxa.tank.Bullet;
import com.zxxa.tank.Dir;
import com.zxxa.tank.Group;
import com.zxxa.tank.Tank;
import com.zxxa.tank.TankFrame;

public class TankFireMsg extends Msg{

	public int x, y, bx, by;
	public Dir dir;
	public Group group;
	public UUID id;
	
	public TankFireMsg() {
		
	}
	
	public TankFireMsg(Tank t) {
		this.x = t.getX();
		this.y = t.getY();
		this.bx = t.getX() + Tank.WIDTH/2 - Bullet.WIDTH/2;
		this.by = t.getY() + Tank.HEIGHT/2 - Bullet.HEIGHT/2;
		this.dir = t.getDir();
		this.group = t.getGroup();
		this.id = t.getUuid();
	}
	
	@Override
	public void handleMsg() {
		if (this.id.equals(TankFrame.INSTANCE.getMainTank().getUuid())) {
			return;
		}
		
		new Bullet(this.bx, this.by, this.dir, this.group, TankFrame.INSTANCE);
	}
	
	@Override
	public byte[] toBytes() {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		
		byte[] bytes = null;
		
		try {
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(bx);
			dos.writeInt(by);
			dos.writeInt(dir.ordinal());
			dos.writeInt(group.ordinal());
			dos.writeLong(id.getMostSignificantBits());
			dos.writeLong(id.getLeastSignificantBits());
			dos.flush();
			bytes = baos.toByteArray();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				dos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return bytes;
	}
	@Override
	public void parseBytes(byte[] bytes) {
		
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		DataInputStream dis = new DataInputStream(bais);
		
		try {
			this.x = dis.readInt();
			this.y = dis.readInt();
			this.bx = dis.readInt();
			this.by = dis.readInt();
			this.dir = Dir.values()[dis.readInt()];
			this.group = Group.values()[dis.readInt()];
			this.id = new UUID(dis.readLong(), dis.readLong());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				dis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				bais.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	@Override
	public MsgType getMsgType() {
		return MsgType.TankFire;
	}
		
}

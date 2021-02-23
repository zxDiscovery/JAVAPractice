package com.zxxa.tank.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

import com.zxxa.tank.Dir;
import com.zxxa.tank.Tank;
import com.zxxa.tank.TankFrame;

public class TankDirMsg extends Msg{
	
	public int x, y;
	public Dir dir;
	public boolean moving;
	public UUID id;

	public TankDirMsg() {
		
	}
	
	public TankDirMsg(int x, int y, Dir dir, boolean moving, UUID id) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.moving = moving;
		this.id = id;
	}
	
	public TankDirMsg(Tank t) {
		this.x = t.getX();
		this.y = t.getY();
		this.dir = t.getDir();
		this.moving = t.isMoving();
		this.id = t.getUuid();
	}

	@Override
	public void handleMsg() {
		if (this.id.equals(TankFrame.INSTANCE.getMainTank().uuid)) {
			return;
		}
				
		Tank t = TankFrame.INSTANCE.findByUuid(this.id);
		t.setDir(this.dir);
		t.setX(this.x);
		t.setY(this.y);
		t.setMoving(this.moving);
	}

	@Override
	public byte[] toBytes() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		
		byte[] bytes = null;
		
		
		try {
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(dir.ordinal());
			dos.writeBoolean(moving);
			dos.writeLong(id.getMostSignificantBits());
			dos.writeLong(id.getLeastSignificantBits());
			dos.flush();
			bytes = baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if (dos != null) {
				try {
					dos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
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
			this.dir = Dir.values()[dis.readInt()];
			this.moving = dis.readBoolean();
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
		return MsgType.TankDir;
	}

}

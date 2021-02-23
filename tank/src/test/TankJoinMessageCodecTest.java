package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.zxxa.tank.Dir;
import com.zxxa.tank.Group;
import com.zxxa.tank.net.TankJoinMsg;
import com.zxxa.tank.net.TankMsgEncoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.embedded.EmbeddedChannel;

class TankJoinMessageCodecTest {

	@Test
	void testEncoder() {
		EmbeddedChannel ch = new EmbeddedChannel();
				
		UUID id = UUID.randomUUID();
		
		TankJoinMsg msg = new TankJoinMsg(5, 10, Dir.DOWN, true, Group.BAD, id);
		
		ch.pipeline().addLast(new TankMsgEncoder());
		
		ch.writeOutbound(msg);
		
		ByteBuf buf = (ByteBuf)ch.readOutbound();
		
		int x = buf.readInt();
		int y = buf.readInt();
		Dir dir = Dir.values()[buf.readInt()];
		boolean ismoving = buf.readBoolean();
		Group group = Group.values()[buf.readInt()];
		UUID id2 = new UUID(buf.readLong(), buf.readLong());
		
		assertEquals(5, x);
		assertEquals(10, y);
		assertEquals(Dir.DOWN, dir);
		assertEquals(true, ismoving);
		assertEquals(Group.BAD, group);
		assertEquals(id, id2);
	}

}

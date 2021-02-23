package com.zxxa.tank.net;

import java.util.List;
import java.util.UUID;

import com.zxxa.tank.Dir;
import com.zxxa.tank.Group;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class TankMsgDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (in.readableBytes() < 8) {
			return;
		}
		
		in.markReaderIndex();
		
		MsgType mt = MsgType.values()[in.readInt()];
		
//		System.out.println(mt.toString());
		
		int lenght = in.readInt();
		
		if (in.readableBytes() < lenght) {
			in.resetReaderIndex();
			return;
		}
		
		byte[] bytes = new byte[lenght];
		in.readBytes(bytes);
		
		Msg msg = null;
		
		msg = (Msg)Class.forName("com.zxxa.tank.net." + mt.toString() + "Msg").getDeclaredConstructor().newInstance();
		
		msg.parseBytes(bytes);
		
		out.add(msg);
	}

}

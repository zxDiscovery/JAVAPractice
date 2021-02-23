package com.zxxa.system.io.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;

@ChannelHandler.Sharable
public class ChannelInit extends ChannelInboundHandlerAdapter {
	
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("MyInHandle invoke");
		Channel client = ctx.channel();
		ChannelPipeline p = client.pipeline();
		p.addLast(new MyInHandle());
		ctx.pipeline().remove(this);
	}

}

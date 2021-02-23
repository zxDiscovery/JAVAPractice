package com.zxxa.system.io.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;

public class MyAcceptHandler extends ChannelInboundHandlerAdapter {
	
	private final EventLoopGroup selector;
	private final ChannelHandler handler;
	
	public MyAcceptHandler(EventLoopGroup thread, ChannelHandler myInHandler ) {
		this.selector = thread;
		this.handler = myInHandler;
	}
	
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("server registerd...");
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// listen socket accept
		// socket  R/W
		System.out.println("Channel Init invoke");
		SocketChannel client = (SocketChannel)msg;
		
		ChannelPipeline p = client.pipeline();
		
		p.addLast(handler);
		
		// register
		selector.register(client);
		

	}

}

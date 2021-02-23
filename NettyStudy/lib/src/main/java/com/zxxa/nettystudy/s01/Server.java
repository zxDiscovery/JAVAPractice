package com.zxxa.nettystudy.s01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

public class Server {

	public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	public static void main(String[] args) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup(2);
		
		try {
			ServerBootstrap b = new ServerBootstrap();	
			ChannelFuture f = b.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>(){
	
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						
						System.out.println(Thread.currentThread().getId()+"a");
						
						ChannelPipeline p1 = ch.pipeline();
						p1.addLast(new ServerChildHandle());
					}
				
			}).bind(8888)
				.sync();
			
			System.out.println("Server started!");
			
			f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
		
	}
}

class ServerChildHandle extends ChannelInboundHandlerAdapter {
	
	
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Server.clients.add(ctx.channel());
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = null;
		try {
			buf = (ByteBuf)msg;
			byte[] bytes = new byte[buf.readableBytes()];
			buf.getBytes(buf.readerIndex(), bytes);
			System.out.println(new String(bytes));
			
			Server.clients.writeAndFlush(msg);
			
		} finally {
//			if (buf != null) {
//				ReferenceCountUtil.release(buf);
//			}
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
		cause.printStackTrace();
		ctx.close();
	}
}

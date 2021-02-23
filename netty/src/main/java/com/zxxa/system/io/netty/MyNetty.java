package com.zxxa.system.io.netty;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.junit.Test;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MyNetty {

	@Test
	public void myBytebuf() {

		ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(8, 20);
		print(buf);
		
	}
	
	public static void print(ByteBuf buf) {

		System.out.println(buf.isReadable());
		System.out.println(buf.readerIndex());
		System.out.println(buf.readableBytes());
		System.out.println(buf.isWritable());
		System.out.println(buf.writerIndex());
		System.out.println(buf.writableBytes());
		System.out.println(buf.capacity());
		System.out.println(buf.maxCapacity());
		System.out.println(buf.isDirect());
		
	}
	
	
	public void LoopExecutor() throws Exception {
		NioEventLoopGroup selector = new NioEventLoopGroup(1);
		selector.execute(()->{
			System.out.println("Hello world");
			
			try {
				while(true) {
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		System.in.read();
	}
	
	public void clientMode() throws InterruptedException {
		NioEventLoopGroup thread = new NioEventLoopGroup(1);
		NioSocketChannel client = new NioSocketChannel();
		thread.register(client);
		ChannelPipeline p = client.pipeline();
		p.addLast(new MyInHandle());
		ChannelFuture connect = client.connect(new InetSocketAddress("127.0.0.1", 9999));
		ChannelFuture sync = connect.sync();
		ByteBuf buf = Unpooled.copiedBuffer("Hello server!".getBytes());
		ChannelFuture send = client.writeAndFlush(buf);
		send.sync();
		
		sync.channel().closeFuture().sync();
		System.out.println("client over....");
	}
	
	public void serverMode() throws Exception {
		NioEventLoopGroup thread = new NioEventLoopGroup(1);
		
		NioServerSocketChannel server = new NioServerSocketChannel();
		
		thread.register(server);
		
		ChannelPipeline p = server.pipeline();
		
		p.addLast(new MyAcceptHandler(thread, new ChannelInit()));
		
		ChannelFuture bind = server.bind(new InetSocketAddress("127.0.0.1", 9999));
		
		bind.sync().channel().closeFuture().sync();
		System.out.println("Server close.....");

	}
	
	public void nettyClient() throws Exception {
		NioEventLoopGroup group = new NioEventLoopGroup(1);
		Bootstrap bs = new Bootstrap();
		ChannelFuture connect = bs.group(group)
			.channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline p = ch.pipeline();
					p.addLast(new MyInHandle());
				}
				
			})
			.connect(new InetSocketAddress("127.0.0.1", 9999));
		
		Channel client = connect.sync().channel();
		
		ByteBuf buf = Unpooled.copiedBuffer("Hello server".getBytes());
		
		ChannelFuture send = client.writeAndFlush(buf);
		send.sync();
		
		client.closeFuture().sync();
	}
	
	public void nettyServer() throws Exception {
		NioEventLoopGroup group = new NioEventLoopGroup(1);
		ServerBootstrap bs = new ServerBootstrap();
		ChannelFuture bind = bs.group(group, group)
			.channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<NioSocketChannel>() {

				@Override
				protected void initChannel(NioSocketChannel ch) throws Exception {
					ChannelPipeline p = ch.pipeline();
					p.addLast(new MyInHandle());
				}
				
			})
			.bind(new InetSocketAddress("127.0.0.1", 9999));
		
		bind.sync().channel().closeFuture().sync();
	}
	

}

package com.zxxa.tank.net;

import com.zxxa.tank.Tank;
import com.zxxa.tank.TankFrame;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

public class Client {
	
	public static Client INSTANCE = new Client();
	
	private Channel channel = null;
	
	private Client() {
		
	}
	
	public void connect() {
		EventLoopGroup group = new NioEventLoopGroup(1);
		
		Bootstrap b = new Bootstrap();
		
		try {
			ChannelFuture f = b.group(group).channel(NioSocketChannel.class)
				.handler(new ClientChannelInitializer())
				.connect("localhost", 8888);
			
			f.addListener(new ChannelFutureListener() {

				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if (!future.isSuccess()) {
						System.out.println("not connected!");
					} else {
						System.out.println("connected!");
						
						channel = future.channel();
					}
				}
			});
			
			
			try {
				f.sync();
				f.channel().closeFuture().sync();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		} finally {
			group.shutdownGracefully();
		}
	}
	
	public void send(Msg msg) {
		channel.writeAndFlush(msg);
	}

	public void closeconnect() {

	}
}


class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline()
			.addLast(new TankMsgEncoder())
			.addLast(new TankMsgDecoder())
			.addLast(new ClientHandle());
	}
}

class ClientHandle extends SimpleChannelInboundHandler<Msg> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
		msg.handleMsg();
		
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));
	}
	
}

/*
class ClientHandle extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = null;
		try {
			buf = (ByteBuf)msg;
			byte[] bytes = new byte[buf.readableBytes()];
			buf.getBytes(buf.readerIndex(), bytes);
			String msgAccepted = new String(bytes);
//			ClientFrame.INSTANCE.updateText(msgAccepted);
		} finally {
			if (buf != null) {
				ReferenceCountUtil.release(buf);
			}
		}
				
	}
	
}*/

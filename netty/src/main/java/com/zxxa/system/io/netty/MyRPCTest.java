package com.zxxa.system.io.netty;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/*
* 1. write a RPC
* 2. the number of connections. Phase the package
* 3. dynamicProxy, serial, 
* 4. connection pool
* 5, Just as invoke the location function to invoke the remote function
*/
public class MyRPCTest {
	
	public static void main(String[] args) {
		MyRPCTest myRPC = new MyRPCTest();
		try {
			myRPC.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startServer() {
		 NioEventLoopGroup boss = new NioEventLoopGroup(1);
		 NioEventLoopGroup worker = boss;
		 
		 ServerBootstrap sbs = new ServerBootstrap();
		 
		 ChannelFuture bind = sbs.group(boss, worker)
		 		.channel(NioServerSocketChannel.class)
		 		.childHandler(new ChannelInitializer<NioSocketChannel>() {

					@Override
					protected void initChannel(NioSocketChannel ch) throws Exception {
						System.out.println("Server accept client port:" + ch.remoteAddress().getPort());
						ChannelPipeline p = ch.pipeline();
						p.addLast(new ServerRequestHandler());
					}
		 			
		 		}).bind(new InetSocketAddress("localhost", 9999));
		 
		 try {
			bind.sync().channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//comsumer
	public void get() {
		
		new Thread(()->{
			startServer();
		}).start();
		
		System.out.println("server started.....");
		
		int size = 10;
		
		Thread[] threads = new Thread[size];
		
		for (int i = 0; i < size; i++) {
			threads[i] = new Thread(()->{
				Car car = proxyGet(Car.class);
				car.ooxx("Hello");
			});
		}
		
		for (Thread thread : threads) {
			thread.start();
		}
		
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		Fly fly = proxyGet(Fly.class);
//		fly.xxoo("Hello");
	}
	
	public static <T>T proxyGet(Class<T> interfaceInfo){
		
		ClassLoader loader = interfaceInfo.getClassLoader();
		Class<?>[] methodInfo = {interfaceInfo};
		
		return (T)Proxy.newProxyInstance(loader, methodInfo, new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				
				//How to desigin consumer invoke the provider
				
				//1 invoke the server function and paramater encapsulate as a message
				String name = interfaceInfo.getName();
				String methodName = method.getName();
				System.out.println("invoke function has been invoked. name is " + name + " methodName is "+ methodName);

				Class<?>[] parameterTypes = method.getParameterTypes();
				MyContent content = new MyContent();
				
				content.setArgs(args);
				content.setName(name);
				content.setMethodName(methodName);
				content.setParameterTypes(parameterTypes);
				
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				ObjectOutputStream oout = new ObjectOutputStream(out);
				oout.writeObject(content);
				byte[] msgBody = out.toByteArray();
				
				//2 request ID + message cacha
				//协议  header msgBody
				MyHeader header = createHeader(msgBody);

				out.reset();
				oout = new ObjectOutputStream(out);
				oout.writeObject(header);
				
				byte[] msgHeader = out.toByteArray();
				//3 connection pool
				ClientFactory factory = ClientFactory.getFactory();
				NioSocketChannel clientChannel = factory.getClient(new InetSocketAddress("localhost", 9999));
				//4 send IO out
				
				ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(msgHeader.length + msgBody.length);
				
				CountDownLatch countDownLatch = new CountDownLatch(1);
				long id = header.getRequestID();
				
				System.out.println("ID is " + id);
				
				ResponseHandler.addCallBack(id, new Runnable() {

					@Override
					public void run() {
						countDownLatch.countDown();
					}
					
				});
				
				byteBuf.writeBytes(msgHeader);
				byteBuf.writeBytes(msgBody);
				
				ChannelFuture channelFuture = clientChannel.writeAndFlush(byteBuf);
				channelFuture.sync();
				
				countDownLatch.await();
				
				//5 How to execut the code here when the IO come back 
				
				// sleep callback
				
				return null;
			}


			
		});
	}
	
	public static MyHeader createHeader(byte[] msg) {
		MyHeader header = new MyHeader();
		int size = msg.length;
		int f = 0x14141414;
		long requestID = Math.abs(UUID.randomUUID().getLeastSignificantBits());
		header.setFlag(f);
		header.setDataLen(size);
		header.setRequestID(requestID);
		return header;
	}
}

interface Car{
	public void ooxx(String msg);
} 

interface Fly{
	public void xxoo(String msg);
}

class MyContent implements Serializable{
	String name;
	String methodName;
	Class<?>[] parameterTypes;
	Object[] args;
	
	public String getName() {
		return name;
	}
	public String getMethodName() {
		return methodName;
	}
	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}
	public Object[] getArgs() {
		return args;
	}
	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public void setArgs(Object[] args) {
		this.args = args;
	}
}

class MyHeader implements Serializable{
	int flag;
	long requestID;
	long dataLen;
	
	public int getFlag() {
		return flag;
	}

	public long getRequestID() {
		return requestID;
	}

	public long getDataLen() {
		return dataLen;
	}

	public void setDataLen(long dataLen) {
		this.dataLen = dataLen;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	public void setRequestID(long requestID) {
		this.requestID = requestID;
	}
}

class ClientFactory{
	int poolSize = 1;
	NioEventLoopGroup clientWorker;
	
	Random rand = new Random();
	
	private ClientFactory() {}
	
	private static final ClientFactory factory;
	
	static {
		factory = new ClientFactory();
	}
	
	public static ClientFactory getFactory() {
		return factory;
	}
	
	ConcurrentHashMap<InetSocketAddress, ClientPool> outboxs = new ConcurrentHashMap<>();
	
	public synchronized NioSocketChannel getClient(InetSocketAddress address) {
		ClientPool clientPool = outboxs.get(address);
		
		if (clientPool == null) {
			outboxs.putIfAbsent(address, new ClientPool(poolSize));
			clientPool = outboxs.get(address);
		}
		
		int i = rand.nextInt(poolSize);
		if (clientPool.clients[i] != null && clientPool.clients[i].isActive()) {
			return clientPool.clients[i];
		}
		
		synchronized(clientPool.lock[i]) {
			return clientPool.clients[i] = create(address);
		}
		
	}
	
	private NioSocketChannel create(InetSocketAddress address) {
		// base on netty client create method
		clientWorker = new NioEventLoopGroup(1);
		
		Bootstrap bs = new Bootstrap();
		ChannelFuture connect = bs.group(clientWorker)
			.channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<NioSocketChannel>() {

				@Override
				protected void initChannel(NioSocketChannel ch) throws Exception {
					ChannelPipeline p = ch.pipeline();
					p.addLast(new ClientResponses());
				}
				
			}).connect(address);

		NioSocketChannel client;
		try {
			client = (NioSocketChannel)connect.sync().channel();
			return client;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return null;

	}
}

class ClientResponses extends ChannelInboundHandlerAdapter{
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf)msg;
		
		if (buf.readableBytes() >= 110) {
			byte[] bytes = new byte[110];
			buf.readBytes(bytes);
			ByteArrayInputStream in = new ByteArrayInputStream(bytes);
			ObjectInputStream oin = new ObjectInputStream(in);
			MyHeader header = (MyHeader) oin.readObject();
			System.out.println(header.dataLen);
			System.out.println("Client response ID" + header.requestID);
			//TODO:
			ResponseHandler.runCallBack(header.requestID);
			
			
//			if (buf.readableBytes() >= header.getDataLen()) {
//				byte[] data = new byte[(int)header.getDataLen()];
//				buf.readBytes(data);
//				ByteArrayInputStream din = new ByteArrayInputStream(data);
//				ObjectInputStream odin = new ObjectInputStream(din);
//				MyContent content = (MyContent)odin.readObject();
//				System.out.println(content.getName());
//			}
		}
		
		super.channelRead(ctx, msg);
	}
}

class ClientPool {
	NioSocketChannel[] clients;
	Object[] lock;
	
	ClientPool(int size){
		clients = new NioSocketChannel[size];
		lock = new Object[size];
		for (int i = 0; i < size; i++ ) {
			lock[i] = new Object();
		}
	}
}

class ResponseHandler{
	static ConcurrentHashMap<Long, Runnable> mapping = new ConcurrentHashMap<>();
	
	public static void addCallBack(long requestID, Runnable cb) {
		mapping.putIfAbsent(requestID, cb);
	}
	
	public static void runCallBack(long requestID) {
		Runnable runnable = mapping.get(requestID);
		runnable.run();
		
		removeCB(requestID);
	}

	private static void removeCB(long requestID) {
		mapping.remove(requestID);
	}
}

class ServerRequestHandler extends ChannelInboundHandlerAdapter{
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf)msg;
		ByteBuf sendBuf = buf.copy();
		
		if (buf.readableBytes() >= 110) {
			byte[] bytes = new byte[110];
			buf.readBytes(bytes);
			ByteArrayInputStream in = new ByteArrayInputStream(bytes);
			ObjectInputStream oin = new ObjectInputStream(in);
			MyHeader header = (MyHeader) oin.readObject();
			System.out.println(header.dataLen);
			System.out.println(header.requestID);
			
			
			if (buf.readableBytes() >= header.getDataLen()) {
				byte[] data = new byte[(int)header.getDataLen()];
				buf.readBytes(data);
				ByteArrayInputStream din = new ByteArrayInputStream(data);
				ObjectInputStream odin = new ObjectInputStream(din);
				MyContent content = (MyContent)odin.readObject();
				System.out.println("content name is " + content.getName());
			}
		}
		
		ChannelFuture channelFuture = ctx.writeAndFlush(sendBuf);
		channelFuture.sync();
	}
}

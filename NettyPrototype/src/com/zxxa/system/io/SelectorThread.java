package com.zxxa.system.io;

import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class SelectorThread extends ThreadLocal<LinkedBlockingQueue<Channel>> implements Runnable{

	Selector selector = null;
	
	LinkedBlockingQueue<Channel> lbq = get();
	
	SelectorThreadGroup stg;
	
	@Override
	protected LinkedBlockingQueue<Channel> initialValue(){
		return new LinkedBlockingQueue<>();
	}
	
	SelectorThread(SelectorThreadGroup stg){
		try {
			
			this.stg = stg;
			selector = Selector.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		//loop
		while(true) {
			try {
//				System.out.println(Thread.currentThread().getName()+" before select ..." + selector.keys().size());
				int nums = selector.select(); //阻塞

	//			Thread.sleep(1000);

//				System.out.println(Thread.currentThread().getName()+" after select ..." + selector.keys().size());
				
				if (nums>0) {
					Set<SelectionKey> keys = selector.selectedKeys();
					Iterator<SelectionKey> iter = keys.iterator();
					while(iter.hasNext()) {
						SelectionKey key = iter.next();
						iter.remove();
						if (key.isAcceptable()) {
							acceptHandler(key);
						}else if (key.isReadable()) {
							readHandler(key);
						}else if (key.isWritable()) {
							
						}
					}
				}
				
				if (!lbq.isEmpty()) {
					Channel c = lbq.take();
					
					if (c instanceof ServerSocketChannel) {
						ServerSocketChannel server = (ServerSocketChannel)c;
						server.register(selector, SelectionKey.OP_ACCEPT);
						System.out.println(Thread.currentThread().getName()+" register licenser");

					}else if (c instanceof SocketChannel) {
						SocketChannel client = (SocketChannel)c;
						ByteBuffer buffer = ByteBuffer.allocateDirect(4096);
						client.register(selector, SelectionKey.OP_READ, buffer);
						
						System.out.println(Thread.currentThread().getName()+" register client:" + client.getRemoteAddress());

					}
				}
			} catch (IOException e ) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void readHandler(SelectionKey key) {
		ByteBuffer buffer = (ByteBuffer)key.attachment();
		SocketChannel client = (SocketChannel)key.channel();
		
		buffer.clear();
		
		while(true) {
			try {
				int num = client.read(buffer);
				
				if (num > 0) {
					buffer.flip();
					while (buffer.hasRemaining()) {
						client.write(buffer);
					}
					buffer.clear();
				} else if (num == 0) {
					break;
				}else if (num < 0) {
					System.out.println("client:" + client.getRemoteAddress()+"closed....");
					key.cancel();
					break;
				}
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void acceptHandler(SelectionKey key) {
		System.out.println("acceptHandler.......");
		
		ServerSocketChannel server = (ServerSocketChannel)key.channel();
		
		try {
			SocketChannel client =  server.accept();
			client.configureBlocking(false);
			stg.nextSelector(client);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setWorker(SelectorThreadGroup stgWorker) {
		this.stg = stgWorker;
	}
	
}

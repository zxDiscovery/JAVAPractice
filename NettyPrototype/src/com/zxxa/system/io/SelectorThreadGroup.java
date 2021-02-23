package com.zxxa.system.io;

import java.io.IOException;

import java.net.InetSocketAddress;
import java.nio.channels.Channel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

public class SelectorThreadGroup {

	SelectorThread[] sts;
	ServerSocketChannel server = null;
	AtomicInteger xid = new AtomicInteger(0);
	SelectorThreadGroup stg = this;

	SelectorThreadGroup(int num){
		
		sts = new SelectorThread[num];
		
		for (int i = 0; i < num; i++) {
			sts[i] = new SelectorThread(this);
			
			new Thread(sts[i]).start();
		}
		
	}
	
	public void setWorker(SelectorThreadGroup stg) {
		this.stg = stg;
	}

	public void bind(int port) {
		try {
			server = ServerSocketChannel.open();
			server.configureBlocking(false);
			server.bind(new InetSocketAddress(port));
			
			//注册到哪个select上呢？
			nextSelector(server);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void nextSelector(Channel c) {
		try {		
			if (c instanceof ServerSocketChannel) {
				SelectorThread st = next();
				st.lbq.put(c);
				st.setWorker(stg);
				st.selector.wakeup();
			} else {
				SelectorThread st = nextv2();
				st.lbq.add(c);
				st.selector.wakeup();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		SelectorThread st = next();
//		
//		st.lbq.add(c);
//		st.selector.wakeup();
			
//		ServerSocketChannel s = (ServerSocketChannel)c;
//		
//		try {
////			st.selector.wakeup();
//			s.register(st.selector, SelectionKey.OP_ACCEPT);
//		} catch (ClosedChannelException e) {
//			e.printStackTrace();
//		}
	}
	
	private SelectorThread nextv2() {
		int index = xid.incrementAndGet() % stg.sts.length;
		return sts[index];
	}

	private SelectorThread next() {
		int index = xid.incrementAndGet() % sts.length;
		return sts[index];
	}
}

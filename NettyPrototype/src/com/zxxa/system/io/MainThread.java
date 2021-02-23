package com.zxxa.system.io;

public class MainThread {

	public static void main(String[] args) {
		
		//1. 创建IO Thread
		SelectorThreadGroup boss = new SelectorThreadGroup(3);
		SelectorThreadGroup worker = new SelectorThreadGroup(3);

		boss.setWorker(worker);
		//2. 把监听的server 注册到某一个selector上
		boss.bind(9999);
		boss.bind(9998);
		boss.bind(9997);
		boss.bind(9996);

		
	}
}

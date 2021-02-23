package com.zxxa.tank.net;

public abstract class Msg {
	abstract void handleMsg();
	abstract byte[] toBytes();
	abstract void parseBytes(byte[] bytes);
	abstract MsgType getMsgType();
}

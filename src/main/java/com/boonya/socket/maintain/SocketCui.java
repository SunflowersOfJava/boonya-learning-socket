package com.boonya.socket.maintain;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 *  
 * @author boonya
 * @note 被重新定义的连接对象，增加了名字这个属性，重写了关闭的方法
 */
public class SocketCui extends Socket {
	/**
	 * 为对象增加名称属性
	 */
	private String name;

	public SocketCui() {
	}

	public SocketCui(String ip, int port) throws UnknownHostException, IOException {
		super(ip, port);
	}

	/**
	 * 覆盖关闭的方法
	 */
	@Override
	public synchronized void close() throws IOException {
		//关闭时只会解除其占用，而不会真正关闭该连接。
		SocketKeep.socketIsLock.put(this.name, "0");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

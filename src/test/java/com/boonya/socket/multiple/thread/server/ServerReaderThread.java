package com.boonya.socket.multiple.thread.server;

import java.io.DataInputStream;
import java.io.IOException;
/**
 * 接受并打印客户端传过来的信息
 * 
 * @author boonya
 *
 */
public class ServerReaderThread  extends Thread {
	
	private DataInputStream dis;

	public ServerReaderThread(DataInputStream dis) {
		this.dis = dis;
	}

	public void run() {
		String info;
		try {
			while (true) {
				// 如果对方，即客户端没有说话，那么就会阻塞到这里，
				// 这里的阻塞并不会影响到其他线程
				info = dis.readUTF();
				// 如果状态有阻塞变为非阻塞，那么就打印接受到的信息
				System.out.println("Server thread received message from client: " + info);
				if (info.equals("bye")) {
					System.out.println("(Server send this message.)Your friend is offline and program is to exit!");
					System.exit(0);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

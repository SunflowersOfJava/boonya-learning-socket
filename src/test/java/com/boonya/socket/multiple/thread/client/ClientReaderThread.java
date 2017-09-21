package com.boonya.socket.multiple.thread.client;

import java.io.DataInputStream;
import java.io.IOException;
/**
 * 接受并打印服务器端传过来的信息
 * @author boonya
 *
 */
public class ClientReaderThread extends Thread {
	
	private DataInputStream dis;

	public ClientReaderThread(DataInputStream dis) {
		this.dis = dis;
	}

	public void run() {
		String info;
		try {
			while (true) {
				info = dis.readUTF();
				System.out.println("Client thread received message from server: " + info);
				if (info.equals("bye")) {
					System.out.println("(Client send this message.)Your friend is offline and program is to exit!");
					System.exit(0);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

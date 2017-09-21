package com.boonya.socket.multiple.thread.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * 从键盘获得输入流并写入信息到客户端
 * 
 * @author boonya
 *
 */
public class ServerWriterThread  extends Thread {
	private DataOutputStream dos;

	public ServerWriterThread(DataOutputStream dos) {
		this.dos = dos;
	}

	public void run() {
		// 读取键盘输入流
		InputStreamReader isr = new InputStreamReader(System.in);
		// 封装键盘输入流
		BufferedReader br = new BufferedReader(isr);
		String info;
		try {
			while (true) {
				info = br.readLine();
				dos.writeUTF(info);
				if (info.equals("bye")) {
					System.out.println("Get offline by yourself and the program is to exit!");
					System.exit(0);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

package com.boonya.socket.multiple.thread.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * 从键盘获得输入流并写入信息到服务器端
 * @author boonya
 *
 */
public class ClientWriterThread extends Thread {
	
	private DataOutputStream dos;

	public ClientWriterThread(DataOutputStream dos) {
		this.dos = dos;
	}

	public void run() {
		InputStreamReader isr = new InputStreamReader(System.in);
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

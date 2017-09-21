package com.boonya.socket.multiple;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import com.boonya.socket.multiple.thread.server.ServerReaderThread;
import com.boonya.socket.multiple.thread.server.ServerWriterThread;

public class MultiChatServer {

	public static void main(String args[]) {
		try {
			// 创建一个socket对象
			ServerSocket server = new ServerSocket(8888);
			System.out.println(server);
			// socket对象调用accept方法，等待连接请求
			Socket s1 = server.accept(); 

			// =========服务器端，在这里应该先打开输出流，在打开输入流，
			// =========因为服务器端执行的操作是先听，再说，听，说，听，说.....

			// 打开输出流
			OutputStream os = s1.getOutputStream();
			// 封装输出流
			DataOutputStream dos = new DataOutputStream(os);
			// 打开输入流
			InputStream is = s1.getInputStream();
			// 封装输入流
			DataInputStream dis = new DataInputStream(is);
			// 创建并启用两个线程
			new ServerReaderThread(dis).start();
			new ServerWriterThread(dos).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

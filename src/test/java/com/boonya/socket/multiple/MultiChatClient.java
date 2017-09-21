package com.boonya.socket.multiple;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import com.boonya.socket.multiple.thread.client.ClientReaderThread;
import com.boonya.socket.multiple.thread.client.ClientWriterThread;

public class MultiChatClient {

	public static void main(String args[]) {
		try {
			// 创建socket对象，指定服务器的ip地址，和服务器监听的端口号
			// 客户端在new的时候，就发出了连接请求，服务器端就会进行处理，如果服务器端没有开启服务，那么
			// 这时候就会找不到服务器，并同时抛出异常==》java.net.ConnectException: Connection
			// refused: connect
			Socket client = new Socket("127.0.0.1", 8888);
			System.out.println(client);
			// =========客户端，在这里应该先打开输入流，在打开输出流，
			// =========因为客户端执行的操作是先说，再听，说，听，说，听.....

			// 打开输入流
			InputStream is = client.getInputStream();
			// 封装输入流
			DataInputStream dis = new DataInputStream(is);
			// 打开输出流
			OutputStream os = client.getOutputStream();
			// 封装输出流
			DataOutputStream dos = new DataOutputStream(os);

			// 创建并启用两个线程
			new ClientReaderThread(dis).start();
			new ClientWriterThread(dos).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
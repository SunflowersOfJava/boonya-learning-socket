package com.boonya.socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Sever {

	public void start() {
		ServerSocket server = null;
		try {
			// 指定服务器端的端口号为8888
			server = new ServerSocket(8888);
			while (true) {
				// 建立连接
				Socket socket = server.accept();
				// 打开输出流
				OutputStream os = socket.getOutputStream();
				// 封装输出流
				DataOutputStream dos = new DataOutputStream(os);
				// s<li>.getInetAddress()获取远程ip地址，s<li>.getPort()远程客户端的断后好
				// 向客户端发送数据
				dos.writeUTF("Received client request: " + socket.getInetAddress() + "\tClient communicate channel port is : " + socket.getPort());
				dos.writeUTF("Server has writen data to client.");
				// 关闭打开的输出流
				dos.close();
				// 关闭打开的socket对象
				socket.close();
			} // 开始下一此循环
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			System.out.println("server is closed server="+server);
		}
	}
	
	public static void main(String[] args) {
		new Sever().start();
	}

}

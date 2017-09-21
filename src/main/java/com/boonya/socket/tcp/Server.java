package com.boonya.socket.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

	private static final int PORT = 10010;
	private ServerSocket serverSocket;
	private final List<ServerThread> socketList = new ArrayList<ServerThread>();
	private boolean isStartServer = false;

	public void startServer() throws IOException {
		if (isStartServer) {
			System.err.println("already>>>>startServer>>>>>>>>>");
			return;
		}
		System.err.println("startServer>>>>>>>>>");
		serverSocket = new ServerSocket(PORT);
		isStartServer = true;
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (isStartServer) {
					try {
						Socket client = serverSocket.accept();

						ServerThread serverThread = new ServerThread(client);
						new Thread(serverThread).start();
						socketList.add(serverThread);
						System.err.println("当前连接数为：" + socketList.size());

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}).start();
	}

	public void stopServer() {
		System.err.println("stopServer>>>>>>>>>");
		isStartServer = false;
		for (ServerThread serverThread : socketList) {
			serverThread.stop();
		}
		socketList.clear();
	}

	public void sendMessage(String str) {
		socketList.get(0).sendMessage(str);
	}

	private class ServerThread implements Runnable {
		private boolean isRunning = false;
		private final Socket socket;
		private final BufferedReader reader;
		private final PrintWriter writer;

		public ServerThread(Socket socket) throws IOException {
			this.socket = socket;
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			isRunning = true;
		}

		public void stop() {
			isRunning = false;
			try {
				reader.close();
				writer.close();
				socket.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void sendMessage(String str) {
			writer.print(str + "\n");
			writer.flush();
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String msg = null;
			while (isRunning) {
				try {
					msg = reader.readLine();
					System.out.println(ServerThread.this.toString() + ":" + msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server tcpServer = new Server();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String msg = null;
		while (true) {
			try {
				msg = in.readLine();
				if ("-start".equalsIgnoreCase(msg)) {
					tcpServer.startServer();
				} else if ("-stop".equalsIgnoreCase(msg)) {
					tcpServer.stopServer();
				} else {
					tcpServer.sendMessage(msg);
					// System.out.println(msg);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

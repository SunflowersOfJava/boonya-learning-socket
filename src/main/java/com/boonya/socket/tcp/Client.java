package com.boonya.socket.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

	private static final String HOST = "localhost";
	private static final int PORT = 10010;
	private Socket socket;
	private ClientThread clientThread;
	private boolean isBind = false;

	public void bindServer() throws IOException {
		if (isBind) {
			System.err.println("already>>>>bindServer>>>>>>>>>");
			return;
		}
		System.err.println("bindServer>>>>>>>>>");
		socket = new Socket(HOST, PORT);
		clientThread = new ClientThread(socket);
		new Thread(clientThread).start();
		isBind = true;
	}

	public void unBindServer() {
		System.err.println("unBindServer>>>>>>>>>");
		isBind = false;
		clientThread.stop();
	}

	public void sendMessage(String str) {
		if (isBind) {
			clientThread.sendMessage(str);
		}
	}

	private class ClientThread implements Runnable {
		private boolean isRunning = false;
		private final Socket socket;
		private final BufferedReader reader;
		private final PrintWriter writer;

		public ClientThread(Socket socket) throws IOException {
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
					System.out.println(HOST + ":" + msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public boolean isBind() {
		return isBind;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client tcpClient = new Client();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String msg = null;
		while (true) {
			try {
				msg = in.readLine();
				if ("-bind".equalsIgnoreCase(msg)) {
					tcpClient.bindServer();
				} else if ("-unbind".equalsIgnoreCase(msg)) {
					tcpClient.unBindServer();
				} else {
					if (tcpClient.isBind()) {
						tcpClient.sendMessage(msg);
					} else {
						System.out.println(msg);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

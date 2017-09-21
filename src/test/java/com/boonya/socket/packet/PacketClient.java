package com.boonya.socket.packet;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class PacketClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new PacketClient().start();
	}

	class SendThread extends Thread {
		private Socket socket;

		public SendThread(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(1000);
					String send = "<SOAP-ENV:Envelope>" + System.currentTimeMillis() + "</SOAP-ENV:Envelope>";
					PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
					pw.write(send);
					pw.flush();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	class ReceiveThread extends Thread {
		private Socket socket;
		private volatile byte[] bytes = new byte[0];

		public ReceiveThread(Socket socket) {
			this.socket = socket;
		}

		public byte[] mergebyte(byte[] a, byte[] b, int begin, int end) {
			byte[] add = new byte[a.length + end - begin];
			int i = 0;
			for (i = 0; i < a.length; i++) {
				add[i] = a[i];
			}
			for (int k = begin; k < end; k++, i++) {
				add[i] = b[k];
			}
			return add;
		}

		@Override
		public void run() {
			while (true) {
				try {
					InputStream reader = socket.getInputStream();
					if (bytes.length < 2) {
						byte[] head = new byte[2 - bytes.length];
						int couter = reader.read(head);
						if (couter < 0) {
							continue;
						}
						bytes = mergebyte(bytes, head, 0, couter);
						if (couter < 2) {
							continue;
						}
					}
					// 下面这个值请注意，一定要取2长度的字节子数组作为报文长度，你懂得
					byte[] temp = new byte[0];
					temp = mergebyte(temp, bytes, 0, 2);
					String templength = new String(temp);
					int bodylength = Integer.parseInt(templength);
					if (bytes.length - 2 < bodylength) {
						byte[] body = new byte[bodylength + 2 - bytes.length];
						int couter = reader.read(body);
						if (couter < 0) {
							continue;
						}
						bytes = mergebyte(bytes, body, 0, couter);
						if (couter < body.length) {
							continue;
						}
					}
					byte[] body = new byte[0];
					body = mergebyte(body, bytes, 2, bytes.length);
					System.out.println("client receive body:   " + new String(body));
					bytes = new byte[0];
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void start() {
		try {
			Socket socket = new Socket("127.0.0.1", 18889);
			new SendThread(socket).start();
			new ReceiveThread(socket).start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

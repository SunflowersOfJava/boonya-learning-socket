package com.boonya.socket.packet;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.CharBuffer;

public class PacketServer {

	private final static String SOAP_BEGIN = "<SOAP-ENV:Envelope";
	private final static String SOAP_END = "</SOAP-ENV:Envelope>";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PacketServer testserver = new PacketServer();
		testserver.start();
	}

	public void start() {
		try {
			@SuppressWarnings("resource")
			ServerSocket serversocket = new ServerSocket(18889);
			while (true) {
				Socket socket = serversocket.accept();
				new SocketThread(socket).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	class SocketThread extends Thread {
		private Socket socket;
		private String temp;

		public SocketThread(Socket socket) {
			this.socket = socket;
		}

		public Socket getsocket() {
			return this.socket;
		}

		public void setsocjet(Socket socket) {
			this.socket = socket;
		}

		@SuppressWarnings("unused")
		@Override
		public void run() {
			try {
				Reader reader = new InputStreamReader(socket.getInputStream());
				// Writer writer=new PrintWriter(new
				// OutputStreamWriter(socket.getOutputStream(),"UTF-8"));
				OutputStream writer = socket.getOutputStream();
				CharBuffer charbuffer = CharBuffer.allocate(8192);
				int readindex = -1;
				while ((readindex = reader.read(charbuffer)) != -1) {
					charbuffer.flip();
					temp += charbuffer.toString();
					if (temp.indexOf(SOAP_BEGIN) != -1 && temp.indexOf(SOAP_END) != -1) {
						// System.out.println(new
						// Date().toLocaleString()+"server:"+temp);
						temp = "";
						String str = "receive the soap message";
						byte[] headbytes = str.getBytes();
						int length = headbytes.length;
						String l = String.valueOf(length);
						byte[] lengthbytes = l.getBytes();
						byte[] bytes = new byte[length + lengthbytes.length];
						int i = 0;
						for (i = 0; i < lengthbytes.length; i++) {
							bytes[i] = lengthbytes[i];
						}
						for (int j = i, k = 0; k < length; k++, j++) {
							bytes[j] = headbytes[k];
						}
						System.out.println("server send:" + new String(bytes));
						writer.write(bytes);
						writer.flush();
					} else if (temp.indexOf(SOAP_BEGIN) != -1) {
						temp = temp.substring(temp.indexOf(SOAP_BEGIN));
					}
					if (temp.length() > 1024 * 16) {
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (socket != null) {
					try {
						if (!socket.isClosed()) {
							socket.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}

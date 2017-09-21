package com.boonya.socket.maintain;

import java.net.ServerSocket;
import com.boonya.socket.maintain.thread.AccumulatorThread;
/**
 * 
 * @author boonya
 * @note 服务端，始终接受连接
 */
public class ServerStartTest {
	
	public static void main(String[] args) {  
        try {  
            ServerSocket ss1 = new ServerSocket(8412);  
            Runnable accumelatora1 = new AccumulatorThread(ss1);  
            Thread threada = new Thread(accumelatora1, "ThreadA");  
            threada.start();  
            System.out.println("服务启动完毕！");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  

}

package com.boonya.socket.maintain.thread;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccumulatorThread  implements Runnable {  
	
    ServerSocket ss = null;  
    
    public AccumulatorThread(ServerSocket s) {  
        this.ss = s;  
    }  
    
    public void run() {  
        try {  
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
            while (true) {  
                Socket s = ss.accept();  
                System.out.println(format.format(new Date()) + " " + "---------收到请求！");  
                new Thread(new HeartBeatThread(s)).start();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  

}

package com.boonya.socket.maintain.thread;

import java.net.Socket;
/**
 * 
 * @author boonya
 * @note 循环发送心跳包保持连接属性
 */
public class HeartBeatThread  implements Runnable {  
	
    Socket socket = null;  
    
    public HeartBeatThread(Socket s) {  
        this.socket = s;  
    }  
    
    public void run() {  
        boolean isKeep = true;  
        try {  
            while (isKeep) {  
                socket.sendUrgentData(0xFF);  
                Thread.sleep(1 * 1000);  
            }  
        } catch (Exception e) {  
            isKeep = false;  
        }  
    }  

}

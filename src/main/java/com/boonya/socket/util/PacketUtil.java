package com.boonya.socket.util;

import java.nio.ByteBuffer;
import java.util.List;

public class PacketUtil {
	
	/*public List<byte[]> getPacket(ByteBuffer buffer) throws Exception{
	    pLink.clear();
	    try{
	    while(buffer.remaining() > 0){
	        if(packetLen == 0){  //此时存在两种情况及在数据包包长没有获得的情况下可能已经获得过一次数据包
	        if(buffer.remaining() + _packet.length < 3){
	        byte[] temp = new byte[buffer.remaining()];
	        buffer.get(temp);
	        _packet = PacketUtil.joinBytes(_packet , temp);
	        break;  //保存包头
	        }else{if(_packet.length == 0){
	        buffer.get();
	        packetLen = PacketUtil.parserBuffer2ToInt(buffer);
	        }else if(_packet.length == 1){
	            packetLen = PacketUtil.parserBuffer2ToInt(buffer);
	        } else if(_packet.length == 2){
	            byte[] lenByte = new byte[2];
	            lenByte[0] = _packet[1];
	            lenByte[1] = buffer.get();
	            packetLen = PacketUtil.parserBytes2ToInt(lenByte);
	        } else{
	            packetLen = PacketUtil.parserBytes2ToInt(_packet , 1);
	        }
	                        
	        }
	    }
	                
	    if(_packet.length <= 3){   //此时_packet 没有有用数据，所需数据都在缓冲区中
	        if(buffer.remaining() < packetLen){
	            _packet = new byte[buffer.remaining()];
	            buffer.get(_packet);
	        }else{
	            byte[] p = new byte[packetLen];
	            buffer.get(p);
	            pLink.add(p);
	            packetLen = 0;
	            _packet = new byte[0];
	        }
	    }else {
	        if(buffer.remaining() + _packet.length - 3 < packetLen){   //剩余数据包不足一个完整包，保存后等待写一个
	                byte[] temp = new byte[buffer.remaining()];
	            buffer.get(temp);
	            _packet = PacketUtil.joinBytes(_packet , temp);break;
	        }else{                                                //数据包完整或者多出
	            byte[] temp = new byte[packetLen - ( _packet.length - 3) ];
	            buffer.get(temp);
	            pLink.add(PacketUtil.subPacket(PacketUtil.joinBytes(_packet , temp)));
	            _packet = new byte[0];
	            packetLen = 0;
	        }
	    }
	    }
	    }catch(Exception e){
	        System.out.println("..GETPACKET packetLen = " + packetLen + " _packet.length = " + _packet.length);
	        throw e;
	    }
	    return pLink;
	}
*/
}

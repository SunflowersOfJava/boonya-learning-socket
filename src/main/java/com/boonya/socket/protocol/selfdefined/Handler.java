package com.boonya.socket.protocol.selfdefined;

import java.io.IOException;

public interface Handler {
	
	byte[] encode(Protocol msg) throws IOException;
	
	Protocol decode(byte[] input) throws IOException;

}

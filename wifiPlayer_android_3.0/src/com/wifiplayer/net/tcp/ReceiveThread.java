package com.wifiplayer.net.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import android.util.Log;

/**
 * 接收服务器发来的信息
 * 
 * @author Administrator
 * 
 */
public class ReceiveThread extends Thread {
	
	private Socket socket;
	
	public ReceiveThread(Socket socket) {
		super();
		this.socket = socket;
	}

	@Override
	public void run() {
		receive();
	}

	private void receive() {

		try {
			while (true) {
				InputStream is = socket.getInputStream();
				int len;
				byte[] buff = new byte[1024];
				while ((len = is.read(buff)) != -1) {
					Log.i("receive", "socket接收的内容：" + new String(buff));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

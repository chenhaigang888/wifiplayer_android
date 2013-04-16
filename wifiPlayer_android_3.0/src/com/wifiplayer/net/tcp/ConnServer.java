package com.wifiplayer.net.tcp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import android.util.Log;

/**
 * 连接服务器
 * @author Administrator
 *
 */
public class ConnServer {
	static Socket s = null;
	public static void conn(final InetAddress addr){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					s = new Socket(addr, 9528);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new ReceiveThread(s).start();
			}
		}).start();
		
	}
	
	/**
	 * 发送内容
	 * @param sendData
	 */
	public static void send(byte[] sendData) {
		Log.i("chg", "socket:" + s);
		if (s != null) {
			new SendThread(null, s, sendData).start();
		} else {
			send(sendData);
		}
		
	}
}

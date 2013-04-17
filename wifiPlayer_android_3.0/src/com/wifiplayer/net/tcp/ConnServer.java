package com.wifiplayer.net.tcp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import android.content.Context;
import android.util.Log;

/**
 * 连接服务器
 * @author Administrator
 *
 */
public class ConnServer {
	static Socket s = null;
	public static void conn(final InetAddress addr, final Context context){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					s = new Socket(addr, 9528);
					new ReceiveThread(s, context).start();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}).start();
		
	}
	
	/**
	 * 发送内容
	 * @param sendData
	 */
	public static void send(byte[] sendData, Context context) {
		Log.i("chg", "socket:" + s);
		if (s != null) {
			new SendThread(context, s, sendData).start();
		} else {
			send(sendData, context);
		}
		
	}
}

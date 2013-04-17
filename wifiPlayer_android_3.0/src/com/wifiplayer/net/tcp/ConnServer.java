package com.wifiplayer.net.tcp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import android.content.Context;

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
//					if (s != null) {
//						close();
//						s = null;
//					}
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
//		Log.i("chg", "socket:" + s);
		if (s != null) {
			new SendThread(context, s, sendData).start();
		} else {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			send(sendData, context);
		}
		
	}
	
	/**
	 * 关闭服务
	 */
	public static void close() {
		try {
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

package com.wifiplayer.net.tcp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import com.wifiplayer.activitys.utils.PcOpManager;

import android.content.Context;
import android.os.Handler;

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
					if (s != null) {
						close();
					}
					s = new Socket();
					SocketAddress sa = new InetSocketAddress(addr, 9528);
					s.connect(sa, 5000);
					if (s != null) {
						new ReceiveThread(s, context).start();
						PcOpManager.openMainDir(context, true);
					}
				} catch (IOException e) {
					e.printStackTrace();
					close();
					System.out.println("连接服务器时候出错");
				}
				
			}
		}).start();
		
	}
	
	/**
	 * 发送内容
	 * @param sendData
	 */
	public static void send(byte[] sendData, Context context) {
		if (s != null) {
			new SendThread(context, s, sendData).start();
		} else {
			try {
				Thread.sleep(1000);
				send(sendData, context);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 关闭服务
	 */
	public static void close() {
		try {
			if (s != null) {
				s.close();
				s = null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

package com.wifiplayer.net.tcp;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import android.content.Context;

/**
 * 发送信息的线程
 * 
 * @author Administrator
 * 
 */
public class SendThread extends Thread {

	private Context context;
	private Socket socket;
	private byte[] sendData;

	public SendThread(Context context, Socket socket, byte[] sendData) {
		super();
		this.context = context;
		this.socket = socket;
		this.sendData = sendData;
	}

	@Override
	public void run() {
		if (!socket.isClosed()) {
			send(sendData);
		}
	}

	/**
	 * 发送数据
	 * 
	 * @param packages
	 */
	private void send(byte[] sendData) {
		OutputStream os = null;
		try {
			os = socket.getOutputStream();
			os.write(sendData);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

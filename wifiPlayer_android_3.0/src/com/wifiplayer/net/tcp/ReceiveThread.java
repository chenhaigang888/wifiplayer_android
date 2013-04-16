package com.wifiplayer.net.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import org.json.JSONObject;

import com.wifiplayer.bean.packages.Head;

import android.content.Context;
import android.util.Log;

/**
 * 接收服务器发来的信息
 * 
 * @author Administrator
 * 
 */
public class ReceiveThread extends Thread {
	
	private Socket socket;
	private Context context;

	public ReceiveThread(Socket socket, Context context) {
		super();
		this.socket = socket;
		this.context = context;
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
					Log.i("receive", "socket接收的内容：" + new String(buff, 0, len));
					byte[] headArray = new byte[12];
					Head head = Head.resolveHead(headArray);
					switch (head.getCmd()) {
					case Head.CONN_SERVER_REPLY://连接服务器返回
						byte[] bodyArray = new byte[head.getPackBodyLenth()];
						connServerReply(bodyArray);
						break;
					case Head.COPY_FILE_2_PHONE_REPLY://拷贝文件到手机上返回
						break;
					case Head.DEL_FILE_REPLY://删除文件返回
						break;
					case Head.OPEN_FILE_REPLY://打开文件返回
						break;
					default:
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 连接服务器返回信息
	 * @param bodyArray
	 */
	private void connServerReply(byte[] bodyArray) {
		String bodyStr = new String(bodyArray);
		Log.i("receive", "接收到的内容：" + bodyStr);
		
	}
	


}

package com.wifiplayer.net.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import org.json.JSONObject;

import com.wifiplayer.activitys.utils.SendBroadCastUtil;
import com.wifiplayer.bean.ReqReplyOp;
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
	public static boolean isReceive = true;

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
			while (isReceive) {
				InputStream is = socket.getInputStream();
				Log.i("chg", "接收线程中socket：" + socket);

				byte[] headArray = new byte[12];
				int len = is.read(headArray);
				if (len == -1) {
					isReceive = false;
					return;
				}

				Head head = Head.resolveHead(headArray);
				byte[] bodyArray = new byte[head.getPackBodyLenth()];
				len = is.read(bodyArray);
				switch (head.getCmd()) {
				case Head.CONN_SERVER_REPLY:// 连接服务器返回
					
					connServerReply(bodyArray);
					break;
				case Head.COPY_FILE_2_PHONE_REPLY:// 拷贝文件到手机上返回
					break;
				case Head.DEL_FILE_REPLY:// 删除文件返回
					break;
				case Head.OPEN_FILE_REPLY:// 打开文件返回
					break;
				case Head.OPEN_DIR_REPLY://打开文件夹返回
					openDirReply(bodyArray);
					break;
				default:
					break;

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 打开文件夹返回
	 * @param bodyArray
	 */
	private void openDirReply(byte[] bodyArray) {
		// TODO Auto-generated method stub
		connServerReply(bodyArray);
	}

	/**
	 * 连接服务器返回信息
	 * 
	 * @param bodyArray
	 */
	private void connServerReply(byte[] bodyArray) {
		String bodyStr = new String(bodyArray);
		Log.i("receive", "接收到的内容：" + bodyStr);
		ReqReplyOp rro = new ReqReplyOp();
		rro.setCmd(Head.CONN_SERVER_REPLY);
		rro.setContent(bodyStr);
		rro.setStatus(0);
		SendBroadCastUtil.sendBroadCast(context, rro);
	}

}

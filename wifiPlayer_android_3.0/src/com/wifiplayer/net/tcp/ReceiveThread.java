package com.wifiplayer.net.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

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
	boolean isReceive = true;

	public ReceiveThread(Socket socket, Context context) {
		super();
		this.socket = socket;
		this.context = context;
	}

	@Override
	public void run() {
		Log.i("receive", "线程中开始监听时socket:" + socket);
		receive_();
		Log.i("receive", "Receive线程中监听结束");
	}

	/**
	 * 打开文件夹返回
	 * 
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
		ReqReplyOp rro = new ReqReplyOp();
		rro.setCmd(Head.CONN_SERVER_REPLY);
		rro.setContent(bodyStr);
		rro.setStatus(0);
		SendBroadCastUtil.sendBroadCast(context, rro);
	}

	public int readData(Socket s, int readPosition, byte[] array) {
		int len = 0;
		InputStream is = null;
		try {
			is = s.getInputStream();
			len = is.read(array, readPosition, array.length - readPosition);
			if (len == -1) {
//				ConnServer.close();
				Log.i("receive", "Receive线程readData中Socket:" + s);
				return len;
			}
			if ((len + readPosition) < array.length) {
				readData(s, len + readPosition, array);
			}
		} catch (IOException e) {
			e.printStackTrace();
//			ConnServer.close();
			return -1;
		}

		return len + readPosition;
	}

	public void receive_() {
		while (isReceive) {
			byte[] headArray = new byte[14];
			int len = readData(socket, 0, headArray);
			if (len == -1) {
				isReceive = false;
				break;
			}
			Head head = Head.resolveHead(headArray);
			byte[] bodyArray = new byte[head.getPackBodyLenth()];
			len = readData(socket, 0, bodyArray);
			if (len == -1) {
				isReceive = false;
				break;
			}
			Log.i("receive", "读取的包的内容bodyArray：" + new String(bodyArray));
			switch (head.getCmd()) {
			case Head.CONN_SERVER_REPLY:// 连接服务器返回
				connServerReply(bodyArray);
				break;
			case Head.COPY_FILE_2_PHONE_REPLY:// 拷贝文件到手机上返回
				break;
			case Head.DEL_FILE_REPLY:// 删除文件返回
				break;
			case Head.OPEN_FILE_REPLY:// 打开文件返回
				openFileReply(bodyArray);
				break;
			case Head.OPEN_DIR_REPLY:// 打开文件夹返回
				openDirReply(bodyArray);
				break;
			default:
				break;

			}
		}
		
	}

	/**
	 * 打开文件返回
	 * @param bodyArray
	 */
	private void openFileReply(byte[] bodyArray) {
		String bodyStr = new String(bodyArray);
		ReqReplyOp rro = new ReqReplyOp();
		rro.setCmd(Head.OPEN_FILE_REPLY);
		rro.setContent(bodyStr);
		rro.setStatus(0);
		SendBroadCastUtil.sendBroadCast(context, rro);
		
	}
}
